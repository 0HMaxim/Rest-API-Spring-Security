package com.example.Spring.Security.API.services;

import com.example.Spring.Security.API.models.Photo;
import com.example.Spring.Security.API.models.Place;
import com.example.Spring.Security.API.repositories.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.Spring.Security.API.models.Review;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;



    public List<Photo> getPhotosByPlaceId(Long placeId) {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(() -> new RuntimeException("Place not found"));
            return place.getPhotos();
    }


    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    @Transactional
    public Place getPlaceWithPhotos(Long placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if (place != null) {
            Hibernate.initialize(place.getPhotos()); // инициализирует ленивую коллекцию внутри активной сессии, чтобы все необходимые данные были подгружены
        }
        return place;
    }

    public Double getRating(Long placeId) {
        return placeRepository.findById(placeId)
                .map(Place::getRating)
                .orElse(null);
    }


}
