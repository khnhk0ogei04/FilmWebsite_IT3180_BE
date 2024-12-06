package com.example.filmxxx.service;

import com.example.filmxxx.exception.CategoryException;
import com.example.filmxxx.repository.CategoryRepository;
import com.example.filmxxx.dto.CategoryDTO;
import com.example.filmxxx.dto.MovieDTO;
import com.example.filmxxx.entity.CategoryEntity;
import com.example.filmxxx.entity.MovieEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryDTO getCategoryById(Long categoryId) throws Exception{
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException.CategoryNotFoundException("Category not found with id: " + categoryId));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        List<MovieEntity> movies = category.getMovies();
        List<MovieDTO> moviesDTO = new ArrayList<>();
        for (MovieEntity movie : movies) {
            MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
            moviesDTO.add(movieDTO);
        }
        categoryDTO.setMovies(moviesDTO);
        return categoryDTO;
    }
    public CategoryEntity addCategory(CategoryDTO categoryDTO) throws Exception {

        String normalizedCategoryName = normalizeCategoryName(categoryDTO.getCategoryName());

        if (categoryRepository.findByCategoryNameIgnoreCase(normalizedCategoryName) != null) {
            throw new CategoryException.CategoryDuplicatedException("Category with this name already exists.");
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        categoryEntity.setImage(categoryDTO.getImage());
        return categoryRepository.save(categoryEntity);
    }

    private String normalizeCategoryName(String categoryName){
        return categoryName != null ? categoryName.toLowerCase().trim() : null;
    }

}
