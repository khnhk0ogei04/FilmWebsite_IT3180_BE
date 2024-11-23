package com.example.filmxxx.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieScheduleDTO {
    private Long scheduleId;
    private String cinemaName;
    private Date scheduleDate;
    private String shiftName;
    private Time shiftStart;
    private Time shiftEnd;

    private String movieName;
    private String movieImage;
}
