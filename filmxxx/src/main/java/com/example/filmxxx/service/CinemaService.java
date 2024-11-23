package com.example.filmxxx.service;

import com.example.filmxxx.Repository.CinemaRepository;
import com.example.filmxxx.dto.CinemaDTO;
import com.example.filmxxx.entity.CinemaEntity;
import com.example.filmxxx.exception.CinemaException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CinemaDTO> getAllCinemas(){
        List<CinemaEntity> cinemas = cinemaRepository.findAll();
        return cinemas.stream().map(cinema -> modelMapper.map(cinema, CinemaDTO.class))
                .collect(Collectors.toList());
    }

    public CinemaDTO getCinemaById(Long id){
        CinemaEntity cinema = cinemaRepository.findById(id).orElseThrow(() -> new CinemaException.CinemaNotFoundException(id));
        return modelMapper.map(cinema, CinemaDTO.class);
    }
}
