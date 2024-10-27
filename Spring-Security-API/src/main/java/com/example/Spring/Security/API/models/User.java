package com.example.Spring.Security.API.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean isBloked = false;
    private String email;


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Review> reviews;





    @Enumerated(EnumType.STRING)
    private Role role;



    public User() {}

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }
    @Enumerated(EnumType.STRING)
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBloked() {
        return this.isBloked;
    }


    public void setEnabled(boolean isBloked) {
        this.isBloked = isBloked;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                // Не включаем reviews для предотвращения бесконечного цикла
                '}';
    }


}



