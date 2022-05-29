package ru.byk0v.expert_system.calculations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.calculations.CalculateService;
import ru.byk0v.expert_system.models.CalculateRequest;
import ru.byk0v.expert_system.models.CalculateResponce;
import ru.byk0v.expert_system.models.DiagnosisDto;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.repositories.PatientRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReCalculateService {

    private PatientRepository patientRepository;
    private CalculateService calculateService;
    private NeuralNetworkService neuralNetworkService;
    private DecisionTreeServices decisionTreeServices;

    public Double reCalculate() {
        List<Patient> patients = patientRepository.findAll();

        int numberOfError = 0;
        for (Patient patient: patients) {
            CalculateResponce result =
                    calculateService.calculate(new CalculateRequest(patient.getId(), patient.getSymptoms()));
            System.out.print(patient.getId() + " ");
            Optional<DiagnosisDto> diagnosisDto = result.getDiagnoses()
                    .stream().sorted(Comparator.reverseOrder()).findFirst();
            if (diagnosisDto.isPresent()) {
                DiagnosisDto diagnosisDtoItem = diagnosisDto.get();

//                System.out.print(", current diagnosis: " + patient.getDiagnosis().getName());
//                System.out.println(", calculate diagnosis: " + patient.getDiagnosis().getName());
//                System.out.println("------------------------------------------------------------------------------");

                if (diagnosisDtoItem.getId() != null &&
                        !diagnosisDtoItem.getId().equals(patient.getDiagnosis().getId())) {
                    numberOfError++;
                }
            }
        }

        System.out.println();
        System.out.print("numberOfError: " + numberOfError);
        System.out.println(", general number: " + patients.size());
        return 0.0;
    }

    public Double reCalculateNS() {
        List<Patient> patients = patientRepository.findAll();

        int numberOfError = 0;
        for (Patient patient: patients) {
            CalculateResponce result =
                    neuralNetworkService.calculate(new CalculateRequest(patient.getId(), patient.getSymptoms()));
//            System.out.print("patient.id: " + patient.getId());
            Optional<DiagnosisDto> diagnosisDto = result.getDiagnoses()
                    .stream().sorted(Comparator.reverseOrder()).findFirst();
            if (diagnosisDto.isPresent()) {
                DiagnosisDto diagnosisDtoItem = diagnosisDto.get();

//                System.out.print(", current diagnosis: " + patient.getDiagnosis().getName());
//                System.out.println(", calculate diagnosis: " + diagnosisDtoItem.getName());
//                System.out.println("------------------------------------------------------------------------------");

                if (diagnosisDtoItem.getId() != null &&
                        !diagnosisDtoItem.getId().equals(patient.getDiagnosis().getId())) {
                    numberOfError++;
                }
            }
        }

        System.out.print("numberOfError: " + numberOfError);
        System.out.println(", general number: " + patients.size());
        return 0.0;
    }

    public Double reCalculateDecisionTree() {
        List<Patient> patients = patientRepository.findAll();

        int numberOfError = 0;
        for (Patient patient: patients) {
            CalculateResponce result =
                    decisionTreeServices.calculate(new CalculateRequest(patient.getId(), patient.getSymptoms()));
//            System.out.print("patient.id: " + patient.getId());
            Optional<DiagnosisDto> diagnosisDto = result.getDiagnoses()
                    .stream().sorted(Comparator.reverseOrder()).findFirst();
            if (diagnosisDto.isPresent()) {
                DiagnosisDto diagnosisDtoItem = diagnosisDto.get();

//                System.out.print(", current diagnosis: " + patient.getDiagnosis().getName());
//                System.out.println(", calculate diagnosis: " + diagnosisDtoItem.getName());
//                System.out.println("------------------------------------------------------------------------------");

                if (diagnosisDtoItem.getId() != null &&
                        !diagnosisDtoItem.getId().equals(patient.getDiagnosis().getId())) {
                    numberOfError++;
                }
            }
        }

        System.out.print("numberOfError: " + numberOfError);
        System.out.println(", general number: " + patients.size());
        return 0.0;
    }
}
