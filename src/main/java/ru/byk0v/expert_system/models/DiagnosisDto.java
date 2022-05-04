package ru.byk0v.expert_system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiagnosisDto {
    // unique
    private Integer id;

    private String description;

    // Вероятность диагноза
    private Double probability = 0.5;

    public DiagnosisDto(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.description = diagnosis.getDescription();
    }
}
