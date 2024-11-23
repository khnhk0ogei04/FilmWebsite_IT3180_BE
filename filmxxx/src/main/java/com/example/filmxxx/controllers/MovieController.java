package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.MovieDTO;
import com.example.filmxxx.dto.MovieStatisticsDTO;
import com.example.filmxxx.entity.MovieEntity;
import com.example.filmxxx.service.MovieService;
import com.example.filmxxx.service.MovieStatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8080/api/movies/statistics?sort=rating&page=0&size=10
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieStatisticsService movieStatisticsService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> movieEntities = movieService.getAllMovies();
        List<MovieDTO> movieDTOs = movieEntities.stream()
                .map(movieEntity -> modelMapper.map(movieEntity, MovieDTO.class))
                .collect(Collectors.toList());

        return movieDTOs;
    }

    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public MovieEntity createMovie(@RequestBody MovieDTO movieDTO){
        return movieService.createMovie(movieDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieEntity> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        MovieEntity updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @GetMapping
    public Page<MovieEntity> getMovies(
            @RequestParam boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String name
    ) {
        return movieService.getMoviesByStatusAndName(status, name, page, limit);
    }


    @GetMapping("/statistics")
    public Page<MovieStatisticsDTO> getMovieStatistics(
            @RequestParam(defaultValue = "rating") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return movieStatisticsService.getMovieStatistics(sort, pageable);
    }

    @GetMapping("/statistics/{movieId}")
    public MovieStatisticsDTO getMovieStatisticsById(@PathVariable Long movieId) {
        return movieStatisticsService.getMovieStatisticsById(movieId);
    }


}
