package com.example.Spring.Security.API.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {



    @GetMapping("/login")
    @ResponseBody
    public Map<String, String> login() {
        return Map.of("message", "Please authenticate through Google.");
    }



    @GetMapping("/home")
    @ResponseBody
        public Map<String, Object> home(OAuth2AuthenticationToken authentication) {



       String email = authentication.getPrincipal().getAttribute("email");
       String name = authentication.getPrincipal().getAttribute("name");


        // Возвращаем информацию о пользователе как JSON
        return Map.of(
                "message", "Welcome to the home page!",
                "user", Map.of(
                        "email", email,
                        "name", name
                )
        );



    }




}
