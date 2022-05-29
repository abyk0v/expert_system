package ru.byk0v.expert_system.calculations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.*;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionTreeServices {

    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private DatasetService datasetService;

    private List<String> symptomValues = List.of("False", "True");;
    private J48 tree;

    @PostConstruct
    private void init() {
        tree = new J48();
        try {
            tree.buildClassifier(datasetService.getDataset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CalculateResponce calculate(CalculateRequest request) {
        List<Symptom> symptoms = symptomRepository.findAll();
        List<Diagnosis> diagnoses = diagnosisRepository.findAll();

        double[] vals = new double[symptoms.size() + 1];
        for (int i = 0; i < vals.length - 1; i++) {
            if (request.getSymptoms().contains(symptoms.get(i))) {
                vals[i] = symptomValues.indexOf("True");
            } else {
                vals[i] = symptomValues.indexOf("False");
            }
        }
        vals[vals.length - 1] = 200.0;
        Instance instance = new DenseInstance(1.0, vals);
        // fix exception - weka.core.UnassignedDatasetException: Instance doesn't have access to a dataset!
        instance.setDataset(datasetService.getDataset());

        double[] results = new double[10];
        try {
            results = tree.distributionForInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("results: " + results);
//        System.out.println("results2: " + results2);
        System.out.print("tree results: ");
        for (int i = 0; i < results.length; i++) {
            System.out.print(results[i] + " ");
        }
        System.out.println();
        // todo код тут
        System.out.println("------------------------------------------------- ok!");

        // Формируем результат
        List<DiagnosisDto> diagnosisDtos = new ArrayList<>(diagnoses.size());
        for (int i = 0; i < diagnoses.size(); i++) {
            DiagnosisDto newDto = new DiagnosisDto(diagnoses.get(i));
            newDto.setProbability(results[i]);
            diagnosisDtos.add(newDto);
        }
        return new CalculateResponce(diagnosisDtos);
    }

}
