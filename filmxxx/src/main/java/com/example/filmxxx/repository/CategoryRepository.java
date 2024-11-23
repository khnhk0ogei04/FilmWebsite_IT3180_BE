package com.example.filmxxx.repository;

import com.example.filmxxx.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByCategoryNameIgnoreCase(String normalizedCategoryName);
}
