package com.example.filmxxx.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String movieName;
    private Long categoryId;
    private String categoryName;
    private String description;
    private String directors;
    private String cast;
    private Time runningTime;
    private String image;
    private Integer status = 1;
    private List<ScheduleDTO> upcomingSchedules;
    private List<RatingDTO> comments;
    private String trailerUrl;
    private Long vipPrice;
    private Long regularPrice;
}