package com.harish.flexfit.service;

import java.util.UUID;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.dto.response.UserProfileResponse;

public interface UserProfileService {
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request);

    public UserProfileResponse getProfile(UUID userId);
}
