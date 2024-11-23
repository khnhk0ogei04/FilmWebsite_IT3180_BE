package com.example.filmxxx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetailDTO {
    private ScheduleDTO schedule;
    private List<SeatDTO> seats;
    private Map<Long, Boolean> seatStatus;
    private List<RatingDTO> comments;
    private String movieName;
    private String movieAvatar;
}
