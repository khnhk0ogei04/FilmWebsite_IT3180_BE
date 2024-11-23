package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private Long id;
    private Long discountPercentage;
    private String notificationTitle;
    private String notificationContent;
    private Long movieId;
}
