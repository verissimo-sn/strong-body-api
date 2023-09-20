package com.aitech.strongBody.infra.database.h2;

import com.aitech.strongBody.domain.entity.Exercise;
import com.aitech.strongBody.domain.repository.ExerciseRepository;
import com.aitech.strongBody.infra.database.h2.model.ExerciseH2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class H2ExerciseRepositoryImpl implements ExerciseRepository {
    private final SpringDataH2ExerciseRepository springDataH2ExerciseRepository;

    public H2ExerciseRepositoryImpl(SpringDataH2ExerciseRepository springDataH2ExerciseRepository) {
        this.springDataH2ExerciseRepository = springDataH2ExerciseRepository;
    }

    @Override
    public void create(Exercise exercise) {
        this.springDataH2ExerciseRepository.save(toH2Entity(exercise));
    }

    @Override
    public Optional<Exercise> getById(UUID id) {
        var foundExercise = this.springDataH2ExerciseRepository.findById(id);
        return foundExercise.map(this::toEntity);
    }

    @Override
    public Page<Exercise> getAll(Pageable pageable) {
        return this.springDataH2ExerciseRepository.findAll(pageable).map(this::toEntity);
    }

    @Override
    public void update(Exercise exercise) {
        this.springDataH2ExerciseRepository.save(toH2Entity(exercise));
    }

    @Override
    public void deleteById(UUID id) {
        this.springDataH2ExerciseRepository.deleteById(id);
    }

    private Exercise toEntity(ExerciseH2 exercise) {
        return Exercise.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .level(exercise.getLevel())
                .type(exercise.getType())
                .equipment(exercise.getEquipment())
                .imageUrl(exercise.getImageUrl())
                .videoUrl(exercise.getVideoUrl())
                .build();
    }

    private ExerciseH2 toH2Entity(Exercise entity) {
        ExerciseH2 exerciseH2 = new ExerciseH2();
        exerciseH2.setId(entity.getId());
        exerciseH2.setName(entity.getName());
        exerciseH2.setDescription(entity.getDescription());
        exerciseH2.setLevel(entity.getLevel());
        exerciseH2.setType(entity.getType());
        exerciseH2.setEquipment(entity.getEquipment());
        exerciseH2.setImageUrl(entity.getImageUrl());
        exerciseH2.setVideoUrl(entity.getVideoUrl());
        return exerciseH2;
    }
}
