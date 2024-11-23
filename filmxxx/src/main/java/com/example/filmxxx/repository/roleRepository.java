package com.example.filmxxx.repository;

import com.example.filmxxx.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<RoleEntity, Long> {
}
