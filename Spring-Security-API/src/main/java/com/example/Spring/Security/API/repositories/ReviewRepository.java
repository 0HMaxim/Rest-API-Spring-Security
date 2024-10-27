package com.example.Spring.Security.API.repositories;

import com.example.Spring.Security.API.models.Place;
import com.example.Spring.Security.API.models.Review;
import com.example.Spring.Security.API.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByPlace(Place place);
    List<Review> findByUser(User user);
    List<Review> findByPlaceId(Long placeId);

}