package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDTO {
    private Long id;
    private String cinemaName;
    private String cinemaAddress;
}
