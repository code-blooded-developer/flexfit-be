package com.harish.flexfit.util;

import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.exception.BadRequestException;

public class UnitConversionUtil {
    public static double convertHeightToCm(double value, HeightUnit unit) {
        switch (unit) {
            case CM: return value;
            case FT: return value * 30.48;
            default: throw new BadRequestException("Invalid height unit");
        }
    }

    public static double convertWeightToKg(double value, WeightUnit unit) {
        switch (unit) {
            case KG: return value;
            case LB: return value * 0.453592;
            default: throw new BadRequestException("Invalid weight unit");
        }
    }
}
