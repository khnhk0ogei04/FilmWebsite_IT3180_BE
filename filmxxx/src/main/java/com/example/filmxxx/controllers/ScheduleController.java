package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.MovieScheduleDTO;
import com.example.filmxxx.dto.ScheduleDTO;
import com.example.filmxxx.dto.ScheduleDetailDTO;
import com.example.filmxxx.entity.ScheduleEntity;
import com.example.filmxxx.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<MovieScheduleDTO> getSchedules(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleService.getSchedules(search, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDetailDTO> getScheduleDetails(@PathVariable Long id) {
        ScheduleDetailDTO scheduleDetailDTO = scheduleService.getScheduleDetails(id);
        return ResponseEntity.ok(scheduleDetailDTO);
    }

    @PostMapping
    public ResponseEntity<ScheduleEntity> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            ScheduleEntity newSchedule = scheduleService.createSchedule(scheduleDTO);
            return ResponseEntity.ok(newSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(null); // Conflict
        }
    }

    @GetMapping(params = "movie")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByMovie(@RequestParam("movie") Long movieId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByMovieId(movieId);
        return ResponseEntity.ok(schedules);
    }

}
