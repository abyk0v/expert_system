package ru.byk0v.expert_system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiagnosisDto {
    // unique
    private Integer id;

    private String name;
    private String description;

    // Вероятность диагноза
    private Double probability = 0.5;

    public DiagnosisDto(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.name = diagnosis.getName();
        this.description = diagnosis.getDescription();
    }
}
