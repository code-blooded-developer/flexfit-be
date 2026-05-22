package com.harish.flexfit.seeder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.enums.EquipmentCategory;
import com.harish.flexfit.enums.EquipmentType;
import com.harish.flexfit.repo.EquipmentRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EquipmentSeeder implements CommandLineRunner {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) {

        if (equipmentRepository.count() > 0) {
            log.info("Equipment already seeded");
            return;
        }

        log.info("Seeding equipment data...");

        List<Equipment> equipments = List.of(
                // Small Weights
                create("Dumbbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS),
                create("Adjustable Dumbbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS),
                create("Kettlebell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS),

                // Bars & Plates
                create("Barbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES),
                create("EZ Bar", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES),
                create("Trap Bar", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES),
                create("Weight Plate", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES),
                create("Landmine", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES),

                // Benches & Racks
                create("Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS),
                create("Adjustable Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS),
                create("Preacher Curl Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS),
                create("Squat Rack", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS),
                create("Power Rack", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS),
                create("Smith Machine", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS),
                create("Hyperextension Bench", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS),
                create("Back Extension Bench", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS),

                // Cable Machines
                create("Cable Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES),
                create("Cable Crossover Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES),
                create("Lat Pulldown Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES),
                create("Seated Row Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES),

                // Resistance Bands
                create("Resistance Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS),
                create("Resistance Tube", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS),
                create("Mini Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS),
                create("Loop Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS),

                // Plate-Loaded Machines
                create("Leg Press Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Leg Extension Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Leg Curl Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Hack Squat Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Hip Adductor Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Hip Abductor Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Calf Raise Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),
                create("Glute Ham Developer", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES),

                // Weight-Stack Machines
                create("Chest Press Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),
                create("Shoulder Press Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),
                create("Pec Deck Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),
                create("Lateral Raise Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),
                create("Triceps Pushdown Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),
                create("Biceps Curl Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES),

                // Ropes & Suspension
                create("Pull-up Bar", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("Dip Station", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("Parallette Bars", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("Gymnastic Rings", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("TRX Suspension Trainer", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("Battle Ropes", EquipmentType.FUNCTIONAL, EquipmentCategory.ROPES_AND_SUSPENSION),
                create("Jump Rope", EquipmentType.CARDIO_ACTIVITY, EquipmentCategory.ROPES_AND_SUSPENSION),

                // Exercise Balls & Core
                create("Medicine Ball", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE),
                create("Slam Ball", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE),
                create("Plyometric Box", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE),
                create("Box", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE),
                create("Ab Wheel", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE),

                // Cardio Machines
                create("Treadmill", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Stationary Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Spin Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Recumbent Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Elliptical Trainer", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Rowing Machine", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Stair Climber", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("Assault Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),
                create("SkiErg", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES),

                // Functional & Strongman
                create("Sandbag", EquipmentType.FREE_WEIGHT, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Farmer's Walk Handles", EquipmentType.FREE_WEIGHT, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Sled", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Tire", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Agility Ladder", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Cone", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),
                create("Platforms", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN),

                // Mobility & Recovery
                create("Foam Roller", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),
                create("Yoga Mat", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),
                create("Massage Gun", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),
                create("Stretching Strap", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),
                create("Lacrosse Ball", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),
                create("PVC Pipe", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY),

                // Aquatic
                create("Swimming Pool", EquipmentType.AQUATIC, EquipmentCategory.AQUATIC)
        );

        equipmentRepository.saveAll(equipments);

        log.info("Equipment seeded successfully");
    }

    private Equipment create(String name, EquipmentType type, EquipmentCategory category) {
        Equipment eq = new Equipment();
        eq.setName(name);
        eq.setType(type);
        eq.setCategory(category);
        return eq;
    }
}
