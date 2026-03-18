package com.harish.flexfit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harish.flexfit.entity.User;
import com.harish.flexfit.entity.dto.UserRequestDTO;
import com.harish.flexfit.entity.dto.UserResponseDTO;
import com.harish.flexfit.repo.UserRepository;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        logger.info("Creating user with email: {}", userDTO.getEmail());
        
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User savedUser = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setEmail(savedUser.getEmail());
        return userResponseDTO;
    }
}
