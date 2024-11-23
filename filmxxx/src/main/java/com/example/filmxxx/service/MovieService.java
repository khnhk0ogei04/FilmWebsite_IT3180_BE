package com.example.filmxxx.service;

import com.example.filmxxx.repository.CategoryRepository;
import com.example.filmxxx.repository.MovieRepository;
import com.example.filmxxx.repository.MovieSeatPriceRepository;
import com.example.filmxxx.dto.MovieDTO;
import com.example.filmxxx.entity.CategoryEntity;
import com.example.filmxxx.entity.MovieEntity;
import com.example.filmxxx.entity.MovieSeatPriceEntity;
import com.example.filmxxx.exception.CategoryNotFoundException;
import com.example.filmxxx.exception.MovieException;
import com.example.filmxxx.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieSeatPriceRepository movieSeatPriceRepository;

    // Fetch all movies
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<MovieEntity> getMoviesByStatus(Boolean status, Integer page, Integer limit) {
        Integer movieStatus = status ? 1 : 0;
        Pageable pageable = PageRequest.of(page, limit);
        return movieRepository.findByStatus(movieStatus, pageable);
    }

    public Page<MovieEntity> getMoviesByStatusAndName(Boolean status, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Integer statusValue = status ? 1 : 0;

        if (name != null && !name.isEmpty()) {
            return movieRepository.findByStatusAndMovieNameContainingIgnoreCase(statusValue, name, pageable);
        } else {
            return movieRepository.findByStatus(statusValue, pageable);
        }
    }


    public MovieDTO getMovieById(Long id) {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new MovieException.MovieNotFoundException(id));
        Long vipPrice = movieSeatPriceRepository.findPriceByMovieIdAndSeatType(id, "VIP");
        Long regularPrice = movieSeatPriceRepository.findPriceByMovieIdAndSeatType(id, "Regular");
        String categoryName = "";
        Optional<CategoryEntity> categoryEntity= categoryRepository.findById(movieEntity.getCategory().getId());
        if (categoryEntity.isPresent()) {
            categoryName = categoryEntity.get().getCategoryName();
        }
        return MovieDTO.builder()
                .id(movieEntity.getId())
                .movieName(movieEntity.getMovieName())
                .categoryId(movieEntity.getCategory().getId())
                .categoryName(categoryName)
                .description(movieEntity.getDescription())
                .directors(movieEntity.getDirectors())
                .cast(movieEntity.getCast())
                .runningTime(movieEntity.getRunningTime())
                .image(movieEntity.getImage())
                .trailerUrl(movieEntity.getTrailerUrl())
                .vipPrice(vipPrice)
                .regularPrice(regularPrice)
                .status(movieEntity.getStatus())
                .build();
    }

    public MovieEntity createMovie(MovieDTO movieDTO) {
        MovieEntity movieEntity = modelMapper.map(movieDTO, MovieEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findById(movieDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + movieDTO.getCategoryId()));
        movieEntity.setCategory(categoryEntity);

        // Save information of film to table
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        MovieSeatPriceEntity vipPrice = new MovieSeatPriceEntity();
        vipPrice.setMovie(savedMovie); 
        vipPrice.setSeatType("VIP");
        vipPrice.setBasePrice(movieDTO.getVipPrice());
        movieSeatPriceRepository.save(vipPrice);
        MovieSeatPriceEntity regularPrice = new MovieSeatPriceEntity();
        regularPrice.setMovie(savedMovie);
        regularPrice.setSeatType("Regular");
        regularPrice.setBasePrice(movieDTO.getRegularPrice());
        movieSeatPriceRepository.save(regularPrice);

        return savedMovie;
    }


    public MovieEntity updateMovie(Long id, MovieDTO movieDTO) {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));
        movieEntity.setMovieName(movieDTO.getMovieName());
        movieEntity.setDescription(movieDTO.getDescription());
        movieEntity.setDirectors(movieDTO.getDirectors());
        movieEntity.setCast(movieDTO.getCast());
        movieEntity.setRunningTime(movieDTO.getRunningTime());
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTrailerUrl(movieDTO.getTrailerUrl());
        movieEntity.setStatus(movieDTO.getStatus());

        movieRepository.save(movieEntity);

        updateMovieSeatPrice(id, "VIP", movieDTO.getVipPrice());
        updateMovieSeatPrice(id, "Regular", movieDTO.getRegularPrice());

        return movieEntity;
    }

    private void updateMovieSeatPrice(Long movieId, String seatType, Long price) {
        MovieSeatPriceEntity priceEntity = movieSeatPriceRepository.findByMovieIdAndSeatType(movieId, seatType)
                .orElseGet(() -> {
                    MovieEntity movie = movieRepository.findById(movieId)
                            .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + movieId));
                    return new MovieSeatPriceEntity(0L, movie, seatType, price);
                });

        priceEntity.setBasePrice(price);
        movieSeatPriceRepository.save(priceEntity);
    }

}
