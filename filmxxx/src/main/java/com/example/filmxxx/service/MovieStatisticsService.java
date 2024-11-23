package com.example.filmxxx.service;

import com.example.filmxxx.Repository.BookingDetailRepository;
import com.example.filmxxx.Repository.ScheduleRepository;
import com.example.filmxxx.Repository.SeatRepository;
import com.example.filmxxx.dto.MovieStatisticsDTO;
import com.example.filmxxx.dto.ScheduleDTO;
import com.example.filmxxx.entity.MovieEntity;
import com.example.filmxxx.Repository.MovieRepository;
import com.example.filmxxx.entity.ScheduleEntity;
import com.example.filmxxx.exception.MovieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieStatisticsService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Page<MovieStatisticsDTO> getMovieStatistics(String sort, Pageable pageable) {
        return movieRepository.getMovieStatistics(sort, pageable);
    }

    public MovieStatisticsDTO getMovieStatisticsById(Long movieId) {
        // Get movie details
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieException.MovieNotFoundException(movieId));

        // Get schedules for the movie
        List<ScheduleEntity> schedules = scheduleRepository.findByMovieId(movieId);

        // Prepare schedule details and count booked seats
        List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> {
            Long totalSeats = seatRepository.countSeatsByCinemaId(schedule.getCinema().getId());
            Long bookedSeats = bookingDetailRepository.countByScheduleId(schedule.getId());
            return ScheduleDTO.builder()
                    .scheduleId(schedule.getId())
                    .cinemaId(schedule.getCinema().getId())
                    .cinemaName(schedule.getCinema().getCinemaName())
                    .scheduleDate(schedule.getScheduleDate())
                    .shiftName(schedule.getShift().getName())
                    .shiftStart(schedule.getShift().getShiftStart())
                    .shiftEnd(schedule.getShift().getShiftEnd())
                    .totalSeats(totalSeats)
                    .bookedSeats(bookedSeats)
                    .build();
        }).collect(Collectors.toList());

        return MovieStatisticsDTO.builder()
                .movieId(movie.getId())
                .movieName(movie.getMovieName())
                .image(movie.getImage())
                .averageRating(calculateAverageRating(movieId))
                .ticketsSold(calculateTicketsSold(movieId))
                .revenue(calculateRevenue(movieId))
                .status(movie.getStatus())
                .schedules(scheduleDTOs)
                .build();
    }

    private double calculateAverageRating(Long movieId) {
        Double averageRating = movieRepository.calculateAverageRatingByMovieId(movieId);
        return averageRating != null ? averageRating : 0.0;
    }

    private Long calculateTicketsSold(Long movieId) {
        return bookingDetailRepository.countTicketsSoldByMovieId(movieId);
    }

    private Long calculateRevenue(Long movieId) {
        Long revenue = bookingDetailRepository.calculateRevenueByMovieId(movieId);
        return revenue;
    }
}
