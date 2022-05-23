package ru.byk0v.expert_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CalculateRequest {
    private Integer patientId;
    private List<Symptom> symptoms;
}
