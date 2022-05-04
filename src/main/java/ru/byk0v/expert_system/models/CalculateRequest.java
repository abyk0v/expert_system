package ru.byk0v.expert_system.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CalculateRequest {
    private Integer patientId;
    private List<Symptom> symptoms;
}
