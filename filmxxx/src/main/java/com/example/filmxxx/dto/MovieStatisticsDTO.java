package com.example.filmxxx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieStatisticsDTO {

    private Long movieId;
    private String movieName;
    private String image;
    private Double averageRating;
    private Long ticketsSold;
    private Long revenue;
    private Integer status;

    private List<ScheduleDTO> schedules;

    public MovieStatisticsDTO(Long movieId, String movieName, String image, double averageRating, Long ticketsSold, Long revenue, int status) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.image = image;
        this.averageRating = averageRating;
        this.ticketsSold = ticketsSold != null ? ticketsSold : 0L;
        this.revenue = revenue != null ? revenue : 0L;
        this.status = status;
    }

}
