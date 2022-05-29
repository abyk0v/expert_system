package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.byk0v.expert_system.calculations.CalculateService;
import ru.byk0v.expert_system.Mappers;
import ru.byk0v.expert_system.models.CalculateRequest;
import ru.byk0v.expert_system.models.CalculateResponce;
import ru.byk0v.expert_system.models.Diagnosis;
import ru.byk0v.expert_system.models.DiagnosisDto;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.models.PatientDto;
import ru.byk0v.expert_system.models.PatientAddRequest;
import ru.byk0v.expert_system.models.Responce;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.models.SymptomDto;
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

    private CalculateService calculateService;

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
    public PatientDto addPatient(@RequestBody PatientAddRequest pationAddRequest) {
        Patient savePatient = new Patient();
        savePatient.setId(pationAddRequest.getPatient().getId());
        savePatient.setName(pationAddRequest.getPatient().getName());
        savePatient.setSurname(pationAddRequest.getPatient().getSurname());
        savePatient.setAge(pationAddRequest.getPatient().getAge());
        savePatient.setDiagnosis(new Diagnosis(pationAddRequest.getDiagnosis()));
        savePatient.setSymptoms(Mappers.SymptomDtoToSymptom(pationAddRequest.getSymptoms()));

        patientRepository.save(savePatient);
        return null;
    }

    @DeleteMapping("/patient")
    public Responce delete(@RequestParam("patient_id") Integer patient_id) {
        patientRepository.deleteById(patient_id);
        return new Responce("SUCCESS");
    }

    @PostMapping("/calculate")
    public CalculateResponce calculate(@RequestBody CalculateRequest request) {
        return calculateService.calculate(request);
    }
}
