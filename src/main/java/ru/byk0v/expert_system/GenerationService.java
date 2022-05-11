package ru.byk0v.expert_system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenerationService {

    private DiagnosisRepository diagnosisRepository;
    private SymptomRepository symptomRepository;
    private PatientRepository patientRepository;

    public void patientsForAllDiagnoses(Integer count) {
        List<Patient> patientList = new LinkedList<>();

        var diagnoses = diagnosisRepository.findAll();
        diagnoses.forEach(diagnosis -> {
            Patient newPatient = new Patient();
            newPatient.setName("Test");
            newPatient.setSurname("Test");
            newPatient.setAge(20);
            newPatient.setDiagnosis(diagnosis);
            newPatient.setSymptoms(new LinkedList<>());

            var symptoms = symptomRepository.findAllForDiagnosis(diagnosis.getId());
            int random = (int) (Math.random() * symptoms.size());

            for (int i = 0; i < random; i++) {
                int s = (int) (Math.random() * symptoms.size());
                newPatient.getSymptoms().add(symptoms.get(s));
                symptoms.remove(s);
            }
            patientList.add(newPatient);
        });

        patientRepository.saveAll(patientList);
    }
}
