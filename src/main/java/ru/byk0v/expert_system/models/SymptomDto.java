package ru.byk0v.expert_system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SymptomDto {
    // unique
    private Integer id;

    private String description;

    public SymptomDto(Symptom symptom) {
        this.id = symptom.getId();
        this.description = symptom.getDescription();
    }
}
