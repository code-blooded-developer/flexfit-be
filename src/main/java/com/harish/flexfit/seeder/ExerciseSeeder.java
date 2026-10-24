package com.harish.flexfit.seeder;

import java.util.ArrayList;
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
import com.harish.flexfit.enums.MeasurementType;
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

        log.info("Seeding exercises...");

        Map<String, Equipment> equipmentMap = equipmentRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Equipment::getName, e -> e));

        List<Exercise> exercises = List.of(

                // 🔥 CHEST — 23 exercises
                create("Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Start in a high plank with hands under your shoulders
                        Brace your core so your hips stay level
                        Lower your chest toward the floor
                        Keep your elbows at about 45 degrees
                        Press back up until your arms are straight
                        Keep your body in one straight line
                        """),

                create("Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        """
                        Lie on a flat bench with your eyes under the bar
                        Plant your feet and squeeze your shoulder blades
                        Grip the bar slightly wider than shoulder-width
                        Unrack and lower the bar to your mid-chest
                        Keep your elbows at about 45 degrees
                        Press back up until your arms are straight
                        """),

                create("Incline Barbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Adjustable Bench"),
                        """
                        Set the bench to 30 to 45 degrees
                        Lie back with your feet planted
                        Grip the bar slightly wider than shoulders
                        Lower the bar to your upper chest
                        Keep your elbows slightly tucked
                        Press up until your arms are straight
                        """),

                create("Incline Dumbbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        """
                        Set the bench to 30 to 45 degrees
                        Hold a dumbbell in each hand at chest height
                        Press the weights up over your upper chest
                        Keep your wrists stacked over your elbows
                        Lower slowly until you feel a stretch
                        Do not let your shoulders roll forward
                        """),

                create("Decline Barbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        """
                        Lie on a decline bench with your legs secured
                        Grip the bar slightly wider than shoulder-width
                        Unrack and lower the bar to your lower chest
                        Keep your elbows tucked at about 45 degrees
                        Press up until your arms are straight
                        Keep your hips on the bench throughout
                        """),

                create("Decline Dumbbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Lie on a decline bench with your legs secured
                        Hold a dumbbell in each hand at chest height
                        Press the weights up over your lower chest
                        Bring them together without clashing
                        Lower slowly until you feel a stretch
                        Keep your wrists stacked over your elbows
                        """),

                create("Dumbbell Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Lie on a flat bench holding a dumbbell in each hand
                        Rest the weights on your thighs before starting
                        Kick them up to chest height
                        Press the weights over your chest
                        Lower until your elbows are just below the bench
                        Keep your shoulder blades pinned back
                        """),

                create("Dumbbell Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Lie on a flat bench with a dumbbell in each hand
                        Press the weights above your chest, palms facing in
                        Keep a soft, fixed bend in your elbows
                        Open your arms in a wide arc
                        Stop when your hands reach chest level
                        Squeeze your chest to bring them back together
                        """),

                create("Incline Dumbbell Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        """
                        Set the bench to a low incline
                        Press the dumbbells above your upper chest
                        Keep a soft bend in your elbows
                        Open your arms wide in an arc
                        Stop at chest level
                        Squeeze your chest to bring them together
                        """),

                create("Cable Crossover", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        """
                        Set both pulleys above head height
                        Grab the handles and step forward
                        Lean slightly with one foot in front
                        Bring your hands together in front of your chest
                        Keep a soft bend in your elbows
                        Return slowly until you feel a stretch
                        """),

                create("Low-to-High Cable Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        """
                        Set both pulleys to the lowest position
                        Grab the handles with your palms facing up
                        Stand tall with a slight forward lean
                        Pull the handles up and inward
                        Cross your hands at chest height
                        Return slowly along the same path
                        """),

                create("High-to-Low Cable Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        """
                        Set both pulleys to the highest position
                        Grab the handles and step forward
                        Stand tall with a slight forward lean
                        Pull the handles down and inward
                        Cross your hands in front of your hips
                        Return slowly to the start
                        """),

                create("Chest Press Machine", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Chest Press Machine"),
                        """
                        Adjust the seat so the handles sit at chest level
                        Sit back with your shoulders against the pad
                        Grip the handles with your wrists straight
                        Press forward until your arms are extended
                        Return slowly until you feel a stretch
                        Keep your back flat against the pad
                        """),

                create("Pec Deck Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pec Deck Machine"),
                        """
                        Adjust the seat so the handles sit at chest height
                        Sit back with your shoulders against the pad
                        Place your forearms on the pads
                        Squeeze your arms together in front of your chest
                        Pause briefly in the middle
                        Open slowly until you feel a stretch
                        """),

                create("Decline Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        """
                        Place your feet on a box with your hands on the floor
                        Set your hands slightly wider than shoulders
                        Keep your body in a straight line
                        Lower your chest toward the floor
                        Press back up without letting your hips sag
                        Keep your elbows at about 45 degrees
                        """),

                create("Wide Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Start in a plank with hands wider than shoulders
                        Point your fingers slightly outward
                        Keep your body in a straight line
                        Lower your chest between your hands
                        Stop when your chest is near the floor
                        Press back up without flaring your elbows
                        """),

                create("Diamond Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Place your hands together under your chest
                        Form a diamond with your thumbs and index fingers
                        Set your body in a straight line from head to heels
                        Lower your chest toward your hands
                        Keep your elbows close to your ribs
                        Press back up until your arms are straight
                        """),

                create("Dip (Chest)", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dip Station"),
                        """
                        Grip the bars and lift yourself up
                        Lean your torso forward slightly
                        Cross your ankles and keep your legs still
                        Lower until your shoulders are below your elbows
                        Stop before you feel any shoulder pinching
                        Press back up to straight arms
                        """),

                create("Svend Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Weight Plate"),
                        """
                        Stand tall holding a plate at chest height
                        Press your palms into the plate
                        Push the plate forward until your arms are straight
                        Squeeze your chest at the end range
                        Bring the plate back to your chest
                        Keep your elbows slightly bent
                        """),

                create("Landmine Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Landmine", "Weight Plate"),
                        """
                        Set a barbell in the landmine attachment
                        Stand facing the bar with feet hip-width
                        Hold the end of the bar at shoulder height
                        Press up and slightly across your body
                        Keep your core braced and ribs down
                        Return slowly to your shoulder
                        """),

                create("Hex Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Lie on a flat bench with a dumbbell in each hand
                        Press the weights together at chest height
                        Keep the dumbbells touching for the whole set
                        Press straight up over your chest
                        Lower slowly until your elbows are below the bench
                        Do not let the weights drift apart
                        """),

                create("Smith Machine Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine", "Bench"),
                        """
                        Place a flat bench inside the Smith machine
                        Set the bar to touch your mid-chest
                        Lie back with your feet planted
                        Unrack and lower the bar to your chest
                        Press up until your arms are straight
                        Keep your shoulder blades squeezed together
                        """),

                create("Dumbbell Pullover", MuscleGroup.CHEST, List.of(MuscleGroup.BACK, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Lie across a bench with only your upper back supported
                        Hold one dumbbell above your chest with both hands
                        Keep your hips lower than your shoulders
                        Lower the weight behind your head in an arc
                        Stop when you feel a stretch in your lats
                        Pull back over your chest with your elbows soft
                        """),

                // 🔥 BACK — 17 exercises
                create("Pull Up", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        """
                        Hang from the bar with an overhand grip
                        Set your hands slightly wider than shoulders
                        Pull your chest toward the bar
                        Get your chin over the bar
                        Lower under control to a full hang
                        Avoid kipping or swinging
                        """),

                create("Chin Up", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        """
                        Hang from the bar with palms facing you
                        Set your grip about shoulder-width apart
                        Pull your chest toward the bar
                        Clear your chin above the bar
                        Lower slowly to a full hang
                        Keep your ribs down and avoid swinging
                        """),

                create("Wide-Grip Pull Up", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        """
                        Hang from the bar with hands well outside your shoulders
                        Set your grip with your thumbs around the bar
                        Pull your chest toward the bar
                        Drive your elbows down and back
                        Lower under control to a full hang
                        Avoid swinging or using momentum
                        """),

                create("Lat Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the knee pad snug over your thighs
                        Grip the bar wider than shoulder-width
                        Pull the bar to your upper chest
                        Lead with your elbows, not your hands
                        Return until your arms are fully straight
                        Keep your torso upright throughout
                        """),

                create("Close-Grip Lat Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the seat so the bar starts overhead
                        Grip the bar narrower than shoulder-width
                        Pull the bar to your upper chest
                        Drive your elbows down toward your ribs
                        Return slowly until your arms are straight
                        Avoid leaning back to move the weight
                        """),

                create("Barbell Row (Overhand)", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar over mid-foot
                        Hinge to about 45 degrees with a flat back
                        Grip slightly wider than shoulders, palms down
                        Pull the bar to your lower ribs
                        Lower under control until your arms are straight
                        Keep your torso still for the whole set
                        """),

                create("Barbell Row (Underhand)", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar over mid-foot
                        Hinge forward with your chest up and back flat
                        Grip shoulder-width with palms facing you
                        Pull the bar toward your belly button
                        Squeeze your shoulder blades at the top
                        Lower slowly without rounding your back
                        """),

                create("Dumbbell Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Place one knee and hand on the bench
                        Let the dumbbell hang straight down
                        Pull the weight toward your hip
                        Keep your elbow close to your body
                        Lower until your arm is fully extended
                        Keep your back flat and hips square
                        """),

                create("Seated Cable Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Seated Row Machine"),
                        """
                        Sit with your feet on the platform, knees soft
                        Grip the handle with your arms straight
                        Sit tall with your chest up
                        Pull the handle to your belly
                        Squeeze your shoulder blades together
                        Return slowly until your arms are straight
                        """),

                create("Single-Arm Cable Row", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley at waist height
                        Stand in a split stance with knees soft
                        Grip the handle with one hand
                        Pull the handle to your hip
                        Keep your shoulders square to the machine
                        Return slowly and repeat on the other side
                        """),

                create("T-Bar Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Landmine", "Weight Plate"),
                        """
                        Straddle the bar with feet hip-width
                        Hinge forward and grip the handles
                        Keep your back flat and chest up
                        Pull the weight to your chest
                        Squeeze your shoulder blades
                        Lower slowly until your arms are straight
                        """),

                create("Straight-Arm Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley above head height
                        Grip the bar with your arms straight
                        Step back until the cable is taut
                        Pull the bar down to your thighs
                        Keep your elbows slightly bent and fixed
                        Return slowly to shoulder height
                        """),

                create("Face Pull", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley at upper chest height
                        Grip the rope with your palms facing in
                        Pull toward your face with your elbows high
                        Separate the rope ends beside your ears
                        Squeeze your rear shoulders
                        Return slowly with control
                        """),

                create("Inverted Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        """
                        Set the bar at hip height
                        Lie under it and grip slightly wider than shoulders
                        Hang with your body straight and heels on the floor
                        Pull your chest to the bar
                        Lower slowly until your arms are straight
                        Keep your hips from sagging
                        """),

                create("Hyperextension", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Hyperextension Bench"),
                        """
                        Set the pad at your hip crease
                        Anchor your ankles under the rollers
                        Cross your arms over your chest
                        Hinge down until you feel a hamstring stretch
                        Raise until your torso lines up with your legs
                        Do not arch past a straight line
                        """),

                create("Back Extension", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Back Extension Bench"),
                        """
                        Set the pad just below your hip crease
                        Cross your arms over your chest
                        Hinge down until you feel your hamstrings stretch
                        Rise until your body forms a straight line
                        Stop at the top, do not arch past neutral
                        """),

                create("Rack Pull", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Squat Rack"),
                        """
                        Set the bar just below your knees in the rack
                        Stand with feet hip-width, bar over mid-foot
                        Hinge and grip just outside your knees
                        Brace your core and flatten your back
                        Stand up by driving your hips forward
                        Lower the bar back to the pins
                        """),

                // 🔥 SHOULDERS — 17 exercises
                create("Overhead Press (Barbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar at your collarbone
                        Grip slightly wider than shoulder-width
                        Brace your core and squeeze your glutes
                        Press the bar overhead
                        Move your head back as the bar passes
                        Lock out with the bar over your ears
                        """),

                create("Dumbbell Shoulder Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        """
                        Sit on a bench with back support
                        Hold dumbbells at shoulder height, palms forward
                        Press the weights overhead
                        Stop when your arms are straight
                        Keep your wrists over your elbows
                        Lower slowly to shoulder height
                        """),

                create("Arnold Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Sit on a bench with back support
                        Hold dumbbells at shoulder height, palms facing you
                        Press up while rotating your palms forward
                        Finish with the weights overhead
                        Reverse the rotation on the way down
                        Keep your ribs down throughout
                        """),

                create("Lateral Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand tall with a dumbbell in each hand
                        Let the weights hang at your sides
                        Keep a soft bend in your elbows
                        Raise your arms out to shoulder height
                        Lead with your elbows, not your hands
                        Lower slowly without swinging
                        """),

                create("Cable Lateral Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Stand side-on with the handle across your body
                        Keep a soft bend in your elbow
                        Raise your arm out to shoulder height
                        Stop when your hand reaches shoulder level
                        Lower slowly and switch sides
                        """),

                create("Front Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand tall holding a dumbbell in each hand
                        Rest the weights in front of your thighs
                        Keep a soft bend in your elbows
                        Raise the weights to shoulder height
                        Stop before your shoulders shrug
                        Lower slowly with control
                        """),

                create("Cable Front Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Stand facing away with the handle at your thighs
                        Keep your arm almost straight
                        Raise the handle to shoulder height
                        Stop before your shoulder shrugs
                        Lower slowly with control
                        """),

                create("Reverse Fly (Dumbbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Sit on the end of a bench and hinge forward
                        Hold a dumbbell in each hand below you
                        Keep a soft bend in your elbows
                        Raise your arms out to the sides
                        Squeeze your shoulder blades at the top
                        Lower slowly with control
                        """),

                create("Reverse Fly (Cable)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set both pulleys at shoulder height
                        Grab the handles with your arms crossed
                        Step back until the cables are taut
                        Pull your arms out and back
                        Squeeze your shoulder blades together
                        Return slowly to the start
                        """),

                create("Upright Row (Barbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar in front of your thighs
                        Grip slightly wider than shoulder-width
                        Pull the bar up along your body
                        Stop when the bar reaches your lower chest
                        Keep the bar close to your torso
                        Lower slowly with control
                        """),

                create("Upright Row (Cable)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Stand facing the machine and grip the bar
                        Keep your back straight and core braced
                        Pull the bar up along your body
                        Stop when the bar reaches your lower chest
                        Lower slowly with control
                        """),

                create("Shoulder Press Machine", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Shoulder Press Machine"),
                        """
                        Adjust the seat so the handles start at shoulder height
                        Sit with your back against the pad
                        Grip the handles with your wrists straight
                        Press up until your arms are extended
                        Stop before your elbows lock hard
                        Lower slowly to shoulder height
                        """),

                create("Lateral Raise Machine", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Lateral Raise Machine"),
                        """
                        Adjust the seat so the pads sit at your sides
                        Sit tall with your back against the pad
                        Place your upper arms on the pads
                        Push the pads out to shoulder height
                        Pause briefly at the top
                        Return slowly to the start
                        """),

                create("Push Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with the bar at your collarbone
                        Brace your core with feet hip-width
                        Dip by bending your knees slightly
                        Drive up with your legs and press the bar
                        Lock out overhead
                        Lower the bar back to your shoulders
                        """),

                create("Single-Arm Dumbbell Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand or sit with a dumbbell in one hand
                        Bring it to shoulder height
                        Brace your core to stay square
                        Press the weight overhead
                        Lower slowly to your shoulder
                        Complete your reps, then switch sides
                        """),

                create("Pike Push Up", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Start in a push-up position
                        Walk your feet toward your hands
                        Lift your hips so your body forms an inverted V
                        Lower the top of your head toward the floor
                        Press back up to straight arms
                        Keep your legs as straight as you can
                        """),

                create("Handstand Push Up", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Kick up into a handstand against a wall
                        Set your hands slightly wider than shoulders
                        Keep your body in a straight line
                        Lower until your head nears the floor
                        Press back up to straight arms
                        Keep your core braced throughout
                        """),

                // 🔥 BICEPS — 14 exercises
                create("Barbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        """
                        Stand with feet hip-width, bar in front of your thighs
                        Grip the bar shoulder-width, palms up
                        Keep your elbows close to your ribs
                        Curl the bar to your shoulders
                        Squeeze at the top
                        Lower slowly to straight arms
                        """),

                create("Dumbbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand tall with a dumbbell in each hand
                        Let the weights hang with palms forward
                        Keep your elbows close to your ribs
                        Curl the weights to your shoulders
                        Squeeze at the top
                        Lower slowly to straight arms
                        """),

                create("Hammer Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand tall with a dumbbell in each hand
                        Hold them with palms facing each other
                        Keep your elbows close to your ribs
                        Curl the weights to your shoulders
                        Squeeze at the top
                        Lower slowly to straight arms
                        """),

                create("Reverse Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        """
                        Stand with the bar in front of your thighs
                        Grip it with your palms facing down
                        Keep your elbows close to your ribs
                        Curl the bar to your chest
                        Squeeze your forearms at the top
                        Lower slowly to straight arms
                        """),

                create("Preacher Curl (Barbell)", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar", "Preacher Curl Bench"),
                        """
                        Sit with the back of your upper arms on the pad
                        Grip the bar with palms up
                        Start with your arms almost straight
                        Curl the bar to your shoulders
                        Squeeze at the top
                        Lower slowly without locking out hard
                        """),

                create("Preacher Curl (Dumbbell)", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Preacher Curl Bench"),
                        """
                        Sit with the back of one upper arm on the pad
                        Hold a dumbbell with your palm up
                        Start with your arm almost straight
                        Curl the weight to your shoulder
                        Squeeze at the top
                        Lower slowly and switch arms
                        """),

                create("Cable Bicep Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Grip the bar with palms up
                        Stand tall with your elbows at your sides
                        Curl the bar toward your shoulders
                        Squeeze your biceps at the top
                        Lower slowly to straight arms
                        """),

                create("Cable Hammer Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Attach a rope to the low pulley
                        Grip the rope with palms facing in
                        Keep your elbows close to your ribs
                        Curl the rope toward your shoulders
                        Squeeze at the top
                        Lower slowly to straight arms
                        """),

                create("Concentration Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Sit on a bench with your legs apart
                        Rest the back of your working arm on your thigh
                        Let the dumbbell hang straight down
                        Curl it toward your shoulder
                        Squeeze at the top
                        Lower slowly and switch arms
                        """),

                create("Incline Dumbbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        """
                        Set the bench to a low incline
                        Lie back with a dumbbell in each hand
                        Let your arms hang straight down
                        Curl the weights without moving your elbows
                        Squeeze at the top
                        Lower slowly to a full stretch
                        """),

                create("Spider Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        """
                        Lie face down on an incline bench
                        Let a dumbbell hang from each hand
                        Keep your elbows still
                        Curl the weights toward your shoulders
                        Squeeze at the top
                        Lower slowly to a full stretch
                        """),

                create("Biceps Curl Machine", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Biceps Curl Machine"),
                        """
                        Adjust the seat so your elbows rest on the pad
                        Grip the handles with palms up
                        Keep your upper arms on the pad
                        Curl the handles toward your shoulders
                        Squeeze at the top
                        Lower slowly to straight arms
                        """),

                create("21s Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        """
                        Hold an EZ bar with an underhand grip
                        Curl from the bottom to halfway
                        Complete seven half reps
                        Then curl from halfway to the top
                        Complete seven more half reps
                        Finish with seven full reps
                        """),

                create("Zottman Curl", MuscleGroup.BICEPS, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand with a dumbbell in each hand, palms up
                        Curl the weights to your shoulders
                        Rotate your palms to face down at the top
                        Lower slowly with your palms down
                        Rotate back to palms up at the bottom
                        Keep your elbows still throughout
                        """),

                // 🔥 TRICEPS — 12 exercises
                create("Tricep Pushdown", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley above head height
                        Grip the bar with your elbows at your sides
                        Keep your upper arms still
                        Push the bar down until your arms are straight
                        Squeeze your triceps at the bottom
                        Return slowly to 90 degrees
                        """),

                create("Overhead Tricep Extension (Dumbbell)", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Hold one dumbbell overhead with both hands
                        Keep your elbows close to your head
                        Lower the weight behind your head
                        Stop when your forearms touch your biceps
                        Press back up until your arms are straight
                        Keep your ribs down throughout
                        """),

                create("Overhead Tricep Extension (Cable)", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Attach a rope to the low pulley
                        Face away and hold the rope behind your head
                        Keep your elbows pointing forward
                        Extend your arms overhead
                        Squeeze your triceps at the top
                        Return slowly behind your head
                        """),

                create("Close-Grip Bench Press", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        """
                        Lie on a flat bench with the bar over your chest
                        Grip the bar about shoulder-width
                        Lower the bar to your lower chest
                        Keep your elbows close to your body
                        Press up until your arms are straight
                        Keep your wrists straight throughout
                        """),

                create("Skull Crushers", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar", "Bench"),
                        """
                        Lie on a flat bench holding an EZ bar
                        Press it above your chest
                        Keep your upper arms still
                        Bend your elbows to lower the bar toward your head
                        Stop just above your forehead
                        Extend your arms back to the start
                        """),

                create("Dip (Tricep)", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dip Station"),
                        """
                        Grip the bars and lift yourself up
                        Keep your torso upright
                        Cross your ankles and stay still
                        Lower until your elbows reach 90 degrees
                        Press back up to straight arms
                        Keep your elbows close to your body
                        """),

                create("Bench Dip", MuscleGroup.TRICEPS, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Bench"),
                        """
                        Sit on the edge of a bench
                        Place your hands beside your hips
                        Slide your hips off the bench
                        Lower until your elbows reach 90 degrees
                        Press back up to straight arms
                        Keep your back close to the bench
                        """),

                create("Tricep Kickback", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Hinge forward with one hand on a bench
                        Hold a dumbbell with your upper arm at your side
                        Keep your elbow bent at 90 degrees
                        Extend your arm straight back
                        Squeeze your triceps at the top
                        Return slowly and switch arms
                        """),

                create("Cable Overhead Extension", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Attach a rope and face away from the machine
                        Bring the rope behind your head with elbows up
                        Extend your arms forward and up
                        Squeeze your triceps at the top
                        Return slowly behind your head
                        """),

                create("Triceps Pushdown Machine", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Triceps Pushdown Machine"),
                        """
                        Adjust the seat so your elbows rest on the pad
                        Grip the handles with your elbows bent
                        Keep your upper arms on the pad
                        Push the handles down until your arms are straight
                        Squeeze at the bottom
                        Return slowly to the start
                        """),

                create("JM Press", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        """
                        Lie on a flat bench with a close grip
                        Lower the bar toward your upper chest
                        Keep your elbows tucked in
                        Stop when the bar nears your throat
                        Press back up with your triceps
                        Keep the path short and controlled
                        """),

                // 🔥 LEGS — 19 exercises
                create("Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Set the bar across your upper back
                        Stand with feet shoulder-width apart
                        Brace your core and keep your chest up
                        Sit back and down with your knees out
                        Descend until your thighs are near parallel
                        Drive up through your whole foot
                        """),

                create("Lunges", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand tall with feet hip-width apart
                        Step one foot forward
                        Lower until both knees are near 90 degrees
                        Keep your front knee over your ankle
                        Push through your front heel to stand
                        Repeat and switch legs
                        """),

                create("Leg Press", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        """
                        Sit with your back and hips against the pad
                        Place your feet shoulder-width on the platform
                        Release the safeties
                        Lower until your knees reach about 90 degrees
                        Keep your lower back against the pad
                        Press back up without locking your knees
                        """),

                create("Leg Extension", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Extension Machine"),
                        """
                        Sit with your knees in line with the pivot
                        Set the roller just above your ankles
                        Grip the handles and sit tall
                        Extend your legs until straight
                        Squeeze your quads at the top
                        Lower slowly without dropping the weight
                        """),

                create("Hack Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Hack Squat Machine"),
                        """
                        Set your shoulders under the pads
                        Place your feet shoulder-width on the platform
                        Release the safeties and stand tall
                        Lower until your thighs are near parallel
                        Keep your back against the pad
                        Press up through your whole foot
                        """),

                create("Bulgarian Split Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Stand a step in front of a bench
                        Place the top of your back foot on the bench
                        Hold a dumbbell in each hand
                        Lower your back knee toward the floor
                        Keep your front shin roughly vertical
                        Press up through your front heel
                        """),

                create("Goblet Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Hold a dumbbell vertically at your chest
                        Stand with feet slightly wider than your hips
                        Keep your elbows inside your knees
                        Sit down with your chest up
                        Descend until your elbows pass your knees
                        Drive up through your heels
                        """),

                create("Front Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Squat Rack"),
                        """
                        Rest the bar on your front shoulders
                        Keep your elbows high and chest up
                        Stand with feet shoulder-width apart
                        Sit down with your torso upright
                        Descend until your thighs are near parallel
                        Drive up through your heels
                        """),

                create("Box Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Box", "Squat Rack"),
                        """
                        Set a box behind you at about knee height
                        Stand with feet shoulder-width, bar on your back
                        Sit back and down onto the box
                        Keep your shins vertical and chest up
                        Pause briefly without relaxing
                        Drive up through your heels to stand
                        """),

                create("Pistol Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand on one leg with the other extended forward
                        Keep your arms out for balance
                        Sit back and down on the working leg
                        Descend as low as you can control
                        Keep your heel flat
                        Press up to standing and switch legs
                        """),

                create("Reverse Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand tall with feet hip-width apart
                        Step one foot backward
                        Lower until both knees are near 90 degrees
                        Keep your front knee over your ankle
                        Push through your front heel to stand
                        Repeat and switch legs
                        """),

                create("Side Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand with your feet wide apart
                        Shift your weight onto one leg
                        Bend that knee and sit back into it
                        Keep the other leg straight
                        Push back to the centre
                        Repeat and switch sides
                        """),

                create("Curtsy Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand with feet hip-width apart
                        Step one leg behind and across the other
                        Lower into a lunge with your chest up
                        Keep your front knee over your toes
                        Push back to standing
                        Repeat and switch sides
                        """),

                create("Step Up", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        """
                        Place one foot fully on the box
                        Keep the other foot on the floor
                        Drive through your top heel to stand up
                        Stand tall without pushing off the back foot
                        Lower slowly until your back foot touches
                        Complete your reps, then switch legs
                        """),

                create("Sissy Squat", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand with your heels slightly raised
                        Hold a support for balance
                        Drive your knees forward and lean back
                        Keep your hips and shoulders in line
                        Descend until you feel a quad stretch
                        Press back up to standing
                        """),

                create("Smith Machine Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        """
                        Set the bar across your upper back
                        Place your feet slightly forward of the bar
                        Unrack and stand tall
                        Lower until your thighs are near parallel
                        Keep your back flat against the bar path
                        Drive up through your heels
                        """),

                create("Single-Leg Leg Press", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        """
                        Sit with your back against the pad
                        Place one foot in the centre of the platform
                        Release the safeties
                        Lower until your knee reaches about 90 degrees
                        Press up without locking your knee
                        Complete your reps, then switch legs
                        """),

                create("Hip Adductor Machine", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hip Adductor Machine"),
                        """
                        Sit with your back against the pad
                        Place your inner thighs against the pads
                        Set the starting width to a comfortable stretch
                        Squeeze your knees together
                        Pause briefly in the middle
                        Return slowly to the start
                        """),

                create("Hip Abductor Machine", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hip Abductor Machine"),
                        """
                        Sit with your back against the pad
                        Place your outer thighs against the pads
                        Grip the handles and sit tall
                        Push your knees apart against the pads
                        Pause at the end range
                        Return slowly without letting the pads slam
                        """),

                // 🔥 GLUTES & HAMSTRINGS — 14 exercises
                create("Romanian Deadlift", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar over mid-foot
                        Grip the bar just outside your thighs
                        Hinge back with a flat back and soft knees
                        Slide the bar down your legs
                        Stop when you feel a hamstring stretch
                        Drive your hips forward to stand tall
                        """),

                create("Hip Thrust", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        """
                        Sit with your upper back against a bench
                        Roll a barbell over your hips
                        Plant your feet flat, shoulder-width apart
                        Drive through your heels to lift your hips
                        Stop when your torso is parallel to the floor
                        Squeeze your glutes, then lower with control
                        """),

                create("Glute Ham Raise", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Glute Ham Developer"),
                        """
                        Set the pads just below your knees
                        Anchor your ankles under the rollers
                        Start with your body straight from the knees up
                        Lower your torso forward with control
                        Pull yourself back up with your hamstrings
                        Keep your hips extended the whole time
                        """),

                create("Cable Pull-Through", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Stand facing away with the rope between your legs
                        Hinge forward and let the cable pull you back
                        Keep your back flat and knees soft
                        Drive your hips forward to stand tall
                        Squeeze your glutes at the top
                        """),

                create("Kettlebell Swing", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Kettlebell"),
                        """
                        Stand with feet slightly wider than your hips
                        Hold the kettlebell with both hands
                        Hinge at your hips and swing it back
                        Snap your hips forward to swing it up
                        Let the bell float to chest height
                        Guide it back down and repeat
                        """),

                create("Single-Leg Romanian Deadlift", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Hold a dumbbell opposite to your working leg
                        Stand tall with a soft knee
                        Hinge forward while lifting the free leg back
                        Keep your hips square to the floor
                        Lower until your torso is near parallel
                        Return by driving your hip forward
                        """),

                create("Standing Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        """
                        Stand facing the machine and hold the handles
                        Set the roller just above your heel
                        Keep your standing leg straight
                        Curl your working heel toward your glute
                        Squeeze at the top
                        Lower slowly and repeat on both legs
                        """),

                create("Lying Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        """
                        Lie face down with your knees past the pad edge
                        Set the roller just above your heels
                        Grip the handles and brace your core
                        Curl your heels toward your glutes
                        Squeeze your hamstrings at the top
                        Lower slowly until your legs are straight
                        """),

                create("Seated Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        """
                        Sit with your back against the pad
                        Set the roller just above your heels
                        Grip the handles to stay in place
                        Curl your heels down and back
                        Squeeze your hamstrings at the end range
                        Return slowly without letting the weight drop
                        """),

                create("Nordic Hamstring Curl", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Kneel on a pad with your ankles anchored
                        Keep your hips extended and body straight
                        Lower your torso forward slowly
                        Resist the fall with your hamstrings
                        Catch yourself with your hands if needed
                        Pull back up using your hamstrings
                        """),

                create("Reverse Hyperextension", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hyperextension Bench"),
                        """
                        Lie face down on the bench
                        Grip the handles and brace your core
                        Keep your legs straight
                        Lift your legs until level with your torso
                        Squeeze your glutes at the top
                        Lower slowly without swinging
                        """),

                create("Dumbbell Hip Thrust", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        """
                        Sit on the floor with your upper back on a bench
                        Place a dumbbell across your hips
                        Plant your feet flat, shoulder-width apart
                        Drive through your heels to lift your hips
                        Stop when your torso is parallel to the floor
                        Squeeze your glutes, then lower slowly
                        """),

                create("Cable Hip Adduction", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Attach the ankle strap to the low pulley
                        Stand side-on with the cable across your body
                        Keep your working leg straight
                        Pull your leg across your body
                        Stop before your hips shift
                        Return slowly and repeat on the other side
                        """),

                create("Cable Hip Abduction", MuscleGroup.GLUTES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Attach the ankle strap to the low pulley
                        Stand side-on and hold the frame
                        Keep your working leg straight
                        Lift your leg out to the side
                        Stop before your hip rotates
                        Return slowly and repeat on the other side
                        """),

                // 🔥 CALVES — 6 exercises
                create("Standing Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Calf Raise Machine"),
                        """
                        Step onto the platform with the balls of your feet
                        Set the shoulder pads at a comfortable height
                        Stand tall with your knees slightly bent
                        Lower your heels until you feel a stretch
                        Rise onto your toes and squeeze hard
                        Pause at the top before lowering
                        """),

                create("Seated Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Barbell", "Box"),
                        """
                        Sit on a box with the balls of your feet on a plate
                        Rest a barbell across your lower thighs
                        Hold the bar to keep it steady
                        Lower your heels toward the floor
                        Rise onto your toes and squeeze
                        Pause at the top before lowering
                        """),

                create("Leg Press Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        """
                        Sit in the leg press with the balls of your feet on the plate
                        Straighten your legs but keep a slight bend
                        Release the safeties
                        Push the plate away by pointing your toes
                        Pause, then let your heels come back toward you
                        Keep your knees still for the whole set
                        """),

                create("Single-Leg Standing Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand on one foot with the ball on a step
                        Hold a dumbbell on the working side
                        Hold a wall with your free hand
                        Lower your heel for a full stretch
                        Rise onto your toes as high as possible
                        Complete your reps, then switch legs
                        """),

                create("Donkey Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Place the balls of your feet on a raised edge
                        Hinge forward at the hips with your legs straight
                        Let your heels drop for a full stretch
                        Rise onto your toes as high as you can
                        Pause at the top, then lower slowly
                        """),

                create("Smith Machine Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        """
                        Set the bar across your upper back
                        Place the balls of your feet on a plate
                        Unrack and stand tall
                        Lower your heels toward the floor
                        Rise onto your toes and squeeze
                        Keep your knees slightly bent throughout
                        """),

                // 🔥 CORE — 16 exercises
                create("Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with knees bent
                        Place your hands behind your head
                        Press your lower back into the mat
                        Curl your shoulders off the floor
                        Squeeze your abs at the top
                        Lower slowly without pulling your neck
                        """),

                create("Reverse Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with knees bent
                        Press your lower back into the mat
                        Bring your knees toward your chest
                        Curl your hips off the floor
                        Lower slowly without dropping
                        Keep your abs engaged throughout
                        """),

                create("Leg Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with legs straight
                        Place your hands under your hips
                        Press your lower back into the mat
                        Raise your legs to vertical
                        Lower slowly without arching
                        Stop before your back lifts
                        """),

                create("Hanging Knee Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        """
                        Hang from the bar with straight arms
                        Brace your core to stop any swing
                        Raise your knees toward your chest
                        Curl your pelvis up at the top
                        Lower slowly to a full hang
                        Keep your shoulders active
                        """),

                create("Hanging Leg Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        """
                        Hang from the bar with straight arms
                        Brace your core to stop any swing
                        Raise your legs straight to hip height
                        Curl your pelvis up at the top
                        Lower slowly with control
                        Keep your shoulders active
                        """),

                create("Russian Twist", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat", "Medicine Ball"),
                        """
                        Sit with knees bent and heels on the floor
                        Lean back slightly with a flat back
                        Hold a medicine ball at your chest
                        Rotate your torso to one side
                        Touch the ball beside your hip
                        Rotate to the other side and repeat
                        """),

                create("Cable Woodchop", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the lowest position
                        Stand side-on and grip the handle with both hands
                        Keep your arms almost straight
                        Pull the handle up and across your body
                        Rotate through your torso, not your arms
                        Return slowly and switch sides
                        """),

                create("Cable Reverse Woodchop", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley to the highest position
                        Stand side-on and grip the handle with both hands
                        Keep your arms almost straight
                        Pull the handle down and across your body
                        Rotate through your torso, not your arms
                        Return slowly and switch sides
                        """),

                create("Ab Wheel Rollout", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Ab Wheel"),
                        """
                        Kneel with the wheel under your shoulders
                        Brace your core and tuck your ribs
                        Roll the wheel forward slowly
                        Stop before your lower back arches
                        Pull back to the start with your abs
                        Keep your hips moving with your torso
                        """),

                create("Pallof Press", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        """
                        Set the pulley at chest height
                        Stand side-on and grip the handle with both hands
                        Hold it at your chest
                        Press the handle straight out
                        Resist the pull that rotates you
                        Return slowly and switch sides
                        """),

                create("Bicycle Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with hands behind your head
                        Lift your shoulders off the floor
                        Bring one knee toward your chest
                        Rotate your opposite elbow toward that knee
                        Alternate sides at a steady pace
                        Keep your lower back on the mat
                        """),

                create("Medicine Ball Slam", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Slam Ball"),
                        """
                        Stand with feet shoulder-width apart
                        Hold the ball overhead with both hands
                        Rise onto your toes
                        Slam the ball down in front of you
                        Pick it up and reset
                        Keep your core braced throughout
                        """),

                create("V-Up", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with arms overhead
                        Keep your legs straight
                        Lift your arms and legs at the same time
                        Reach for your toes at the top
                        Lower slowly with control
                        Keep your lower back on the mat
                        """),

                create("Dragon Flag", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Bench"),
                        """
                        Lie on a bench and grip it behind your head
                        Brace your core and lift your legs and hips
                        Keep your body in a straight line
                        Lower slowly without bending at the hips
                        Stop before your back arches
                        Press back up with your abs
                        """),

                create("Farmer's Walk", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        """
                        Stand tall with a heavy dumbbell in each hand
                        Pull your shoulders back and down
                        Brace your core
                        Walk with short, steady steps
                        Keep your torso upright
                        Do not let the weights swing
                        """),

                create("L-Sit", MuscleGroup.CORE, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Parallette Bars"),
                        """
                        Grip the bars and press down
                        Lift your hips off the floor
                        Straighten your legs in front of you
                        Keep your shoulders down and back
                        Hold with your legs parallel to the floor
                        Lower slowly when done
                        """),

                // 🔥 FULL BODY — 16 exercises
                create("Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with feet hip-width, bar over mid-foot
                        Hinge and grip the bar just outside your knees
                        Brace your core and flatten your back
                        Push the floor away to stand up
                        Keep the bar close to your legs
                        Hinge back to lower the bar
                        """),

                create("Sumo Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with your feet wide, toes out
                        Grip the bar inside your legs
                        Brace your core and flatten your back
                        Push the floor away to stand
                        Keep the bar close to your body
                        Hinge back to lower the bar
                        """),

                create("Power Clean", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with the bar over mid-foot
                        Hinge and grip just outside your knees
                        Pull the bar explosively upward
                        Shrug and pull yourself under the bar
                        Catch it at your shoulders in a quarter squat
                        Stand up and lower the bar
                        """),

                create("Clean and Press", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with the bar over mid-foot
                        Grip it just outside your knees
                        Pull the bar to your shoulders in one motion
                        Catch it at your collarbone
                        Brace and press it overhead
                        Lower the bar back to the floor
                        """),

                create("Snatch", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Stand with the bar over mid-foot
                        Grip it wide with your arms straight
                        Pull the bar explosively upward
                        Shrug and pull yourself under
                        Catch it locked out overhead
                        Stand up and lower the bar
                        """),

                create("Burpee", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CHEST, MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand tall with feet hip-width apart
                        Squat down and place your hands on the floor
                        Jump your feet back into a plank
                        Perform a push-up if you want
                        Jump your feet back to your hands
                        Stand and jump up, reaching overhead
                        """),

                create("Turkish Get-Up", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.CORE, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Kettlebell"),
                        """
                        Lie on your back with the bell over your shoulder
                        Keep your eyes on the bell
                        Roll to your elbow, then your hand
                        Lift your hips and sweep your leg back
                        Come to a half-kneel, then stand
                        Reverse the steps to lie back down
                        """),

                create("Thrusters", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        """
                        Hold the bar at your front shoulders
                        Stand with feet shoulder-width apart
                        Squat down with your chest up
                        Drive up and press the bar overhead
                        Lower the bar back to your shoulders
                        Repeat in one smooth motion
                        """),

                create("Wall Ball", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Medicine Ball"),
                        """
                        Hold the ball at your chest
                        Stand facing the wall a step away
                        Squat down with your chest up
                        Drive up and throw the ball at the wall
                        Catch it and absorb into the next squat
                        Keep a steady rhythm
                        """),

                create("Sled Push", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sled"),
                        """
                        Grip the sled handles at chest height
                        Lean forward with your arms straight
                        Drive through your legs to push
                        Keep your back flat
                        Take short, powerful steps
                        Keep your hips low
                        """),

                create("Sled Pull", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sled"),
                        """
                        Attach a rope or strap to the sled
                        Stand facing the sled with the rope in both hands
                        Lean back and brace your core
                        Walk backward, pulling the sled
                        Keep your arms extended
                        Take steady steps
                        """),

                create("Sandbag Carry", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.SHOULDERS, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sandbag"),
                        """
                        Lift the sandbag to your chest
                        Hug it close with both arms
                        Stand tall with your core braced
                        Walk with short, steady steps
                        Keep your chest up
                        Set it down with a flat back
                        """),

                create("Tire Flip", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Tire"),
                        """
                        Stand close to the tire with feet wide
                        Hinge and grip the bottom edge
                        Brace your core and keep your back flat
                        Drive with your legs to lift the tire
                        Push it over with your arms
                        Reset and repeat
                        """),

                create("Battle Ropes", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.CORE, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Battle Ropes"),
                        """
                        Hold one rope end in each hand
                        Stand with feet shoulder-width, knees soft
                        Brace your core and keep your chest up
                        Drive your arms up and down fast
                        Keep the waves reaching the anchor
                        Stay low for the whole set
                        """),

                create("Box Jump", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        """
                        Stand a step back from the box
                        Set your feet hip-width apart
                        Swing your arms back and bend your knees
                        Jump onto the box, landing softly
                        Stand up fully on the box
                        Step down and reset
                        """),

                create("Broad Jump", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        """
                        Stand with feet hip-width apart
                        Swing your arms back and bend your knees
                        Jump forward as far as you can
                        Land softly with bent knees
                        Absorb the landing with your hips back
                        Reset before the next rep
                        """),

                // 🔥 WARMUP — 35 exercises
                create("Jumping Jacks", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your arms at your sides
                        Jump your feet out wide
                        Raise your arms overhead at the same time
                        Jump your feet back together
                        Lower your arms to your sides
                        Keep a steady rhythm
                        """),

                create("Arm Circles", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your arms out to the sides
                        Keep your arms straight at shoulder height
                        Draw small circles forward
                        Increase the size gradually
                        Reverse direction halfway through
                        """, MeasurementType.DURATION),

                create("High Knees", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your arms bent
                        Drive one knee up to hip height
                        Alternate legs quickly
                        Stay on the balls of your feet
                        Keep your torso upright
                        Pump your arms with each step
                        """),

                create("Butt Kicks", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your arms at your sides
                        Jog in place
                        Kick your heels toward your glutes
                        Keep your torso upright
                        Stay light on your feet
                        Alternate legs at a steady pace
                        """),

                create("Mountain Climbers", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Start in a high plank
                        Keep your hips level with your shoulders
                        Drive one knee toward your chest
                        Switch legs quickly
                        Keep your hands under your shoulders
                        Stay light on your toes
                        """),

                create("Jump Rope", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Jump Rope"),
                        """
                        Hold the handles with your elbows close in
                        Turn the rope with your wrists
                        Jump just high enough to clear it
                        Land softly on the balls of your feet
                        Keep your torso upright
                        Hold a steady rhythm
                        """),

                create("Star Jumps", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand with feet hip-width apart
                        Bend your knees slightly
                        Jump up and spread your arms and legs
                        Land softly with bent knees
                        Bring your arms and feet back in
                        Keep a steady rhythm
                        """),

                create("Skater Hops", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand on one leg with a soft knee
                        Jump sideways onto the other leg
                        Land softly and absorb the impact
                        Swing the free leg behind you
                        Hop back to the start
                        Keep the movement rhythmic
                        """),

                create("Lateral Shuffles", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand with your knees bent and hips back
                        Stay low in an athletic stance
                        Shuffle sideways with quick steps
                        Keep your feet from crossing
                        Swing your arms naturally
                        Shuffle back to the start
                        """),

                create("Inchworms", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand with feet hip-width apart
                        Hinge and place your hands on the floor
                        Walk your hands forward to a plank
                        Hold for a moment
                        Walk your hands back to your feet
                        Stand up and repeat
                        """),

                create("Leg Swings (Forward)", MuscleGroup.LEGS, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Hold a wall or rack for balance
                        Stand on one leg
                        Swing the other leg forward and back
                        Keep your torso upright
                        Increase the range gradually
                        Switch legs after your reps
                        """),

                create("Leg Swings (Lateral)", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Hold a wall or rack for balance
                        Stand on one leg
                        Swing the other leg across your body and out
                        Keep your hips facing forward
                        Increase the range gradually
                        Switch legs after your reps
                        """),

                create("Hip Circles", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your hands on your hips
                        Keep your feet planted
                        Draw big circles with your hips
                        Move slowly through the full range
                        Complete your reps, then reverse direction
                        """),

                create("Torso Twists", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand with feet shoulder-width apart
                        Keep your hips facing forward
                        Swing your arms side to side
                        Let your torso rotate naturally
                        Keep the movement controlled
                        Increase the range gradually
                        """),

                create("Cat-Cow Stretch", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Start on all fours, wrists under shoulders
                        Inhale and drop your belly, lift your chest
                        Exhale and round your spine, tuck your chin
                        Move slowly with your breath for the whole hold
                        """, MeasurementType.DURATION),

                create("World's Greatest Stretch", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Step into a deep lunge
                        Place both hands inside your front foot
                        Drop your back knee and lift your chest
                        Rotate your inside arm toward the ceiling
                        Return your hand and switch sides
                        Move slowly with your breath
                        """, MeasurementType.DURATION),

                create("Thoracic Spine Rotation", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Kneel on all fours with one hand behind your head
                        Keep your hips square to the floor
                        Rotate your elbow toward the ceiling
                        Follow your elbow with your eyes
                        Return slowly and repeat on both sides
                        """, MeasurementType.DURATION),

                create("Neck Rolls", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Sit or stand tall with relaxed shoulders
                        Drop your chin toward your chest
                        Roll your head slowly to one side
                        Continue around to the other side
                        Move slowly through the full circle
                        Reverse direction halfway
                        """, MeasurementType.DURATION),

                create("Ankle Rolls", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand on one foot with the other lifted
                        Point your toes down
                        Draw circles with your foot
                        Move through the full range
                        Reverse direction halfway
                        Switch feet when done
                        """, MeasurementType.DURATION),

                create("Wrist Circles", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Hold your arms out in front of you
                        Make loose fists
                        Draw circles with your wrists
                        Move through the full range
                        Reverse direction halfway
                        Keep your forearms still
                        """, MeasurementType.DURATION),

                create("Glute Bridges", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with your knees bent
                        Place your feet flat, hip-width apart
                        Press through your heels to lift your hips
                        Squeeze your glutes at the top
                        Lower slowly without dropping
                        Keep your ribs down throughout
                        """),

                create("Scapular Push-Ups", MuscleGroup.CHEST, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Start in a high plank with straight arms
                        Keep your elbows locked for the whole set
                        Let your chest sink between your shoulder blades
                        Push the floor away to spread your shoulder blades
                        Move only a few centimetres each way
                        Keep your hips still
                        """),

                create("Band Pull-Aparts", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Resistance Band"),
                        """
                        Hold the band at shoulder height with straight arms
                        Keep a soft bend in your elbows
                        Pull the band apart until it reaches your chest
                        Return slowly and keep tension on the band
                        """),

                create("Dead Bugs", MuscleGroup.CORE, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with arms up and knees bent
                        Press your lower back into the mat
                        Lower one arm overhead and the opposite leg
                        Keep your back flat the whole time
                        Return and switch sides
                        Move slowly and breathe
                        """, MeasurementType.DURATION),

                create("Bird-Dog", MuscleGroup.CORE, List.of(MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Start on all fours with a flat back
                        Brace your core
                        Extend one arm forward and the opposite leg back
                        Keep your hips level
                        Hold without arching your back
                        Return and switch sides
                        """, MeasurementType.DURATION),

                create("Fire Hydrants", MuscleGroup.GLUTES, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Start on all fours with wrists under shoulders
                        Keep your core braced and back flat
                        Lift one knee out to the side
                        Stop when your thigh is parallel to the floor
                        Lower slowly and repeat on both sides
                        """),

                create("Bodyweight Squats", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand with feet shoulder-width apart
                        Point your toes slightly outward
                        Sit back and down with your chest up
                        Keep your heels flat on the floor
                        Descend until your thighs are near parallel
                        Stand up and squeeze your glutes
                        """),

                create("Walking Lunges", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your core braced
                        Step forward into a lunge
                        Lower until both knees are near 90 degrees
                        Push up and bring the back foot forward
                        Continue alternating legs
                        Keep your torso upright
                        """),

                create("Plank", MuscleGroup.CORE, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Set your forearms on the floor
                        Stack your elbows under your shoulders
                        Extend your legs and rise onto your toes
                        Squeeze your glutes and brace your core
                        Keep your hips level with your shoulders
                        Breathe steadily and hold
                        """, MeasurementType.DURATION),

                create("Downward Dog to Upward Dog", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        """
                        Start in a plank with hands under your shoulders
                        Lift your hips up and back into downward dog
                        Press your heels toward the floor
                        Lower your hips and lift your chest
                        Straighten your arms into upward dog
                        Flow between the two positions
                        """, MeasurementType.DURATION),

                create("Bear Crawl", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Start on all fours with knees off the floor
                        Keep your back flat and hips low
                        Move one hand and the opposite foot forward
                        Keep your hips from swaying
                        Crawl forward, then back
                        Stay low throughout
                        """),

                create("Hip Flexor March", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your core braced
                        Lift one knee toward your chest
                        Hold for a moment without leaning back
                        Lower slowly and switch legs
                        Keep your torso upright throughout
                        """),

                create("Skipping", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CALVES, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        """
                        Stand tall with your arms relaxed
                        Hop from one foot to the other
                        Keep the hops small and quick
                        Stay on the balls of your feet
                        Swing your arms naturally
                        Keep a steady rhythm
                        """),

                create("PVC Passthrough", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK, MuscleGroup.CHEST),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("PVC Pipe"),
                        """
                        Hold a PVC pipe with a wide grip
                        Raise it overhead with straight arms
                        Move it behind your head
                        Continue down to your lower back
                        Reverse the path back to the front
                        Keep your elbows straight throughout
                        """, MeasurementType.DURATION),

                create("Shoulder Passthrough", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK, MuscleGroup.CHEST),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Resistance Band"),
                        """
                        Hold a band with a wide grip
                        Raise it overhead with straight arms
                        Move it behind your head
                        Continue down to your lower back
                        Reverse the path back to the front
                        Keep your elbows straight throughout
                        """, MeasurementType.DURATION),

                // 🔥 STRETCHING — 35 exercises
                create("Chest Stretch", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand in a doorway or beside a rack
                        Place your forearm on the frame at shoulder height
                        Turn your chest away from your arm
                        Keep your shoulder down and back
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Doorway Chest Stretch", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand in a doorway with your feet together
                        Place both forearms on the frame at shoulder height
                        Step forward until you feel a stretch
                        Keep your shoulders down and chest up
                        Breathe and hold without bouncing
                        """, MeasurementType.DURATION),

                create("Hamstring Stretch", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Sit with one leg straight in front of you
                        Bend the other leg with your foot to your thigh
                        Keep your back straight
                        Hinge forward from your hips
                        Reach toward your toes
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Cross-Body Shoulder Stretch", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand or sit tall
                        Bring one arm across your chest
                        Hold it above the elbow with the other hand
                        Pull gently until you feel the stretch
                        Keep your shoulder down
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Overhead Triceps Stretch", MuscleGroup.TRICEPS, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand or sit tall
                        Reach one arm overhead and bend the elbow
                        Rest your hand between your shoulder blades
                        Use the other hand to pull the elbow gently
                        Keep your ribs down
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Neck Side Stretch", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Sit or stand tall with your shoulders relaxed
                        Tilt your head toward one shoulder
                        Let the opposite shoulder drop
                        Use your hand for a gentle pull if needed
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Upper Trap Stretch", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Sit or stand tall
                        Tilt your head toward one shoulder
                        Look slightly down and away
                        Rest your hand on your head for a gentle pull
                        Keep the opposite shoulder down
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Sleeper Stretch", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie on your side with the bottom arm out
                        Bend that elbow to 90 degrees
                        Use your top hand to press the forearm down
                        Keep your shoulder from rolling forward
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Child's Pose", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Kneel and sit back toward your heels
                        Walk your hands forward until your arms are straight
                        Rest your forehead on the mat
                        Breathe deeply and let your back widen
                        Hold without forcing your hips down
                        """, MeasurementType.DURATION),

                create("Seated Forward Fold", MuscleGroup.BACK, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Sit with both legs straight in front of you
                        Hinge at your hips, not your lower back
                        Reach toward your feet
                        Keep your spine long as you fold
                        Breathe and hold without bouncing
                        """, MeasurementType.DURATION),

                create("Seated Spinal Twist", MuscleGroup.BACK, List.of(MuscleGroup.CORE, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Sit with one leg crossed over the other
                        Place the opposite elbow outside your knee
                        Rotate your torso toward the bent knee
                        Keep your spine tall as you twist
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Supine Spinal Twist", MuscleGroup.BACK, List.of(MuscleGroup.CORE, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with arms out to the sides
                        Bend one knee and draw it across your body
                        Turn your head the opposite way
                        Keep both shoulders on the floor
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Cobra Stretch", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie face down with hands under your shoulders
                        Press your hips into the floor
                        Lift your chest by straightening your arms
                        Keep your shoulders down away from your ears
                        Breathe steadily and hold
                        """, MeasurementType.DURATION),

                create("Lat Stretch (Overhead Reach)", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand tall and reach one arm overhead
                        Lean away from the raised arm
                        Hold a wall or rack with the other hand
                        Feel the stretch along your side and lat
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Standing Side Bend", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand tall with feet hip-width apart
                        Reach one arm overhead
                        Bend your torso to the opposite side
                        Keep your hips facing forward
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Figure 4 Stretch (Seated)", MuscleGroup.GLUTES, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Sit on the floor with both knees bent
                        Cross one ankle over the opposite thigh
                        Sit tall and keep your back straight
                        Hinge forward until you feel a stretch
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Figure 4 Stretch (Supine)", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back with both knees bent
                        Cross one ankle over the opposite thigh
                        Reach through and hold behind the lower thigh
                        Pull the leg toward your chest
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Pigeon Pose", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Start on all fours
                        Bring one knee forward behind your wrist
                        Extend the other leg straight back
                        Square your hips toward the floor
                        Fold forward and breathe
                        Hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Happy Baby", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie on your back and bend your knees
                        Bring your knees toward your armpits
                        Hold the outsides of your feet
                        Gently rock side to side
                        Breathe and hold without forcing
                        """, MeasurementType.DURATION),

                create("Butterfly Stretch", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Sit with the soles of your feet together
                        Hold your feet with both hands
                        Let your knees fall toward the floor
                        Sit tall and hinge slightly forward
                        Breathe and hold without pressing hard
                        """, MeasurementType.DURATION),

                create("90/90 Stretch", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Sit with one leg bent in front at 90 degrees
                        Bend the other leg behind you at 90 degrees
                        Sit tall with your hands behind you
                        Lean forward over the front shin
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Kneeling Hip Flexor Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Kneel on one knee with the other foot forward
                        Keep your torso upright
                        Tuck your pelvis under
                        Shift your hips forward slightly
                        Feel the stretch at the front of the kneeling hip
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Standing Quad Stretch", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand tall and hold a wall for balance
                        Bend one knee and reach back for your foot
                        Keep your knees together
                        Press your hip forward
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Lying Quad Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie face down on the mat
                        Bend one knee and reach back for your foot
                        Keep your knees together
                        Press your hip into the floor
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Half-Kneeling Adductor Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Kneel on one knee with the other leg out to the side
                        Keep the extended leg's foot flat
                        Sit back slightly into the kneeling hip
                        Feel the stretch along your inner thigh
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Standing Forward Fold", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Stand with feet hip-width apart
                        Hinge at your hips with a soft knee bend
                        Let your hands hang toward the floor
                        Relax your neck and shoulders
                        Breathe and hold without bouncing
                        """, MeasurementType.DURATION),

                create("Seated Hamstring Stretch", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat", "Stretching Strap"),
                        """
                        Sit with one leg straight in front of you
                        Loop a strap around the ball of that foot
                        Keep your back straight and chest up
                        Pull the strap to hinge forward
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Lying Hamstring Stretch (Strap)", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Stretching Strap"),
                        """
                        Lie on your back with both legs straight
                        Loop the strap around one foot
                        Lift that leg toward the ceiling
                        Keep the other leg flat on the floor
                        Straighten the raised knee and pull gently
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Wide-Legged Forward Fold", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Stand with your feet wide apart
                        Turn your toes slightly outward
                        Hinge at your hips and fold forward
                        Let your hands rest on the floor or a block
                        Keep your spine long as you fold
                        Breathe and hold
                        """, MeasurementType.DURATION),

                create("Standing Calf Stretch", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand facing a wall with one foot back
                        Keep the back leg straight and heel down
                        Lean into the wall with both hands
                        Feel the stretch through your calf
                        Breathe and hold, then switch legs
                        """, MeasurementType.DURATION),

                create("Heel Drop (Step)", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Box"),
                        """
                        Stand with the balls of your feet on a box edge
                        Hold a wall or rack for balance
                        Let your heels sink below the box
                        Keep your knees straight but not locked
                        Breathe and hold, then step down slowly
                        """, MeasurementType.DURATION),

                create("Abdominal Stretch (Supine)", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie face down with your hands under your shoulders
                        Press your hips into the floor
                        Straighten your arms to lift your chest
                        Look slightly up and forward
                        Breathe and hold
                        Lower slowly when done
                        """, MeasurementType.DURATION),

                create("Side-Lying Oblique Stretch", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        """
                        Lie on your side with legs straight
                        Support your head with your lower arm
                        Reach your top arm overhead
                        Feel the stretch along your side
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Wrist Flexor Stretch", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Hold one arm out with the palm up
                        Bend your wrist so your fingers point down
                        Use the other hand to pull gently
                        Keep your elbow straight
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Wrist Extensor Stretch", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Hold one arm out with the palm down
                        Bend your wrist so your fingers point down
                        Use the other hand to pull gently
                        Keep your elbow straight
                        Breathe and hold, then switch sides
                        """, MeasurementType.DURATION),

                create("Garland Pose (Malasana)", MuscleGroup.LEGS, List.of(MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        """
                        Stand with your feet wider than your hips
                        Turn your toes outward
                        Squat down as deep as you can
                        Bring your palms together at your chest
                        Press your elbows against your knees
                        Breathe and hold
                        """, MeasurementType.DURATION),

                // 🔥 CARDIO — 12 exercises
                create("Treadmill Walk", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Treadmill"),
                        """
                        Set the treadmill to a flat or slight incline
                        Start at an easy walking pace
                        Stand tall and avoid holding the rails
                        Take steady, full steps
                        Keep your arms swinging naturally
                        Hold a pace you can sustain
                        """, MeasurementType.DURATION),

                create("Treadmill Run", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Treadmill"),
                        """
                        Start with a few minutes of walking
                        Increase to a comfortable running pace
                        Land mid-foot under your hips
                        Keep your shoulders relaxed
                        Hold a pace you can sustain
                        Cool down with a walk
                        """, MeasurementType.DURATION),

                create("Incline Treadmill Walk", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Treadmill"),
                        """
                        Set the treadmill to a moderate incline
                        Start at an easy walking pace
                        Stand tall and avoid holding the rails
                        Take steady, full steps
                        Keep your arms swinging naturally
                        Stay at a pace you can hold
                        """, MeasurementType.DURATION),

                create("Stationary Bike", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Stationary Bike"),
                        """
                        Adjust the seat so your knee has a slight bend
                        Start pedalling at an easy pace
                        Keep your back relaxed and shoulders down
                        Grip the bars lightly
                        Hold a steady rhythm
                        Keep your breathing even
                        """, MeasurementType.DURATION),

                create("Spin Bike HIIT", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Spin Bike"),
                        """
                        Set the seat so your knee has a slight bend
                        Warm up with easy pedalling
                        Alternate hard efforts with easy recovery
                        Stay seated and keep your hips still
                        Grip the bars lightly
                        Keep your breathing controlled
                        """, MeasurementType.DURATION),

                create("Recumbent Bike", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Recumbent Bike"),
                        """
                        Adjust the seat so your knee has a slight bend
                        Start pedalling at an easy pace
                        Keep your back against the seat
                        Relax your shoulders and grip lightly
                        Hold a pace you can sustain
                        Keep your breathing steady
                        """, MeasurementType.DURATION),

                create("Elliptical Training", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Elliptical Trainer"),
                        """
                        Step on and start at an easy pace
                        Stand tall with a light grip on the handles
                        Push and pull with your arms
                        Keep your whole foot on the pedals
                        Hold a steady rhythm
                        Keep your breathing even
                        """, MeasurementType.DURATION),

                create("Rowing Machine", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Rowing Machine"),
                        """
                        Set the damper to a moderate level
                        Drive with your legs first
                        Lean back slightly, then pull the handle
                        Return the handle, then bend your knees
                        Keep your back straight throughout
                        Hold a steady rhythm
                        """, MeasurementType.DURATION),

                create("Rowing Sprint Intervals", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Rowing Machine"),
                        """
                        Strap in and set a moderate damper
                        Warm up with easy strokes
                        Pull hard for your work interval
                        Ease off for the recovery interval
                        Keep your form as you tire
                        Repeat for your rounds
                        """, MeasurementType.DURATION),

                create("Stair Climber", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Stair Climber"),
                        """
                        Step on at an easy pace
                        Stand tall and avoid leaning on the rails
                        Take full steps, not tiny shuffles
                        Keep your whole foot on each step
                        Hold a pace you can sustain
                        Keep your breathing steady
                        """, MeasurementType.DURATION),

                create("Assault Bike Sprint", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("Assault Bike"),
                        """
                        Set the seat so your knee has a slight bend
                        Start pedalling at an easy pace
                        Push hard with your arms and legs
                        Keep your torso upright
                        Hold the effort, then ease off
                        Repeat for your rounds
                        """, MeasurementType.DURATION),

                create("SkiErg", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.CARDIO, ExerciseCategory.WARMUP,
                        List.of("SkiErg"),
                        """
                        Grip the handles above your head
                        Stand with feet hip-width, knees soft
                        Pull the handles down past your hips
                        Hinge forward as you pull
                        Return your arms overhead
                        Keep a steady rhythm
                        """, MeasurementType.DURATION),

                // 🔥 FREE WEIGHTS (NEW) — 2 exercises
                create("Trap Bar Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Trap Bar"),
                        """
                        Step inside the trap bar
                        Hinge and grip both handles
                        Brace your core and flatten your back
                        Push the floor away to stand tall
                        Keep your arms straight throughout
                        Lower with control to the floor
                        """),

                create("Trap Bar Shrug", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Trap Bar"),
                        """
                        Stand inside the trap bar and grip the handles
                        Stand tall with your arms straight
                        Keep your core braced
                        Lift your shoulders toward your ears
                        Pause at the top
                        Lower slowly with control
                        """),

                // 🔥 RACKS & MACHINES (NEW) — 4 exercises
                create("Pin Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Power Rack"),
                        """
                        Set the pins at about knee height
                        Stand with the bar on your back
                        Lower until the bar rests on the pins
                        Pause without relaxing
                        Drive up through your heels
                        Keep your chest up the whole rep
                        """),

                create("Spoto Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Power Rack", "Bench"),
                        """
                        Set the pins just above your chest
                        Lie on the bench and unrack the bar
                        Lower the bar toward your chest
                        Pause one inch above your chest
                        Keep tension without touching down
                        Press back up to straight arms
                        """),

                create("Straight-Arm Pulldown (Machine)", MuscleGroup.BACK, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Lat Pulldown Machine"),
                        """
                        Stand facing the machine with feet hip-width
                        Grip the bar with your arms extended
                        Hinge slightly and brace your core
                        Pull the bar down to your thighs
                        Squeeze your lats at the bottom
                        Return slowly without shrugging
                        """),

                create("Depth Jump", MuscleGroup.LEGS, List.of(MuscleGroup.CALVES, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Plyometric Box"),
                        """
                        Stand on a box with feet hip-width
                        Step off and land softly on both feet
                        Absorb the landing with bent knees
                        Jump straight up as high as you can
                        Land softly again and reset
                        Keep each rep crisp and controlled
                        """),

                // 🔥 BANDS (NEW) — 4 exercises
                create("Banded Overhead Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Resistance Tube"),
                        """
                        Stand on the band with feet hip-width
                        Hold the handles at shoulder height
                        Brace your core and squeeze your glutes
                        Press the handles overhead
                        Stop before your lower back arches
                        Lower slowly to your shoulders
                        """),

                create("Mini Band Lateral Walk", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Mini Band"),
                        """
                        Place a mini band just above your knees
                        Stand with feet hip-width and knees soft
                        Sit back slightly into a quarter squat
                        Step sideways to keep tension on the band
                        Take small steps and stay low
                        Lead with the same leg each direction
                        """),

                create("Clamshell", MuscleGroup.GLUTES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Mini Band"),
                        """
                        Lie on your side with your knees bent
                        Place a mini band just above your knees
                        Stack your hips and shoulders
                        Keep your feet together
                        Lift your top knee against the band
                        Lower slowly and repeat on both sides
                        """),

                create("Banded Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Loop Band"),
                        """
                        Place a loop band just above your knees
                        Stand with feet shoulder-width apart
                        Push your knees out against the band
                        Sit back and down into a squat
                        Keep your chest up and heels down
                        Stand up and squeeze your glutes
                        """),

                // 🔥 GYMNASTIC RINGS — 3 exercises
                create("Ring Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        """
                        Set the rings just above the floor
                        Grip the rings and get into a plank
                        Keep your body straight from head to heels
                        Lower your chest between the rings
                        Press up while keeping the rings steady
                        Move slowly to control the rings
                        """),

                create("Ring Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        """
                        Hang the rings at hip height
                        Grip the rings and walk your feet forward
                        Keep your body in a straight line
                        Pull your chest to the rings
                        Squeeze your shoulder blades at the top
                        Lower slowly to straight arms
                        """),

                create("Ring Dip", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        """
                        Set the rings at hip height
                        Grip the rings and lift yourself up
                        Keep your torso upright
                        Lower until your elbows reach 90 degrees
                        Press back up to straight arms
                        Keep the rings close to your body
                        """),

                // 🔥 TRX — 3 exercises
                create("TRX Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        """
                        Hold the handles and lean back
                        Walk your feet forward to set the angle
                        Keep your body straight from head to heels
                        Pull your chest up to the handles
                        Squeeze your shoulder blades at the top
                        Lower slowly to straight arms
                        """),

                create("TRX Chest Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        """
                        Hold the handles and lean forward
                        Walk your feet back to set the angle
                        Keep your body straight from head to heels
                        Lower your chest between the handles
                        Press back up until your arms are straight
                        Keep your core braced throughout
                        """),

                create("TRX Pike", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        """
                        Place your feet in the straps
                        Start in a plank on your hands
                        Keep your core braced
                        Lift your hips toward the ceiling
                        Keep your legs straight
                        Lower slowly to a plank
                        """),

                // 🔥 FUNCTIONAL (NEW) — 4 exercises
                create("Farmer's Walk (Heavy)", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Farmer's Walk Handles"),
                        """
                        Grip a heavy handle in each hand
                        Stand tall with your shoulders back
                        Brace your core and lift your chest
                        Walk with short, controlled steps
                        Keep your torso upright
                        Set the weight down with a flat back
                        """),

                create("Agility Ladder Drill", MuscleGroup.LEGS, List.of(MuscleGroup.CALVES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Agility Ladder"),
                        """
                        Lay the ladder flat on the floor
                        Start at one end with your knees soft
                        Step one foot into each square
                        Stay light and quick on your feet
                        Keep your arms moving with your legs
                        Move through the ladder, then walk back
                        """),

                create("Cone Shuffle Drill", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Cone"),
                        """
                        Set cones a few steps apart
                        Stand in an athletic stance
                        Shuffle between the cones
                        Stay low with your hips back
                        Keep your feet from crossing
                        Keep your head up throughout
                        """),

                create("Elevated Step-Up", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Platforms"),
                        """
                        Place one foot fully on an elevated platform
                        Keep the other foot on the floor
                        Drive through your top heel to stand up
                        Stand tall without pushing off the back foot
                        Lower slowly until your back foot touches
                        Complete your reps, then switch legs
                        """),

                // 🔥 MOBILITY & RECOVERY (NEW) — 5 exercises
                create("Foam Rolling (Upper Back)", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        """
                        Lie back with the roller under your upper back
                        Support your head with both hands
                        Lift your hips slightly off the floor
                        Roll slowly from your shoulder blades to mid back
                        Pause and breathe on the tight spots
                        """, MeasurementType.DURATION),

                create("Foam Rolling (Quads)", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        """
                        Lie face down with the roller under one thigh
                        Support yourself on your forearms
                        Roll slowly from your hip to above your knee
                        Pause and breathe on tight spots
                        Keep your core braced throughout
                        Switch legs when done
                        """, MeasurementType.DURATION),

                create("Foam Rolling (IT Band)", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        """
                        Lie on your side with the roller under your outer thigh
                        Support yourself on your forearm
                        Roll from your hip to just above your knee
                        Pause and breathe on tight spots
                        Keep your top leg steady
                        Switch sides when done
                        """, MeasurementType.DURATION),

                create("Percussive Massage", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Massage Gun"),
                        """
                        Set the gun to a low speed
                        Apply it to the muscle belly, not bone
                        Move slowly over the area
                        Keep the pressure light
                        Spend about a minute per spot
                        Avoid sore or injured areas
                        """, MeasurementType.DURATION),

                create("Lacrosse Ball Release", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Lacrosse Ball"),
                        """
                        Place the ball on a tight spot
                        Lean your body weight onto it
                        Hold until the tension eases
                        Move the ball slightly to the next spot
                        Keep your breathing slow
                        Repeat on both sides
                        """, MeasurementType.DURATION),

                // 🔥 SWIMMING POOL — 2 exercises
                create("Freestyle Swimming", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Swimming Pool"),
                        """
                        Start with a steady kick from the wall
                        Reach one arm forward at a time
                        Rotate your body with each stroke
                        Breathe to the side every few strokes
                        Keep your hips high in the water
                        Hold a pace you can sustain
                        """),

                create("Pool Running", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Swimming Pool"),
                        """
                        Stand in water at chest depth
                        Lean forward slightly
                        Run in place with high knees
                        Pump your arms with each step
                        Keep your feet off the bottom
                        Hold a steady pace
                        """)
        );

        // Attach equipment
        exercises.forEach(exercise -> {
            List<Equipment> eqList = exercise.getEquipments().stream()
                    .map(eq -> equipmentMap.get(eq.getName()))
                    .toList();
            exercise.setEquipments(eqList);
        });

        Map<String, Exercise> existingByName = exerciseRepository.findAll().stream()
                .collect(Collectors.toMap(Exercise::getName, e -> e));

        List<Exercise> toSave = new ArrayList<>();

        for (Exercise exercise : exercises) {
            Exercise existing = existingByName.get(exercise.getName());
            if (existing == null) {
                toSave.add(exercise);
            } else {
                existing.setPrimaryMuscle(exercise.getPrimaryMuscle());
                existing.setSecondaryMuscles(exercise.getSecondaryMuscles());
                existing.setDifficultyLevel(exercise.getDifficultyLevel());
                existing.setExerciseType(exercise.getExerciseType());
                existing.setCategory(exercise.getCategory());
                existing.setMeasurementType(exercise.getMeasurementType());
                existing.setInstructions(exercise.getInstructions());
                existing.setEquipments(exercise.getEquipments());
                toSave.add(existing);
            }
        }

        if (toSave.isEmpty()) {
            log.info("Exercises already seeded");
            return;
        }

        log.info("Saving/updating {} exercises...", toSave.size());
        exerciseRepository.saveAll(toSave);

        log.info("Exercises seeded successfully");
    }

    private Exercise create(String name,
                            MuscleGroup primary,
                            List<MuscleGroup> secondary,
                            DifficultyLevel difficulty,
                            ExerciseType type,
                            ExerciseCategory category,
                            List<String> equipmentNames,
                            String instructions) {
        return create(name, primary, secondary, difficulty, type, category,
                equipmentNames, instructions, MeasurementType.REPS);
    }

    private Exercise create(String name,
                            MuscleGroup primary,
                            List<MuscleGroup> secondary,
                            DifficultyLevel difficulty,
                            ExerciseType type,
                            ExerciseCategory category,
                            List<String> equipmentNames,
                            String instructions,
                            MeasurementType measurementType) {

        Exercise e = new Exercise();
        e.setName(name);
        e.setPrimaryMuscle(primary);
        e.setSecondaryMuscles(secondary);
        e.setDifficultyLevel(difficulty);
        e.setExerciseType(type);
        e.setCategory(category);
        e.setMeasurementType(measurementType);
        e.setInstructions(
                instructions == null ? null : instructions.strip().replace("\r\n", "\n"));

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
