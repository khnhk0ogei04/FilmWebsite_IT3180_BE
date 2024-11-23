package com.example.filmxxx.service;

import com.example.filmxxx.repository.SeatRepository;
import com.example.filmxxx.repository.SeatScheduleStatusRepository;
import com.example.filmxxx.dto.SeatDTO;
import com.example.filmxxx.dto.SeatScheduleStatusDTO;
import com.example.filmxxx.entity.SeatEntity;
import com.example.filmxxx.entity.SeatScheduleStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatScheduleStatusRepository seatScheduleStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SeatDTO> getSeatsForSchedule(Long scheduleId) {
        Long cinemaId = seatRepository.findCinemaIdByScheduleId(scheduleId);

        List<SeatEntity> allSeats = seatRepository.findByCinemaId(cinemaId);

        return allSeats.stream()
                .map(seat -> modelMapper.map(seat, SeatDTO.class))
                .collect(Collectors.toList());
    }

    public List<SeatScheduleStatusDTO> getBookedSeatsForSchedule(Long scheduleId) {
        List<SeatScheduleStatusEntity> bookedSeats = seatScheduleStatusRepository.findByScheduleId(scheduleId);
        return bookedSeats.stream()
                .map(status -> modelMapper.map(status, SeatScheduleStatusDTO.class))
                .collect(Collectors.toList());
    }
}
