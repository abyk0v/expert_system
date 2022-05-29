package ru.byk0v.expert_system.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PatientAddRequest {
    private PatientDto patient;
    private DiagnosisDto diagnosis;
    private List<SymptomDto> symptoms;
}
