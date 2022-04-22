package ru.byk0v.expert_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PationAddRequest {
    private PatientDto patient;
    private DiagnosisDto diagnosis;
    private List<SymptomDto> symptoms;
}
