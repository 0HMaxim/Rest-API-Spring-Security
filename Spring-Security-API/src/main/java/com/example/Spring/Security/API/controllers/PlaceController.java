package com.example.Spring.Security.API.controllers;

import com.example.Spring.Security.API.models.Photo;
import com.example.Spring.Security.API.models.Place;
import com.example.Spring.Security.API.models.Review;
import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.PlaceRepository;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import com.example.Spring.Security.API.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public PlaceController(PlaceRepository placeRepository, ReviewRepository reviewRepository, PlaceService placeService) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
        this.placeService = placeService;
    }



    @GetMapping("/{id}/rating")
    public Double getPlaceRating(@PathVariable Long id) {
        return placeService.getRating(id);
    }


    @PostMapping("/places/{placeId}/reviews")
    public ResponseEntity<Review> addReview(@PathVariable Long placeId, @RequestBody Review review, Authentication auth) {
        User user = (User) auth.getPrincipal();

        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        review.setUser(user);
        review.setPlace(optionalPlace.get());

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }


    @GetMapping("/{placeId}/reviews")
    public List<Review> getReviewsByPlace(@PathVariable Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }


}