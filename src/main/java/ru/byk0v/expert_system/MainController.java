package ru.byk0v.expert_system;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.byk0v.expert_system.models.*;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class MainController {

    private PatientRepository patientRepository;
    private SymptomRepository symptomRepository;
    private DiagnosisRepository diagnosisRepository;

    @GetMapping("/patients")
    public List<PatientDto> getAllPatient() {
        List<Patient> entity = patientRepository.findAll();
        List<PatientDto> patients = entity.stream()
                .map(PatientDto::new)
                .collect(Collectors.toList());
        return patients;
    }

    @GetMapping("/patient")
    public PatientDto getAllPatient(@RequestParam("patient_id") Integer patient_id) {
        Optional<Patient> entity = patientRepository.findById(patient_id);

        if (entity.isPresent()) {
            return new PatientDto(entity.get());
        }
        return null;
    }

    @GetMapping("/symptoms-for-patient-by-id")
    public List<SymptomDto> getAllSymptomsForPatient(@RequestParam("patient_id") Integer patient_id) {
        List<Symptom> entity = symptomRepository.findAllForPatient(patient_id);
        List<SymptomDto> symptoms = entity.stream()
                .map(SymptomDto::new)
                .collect(Collectors.toList());
        return symptoms;
    }

    @GetMapping("/symptoms")
    public List<SymptomDto> getAllSymptom() {
        List<Symptom> entity = symptomRepository.findAll();
        List<SymptomDto> symptoms = entity.stream()
                .map(SymptomDto::new)
                .collect(Collectors.toList());
        return symptoms;
    }

    @GetMapping("/diagnoses")
    public List<DiagnosisDto> getAllDiagnosis() {
        List<Diagnosis> entity = diagnosisRepository.findAll();
        List<DiagnosisDto> diagnoses = entity.stream()
                .map(DiagnosisDto::new)
                .collect(Collectors.toList());
        return diagnoses;
    }

    @GetMapping("/diagnosis-for-patient-by-id")
    public DiagnosisDto getDiagnosisForPatient(@RequestParam("patient_id") Integer patient_id) {
        Optional<Diagnosis> entity = diagnosisRepository.getDiagnosisForPatient(patient_id);

        if (entity.isPresent()) {
            return new DiagnosisDto(entity.get());
        }
        return null;
    }

    @PostMapping("/patient")
    public PatientDto addPatient(@RequestBody PationAddRequest pationAddRequest) {
        Patient savePatient = new Patient();
        savePatient.setId(pationAddRequest.getPatient().getId());
        savePatient.setName(pationAddRequest.getPatient().getName());
        savePatient.setSurname(pationAddRequest.getPatient().getSurname());
        savePatient.setAge(pationAddRequest.getPatient().getAge());

        savePatient.addDiagnosis(new Diagnosis(pationAddRequest.getDiagnosis()));
        savePatient.setSymptoms(Mappers.SymptomDtoToSymptom(pationAddRequest.getSymptoms()));

        patientRepository.save(savePatient);
        return null;
    }

    @DeleteMapping("/patient")
    public Responce delete(@RequestParam("patient_id") Integer patient_id) {
        patientRepository.deleteById(patient_id);
        return new Responce("SUCCESS");
    }
}
