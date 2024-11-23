package com.example.filmxxx.repository;

import com.example.filmxxx.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    public UserEntity findByEmail(String email);

    public UserEntity findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByResetToken(String resetToken);

    @Query("SELECT COUNT(bd) FROM BookingDetailsEntity bd WHERE bd.user.id = :userId")
    Long countTicketsByUserId(@Param("userId") Long userId);
}
