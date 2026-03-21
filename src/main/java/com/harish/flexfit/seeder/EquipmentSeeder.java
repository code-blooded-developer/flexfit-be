package com.harish.flexfit.seeder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harish.flexfit.entity.Equipment;
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
                create("Dumbbell"),
                create("Barbell"),
                create("Bench"),
                create("Pull-up Bar"),
                create("Resistance Band"),
                create("Kettlebell"),
                create("Treadmill"),
                create("Cable Machine"),
                create("Leg Press Machine"),
                create("Bodyweight"),
                create("Swimming Pool")
        );

        equipmentRepository.saveAll(equipments);

        log.info("Equipment seeded successfully");
    }

    private Equipment create(String name) {
        Equipment eq = new Equipment();
        eq.setName(name);
        return eq;
    }
}
