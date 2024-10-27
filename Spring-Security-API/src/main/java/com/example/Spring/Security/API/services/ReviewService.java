package com.example.Spring.Security.API.services;

import com.example.Spring.Security.API.models.*;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByPlace(Place place) {
        return reviewRepository.findByPlace(place);
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getUser().equals(user) || review.getUser().getRole() == Role.ADMIN) {
            reviewRepository.delete(review);
        } else {
            throw new SecurityException("You can only delete your own reviews.");
        }
    }




    @Transactional(readOnly = true)
    public List<Review> findReviews(String username, String place, int rating) {


        Specification<Review> spec = buildSpecification(username, place, rating);
        return reviewRepository.findAll(spec);
    }


    private Specification<Review> buildSpecification(String username, String place, int rating) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (username != null && !username.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }

            if (place != null && !place.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("place"), place));
            }

            if (rating > 0) {
                predicates.add(criteriaBuilder.equal(root.get("rating"), rating));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}