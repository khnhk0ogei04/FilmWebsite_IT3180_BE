package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.ChangePasswordRequestDTO;
import com.example.filmxxx.dto.RoleDTO;
import com.example.filmxxx.dto.UserDTO;
import com.example.filmxxx.entity.UserEntity;
import com.example.filmxxx.security.JwtUtil;
import com.example.filmxxx.service.UserDetailsServiceImpl;
import com.example.filmxxx.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getAuthorities().toString()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow();


        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullname(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setEmail(user.getEmail());
        userDTO.setCity(user.getCity());
        userDTO.setAccountBalance(user.getAccountBalance());
        userDTO.setOrderCount(user.getOrderCount());
        RoleDTO roleDTO = modelMapper.map(user.getRole(), RoleDTO.class);
        userDTO.setRole(roleDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) throws Exception {
        System.out.println("Received Request: " + changePasswordRequestDTO.getUsername() + " " + changePasswordRequestDTO.getCurrentPassword() + " " + changePasswordRequestDTO.getNewPassword());
        boolean isPasswordChanged = userService.changePassword(
                changePasswordRequestDTO.getUsername(),
                changePasswordRequestDTO.getCurrentPassword(),
                changePasswordRequestDTO.getNewPassword()
        );
        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed");
        } else {
            return ResponseEntity.badRequest().body("Can't change password");
        }
    }

}
