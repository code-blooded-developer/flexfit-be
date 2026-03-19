package com.harish.flexfit.validator.impl;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.validator.annotation.ValidUserProfile;

import jakarta.validation.ConstraintValidator;

public class UserProfileValidator implements ConstraintValidator<ValidUserProfile, UserProfileRequest> {
    @Override
    public boolean isValid(UserProfileRequest request, jakarta.validation.ConstraintValidatorContext context) {
        if(request == null) return true;

        boolean valid = true;

        context.disableDefaultConstraintViolation();

       if (request.getHeightUnit() == HeightUnit.FT && request.getHeight() != null && request.getHeight() > 10) {
            context.buildConstraintViolationWithTemplate("Height in feet is unrealistic")
                    .addPropertyNode("heightValue")
                    .addConstraintViolation();
            valid = false;
        }

        if (request.getWeightUnit() == WeightUnit.KG && request.getWeight() != null && request.getWeight() > 300) {
            context.buildConstraintViolationWithTemplate("Weight is unrealistic")
                    .addPropertyNode("weightValue")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
