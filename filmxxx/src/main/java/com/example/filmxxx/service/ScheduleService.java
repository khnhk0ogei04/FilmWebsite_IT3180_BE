package com.example.filmxxx.service;

import com.example.filmxxx.repository.*;
import com.example.filmxxx.dto.*;
import com.example.filmxxx.entity.*;
import com.example.filmxxx.exception.CinemaException;
import com.example.filmxxx.exception.MovieException;
import com.example.filmxxx.exception.ScheduleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private SeatScheduleStatusRepository seatScheduleStatusRepository;

    public Page<MovieScheduleDTO> getSchedules(String search, Pageable pageable) {
        Page<ScheduleEntity> schedules;

        if (search != null && !search.trim().isEmpty()) {
            schedules = scheduleRepository.findByMovieNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            schedules = scheduleRepository.findAll(pageable);
        }

        return schedules.map(schedule -> MovieScheduleDTO.builder()
                .scheduleId(schedule.getId())
                .cinemaName(schedule.getCinema().getCinemaName())
                .scheduleDate(schedule.getScheduleDate())
                .shiftName(schedule.getShift().getName())
                .shiftStart(schedule.getShift().getShiftStart())
                .shiftEnd(schedule.getShift().getShiftEnd())
                .movieName(schedule.getMovie().getMovieName())
                .movieImage(schedule.getMovie().getImage())
                .build());
    }

    public ScheduleDetailDTO getScheduleDetails(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException.ScheduleNotFoundException(scheduleId));

        Long movieId = schedule.getMovie().getId();
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException.MovieNotFoundException(movieId));

        String movieName = movie.getMovieName();
        String movieAvatar = movie.getImage();

        // lấy danh sách ghế của rạp cho suất chiếu cụ thể
        List<SeatDTO> seatDTOs = seatRepository.findByCinemaId(schedule.getCinema().getId()).stream()
                .map(seat -> SeatDTO.builder()
                        .id(seat.getId())
                        .seatType(seat.getSeatType())
                        .seatRow(seat.getSeatRow())
                        .seatNumber(seat.getSeatNumber())
                        .cinemaId(seat.getCinema().getId())
                        .build())
                .collect(Collectors.toList());

        Map<Long, Boolean> seatStatus = seatDTOs.stream()
                .collect(Collectors.toMap(
                        SeatDTO::getId,
                        seat -> bookingDetailRepository.existsBySeatIdAndScheduleId(seat.getId(), scheduleId)
                ));

        List<RatingDTO> ratingDTOs = ratingRepository.findByMovieId(schedule.getMovie().getId()).stream()
                .map(rating -> {
                    UserEntity user = userRepository.findById(rating.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return RatingDTO.builder()
                            .id(rating.getId())
                            .userId(user.getId())
                            .userName(user.getFullName())
                            .userAvatar(user.getAvatar())
                            .ratingValue(rating.getRatingValue())
                            .ratingComment(rating.getRatingComment())
                            .build();
                })
                .collect(Collectors.toList());

        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .scheduleId(schedule.getId())
                .cinemaId(schedule.getCinema().getId())
                .cinemaName(schedule.getCinema().getCinemaName())
                .scheduleDate(schedule.getScheduleDate())
                .movieId(schedule.getMovie().getId())
                .movieName(schedule.getMovie().getMovieName())
                .movieImage(schedule.getMovie().getImage())
                .shiftName(schedule.getShift().getName())
                .shiftStart(schedule.getShift().getShiftStart())
                .shiftEnd(schedule.getShift().getShiftEnd())
                .totalSeats((long)seatDTOs.size()) // Tổng số ghế trong rạp
                .bookedSeats(seatStatus.values().stream().filter(isBooked -> isBooked).count()) // Số ghế đã đặt
                .build();

        return new ScheduleDetailDTO(scheduleDTO, seatDTOs, seatStatus, ratingDTOs, movieName, movieAvatar);
    }

    public ScheduleEntity createSchedule(ScheduleDTO scheduleDTO) {

        System.out.println("Creating schedule for CinemaId: " + scheduleDTO.getCinemaId() +
                ", ShiftId: " + scheduleDTO.getShiftId() +
                ", ScheduleDate: " + scheduleDTO.getScheduleDate());

        List<ScheduleEntity> conflictSchedules = scheduleRepository.findConflictSchedules(scheduleDTO.getCinemaId(), scheduleDTO.getShiftId(), scheduleDTO.getScheduleDate());
        if (conflictSchedules.size() > 0) {
            throw new RuntimeException("Conflict schedules found");
        }
        CinemaEntity cinema = cinemaRepository.findById(scheduleDTO.getCinemaId())
                .orElseThrow(() -> new CinemaException.CinemaNotFoundException(scheduleDTO.getCinemaId()));

        MovieEntity movie = movieRepository.findById(scheduleDTO.getMovieId())
                .orElseThrow(() -> new MovieException.MovieNotFoundException(scheduleDTO.getMovieId()));

        ShiftEntity shift = shiftRepository.findById(scheduleDTO.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setMovie(movie);
        schedule.setCinema(cinema);
        schedule.setShift(shift);
        schedule.setScheduleDate(scheduleDTO.getScheduleDate());
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleDTO> getSchedulesByMovieId(Long movieId) {
        List<ScheduleEntity> schedules = scheduleRepository.findByMovieId(movieId);

        return schedules.stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertToScheduleDTO(ScheduleEntity schedule) {
        Long totalSeats = seatRepository.countSeatsByCinemaId(schedule.getCinema().getId());
        List<SeatScheduleStatusEntity> bookedSeats = seatScheduleStatusRepository.findByScheduleId(schedule.getId());

        long bookedSeatsCount = bookedSeats.size();

        return ScheduleDTO.builder()
                .scheduleId(schedule.getId())
                .cinemaId(schedule.getCinema().getId())
                .cinemaName(schedule.getCinema().getCinemaName())
                .scheduleDate(schedule.getScheduleDate())
                .movieId(schedule.getMovie().getId())
                .movieName(schedule.getMovie().getMovieName())
                .movieImage(schedule.getMovie().getImage())
                .shiftId(schedule.getShift().getId())
                .shiftName(schedule.getShift().getName())
                .shiftStart(schedule.getShift().getShiftStart())
                .shiftEnd(schedule.getShift().getShiftEnd())
                .totalSeats(totalSeats)
                .bookedSeats(bookedSeatsCount)
                .build();
    }
}

