package com.example.Spring.Security.API.services;

import com.example.Spring.Security.API.models.*;
import com.example.Spring.Security.API.repositories.PhotoRepository;
import com.example.Spring.Security.API.repositories.PlaceRepository;
import com.example.Spring.Security.API.repositories.ReviewRepository;
import com.example.Spring.Security.API.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoad implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final PhotoRepository photoRepository;

    public DataLoad(UserRepository userRepository, PlaceRepository placeRepository,
                    ReviewRepository reviewRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public void run(String... args) {
        // Создание пользователей
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("password2");
        user2.setRole(Role.ADMIN);


        userRepository.saveAll(Arrays.asList(user1, user2));
        System.out.println("Users created: " + userRepository.findAll());

        // Создание мест
        Place place1 = new Place();
        place1.setName("Place 1");
        place1.setDescription("Description of Place 1");

        Place place2 = new Place();
        place2.setName("Place 2");
        place2.setDescription("Description of Place 2");

        placeRepository.saveAll(Arrays.asList(place1, place2));
        System.out.println("Places created: " + placeRepository.findAll());

        // Создание отзывов
        Review review1 = new Review();
        review1.setContent("Great place!");
        review1.setRating(5);
        review1.setUser(user1);
        review1.setPlace(place1);

        Review review2 = new Review();
        review2.setContent("Not bad.");
        review2.setRating(4);
        review2.setUser(user2);
        review2.setPlace(place2);

        reviewRepository.saveAll(Arrays.asList(review1, review2));
        System.out.println("Reviews created: " + reviewRepository.findAll());

        // Создание фотографий
        Photo photo1 = new Photo();
        photo1.setUrl("http://example.com/photo1.jpg");

        Photo photo2 = new Photo();
        photo2.setUrl("http://example.com/photo2.jpg");

        photoRepository.saveAll(Arrays.asList(photo1, photo2));
        System.out.println("Photos created: " + photoRepository.findAll());




    }
}
