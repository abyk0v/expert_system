package ru.byk0v.expert_system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.*;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalculateService {

    DiagnosisRepository diagnosisRepository;
    SymptomRepository symptomRepository;
    PatientRepository patientRepository;

    public CalculateResponce calculate(CalculateRequest request) {
        // D - все диагнозы
        List<Diagnosis> D = diagnosisRepository.findAll();
        // P_D - массив вероятностей диагнозов. Начально значения для каждого диагноза 0.5
        List<DiagnosisDto> P_Di = D.stream()
                .map(item -> Mappers.diagnosisToDiagnosisDto(item))
                .collect(Collectors.toList());
        // A - симптомы пациента
        List<Symptom> A = request.getSymptoms();
        // B - симптомы которых нет у пациента
        List<Symptom> B = symptomRepository.findAll();
        B.removeAll(A);

        // Вычисляем вероятности для каждого диагноза
        for (DiagnosisDto d: P_Di) {
            System.out.println("------------------------- Diagnosis: " + d.getDescription());
            for (Symptom a: A) {
                double result = formula_one(d, a);
                System.out.println(a.getDescription() + "- for (Symptom a: A), result: " + result);
                if (result == 0.0 || Double.isNaN(result)) {
                    continue;
                }
                d.setProbability(result);
            }
            for (Symptom b: B) {
                double result = formula_two(d, b);
                System.out.println(b.getDescription() + "- for (Symptom b: B), result: " + result);
                if (result == 0.0 || Double.isNaN(result)) {
                    continue;
                }
                d.setProbability(result);
            }
        }

        return new CalculateResponce(P_Di);
    }

    private double formula_one(DiagnosisDto Di, Symptom s) {
        double PDi = Di.getProbability();
        double PSD = PSD(Di, s);
        double PSnotD = PSnotD(Di, s);
        double tmp = ( PDi*PSD ) / ( PDi*PSD + (1 - PDi)*PSnotD );
        return tmp;
    }

    private double formula_two(DiagnosisDto Di, Symptom s) {
        double PDi = Di.getProbability();
        double PSD = PSD(Di, s);
        double PSnotD = PSnotD(Di, s);
        double tmp = ( PDi*(1 - PSD) ) / ( PDi*(1 - PSD) + (1 - PDi)*(1 - PSnotD) );
        return tmp;
    }

    private double PSD(DiagnosisDto Di, Symptom A) {
        // Число пациентов с диагнозом Di (β1)
        List<Patient> patients = patientRepository.patientsWithDiagnosisById(Di.getId());
        // Число пациентов с диагнозом Di и с симптомом A (α1)
        List<Patient> test = patientRepository.specificPatientsWithSymptomById(patients, A.getId());
        return (double) test.size() / patients.size();
    }

    private double PSnotD(DiagnosisDto Di, Symptom A) {
        // Число пациентов БЕЗ диагноза Di (β2)
        List<Patient> patients = patientRepository.patientsWithoutDiagnosisById(Di.getId());
        // Число пациентов БЕЗ диагноза Di, с симптомом A (α2)
        List<Patient> test = patientRepository.alpha2(patients, A.getId());
        return (double) test.size() / patients.size();
    }

}
