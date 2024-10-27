package com.example.Spring.Security.API;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.Spring.Security.API.models.Review;
import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import com.example.Spring.Security.API.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        reviewRepository.deleteAll();

        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void testAddReview() throws Exception {
        String reviewContent = "{\"content\":\"Great place!\",\"rating\":5}";

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great place!"))
                .andExpect(jsonPath("$.rating").value(5))
                .andDo(print());

        Review savedReview = reviewRepository.findAll().get(0);
        System.out.println("Review added: " + savedReview.getContent());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void testEditReview() throws Exception {
        Review review = new Review();
        review.setContent("Old review");
        review.setRating(3);
        review.setUser(user);
        reviewRepository.save(review);

        String updatedReviewContent = "{\"id\":" + review.getId() + ",\"content\":\"Updated review\",\"rating\":4}";

        mockMvc.perform(put("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReviewContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated review"))
                .andExpect(jsonPath("$.rating").value(4))
                .andDo(print());

        Optional<Review> updatedReviewOpt = reviewRepository.findById(review.getId());

        assertTrue(updatedReviewOpt.isPresent(), "Updated review should be present");
        Review updatedReview = updatedReviewOpt.get();
        System.out.println("Review updated: " + updatedReview.getContent());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void testDeleteReview() throws Exception {
        Review review = new Review();
        review.setContent("To be deleted");
        review.setRating(2);
        review.setUser(user);
        reviewRepository.save(review);

        mockMvc.perform(delete("/reviews/" + review.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        Optional<Review> deletedReviewOpt = reviewRepository.findById(review.getId());

        assertTrue(deletedReviewOpt.isEmpty(), "Review should be deleted");
        System.out.println("Review deleted: " + review.getContent());
    }


    @Test
    @WithMockUser(username = "testUser", password = "testPassword")
    void testBlockUser() throws Exception {
        user.setEnabled(false); // Блокируем пользователя
        userRepository.save(user);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This should not work!\",\"rating\":1}"))
                .andExpect(status().isForbidden()) // Проверяем, что запрос отклонен
                .andDo(print());

        System.out.println("User blocked: " + user.getUsername());
    }
}
