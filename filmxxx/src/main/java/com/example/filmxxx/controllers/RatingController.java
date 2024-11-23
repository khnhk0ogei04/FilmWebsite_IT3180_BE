package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.RatingDTO;
import com.example.filmxxx.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByMovieId(@PathVariable Long movieId) {
        List<RatingDTO> ratings = ratingService.getRatingsByMovieId(movieId);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/movie/{movieId}")
    public ResponseEntity<RatingDTO> createRating(@PathVariable Long movieId, @RequestBody RatingDTO ratingDTO) {
        ratingDTO.setMovieId(movieId);
        RatingDTO savedRating = ratingService.saveRating(ratingDTO);
        return ResponseEntity.ok(savedRating);
    }

    @GetMapping("/movie/{movieId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long movieId) {
        Double averageRating = ratingService.getAverageRatingByMovieId(movieId);
        return ResponseEntity.ok(averageRating);
    }
}
