package com.aitech.strongBody.domain.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder(toBuilder = true)
public class TrainingGroup {
    private String tag;
    private String description;
    private int order;
    private List<Exercise> exercises = new ArrayList<>();

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }
}
