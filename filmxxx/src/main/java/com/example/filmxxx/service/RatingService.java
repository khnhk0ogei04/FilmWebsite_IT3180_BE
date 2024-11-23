package com.example.filmxxx.service;

import com.example.filmxxx.Repository.MovieRepository;
import com.example.filmxxx.Repository.RatingRepository;
import com.example.filmxxx.Repository.UserRepository;
import com.example.filmxxx.dto.RatingDTO;
import com.example.filmxxx.entity.MovieEntity;
import com.example.filmxxx.entity.RatingEntity;
import com.example.filmxxx.entity.UserEntity;
import com.example.filmxxx.exception.MovieException;
import com.example.filmxxx.exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    // Lay ra cac binh luan cho 1 bo phim
    public List<RatingDTO> getRatingsByMovieId(Long movieId){
        List<RatingEntity> ratingEntities = ratingRepository.findByMovieId(movieId);
        List<RatingDTO> ratingDTOS = new ArrayList<>();
        for (RatingEntity ratingEntity : ratingEntities) {
            RatingDTO ratingDTO = modelMapper.map(ratingEntity, RatingDTO.class);
            ratingDTOS.add(ratingDTO);
        }
        return ratingDTOS;
    }

    public RatingDTO saveRating(RatingDTO ratingDTO) {
        MovieEntity movie = movieRepository.findById(ratingDTO.getMovieId())
                .orElseThrow(() -> new MovieException.MovieNotFoundException(ratingDTO.getMovieId()));

        UserEntity user = userRepository.findById(ratingDTO.getUserId())
                .orElseThrow(() -> new UserException.UserNotFoundException(ratingDTO.getUserId()));

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setMovie(movie);
        ratingEntity.setUser(user);
        ratingEntity.setRatingValue(ratingDTO.getRatingValue());
        ratingEntity.setRatingComment(ratingDTO.getRatingComment());

        ratingRepository.save(ratingEntity);

        return modelMapper.map(ratingEntity, RatingDTO.class);
    }


    public Double getAverageRatingByMovieId(Long movieId){
        return ratingRepository.findAverageRatingByMovieId(movieId);
    }
}
