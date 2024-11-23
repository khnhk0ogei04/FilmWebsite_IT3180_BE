package com.example.filmxxx.Repository;

import com.example.filmxxx.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {

}
