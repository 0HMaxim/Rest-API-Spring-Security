package com.example.Spring.Security.API.controllers;
import com.example.Spring.Security.API.models.Place;
import com.example.Spring.Security.API.models.Review;
import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import com.example.Spring.Security.API.services.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import java.util.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;



    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/place/{placeId}")
    public List<Review> getReviewsByPlace(@PathVariable Place place) {
        return reviewService.getReviewsByPlace(place);
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id, @RequestBody User user) {
        reviewService.deleteReview(id, user);
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




        @PutMapping("/reviews/{reviewId}")
        public ResponseEntity<Review> editReview(
                @PathVariable Long reviewId,
                @RequestBody Review updatedReview,
                Authentication auth) {

            Review review = reviewRepository.findById(reviewId)
                    .orElse(null);

            if (review == null) {
                return ResponseEntity.notFound().build();
            }

            User user = (User) auth.getPrincipal();
            if (!review.getUser().equals(user)) {
                throw new AccessDeniedException("You can only edit your own reviews.");
            }

            review.setContent(updatedReview.getContent());
            review.setRating(updatedReview.getRating());

            Review savedReview = reviewRepository.save(review);
            return ResponseEntity.ok(savedReview);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchReviews(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String place,
            @RequestParam(required = false, defaultValue = "0") int rating) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<Review> reviews = reviewService.findReviews(username, place, rating);

            if (reviews.isEmpty()) {
                response.put("message", "No reviews found.");
            } else {
                response.put("message", "Reviews retrieved successfully.");
                response.put("reviews", reviews);
            }
            response.put("total", reviews.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




}
