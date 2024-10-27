package com.example.Spring.Security.API.repositories;


import com.example.Spring.Security.API.models.Photo;
import com.example.Spring.Security.API.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Методы для работы с photo
    List<Photo> findByPlace(Place place);
}