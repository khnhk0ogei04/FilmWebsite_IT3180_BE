package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.MovieStatisticsDTO;
import com.example.filmxxx.service.MovieStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/statistics/movies")
public class MovieStatisticsController {

    @Autowired
    private MovieStatisticsService movieStatisticsService;

    @GetMapping
    public Page<MovieStatisticsDTO> getMovieStatistics(
            @RequestParam(defaultValue = "rating") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return movieStatisticsService.getMovieStatistics(sort, pageable);
    }

    @GetMapping("/{movieId}")
    public MovieStatisticsDTO getMovieStatisticsById(@PathVariable Long movieId) {
        return movieStatisticsService.getMovieStatisticsById(movieId);
    }
}
