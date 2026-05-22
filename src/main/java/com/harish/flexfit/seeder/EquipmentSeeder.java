package com.harish.flexfit.seeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.EquipmentVariant;
import com.harish.flexfit.enums.EquipmentCategory;
import com.harish.flexfit.enums.EquipmentType;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.repo.EquipmentRepository;
import com.harish.flexfit.repo.EquipmentVariantRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EquipmentSeeder implements CommandLineRunner {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentVariantRepository equipmentVariantRepository;

    @Override
    public void run(String... args) {

        if (equipmentRepository.count() > 0) {
            log.info("Equipment already seeded");
            return;
        }

        log.info("Seeding equipment data...");

        List<Equipment> equipments = new ArrayList<>();

        // Small Weights
        equipments.add(create("Dumbbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS, true));
        equipments.add(create("Adjustable Dumbbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS, false));
        equipments.add(create("Kettlebell", EquipmentType.FREE_WEIGHT, EquipmentCategory.SMALL_WEIGHTS, true));

        // Bars & Plates
        equipments.add(create("Barbell", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES, true));
        equipments.add(create("EZ Bar", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES, true));
        equipments.add(create("Trap Bar", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES, true));
        equipments.add(create("Weight Plate", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES, true));
        equipments.add(create("Landmine", EquipmentType.FREE_WEIGHT, EquipmentCategory.BARS_AND_PLATES, false));

        // Benches & Racks
        equipments.add(create("Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Adjustable Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Preacher Curl Bench", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Squat Rack", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Power Rack", EquipmentType.FREE_WEIGHT, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Smith Machine", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Hyperextension Bench", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS, false));
        equipments.add(create("Back Extension Bench", EquipmentType.MACHINE, EquipmentCategory.BENCHES_AND_RACKS, false));

        // Cable Machines
        equipments.add(create("Cable Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES, false));
        equipments.add(create("Cable Crossover Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES, false));
        equipments.add(create("Lat Pulldown Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES, false));
        equipments.add(create("Seated Row Machine", EquipmentType.MACHINE, EquipmentCategory.CABLE_MACHINES, false));

        // Resistance Bands
        equipments.add(create("Resistance Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS, false));
        equipments.add(create("Resistance Tube", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS, false));
        equipments.add(create("Mini Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS, false));
        equipments.add(create("Loop Band", EquipmentType.RESISTANCE, EquipmentCategory.RESISTANCE_BANDS, false));

        // Plate-Loaded Machines
        equipments.add(create("Leg Press Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Leg Extension Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Leg Curl Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Hack Squat Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Hip Adductor Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Hip Abductor Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Calf Raise Machine", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));
        equipments.add(create("Glute Ham Developer", EquipmentType.MACHINE, EquipmentCategory.PLATE_LOADED_MACHINES, false));

        // Weight-Stack Machines
        equipments.add(create("Chest Press Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));
        equipments.add(create("Shoulder Press Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));
        equipments.add(create("Pec Deck Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));
        equipments.add(create("Lateral Raise Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));
        equipments.add(create("Triceps Pushdown Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));
        equipments.add(create("Biceps Curl Machine", EquipmentType.MACHINE, EquipmentCategory.WEIGHT_STACK_MACHINES, false));

        // Ropes & Suspension
        equipments.add(create("Pull-up Bar", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("Dip Station", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("Parallette Bars", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("Gymnastic Rings", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("TRX Suspension Trainer", EquipmentType.SUSPENSION, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("Battle Ropes", EquipmentType.FUNCTIONAL, EquipmentCategory.ROPES_AND_SUSPENSION, false));
        equipments.add(create("Jump Rope", EquipmentType.CARDIO_ACTIVITY, EquipmentCategory.ROPES_AND_SUSPENSION, false));

        // Exercise Balls & Core
        equipments.add(create("Medicine Ball", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE, true));
        equipments.add(create("Slam Ball", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE, true));
        equipments.add(create("Plyometric Box", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE, false));
        equipments.add(create("Box", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE, false));
        equipments.add(create("Ab Wheel", EquipmentType.FUNCTIONAL, EquipmentCategory.EXERCISE_BALLS_AND_CORE, false));

        // Cardio Machines
        equipments.add(create("Treadmill", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Stationary Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Spin Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Recumbent Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Elliptical Trainer", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Rowing Machine", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Stair Climber", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("Assault Bike", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));
        equipments.add(create("SkiErg", EquipmentType.CARDIO_MACHINE, EquipmentCategory.CARDIO_MACHINES, false));

        // Functional & Strongman
        equipments.add(create("Sandbag", EquipmentType.FREE_WEIGHT, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, true));
        equipments.add(create("Farmer's Walk Handles", EquipmentType.FREE_WEIGHT, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));
        equipments.add(create("Sled", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));
        equipments.add(create("Tire", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));
        equipments.add(create("Agility Ladder", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));
        equipments.add(create("Cone", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));
        equipments.add(create("Platforms", EquipmentType.FUNCTIONAL, EquipmentCategory.FUNCTIONAL_AND_STRONGMAN, false));

        // Mobility & Recovery
        equipments.add(create("Foam Roller", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));
        equipments.add(create("Yoga Mat", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));
        equipments.add(create("Massage Gun", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));
        equipments.add(create("Stretching Strap", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));
        equipments.add(create("Lacrosse Ball", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));
        equipments.add(create("PVC Pipe", EquipmentType.MOBILITY, EquipmentCategory.MOBILITY_AND_RECOVERY, false));

        // Aquatic
        equipments.add(create("Swimming Pool", EquipmentType.AQUATIC, EquipmentCategory.AQUATIC, false));

        equipmentRepository.saveAll(equipments);

        List<Equipment> saved = equipmentRepository.findAll();
        Map<String, Equipment> byName = saved.stream()
                .collect(Collectors.toMap(Equipment::getName, e -> e));

        List<EquipmentVariant> variants = new ArrayList<>();

        // Dumbbell variants (kg)
        Equipment dumbbell = byName.get("Dumbbell");
        int order = 1;
        for (double w : new double[]{2.5, 5, 7.5, 10, 12.5, 15, 17.5, 20, 22.5, 25}) {
            variants.add(createVariant(dumbbell, w, WeightUnit.KG, order++));
        }

        // Kettlebell variants (kg)
        Equipment kettlebell = byName.get("Kettlebell");
        order = 1;
        for (double w : new double[]{4, 6, 8, 10, 12, 16, 20, 24}) {
            variants.add(createVariant(kettlebell, w, WeightUnit.KG, order++));
        }

        // Barbell variants (kg)
        Equipment barbell = byName.get("Barbell");
        variants.add(createVariant(barbell, 15, WeightUnit.KG, 1));
        variants.add(createVariant(barbell, 20, WeightUnit.KG, 2));

        // EZ Bar variants (kg)
        Equipment ezBar = byName.get("EZ Bar");
        variants.add(createVariant(ezBar, 10, WeightUnit.KG, 1));
        variants.add(createVariant(ezBar, 15, WeightUnit.KG, 2));

        // Trap Bar variants (kg)
        Equipment trapBar = byName.get("Trap Bar");
        variants.add(createVariant(trapBar, 20, WeightUnit.KG, 1));
        variants.add(createVariant(trapBar, 25, WeightUnit.KG, 2));

        // Weight Plate variants (kg)
        Equipment weightPlate = byName.get("Weight Plate");
        order = 1;
        for (double w : new double[]{1.25, 2.5, 5, 10, 15, 20, 25}) {
            variants.add(createVariant(weightPlate, w, WeightUnit.KG, order++));
        }

        // Medicine Ball variants (kg)
        Equipment medicineBall = byName.get("Medicine Ball");
        order = 1;
        for (double w : new double[]{2, 3, 4, 5, 6, 8, 10}) {
            variants.add(createVariant(medicineBall, w, WeightUnit.KG, order++));
        }

        // Slam Ball variants (kg)
        Equipment slamBall = byName.get("Slam Ball");
        order = 1;
        for (double w : new double[]{5, 10, 15, 20}) {
            variants.add(createVariant(slamBall, w, WeightUnit.KG, order++));
        }

        // Sandbag variants (kg)
        Equipment sandbag = byName.get("Sandbag");
        order = 1;
        for (double w : new double[]{10, 20, 30, 40, 50}) {
            variants.add(createVariant(sandbag, w, WeightUnit.KG, order++));
        }

        equipmentVariantRepository.saveAll(variants);

        log.info("Equipment seeded successfully with {} variants", variants.size());
    }

    private Equipment create(String name, EquipmentType type, EquipmentCategory category, boolean hasVariants) {
        Equipment eq = new Equipment();
        eq.setName(name);
        eq.setType(type);
        eq.setCategory(category);
        eq.setHasVariants(hasVariants);
        return eq;
    }

    private EquipmentVariant createVariant(Equipment equipment, double weight, WeightUnit unit, int sortOrder) {
        EquipmentVariant variant = new EquipmentVariant();
        variant.setEquipment(equipment);
        variant.setWeight(weight);
        variant.setWeightUnit(unit);
        variant.setSortOrder(sortOrder);
        return variant;
    }
}
