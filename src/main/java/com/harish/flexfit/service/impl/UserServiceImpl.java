package com.harish.flexfit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.enums.Role;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.UserRepository;
import com.harish.flexfit.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
   
    @Override
    public User registerUser(SignupRequest request) {
        logger.info("Attempting to register user with email: {}", request.getEmail());

        if(userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed for email: {} - user already exists", request.getEmail());
            throw new BadRequestException("User with email already exists");
        }

        User user = new User();
        String email = request.getEmail().toLowerCase().trim();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        logger.info("User registered successfully with email: {}", savedUser.getEmail());

        return savedUser;
    }

}
