package com.example.Spring.Security.API.controllers;

import com.example.Spring.Security.API.models.Review;
import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import com.example.Spring.Security.API.repositories.UserRepository;
import com.example.Spring.Security.API.services.CustomUserDetailsService;
import com.example.Spring.Security.API.services.ReviewService;
import com.example.Spring.Security.API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public UserController(UserService userService, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        User user = userOptional.get();
        List<Review> reviews = reviewRepository.findByUser(user);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @DeleteMapping("/admin/reviews/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReviewByAdmin(@PathVariable Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/{userId}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> blockUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        return ResponseEntity.ok(userRepository.save(user));
    }
}


