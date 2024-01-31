package com.aitech.strongBody.domain.entity;

import com.aitech.strongBody.domain.enums.TrainingStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@SuperBuilder
@ToString
public class Training extends Entity {
    @NonNull private UUID userId;
    @NonNull private String name;
    @NonNull private String level;
    private TrainingStatus status = TrainingStatus.NOT_STARTED;
    private int requiredSessions = 0;
    private int finishedSessions = 0;
    private List<TrainingGroup> trainingGroups = new ArrayList<>();

    public void update(String name, String level) {
        this.setName(name == null ? this.name : name);
        this.setLevel(level == null ? this.level : level);
    }
}
