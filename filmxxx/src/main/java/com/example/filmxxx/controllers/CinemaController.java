package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.CinemaDTO;
import com.example.filmxxx.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@CrossOrigin(origins = "http://localhost:3000")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public List<CinemaDTO> getAllCinemas() {
        return cinemaService.getAllCinemas();
    }

    @GetMapping("/{id}")
    public CinemaDTO getCinemaById(@PathVariable Long id) {
        return cinemaService.getCinemaById(id);
    }
}
