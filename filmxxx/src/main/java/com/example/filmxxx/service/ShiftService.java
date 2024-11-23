package com.example.filmxxx.service;

import com.example.filmxxx.Repository.MovieRepository;
import com.example.filmxxx.Repository.ShiftRepository;
import com.example.filmxxx.dto.ShiftDTO;
import com.example.filmxxx.entity.ShiftEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ShiftDTO> getAllShifts(){
        List<ShiftEntity> shifts = shiftRepository.findAll();
        return shifts.stream().map(shift -> modelMapper.map(shift, ShiftDTO.class))
                .collect(Collectors.toList());
    }

    public ShiftDTO getShiftById(Long id){
        ShiftEntity shift = shiftRepository.findById(id).orElse(null);
        return modelMapper.map(shift, ShiftDTO.class);
    }

}
