package ru.byk0v.expert_system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiagnosisDto {
    // unique
    private Integer id;

    private String description;

    public DiagnosisDto(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.description = diagnosis.getDescription();
    }
}
