package ru.byk0v.expert_system.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CalculateResponce {
    private List<DiagnosisDto> diagnoses;

    public CalculateResponce(List<DiagnosisDto> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
