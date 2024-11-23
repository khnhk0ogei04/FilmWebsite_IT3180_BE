package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.SeatDTO;
import com.example.filmxxx.dto.SeatScheduleStatusDTO;
import com.example.filmxxx.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seats")
public class SeatController {
// http://localhost:8080/api/seats/booked?scheduleId=1
    @Autowired
    private SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatDTO>> getSeatsForSchedule(@RequestParam("scheduleId") Long scheduleId) {
        List<SeatDTO> seats = seatService.getSeatsForSchedule(scheduleId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/booked")
    public ResponseEntity<List<SeatScheduleStatusDTO>> getBookedSeatsForSchedule(@RequestParam("scheduleId") Long scheduleId) {
        List<SeatScheduleStatusDTO> bookedSeats = seatService.getBookedSeatsForSchedule(scheduleId);
        return ResponseEntity.ok(bookedSeats);
    }
}
