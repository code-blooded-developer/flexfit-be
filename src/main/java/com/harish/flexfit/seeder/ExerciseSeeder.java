package com.harish.flexfit.seeder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.Exercise;
import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.repo.EquipmentRepository;
import com.harish.flexfit.repo.ExerciseRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExerciseSeeder implements CommandLineRunner {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) {

        if (exerciseRepository.count() > 0) {
            log.info("Exercises already seeded");
            return;
        }

        log.info("Seeding exercises...");

        Map<String, Equipment> equipmentMap = equipmentRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Equipment::getName, e -> e));

        List<Exercise> exercises = List.of(

                // 🔥 CHEST
                create("Push Up", MuscleGroup.CHEST, DifficultyLevel.BEGINNER,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of()),

                create("Bench Press", MuscleGroup.CHEST, DifficultyLevel.INTERMEDIATE,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench")),

                // 🔥 BACK
                create("Pull Up", MuscleGroup.BACK, DifficultyLevel.INTERMEDIATE,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar")),

                create("Lat Pulldown", MuscleGroup.BACK, DifficultyLevel.BEGINNER,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine")),

                // 🔥 LEGS
                create("Squat", MuscleGroup.LEGS, DifficultyLevel.INTERMEDIATE,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell")),

                create("Lunges", MuscleGroup.LEGS, DifficultyLevel.BEGINNER,
                        ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of()),

                // 🔥 WARMUP
                create("Jumping Jacks", MuscleGroup.FULL_BODY, DifficultyLevel.BEGINNER,
                        ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of()),

                create("Arm Circles", MuscleGroup.SHOULDERS, DifficultyLevel.BEGINNER,
                        ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of()),

                // 🔥 STRETCHING
                create("Chest Stretch", MuscleGroup.CHEST, DifficultyLevel.BEGINNER,
                        ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of()),

                create("Hamstring Stretch", MuscleGroup.HAMSTRINGS, DifficultyLevel.BEGINNER,
                        ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of())
        );

        // Attach equipment
        exercises.forEach(exercise -> {
            List<Equipment> eqList = exercise.getEquipments().stream()
                    .map(eq -> equipmentMap.get(eq.getName()))
                    .toList();
            exercise.setEquipments(eqList);
        });

        exerciseRepository.saveAll(exercises);

        log.info("Exercises seeded successfully");
    }

    private Exercise create(String name,
                            MuscleGroup muscle,
                            DifficultyLevel difficulty,
                            ExerciseType type,
                            ExerciseCategory category,
                            List<String> equipmentNames) {

        Exercise e = new Exercise();
        e.setName(name);
        e.setPrimaryMuscle(muscle);
        e.setDifficultyLevel(difficulty);
        e.setExerciseType(type);
        e.setCategory(category);

        // Temporary equipment objects (will map later)
        List<Equipment> equipments = equipmentNames.stream()
                .map(eqName -> {
                    Equipment eq = new Equipment();
                    eq.setName(eqName);
                    return eq;
                })
                .toList();

        e.setEquipments(equipments);

        return e;
    }
}
