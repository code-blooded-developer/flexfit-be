package com.harish.flexfit.dto.response;

import java.util.List;

import com.harish.flexfit.enums.MuscleGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlternateMuscleOptionResponse {
    private Integer splitDayIndex;
    private String name;
    private MuscleGroup muscle;
    private List<MuscleGroup> targetMuscles;
}
