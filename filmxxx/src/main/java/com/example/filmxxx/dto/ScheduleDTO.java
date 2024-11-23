package com.example.filmxxx.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long scheduleId;
    private Long cinemaId;
    private String cinemaName;
    private Date scheduleDate;

    private Long movieId;
    private String movieName;
    private String movieImage;

    private Long shiftId;
    private String shiftName;
    private Time shiftStart;
    private Time shiftEnd;

    private Long totalSeats;
    private Long bookedSeats;

}
