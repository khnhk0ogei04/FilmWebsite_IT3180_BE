package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.UserDTO;
import com.example.filmxxx.exception.UserException;
import com.example.filmxxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "5") Integer limit) {
        Page<UserDTO> users = userService.getUsers(PageRequest.of(page, limit));
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username){
        try {
            userService.resetPassword(username);
            return ResponseEntity.ok().body("Password reset successful.");
        } catch (UserException.UserNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPasswordConfirm(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.updatePasswordWithToken(token, newPassword);
            return ResponseEntity.ok("OKE");
        } catch (UserException.InvalidTokenException | UserException.ExpiredTokenException exception) {
            return ResponseEntity.badRequest().body("Error in changing password");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/add-balance")
    public ResponseEntity<UserDTO> addBalance(@PathVariable Long id, @RequestParam Long amount) {
        UserDTO updatedUser = userService.addBalance(id, amount);
        return ResponseEntity.ok(updatedUser);
    }
}
