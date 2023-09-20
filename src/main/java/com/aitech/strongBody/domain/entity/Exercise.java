package com.aitech.strongBody.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Exercise extends BaseEntity {
    private String name;
    private String description;
    private String level;
    private String type;
    private String equipment;
    private String imageUrl;
    private String videoUrl;

    public void update(
            final String name,
            final String description,
            final String level,
            final String type,
            final String equipment,
            final String imageUrl,
            final String videoUrl) {
        this.setName(name == null ? this.name : name);
        this.setDescription(description == null ? this.description : description);
        this.setLevel(level == null ? this.level : level);
        this.setType(type == null ? this.type : type);
        this.setEquipment(equipment == null ? this.equipment : equipment);
        this.setImageUrl(imageUrl == null ? this.imageUrl : imageUrl);
        this.setVideoUrl(videoUrl == null ? this.videoUrl : videoUrl);
    }
}
