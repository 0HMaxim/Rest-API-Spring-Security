package com.example.Spring.Security.API;

import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        userRepository.save(user);

        assertNotNull(user.getId());
        System.out.println("User created: " + user.getUsername());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("testUser");
        userRepository.save(user);

        userRepository.delete(user);
        assertFalse(userRepository.findById(user.getId()).isPresent());
        System.out.println("User deleted: " + user.getUsername());
    }
}
