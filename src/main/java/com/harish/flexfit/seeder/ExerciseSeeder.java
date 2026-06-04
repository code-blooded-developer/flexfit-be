package com.harish.flexfit.seeder;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

        log.info("Seeding exercises...");

        Map<String, Equipment> equipmentMap = equipmentRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Equipment::getName, e -> e));

        List<Exercise> exercises = List.of(

                // 🔥 CHEST — 23 exercises
                create("Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Start in a high plank with hands shoulder-width apart. Lower your chest toward the floor, keeping elbows at 45 degrees. Push back up."),

                create("Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        "Lie on a flat bench, grip the bar slightly wider than shoulder-width. Lower the bar to your chest, then press back up."),

                create("Incline Barbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Adjustable Bench"),
                        "Set the bench to 30-45 degrees. Press the barbell from the upper chest, keeping elbows slightly tucked."),

                create("Incline Dumbbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        "Lie on an incline bench holding dumbbells at chest height. Press the weights up until arms are extended."),

                create("Decline Barbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        "Lie on a decline bench, secure your legs. Lower the bar to the lower chest and press back up."),

                create("Decline Dumbbell Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Lie on a decline bench holding dumbbells. Press upward from the lower chest."),

                create("Dumbbell Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Lie on a flat bench holding dumbbells at chest height. Press them upward, bringing them together at the top."),

                create("Dumbbell Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Lie on a flat bench with dumbbells extended above your chest. Lower the weights out to the sides in a wide arc, then squeeze back up."),

                create("Incline Dumbbell Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        "Lie on an incline bench with dumbbells extended above the upper chest. Open arms wide in an arc and squeeze back together."),

                create("Cable Crossover", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        "Stand between two high pulleys, grab each handle. Lean forward slightly and bring hands together in front of your chest."),

                create("Low-to-High Cable Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        "Set both pulleys to the lowest setting. Grab the handles and pull upward and inward, crossing hands at the top."),

                create("High-to-Low Cable Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Crossover Machine"),
                        "Set both pulleys to the highest setting. Pull handles downward and inward, crossing at the bottom."),

                create("Chest Press Machine", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Chest Press Machine"),
                        "Sit in the chest press machine with handles at chest level. Press forward until arms are extended, then return with control."),

                create("Pec Deck Fly", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pec Deck Machine"),
                        "Sit in the pec deck with forearms against the pads. Squeeze your arms together in front of your chest."),

                create("Decline Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        "Place your feet on a box and hands on the floor. Lower your chest toward the ground and push back up."),

                create("Wide Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Place hands wider than shoulder-width. Lower your chest to the floor and push back up."),

                create("Diamond Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        "Place hands close together forming a diamond shape under your chest. Lower and push back up."),

                create("Dip (Chest)", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dip Station"),
                        "Grip the dip bars and lean forward. Lower your body until shoulders are below elbows, then press back up."),

                create("Svend Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Weight Plate"),
                        "Hold a weight plate at chest height with palms pressed together. Press the plate forward until arms are straight."),

                create("Landmine Press", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Landmine", "Weight Plate"),
                        "Position a barbell in a landmine attachment. Press the barbell upward and slightly across your body."),

                create("Hex Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Lie on a flat bench holding dumbbells together at chest height. Press straight up, keeping the weights touching."),

                create("Smith Machine Bench Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine", "Bench"),
                        "Position a flat bench inside the Smith machine. Unrack the bar and lower to the chest, then press back up."),

                create("Dumbbell Pullover", MuscleGroup.CHEST, List.of(MuscleGroup.BACK, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Lie across a bench with only upper back supported. Hold a dumbbell above your chest, then lower it back behind your head."),

                // 🔥 BACK — 17 exercises
                create("Pull Up", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        "Hang from a pull-up bar with an overhand grip. Pull yourself up until your chin is over the bar, then lower with control."),

                create("Chin Up", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        "Hang from a bar with an underhand grip. Pull your chin to the bar, focusing on the biceps and lats."),

                create("Wide-Grip Pull Up", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        "Take a wide overhand grip on the bar. Pull up, driving elbows down and back."),

                create("Lat Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Sit at the lat pulldown machine with a wide grip. Pull the bar down to your upper chest, squeezing the lats."),

                create("Close-Grip Lat Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Use a close-grip handle on the lat pulldown. Pull to your upper chest, keeping elbows close to the body."),

                create("Barbell Row (Overhand)", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Hinge forward with a flat back, holding the bar with an overhand grip. Pull the bar to your lower ribcage."),

                create("Barbell Row (Underhand)", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Hinge forward with a flat back using an underhand grip. Pull the bar to your belly button."),

                create("Dumbbell Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Place one knee and hand on a bench. Row a dumbbell to your hip, squeezing the lat."),

                create("Seated Cable Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Seated Row Machine"),
                        "Sit at the row machine with feet braced. Pull the handle to your torso, squeezing your shoulder blades together."),

                create("Single-Arm Cable Row", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand facing a cable machine with a single handle. Row the handle past your hip, rotating your torso slightly."),

                create("T-Bar Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Landmine", "Weight Plate"),
                        "Straddle a landmine bar with a V-grip. Pull the bar toward your chest, keeping your back flat."),

                create("Straight-Arm Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand facing a high pulley with a straight bar. Keeping arms straight, pull the bar down to your thighs."),

                create("Face Pull", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Set a rope attachment at upper-chest height. Pull toward your face, separating the rope and squeezing the rear delts."),

                create("Inverted Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        "Set a barbell in the Smith machine at hip height. Hang underneath and pull your chest to the bar."),

                create("Hyperextension", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Hyperextension Bench"),
                        "Position yourself face down on the bench. Hinge at the hips, lowering your torso, then raise back to parallel."),

                create("Back Extension", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Back Extension Bench"),
                        "Secure your feet on the back extension bench. Lower your torso and extend back up, squeezing the lower back."),

                create("Rack Pull", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Squat Rack"),
                        "Set the barbell at knee height in a squat rack. Hinge and pull the bar upward, extending through the hips."),

                // 🔥 SHOULDERS — 17 exercises
                create("Overhead Press (Barbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Stand with the bar at shoulder height, grip just wider than shoulders. Press the bar overhead, keeping your core tight."),

                create("Dumbbell Shoulder Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        "Sit on an upright bench holding dumbbells at shoulder height. Press them overhead."),

                create("Arnold Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Hold dumbbells in front of your shoulders with palms facing you. Press overhead while rotating palms outward."),

                create("Lateral Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand with dumbbells at your sides. Raise them out to the sides until they reach shoulder height."),

                create("Cable Lateral Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand sideways to a low pulley. Raise the handle out to the side to shoulder height."),

                create("Front Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand holding dumbbells in front of your thighs. Raise them forward to shoulder height."),

                create("Cable Front Raise", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Face away from a low pulley with the handle between your legs. Raise the handle forward to shoulder height."),

                create("Reverse Fly (Dumbbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Hinge forward holding dumbbells. Raise them out to the sides, squeezing the rear delts."),

                create("Reverse Fly (Cable)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand facing a crossover machine, grab the opposite handles. Pull them back and out, squeezing the rear delts."),

                create("Upright Row (Barbell)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Hold a barbell at arm's length in front of you. Pull it up to your chin, leading with your elbows."),

                create("Upright Row (Cable)", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Attach a straight bar to a low cable. Pull the bar up toward your chin."),

                create("Shoulder Press Machine", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Shoulder Press Machine"),
                        "Sit in the shoulder press machine with handles at shoulder height. Press overhead."),

                create("Lateral Raise Machine", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Lateral Raise Machine"),
                        "Sit in the lateral raise machine with arms against the pads. Lift your arms out to the sides."),

                create("Push Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Dip your knees slightly, then drive the bar overhead using leg drive. A more explosive overhead press."),

                create("Single-Arm Dumbbell Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand holding one dumbbell at shoulder height. Press it overhead, engaging your core to prevent leaning."),

                create("Pike Push Up", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Get into a downward dog position. Bend your elbows and lower your head toward the floor, then press back up."),

                create("Handstand Push Up", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Kick up into a handstand against a wall. Lower yourself until your head touches the floor, then press back up."),

                // 🔥 BICEPS — 14 exercises
                create("Barbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        "Stand holding an EZ bar with an underhand grip. Curl the bar toward your shoulders, keeping elbows pinned to your sides."),

                create("Dumbbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand holding dumbbells at your sides. Curl them toward your shoulders, supinating your palms at the top."),

                create("Hammer Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand holding dumbbells with a neutral grip. Curl the weights toward your shoulders without rotating your palms."),

                create("Reverse Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        "Hold an EZ bar with an overhand grip. Curl the bar toward your shoulders, targeting the brachialis and forearms."),

                create("Preacher Curl (Barbell)", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar", "Preacher Curl Bench"),
                        "Sit at a preacher bench with your upper arms against the pad. Curl the EZ bar toward your shoulders."),

                create("Preacher Curl (Dumbbell)", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Preacher Curl Bench"),
                        "Use the preacher bench with one dumbbell at a time. Curl the weight toward your shoulder."),

                create("Cable Bicep Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand facing a low pulley with a straight bar. Curl the bar toward your shoulders, squeezing the biceps at the top."),

                create("Cable Hammer Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Attach a rope to a low pulley. Pull the rope toward your shoulders with a neutral grip."),

                create("Concentration Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Sit on a bench with legs spread. Rest your elbow against your inner thigh and curl the dumbbell toward your shoulder."),

                create("Incline Dumbbell Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        "Lie back on an incline bench holding dumbbells. Let them hang straight down, then curl toward your shoulders."),

                create("Spider Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Adjustable Bench"),
                        "Lie face down on an incline bench holding dumbbells. Curl the weights toward your shoulders."),

                create("Biceps Curl Machine", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Biceps Curl Machine"),
                        "Sit in the biceps curl machine with your elbows on the pad. Curl the handles toward your shoulders."),

                create("21s Curl", MuscleGroup.BICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar"),
                        "Do 7 curls from the bottom to halfway, 7 from halfway to the top, and 7 full-range curls in one set."),

                create("Zottman Curl", MuscleGroup.BICEPS, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Curl the dumbbells with palms up, then rotate to palms down and lower slowly."),

                // 🔥 TRICEPS — 12 exercises
                create("Tricep Pushdown", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Attach a straight bar to a high pulley. Push the bar down until arms are straight, keeping elbows at your sides."),

                create("Overhead Tricep Extension (Dumbbell)", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Hold one dumbbell with both hands overhead. Lower it behind your head by bending your elbows, then extend back up."),

                create("Overhead Tricep Extension (Cable)", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Attach a rope to a high pulley. Face away from the machine, bring the rope behind your head, and extend your arms overhead."),

                create("Close-Grip Bench Press", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        "Lie on a flat bench with hands close together on the bar. Lower the bar to your lower chest, keeping elbows tucked."),

                create("Skull Crushers", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("EZ Bar", "Bench"),
                        "Lie on a bench holding an EZ bar above your chest. Lower the bar toward your forehead, then extend back up."),

                create("Dip (Tricep)", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dip Station"),
                        "Grip the dip bars with your torso upright. Lower your body and press back up, keeping elbows pointed back."),

                create("Bench Dip", MuscleGroup.TRICEPS, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Bench"),
                        "Place your hands on a bench behind you with feet on the floor. Lower your body by bending your elbows, then push back up."),

                create("Tricep Kickback", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Hinge forward with a dumbbell in hand. Extend your arm straight back, squeezing the tricep at the top."),

                create("Cable Overhead Extension", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Attach a rope to a high pulley and face away. Extend your arms overhead, squeezing the triceps."),

                create("Triceps Pushdown Machine", MuscleGroup.TRICEPS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Triceps Pushdown Machine"),
                        "Sit in the triceps pushdown machine and press the handles down until arms are fully extended."),

                create("JM Press", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        "Lie on a bench, lower the bar to your throat area by drifting your elbows back, then press back up."),

                // 🔥 LEGS — 19 exercises
                create("Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Rack the bar on your upper back. Squat down to at least parallel, then drive through your heels to stand."),

                create("Lunges", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Step forward into a lunge with both knees at 90 degrees. Push off the front foot to return to standing. Alternate legs."),

                create("Leg Press", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        "Sit in the leg press machine with feet shoulder-width apart. Lower the platform until knees are at 90 degrees, then press back up."),

                create("Leg Extension", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Extension Machine"),
                        "Sit in the leg extension machine. Extend your legs fully, squeezing the quads at the top."),

                create("Hack Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Hack Squat Machine"),
                        "Stand on the hack squat platform with shoulders under the pads. Lower into a squat, then press back up."),

                create("Bulgarian Split Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Place one foot on a bench behind you. Lower into a lunge with the front leg, then drive back up."),

                create("Goblet Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Hold a dumbbell at your chest. Squat down, keeping the weight close and your elbows between your knees."),

                create("Front Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Squat Rack"),
                        "Rack the bar on your front shoulders with elbows high. Squat down and drive back up, keeping the torso upright."),

                create("Box Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell", "Box", "Squat Rack"),
                        "Squat down to a box or bench, pause briefly, then drive back up explosively."),

                create("Pistol Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Stand on one leg with the other extended forward. Lower into a single-leg squat, then push back up."),

                create("Reverse Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Step backward into a lunge, both knees at 90 degrees. Drive through the front heel to return to standing. Alternate legs."),

                create("Side Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Step out to the side, bending the stepping knee while keeping the other leg straight. Push back to center."),

                create("Curtsy Lunge", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Step one leg behind and across the other. Lower into a lunge, then return to standing. Alternate legs."),

                create("Step Up", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        "Stand facing a box. Step up with one foot and drive through the heel to bring the other foot up. Step down and alternate."),

                create("Sissy Squat", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        "Stand with feet hip-width apart. Lean back while bending your knees, keeping your torso and thighs in line."),

                create("Smith Machine Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        "Position the bar on your upper back in the Smith machine. Squat to parallel and press back up."),

                create("Single-Leg Leg Press", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        "Place one foot on the leg press platform. Lower and press back up using only that leg."),

                create("Hip Adductor Machine", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hip Adductor Machine"),
                        "Sit in the adductor machine with legs apart. Squeeze your legs together against the resistance."),

                create("Hip Abductor Machine", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hip Abductor Machine"),
                        "Sit in the abductor machine with legs together. Push your legs apart against the pads."),

                // 🔥 GLUTES & HAMSTRINGS — 14 exercises
                create("Romanian Deadlift", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Hold a barbell at hip height. Hinge at the hips, pushing them back while keeping the bar close to your legs. Squeeze the glutes to return."),

                create("Hip Thrust", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Barbell", "Bench"),
                        "Sit on the floor with upper back against a bench and a barbell across your hips. Drive your hips up by squeezing the glutes."),

                create("Glute Ham Raise", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Glute Ham Developer"),
                        "Position yourself face down on the GHD. Lower your torso slowly, then raise back up using the hamstrings and glutes."),

                create("Cable Pull-Through", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Face away from a low pulley with the handle between your legs. Hinge forward and drive your hips through, squeezing the glutes."),

                create("Kettlebell Swing", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Kettlebell"),
                        "Stand with feet wider than hips, holding a kettlebell with both hands. Hinge back and swing the bell up to chest height by driving the hips forward."),

                create("Single-Leg Romanian Deadlift", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand on one leg holding a dumbbell in the opposite hand. Hinge forward, keeping the back flat, and return."),

                create("Standing Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        "Stand in the leg curl machine. Curl the pad toward your glutes by bending your knee."),

                create("Lying Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        "Lie face down on the leg curl machine. Curl the pad toward your glutes."),

                create("Seated Leg Curl", MuscleGroup.HAMSTRINGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Curl Machine"),
                        "Sit in the seated leg curl machine. Curl the pad down by flexing your knees."),

                create("Nordic Hamstring Curl", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        "Kneel with your ankles secured. Slowly lower your torso toward the floor, resisting with your hamstrings."),

                create("Reverse Hyperextension", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Hyperextension Bench"),
                        "Lie face down on a hyperextension bench. Lift your legs back and up, squeezing the glutes."),

                create("Dumbbell Hip Thrust", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell", "Bench"),
                        "Sit on the floor with a dumbbell across your hips and upper back against a bench. Drive hips upward."),

                create("Cable Hip Adduction", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand sideways to a low pulley with the cuff on your outer ankle. Adduct your leg across your body."),

                create("Cable Hip Abduction", MuscleGroup.GLUTES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand sideways to a low pulley with the cuff on your inner ankle. Abduct your leg away from your body."),

                // 🔥 CALVES — 6 exercises
                create("Standing Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Calf Raise Machine"),
                        "Stand in the calf raise machine with shoulders under the pads. Rise up on your toes, hold briefly, then lower."),

                create("Seated Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Barbell", "Box"),
                        "Sit on a box with a barbell across your knees. Rise up on your toes, then lower."),

                create("Leg Press Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Leg Press Machine"),
                        "Sit in the leg press with only the balls of your feet on the platform. Press through your toes to raise the platform."),

                create("Single-Leg Standing Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Stand on one foot holding a dumbbell. Rise up on your toes, then lower."),

                create("Donkey Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of(),
                        "Bend forward at the hips with hands on a bench. Have a partner sit on your lower back. Rise up on your toes."),

                create("Smith Machine Calf Raise", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Smith Machine"),
                        "Position the Smith machine bar on your upper back. Rise up on your toes and lower."),

                // 🔥 CORE — 16 exercises
                create("Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        "Lie on your back with knees bent and hands behind your head. Curl your shoulders off the mat, squeezing your abs."),

                create("Reverse Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        "Lie on your back with legs raised. Curl your hips off the mat, bringing your knees toward your chest."),

                create("Leg Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        "Lie on your back with legs straight. Raise them to 90 degrees, then lower slowly without arching your back."),

                create("Hanging Knee Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        "Hang from a bar. Raise your knees toward your chest, then lower with control."),

                create("Hanging Leg Raise", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Pull-up Bar"),
                        "Hang from a bar. Raise your straight legs to parallel with the ground, then lower slowly."),

                create("Russian Twist", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Yoga Mat", "Medicine Ball"),
                        "Sit with knees bent and feet off the floor. Rotate your torso side to side, tapping the medicine ball on the ground."),

                create("Cable Woodchop", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Set a cable to shoulder height. Rotate your torso, pulling the handle diagonally across your body."),

                create("Cable Reverse Woodchop", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Set a cable to low height. Rotate your torso, pulling the handle diagonally upward across your body."),

                create("Ab Wheel Rollout", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BACK),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Ab Wheel"),
                        "Kneel on the floor holding an ab wheel. Roll forward, extending your body, then pull back using your core."),

                create("Pallof Press", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Cable Machine"),
                        "Stand sideways to a cable machine at chest height. Press the handle forward and resist the rotation."),

                create("Bicycle Crunch", MuscleGroup.CORE, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        "Lie on your back with hands behind your head. Bring opposite elbow to knee in a pedaling motion."),

                create("Medicine Ball Slam", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Slam Ball"),
                        "Stand holding a slam ball overhead. Slam it down to the floor as hard as possible, squatting as you follow through."),

                create("V-Up", MuscleGroup.CORE, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Yoga Mat"),
                        "Lie flat on the mat. Simultaneously raise your legs and torso to form a V shape, reaching toward your feet."),

                create("Dragon Flag", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Bench"),
                        "Lie on a bench and grip it behind your head. Raise your entire body into a straight line, then lower slowly."),

                create("Farmer's Walk", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Dumbbell"),
                        "Hold a heavy dumbbell in each hand. Walk upright with a braced core for a set distance or time."),

                create("L-Sit", MuscleGroup.CORE, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Parallette Bars"),
                        "Support yourself on parallel bars or parallettes. Lift your legs to parallel with the ground and hold."),

                // 🔥 FULL BODY — 16 exercises
                create("Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Stand with the bar over midfoot. Hinge down, grip the bar, and drive through your heels to stand upright."),

                create("Sumo Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Take a wide stance with toes pointed out. Grip the bar between your legs and drive up."),

                create("Power Clean", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Explosively pull the bar from the floor, catch it on your front shoulders in a quarter squat, then stand."),

                create("Clean and Press", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Clean the bar to your shoulders, then press it overhead in one fluid motion."),

                create("Snatch", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Pull the bar from the floor to overhead in one explosive motion."),

                create("Burpee", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CHEST, MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Drop into a squat, kick feet back into a plank, do a push up, jump feet forward, and explode upward."),

                create("Turkish Get-Up", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.CORE, MuscleGroup.LEGS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Kettlebell"),
                        "Lie down holding a kettlebell overhead. Stand up while keeping the weight locked out, then reverse the motion."),

                create("Thrusters", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Barbell"),
                        "Hold the bar at your front shoulders. Squat down, then drive up and press the bar overhead in one motion."),

                create("Wall Ball", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Medicine Ball"),
                        "Hold a medicine ball at your chest. Squat down, then explode up and throw the ball to a target on the wall."),

                create("Sled Push", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sled"),
                        "Load a sled and push it across a flat surface using a forward-leaning athletic stance."),

                create("Sled Pull", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sled"),
                        "Attach a rope or harness to a sled and pull it while walking or running backward or forward."),

                create("Sandbag Carry", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.SHOULDERS, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Sandbag"),
                        "Lift a sandbag to your shoulder or chest and walk for distance. Switch sides."),

                create("Tire Flip", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Tire"),
                        "Squat down, grip the bottom of a tire, and drive through your legs to flip it over."),

                create("Battle Ropes", MuscleGroup.FULL_BODY, List.of(MuscleGroup.SHOULDERS, MuscleGroup.CORE, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Battle Ropes"),
                        "Anchor a heavy rope and create alternating waves by slamming the ropes up and down."),

                create("Box Jump", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Box"),
                        "Stand facing a box. Drop into a quarter squat, then explode up and land softly on the box."),

                create("Broad Jump", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of(),
                        "Squat down and jump forward as far as possible, landing softly on both feet."),

                // 🔥 WARMUP — 35 exercises
                create("Jumping Jacks", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand with feet together and arms at sides. Jump while spreading legs and raising arms overhead. Return to start and repeat."),

                create("Arm Circles", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand with arms extended out to sides. Make small circles forward, gradually increasing size. Reverse direction after 30 seconds."),

                create("High Knees", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand tall and jog in place, driving knees up toward chest. Pump arms opposite to legs. Maintain quick pace."),

                create("Butt Kicks", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Jog in place while kicking heels back toward glutes. Keep upper body upright and maintain a rhythm."),

                create("Mountain Climbers", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Start in plank position. Drive one knee toward chest, then alternate legs quickly. Keep hips low and core engaged."),

                create("Jump Rope", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Jump Rope"),
                        "Hold rope handles at hip height. Swing rope overhead and jump as it passes under feet. Land softly on balls of feet."),

                create("Star Jumps", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Slightly bend knees then explode upward, spreading arms and legs into a star shape. Land softly and repeat."),

                create("Skater Hops", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Leap laterally to right, landing on right foot with left leg behind. Immediately push off to left side. Keep chest up."),

                create("Lateral Shuffles", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand with knees bent in athletic stance. Shuffle sideways quickly without crossing feet. Repeat in both directions."),

                create("Inchworms", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Bend forward and walk hands out to a plank. Take small steps forward with feet toward hands. Stand and repeat."),

                create("Leg Swings (Forward)", MuscleGroup.LEGS, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Hold a wall or support. Swing one leg forward and back in a controlled motion. Keep upper body still."),

                create("Leg Swings (Lateral)", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand sideways to a wall for support. Swing outside leg side to side across body. Keep torso stable."),

                create("Hip Circles", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand on one leg, lift other knee to 90 degrees. Circle knee outward in large arcs. Switch directions and legs."),

                create("Torso Twists", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand with feet shoulder-width apart. Rotate upper body side to side, letting arms swing naturally. Keep hips facing forward."),

                create("Cat-Cow Stretch", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Start on hands and knees. Inhale, arch back and lift head (cow). Exhale, round spine and tuck chin (cat). Move slowly."),

                create("World's Greatest Stretch", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "From a deep lunge with right foot forward, place left hand on floor. Rotate torso and open right arm toward ceiling. Hold. Switch sides."),

                create("Thoracic Spine Rotation", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Lie on right side with arms extended. Rotate left arm up and over to the left side, following hand with eyes. Return. Repeat on other side."),

                create("Neck Rolls", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Slowly roll head in a circle, bringing chin to chest, ear to shoulder, and head back. Alternate directions."),

                create("Ankle Rolls", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Lift one foot and rotate ankle clockwise then counterclockwise. Repeat with other foot."),

                create("Wrist Circles", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Extend arms forward and rotate wrists in circles. Alternate directions."),

                create("Glute Bridges", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Lie on back with knees bent and feet flat. Drive hips up by squeezing glutes. Lower with control."),

                create("Scapular Push-Ups", MuscleGroup.CHEST, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Start in plank position. Without bending elbows, squeeze shoulder blades together then push them apart. Keep body rigid."),

                create("Band Pull-Aparts", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Resistance Band"),
                        "Hold a resistance band in front of you with arms extended. Pull hands apart, squeezing shoulder blades together. Return slowly."),

                create("Dead Bugs", MuscleGroup.CORE, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Lie on back with arms extended up and knees at 90 degrees. Slowly extend opposite arm and leg toward floor. Return to center and alternate."),

                create("Bird-Dog", MuscleGroup.CORE, List.of(MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Start on hands and knees. Simultaneously extend right arm forward and left leg back. Hold briefly, then switch sides."),

                create("Fire Hydrants", MuscleGroup.GLUTES, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Start on hands and knees. Lift one leg out to the side, keeping knee bent. Lower with control. Repeat on both sides."),

                create("Bodyweight Squats", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand feet shoulder-width apart. Lower hips back and down as if sitting in a chair. Drive through heels to stand."),

                create("Walking Lunges", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Step forward into a lunge, both knees at 90 degrees. Push off back foot and bring it forward into the next lunge."),

                create("Plank", MuscleGroup.CORE, List.of(MuscleGroup.FULL_BODY),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Hold a straight line from head to heels on forearms and toes. Keep core braced and hips level. Breathe steadily."),

                create("Downward Dog to Upward Dog", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Yoga Mat"),
                        "Start in plank. Push hips up and back into downward dog. Then lower hips and lift chest into upward dog. Flow between both."),

                create("Bear Crawl", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Start on hands and knees with knees hovering. Crawl forward by moving opposite hand and foot. Keep back flat and core tight."),

                create("Hip Flexor March", MuscleGroup.LEGS, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of(),
                        "Stand tall. Drive one knee up toward chest while keeping the other leg straight. Lower and alternate. Maintain upright posture."),

                create("Skipping", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CALVES, MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of(),
                        "Skip forward with an exaggerated step-hop motion. Swing arms naturally. Keep a light, rhythmic bounce."),

                create("PVC Passthrough", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK, MuscleGroup.CHEST),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("PVC Pipe"),
                        "Hold a PVC pipe with a wide overhand grip. Keeping arms straight, pass it overhead to behind you. Return to start."),

                create("Shoulder Passthrough", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK, MuscleGroup.CHEST),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Resistance Band"),
                        "Hold a resistance band or dowel with wide grip. Pass it overhead to behind the body and back. Keep arms straight."),

                // 🔥 STRETCHING — 35 exercises
                create("Chest Stretch", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand near a wall or door frame. Place forearm on the frame and gently rotate away. Hold and repeat on the other side."),

                create("Doorway Chest Stretch", MuscleGroup.CHEST, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand in a doorway with hands on each side at shoulder height. Lean forward until a stretch is felt in the chest. Hold."),

                create("Hamstring Stretch", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Sit on the floor with one leg extended and the other bent. Reach toward the extended foot. Hold and switch legs."),

                create("Cross-Body Shoulder Stretch", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Bring one arm across the body at shoulder height. Use the other hand to gently pull it closer. Hold and switch sides."),

                create("Overhead Triceps Stretch", MuscleGroup.TRICEPS, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Raise one arm overhead and bend the elbow, reaching hand down the back. Use the other hand to gently push the elbow. Switch sides."),

                create("Neck Side Stretch", MuscleGroup.SHOULDERS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Sit or stand upright. Gently tilt the head toward one shoulder. Hold. Repeat on the other side."),

                create("Upper Trap Stretch", MuscleGroup.SHOULDERS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Tilt head to one side and slightly rotate chin toward the chest. Use a hand on the head for a gentle pull. Switch sides."),

                create("Sleeper Stretch", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on the side with the shoulder pressed into the mat. Bend the elbow to 90 degrees and use the other hand to push the forearm down."),

                create("Child's Pose", MuscleGroup.BACK, List.of(MuscleGroup.GLUTES, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Kneel on the mat, sit back on heels, and extend arms forward on the floor. Rest forehead on the mat and breathe deeply."),

                create("Seated Forward Fold", MuscleGroup.BACK, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Sit with legs extended straight. Hinge at the hips and reach toward the feet. Keep the spine long."),

                create("Seated Spinal Twist", MuscleGroup.BACK, List.of(MuscleGroup.CORE, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Sit with one leg crossed over the other. Twist the torso toward the bent knee, using the opposite arm for leverage. Switch sides."),

                create("Supine Spinal Twist", MuscleGroup.BACK, List.of(MuscleGroup.CORE, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on back, bring one knee to chest, then cross it over the body. Extend the opposite arm and turn the head. Switch sides."),

                create("Cobra Stretch", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie face down with hands under shoulders. Gently press up, lifting the chest while keeping hips on the mat. Hold."),

                create("Lat Stretch (Overhead Reach)", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand and reach both arms overhead, grabbing one wrist. Lean to the side, feeling the stretch along the torso. Switch sides."),

                create("Standing Side Bend", MuscleGroup.BACK, List.of(MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand with feet together. Raise one arm overhead and lean to the opposite side. Keep hips stable. Switch sides."),

                create("Figure 4 Stretch (Seated)", MuscleGroup.GLUTES, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Sit on the mat, cross one ankle over the opposite knee. Gently lean forward, keeping the back straight. Switch sides."),

                create("Figure 4 Stretch (Supine)", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on back, cross one ankle over the opposite knee. Pull the bottom leg toward the chest. Switch sides."),

                create("Pigeon Pose", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "From a plank, bring one knee forward and place it behind the wrist. Extend the other leg behind. Lower the hips. Switch sides."),

                create("Happy Baby", MuscleGroup.GLUTES, List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on back, grab the outsides of the feet, and gently pull knees toward the armpits. Rock side to side."),

                create("Butterfly Stretch", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Sit with soles of feet together and knees dropped out to the sides. Gently press knees down. Hold."),

                create("90/90 Stretch", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Sit with both knees bent at 90 degrees, front leg forward and back leg to the side. Lean forward slightly. Switch sides."),

                create("Kneeling Hip Flexor Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Kneel on one knee with the other foot forward. Push hips forward while keeping the torso upright. Switch sides."),

                create("Standing Quad Stretch", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand on one leg, bend the other knee, and grab the ankle. Pull the heel toward the glutes. Keep knees together. Switch sides."),

                create("Lying Quad Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on the side, bend the top knee, and grab the ankle. Pull the heel toward the glutes. Switch sides."),

                create("Half-Kneeling Adductor Stretch", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Kneel on both knees, then extend one leg out to the side. Lean slightly into the extended leg. Switch sides."),

                create("Standing Forward Fold", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Stand with feet hip-width apart. Hinge at the hips and fold forward, letting the head hang. Grab opposite elbows."),

                create("Seated Hamstring Stretch", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat", "Stretching Strap"),
                        "Sit with one leg extended. Loop a strap around the foot and gently pull the leg upward. Keep the back straight. Switch sides."),

                create("Lying Hamstring Stretch (Strap)", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Stretching Strap"),
                        "Lie on back with one leg extended on the floor. Loop a strap around the other foot and pull the leg toward the chest. Switch sides."),

                create("Wide-Legged Forward Fold", MuscleGroup.HAMSTRINGS, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Stand with legs wide apart. Hinge forward at the hips, lowering the head toward the floor. Rest hands on the ground."),

                create("Standing Calf Stretch", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Stand facing a wall with one foot forward and one back. Keep the back leg straight, heel on the ground. Lean forward. Switch sides."),

                create("Heel Drop (Step)", MuscleGroup.CALVES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Box"),
                        "Stand on the edge of a box with heels hanging off. Lower the heels down, feeling a stretch in the calves. Hold."),

                create("Abdominal Stretch (Supine)", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on stomach with hands under shoulders. Gently press up, keeping hips on the mat. Breathe deeply."),

                create("Side-Lying Oblique Stretch", MuscleGroup.CORE, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Yoga Mat"),
                        "Lie on one side, extend the bottom arm forward, and sweep the top arm overhead in a arc. Hold. Switch sides."),

                create("Wrist Flexor Stretch", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Extend one arm forward with palm up. Use the other hand to gently pull the fingers back. Switch sides."),

                create("Wrist Extensor Stretch", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Extend one arm forward with palm down. Use the other hand to gently push the fingers down. Switch sides."),

                create("Garland Pose (Malasana)", MuscleGroup.LEGS, List.of(MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of(),
                        "Squat down with feet wider than hips. Bring palms together at the chest and use elbows to press knees outward. Hold."),

                // 🔥 CARDIO — 12 exercises
                create("Treadmill Walk", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Treadmill"),
                        "Walk on a treadmill at a comfortable pace, maintaining upright posture. Swing arms naturally and land softly on your heels."),

                create("Treadmill Run", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CALVES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Treadmill"),
                        "Run on a treadmill at a steady, challenging pace. Maintain good form, relaxed shoulders, and controlled breathing."),

                create("Incline Treadmill Walk", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Treadmill"),
                        "Walk on a treadmill at a steep incline. Drive through your heels and squeeze your glutes with each step. Avoid holding the handles."),

                create("Stationary Bike", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Stationary Bike"),
                        "Cycle on a stationary bike at a steady, comfortable pace. Keep your core engaged and avoid rocking side to side."),

                create("Spin Bike HIIT", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Spin Bike"),
                        "Alternate between all-out sprints and slow recovery on a spin bike. Stand on the pedals during sprints and sit during recovery."),

                create("Recumbent Bike", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Recumbent Bike"),
                        "Cycle on a recumbent bike at a comfortable pace. Focus on a smooth pedal stroke and controlled breathing."),

                create("Elliptical Training", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Elliptical Trainer"),
                        "Use the elliptical trainer with a steady stride. Push and pull the handles for full-body engagement. Maintain an upright posture."),

                create("Rowing Machine", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Rowing Machine"),
                        "Sit on the rower, drive through your legs, lean back slightly, and pull the handle to your lower chest. Extend arms, hinge forward, and slide back."),

                create("Rowing Sprint Intervals", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.TRICEPS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Rowing Machine"),
                        "Perform short, all-out rowing intervals (250-500m) with full recovery between sets. Focus on explosive leg drive and high stroke rate."),

                create("Stair Climber", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CALVES),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Stair Climber"),
                        "Step up continuously on the stair climber. Keep your upright posture, avoid leaning on the handles, and take full steps."),

                create("Assault Bike Sprint", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Assault Bike"),
                        "Pedal the assault bike at maximal effort using both arms and legs. Maintain a high RPM for the sprint duration."),

                create("SkiErg", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("SkiErg"),
                        "Stand facing the SkiErg, drive arms down and back in a fluid ski-poling motion. Engage your core and hinge at the hips with each pull."),

                // 🔥 FREE WEIGHTS (NEW) — 2 exercises
                create("Trap Bar Deadlift", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.BACK, MuscleGroup.GLUTES),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Trap Bar"),
                        "Stand inside the trap bar with feet shoulder-width apart. Hinge at the hips and bend your knees to grip the handles. Drive through your heels to stand."),

                create("Trap Bar Shrug", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Trap Bar"),
                        "Stand holding a trap bar at arm's length. Shrug your shoulders straight up toward your ears, hold briefly, then lower."),

                // 🔥 RACKS & MACHINES (NEW) — 4 exercises
                create("Pin Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Power Rack"),
                        "Set the safety pins in the power rack at your desired depth. Unrack the bar, squat down until the bar touches the pins, pause, then drive back up."),

                create("Spoto Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Power Rack", "Bench"),
                        "Lie on a flat bench inside the power rack. Lower the bar to your chest, pause an inch above, then press back up."),

                create("Straight-Arm Pulldown", MuscleGroup.BACK, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.MAIN,
                        List.of("Lat Pulldown Machine"),
                        "Stand or sit at the lat pulldown machine with a straight bar. Keeping arms fully extended, pull the bar down to your thighs, squeezing the lats."),

                create("Depth Jump", MuscleGroup.LEGS, List.of(MuscleGroup.CALVES, MuscleGroup.GLUTES),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Plyometric Box"),
                        "Stand on a box, step off, and upon landing immediately explode upward into a vertical jump. Land softly and reset."),

                // 🔥 BANDS (NEW) — 4 exercises
                create("Banded Overhead Press", MuscleGroup.SHOULDERS, List.of(MuscleGroup.TRICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Resistance Tube"),
                        "Stand on the center of a resistance tube and hold handles at shoulder height. Press overhead until arms are fully extended."),

                create("Mini Band Lateral Walk", MuscleGroup.GLUTES, List.of(MuscleGroup.LEGS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Mini Band"),
                        "Place a mini band around your ankles. Slight squat position, then step laterally keeping tension on the band."),

                create("Clamshell", MuscleGroup.GLUTES, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.WARMUP,
                        List.of("Mini Band"),
                        "Lie on your side with a mini band around your thighs. Keeping feet together, open your top knee like a clam. Lower and repeat."),

                create("Banded Squat", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Loop Band"),
                        "Stand on a loop band with it looped around your upper thighs. Perform a squat, pushing your knees outward against the band."),

                // 🔥 GYMNASTIC RINGS — 3 exercises
                create("Ring Push Up", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        "Set rings low and grip them. Lower your chest to ring height in a push-up position, keeping elbows at 45 degrees. Press back up."),

                create("Ring Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        "Hold rings with arms extended, body at an angle. Pull your chest toward the rings, squeezing your shoulder blades together."),

                create("Ring Dip", MuscleGroup.TRICEPS, List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
                        DifficultyLevel.ADVANCED, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Gymnastic Rings"),
                        "Support yourself on rings above your head. Lower your body until shoulders are below your elbows, then press back up. Stabilize throughout."),

                // 🔥 TRX — 3 exercises
                create("TRX Row", MuscleGroup.BACK, List.of(MuscleGroup.BICEPS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        "Hold the TRX handles with arms extended and walk feet forward. Pull your chest toward the handles, keeping your body rigid."),

                create("TRX Chest Press", MuscleGroup.CHEST, List.of(MuscleGroup.TRICEPS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        "Face away from the anchor point and hold handles at chest height. Lean forward and lower your chest between the handles, then press back."),

                create("TRX Pike", MuscleGroup.CORE, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("TRX Suspension Trainer"),
                        "Place feet in the TRX straps and get into a plank. Pike your hips up toward the ceiling, keeping legs straight. Lower with control."),

                // 🔥 FUNCTIONAL (NEW) — 4 exercises
                create("Farmer's Walk (Heavy)", MuscleGroup.FULL_BODY, List.of(MuscleGroup.CORE, MuscleGroup.LEGS, MuscleGroup.SHOULDERS),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Farmer's Walk Handles"),
                        "Load the farmer's walk handles and pick them up. Walk upright with a braced core, taking controlled steps for distance or time."),

                create("Agility Ladder Drill", MuscleGroup.LEGS, List.of(MuscleGroup.CALVES, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Agility Ladder"),
                        "Run through the agility ladder using quick high-knee steps. Place each foot in each square. Repeat forward, backward, and laterally."),

                create("Cone Shuffle Drill", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Cone"),
                        "Set cones in a pattern. Shuffle, sprint, and backpedal between cones in sequence. Focus on quick direction changes and low center of gravity."),

                create("Elevated Step-Up", MuscleGroup.LEGS, List.of(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Platforms"),
                        "Stand facing a platform. Step up with one foot and drive through the heel to bring the other foot up. Step down and alternate."),

                // 🔥 MOBILITY & RECOVERY (NEW) — 5 exercises
                create("Foam Rolling (Upper Back)", MuscleGroup.BACK, List.of(MuscleGroup.SHOULDERS),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        "Lie on your back with a foam roller under your upper back. Support your head with your hands. Roll slowly from mid-back to shoulders, pausing on tight spots."),

                create("Foam Rolling (Quads)", MuscleGroup.LEGS, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        "Lie face down with a foam roller under your quads. Roll from hips to knees, pausing on any tight or tender areas."),

                create("Foam Rolling (IT Band)", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Foam Roller"),
                        "Lie on your side with the foam roller under your outer thigh. Roll from hip to knee along the IT band. Switch sides."),

                create("Percussive Massage", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Massage Gun"),
                        "Use a massage gun on major muscle groups. Glide the head slowly over each muscle for 30-60 seconds. Avoid joints and bones."),

                create("Lacrosse Ball Release", MuscleGroup.FULL_BODY, List.of(),
                        DifficultyLevel.BEGINNER, ExerciseType.ISOLATION, ExerciseCategory.STRETCHING,
                        List.of("Lacrosse Ball"),
                        "Place a lacrosse ball under a tight or tender area (glutes, shoulders, upper back). Apply gentle pressure and breathe deeply until the tension releases."),

                // 🔥 SWIMMING POOL — 2 exercises
                create("Freestyle Swimming", MuscleGroup.FULL_BODY, List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.CORE),
                        DifficultyLevel.INTERMEDIATE, ExerciseType.COMPOUND, ExerciseCategory.MAIN,
                        List.of("Swimming Pool"),
                        "Swim freestyle laps using alternating arm strokes and a flutter kick. Breathe to the side every stroke cycle. Maintain a steady rhythm."),

                create("Pool Running", MuscleGroup.FULL_BODY, List.of(MuscleGroup.LEGS, MuscleGroup.CORE),
                        DifficultyLevel.BEGINNER, ExerciseType.COMPOUND, ExerciseCategory.WARMUP,
                        List.of("Swimming Pool"),
                        "Jog in place or across the pool with the water at chest height. Drive your knees and pump your arms against the water resistance.")
        );

        // Attach equipment
        exercises.forEach(exercise -> {
            List<Equipment> eqList = exercise.getEquipments().stream()
                    .map(eq -> equipmentMap.get(eq.getName()))
                    .toList();
            exercise.setEquipments(eqList);
        });

        if (exerciseRepository.count() > 0) {
            Set<String> existingNames = exerciseRepository.findAll().stream()
                    .map(Exercise::getName)
                    .collect(Collectors.toSet());

            List<Exercise> newExercises = exercises.stream()
                    .filter(e -> !existingNames.contains(e.getName()))
                    .toList();

            if (newExercises.isEmpty()) {
                log.info("Exercises already seeded");
                return;
            }

            log.info("Adding {} new exercises...", newExercises.size());
            exerciseRepository.saveAll(newExercises);
        } else {
            exerciseRepository.saveAll(exercises);
        }

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

        Exercise e = new Exercise();
        e.setName(name);
        e.setPrimaryMuscle(primary);
        e.setSecondaryMuscles(secondary);
        e.setDifficultyLevel(difficulty);
        e.setExerciseType(type);
        e.setCategory(category);
        e.setInstructions(instructions);

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
