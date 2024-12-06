package com.example.filmxxx.controllers;

import com.example.filmxxx.dto.CategoryDTO;
import com.example.filmxxx.entity.CategoryEntity;
import com.example.filmxxx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) throws Exception {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }


    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {
        CategoryEntity categoryEntity = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(categoryEntity);
    }
}
