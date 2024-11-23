package com.example.filmxxx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDTO {
    private Long id;
    private String name;
    private Time shiftStart;
    private Time shiftEnd;
}
