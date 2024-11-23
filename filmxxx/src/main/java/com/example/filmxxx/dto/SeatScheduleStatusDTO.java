package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatScheduleStatusDTO {
    private Long id;
    private Long seatId;
    private Long scheduleId;
    private Integer status;
}
