package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    private String username;
    private String currentPassword;
    private String newPassword;
}
