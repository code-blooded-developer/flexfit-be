package com.harish.flexfit.service;

import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.entity.User;

public interface UserService {
    User registerUser(SignupRequest request);
}
