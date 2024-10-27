package com.example.Spring.Security.API.services;

import com.example.Spring.Security.API.models.User;
import com.example.Spring.Security.API.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = defaultOAuth2UserService.loadUser(userRequest);


        String email = oauth2User.getAttribute("email");



        User user = userRepository.findByEmail(email);
        if (user == null) {


            user = new User();
            user.setEmail(email);
            user.setUsername(email);


            userRepository.save(user);
        }


        return oauth2User;
    }
}

