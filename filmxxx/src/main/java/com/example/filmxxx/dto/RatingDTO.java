package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long id;
    private Long userId;
    private Long movieId;
    private String userName;
    private String userAvatar;
    private Long ratingValue;
    private String ratingComment;
}
