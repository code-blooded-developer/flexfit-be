package com.harish.flexfit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.enums.Role;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.UserRepository;
import com.harish.flexfit.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Override
    public User registerUser(SignupRequest request) {
        log.info("Attempting to register user with email: {}", request.getEmail());

        if(userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed for email: {} - user already exists", request.getEmail());
            throw new BadRequestException("User with email already exists");
        }

        User user = new User();
        String email = request.getEmail().toLowerCase().trim();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return savedUser;
    }

}
