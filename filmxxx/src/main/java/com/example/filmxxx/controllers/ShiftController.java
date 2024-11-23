package com.example.filmxxx.controllers;


import com.example.filmxxx.dto.ShiftDTO;
import com.example.filmxxx.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin("http://localhost:3000")
public class ShiftController {
    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts(){
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getShiftById(@PathVariable Long id){
        return ResponseEntity.ok(shiftService.getShiftById(id));
    }

}
