package com.example.filmxxx.service;

import com.example.filmxxx.Repository.DiscountRepository;
import com.example.filmxxx.Repository.MovieRepository;
import com.example.filmxxx.Repository.MovieSeatPriceRepository;
import com.example.filmxxx.dto.DiscountDTO;
import com.example.filmxxx.entity.DiscountEntity;
import com.example.filmxxx.entity.MovieEntity;
import com.example.filmxxx.exception.MovieException;
import com.example.filmxxx.exception.MovieNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieSeatPriceRepository movieSeatPriceRepository;

    public List<DiscountDTO> getAllDiscounts(){
        return discountRepository.findAll()
                .stream()
                .map(discount -> modelMapper.map(discount, DiscountDTO.class))
                .collect(Collectors.toList());
    }

    public Map<String, Long> calculatePrice(Long movieId, String seatType){
        Long basePrice = movieSeatPriceRepository.findPriceByMovieIdAndSeatType(movieId, seatType);
        DiscountEntity discount = discountRepository.findByMovieId(movieId);

        Map<String, Long> priceDetails = new HashMap<>();
        priceDetails.put("originalPrice", basePrice);
        if (discount != null) {
            Long discountPercentage = discount.getDiscountPercentage();
            Long discountedPrice = (basePrice - (discount.getDiscountPercentage() * basePrice / 100));
            priceDetails.put("discountedPrice", discountedPrice);
        } else {
            priceDetails.put("discountedPrice", basePrice);
        }
        return priceDetails;
    }

    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        MovieEntity movie = movieRepository.findById(discountDTO.getMovieId())
                .orElseThrow(() -> new MovieException.MovieNotFoundException(discountDTO.getMovieId()));

        DiscountEntity discountEntity = modelMapper.map(discountDTO, DiscountEntity.class);
        discountEntity.setMovie(movie);
        DiscountEntity savedDiscount = discountRepository.save(discountEntity);

        return modelMapper.map(savedDiscount, DiscountDTO.class);
    }

    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
