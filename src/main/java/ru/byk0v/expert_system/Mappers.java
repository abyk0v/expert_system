package ru.byk0v.expert_system;

import ru.byk0v.expert_system.models.Diagnosis;
import ru.byk0v.expert_system.models.DiagnosisDto;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.models.SymptomDto;

import java.util.LinkedList;
import java.util.List;

public class Mappers {

    public static List<Symptom> SymptomDtoToSymptom(List<SymptomDto> source) {
        List<Symptom> symptoms = new LinkedList<>();
        source.forEach(item -> {
            symptoms.add(symptomDtoToSymptom(item));
        });
        return symptoms;
    }

    public static Symptom symptomDtoToSymptom(SymptomDto symptomDto) {
        Symptom symptom = new Symptom();
        symptom.setId(symptomDto.getId());
        symptom.setDescription(symptomDto.getDescription());
        return symptom;
    }

    public static DiagnosisDto diagnosisToDiagnosisDto(Diagnosis diagnosis) {
        DiagnosisDto diagnosisDto = new DiagnosisDto();
        diagnosisDto.setId(diagnosis.getId());
        diagnosisDto.setName(diagnosis.getName());
        diagnosisDto.setDescription(diagnosis.getDescription());
        return diagnosisDto;
    }
}
