package ru.byk0v.expert_system.calculations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.Diagnosis;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatasetService {

    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    private Instances data;

    private List<String> save_diagnosisValues;
    private List<String> symptomValues = List.of("False", "True");

    @PostConstruct
    private void init() {
        data = createInstances();
    }

    public Instances getDataset() {
        return data;
    }

    private Instances createInstances() {
        ArrayList<Attribute> atts = new ArrayList<Attribute>();

        List<Symptom> symptoms = symptomRepository.findAll();
        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
//        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        for (int i = 0; i < symptoms.size(); i++) {
            atts.add(new Attribute("symptom_" + i, List.of("False", "True")));
        }
        List<String> diagnosisValues = new ArrayList<>(diagnoses.size());
        diagnoses.forEach(item -> {
            diagnosisValues.add(item.getName());
        });
        atts.add(new Attribute("diagnosis", diagnosisValues));
        save_diagnosisValues = diagnosisValues;

        data = new Instances("symptom-diagnosis-relation", atts, 0);
        System.out.println("data.numAttributes(): " + data.numAttributes());
        for (int i = 0; i < patients.size(); i++) {
            double[] vals = new double[data.numAttributes()];
            for (int j = 0; j < data.numAttributes()-1; j++) {
                if (patients.get(i).getSymptoms().contains(new Symptom(j))) {
                    vals[j] = symptomValues.indexOf("True");
                } else {
                    vals[j] = symptomValues.indexOf("False");
                }
            }
            vals[data.numAttributes()-1] = save_diagnosisValues.indexOf(patients.get(i).getDiagnosis().getName());
            data.add(new DenseInstance(1.0, vals));
        }

        // Нужно обязательно установить атрибут класса. Данный атрибут будет выходами НС
        data.setClassIndex(data.numAttributes() - 1);

        System.out.println(data);

        try(FileWriter writer = new FileWriter("MY.arff", false))
        {
            writer.write(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
