package com.example.filmxxx.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String fullname;
    private String avatar;
    private String email;
    private String city;
    private String phone;
    private Long accountBalance;
    private Long orderCount;
    private RoleDTO role;
}
