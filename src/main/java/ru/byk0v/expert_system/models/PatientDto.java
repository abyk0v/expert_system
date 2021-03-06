package ru.byk0v.expert_system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDto {
    // unique
    private int id;

    private String name;

    private String surname;

    private int age;

    private int diagnosis_id;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.surname = patient.getSurname();
        this.age = patient.getAge();
        this.diagnosis_id = patient.getDiagnosis().getId();
    }
}
