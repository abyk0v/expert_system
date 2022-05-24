package ru.byk0v.expert_system.calculations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.Diagnosis;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NeuralNetworkService {

    private SymptomRepository symptomRepository;
    private PatientRepository patientRepository;
    private DiagnosisRepository diagnosisRepository;

    // Если при создании передавать этот список, тоак не работает
    private List<String> symptomValues;
    private List<String> save_diagnosisValues;

    @PostConstruct
    private void init() {
        System.out.println("NeuralNetworkService.init()");
        symptomValues = List.of("True", "False");

        //Instance of NN
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        //Setting Parameters
        mlp.setLearningRate(0.1);
        mlp.setMomentum(0.2);
        mlp.setTrainingTime(2000);
        mlp.setHiddenLayers("3");
//        mlp.setGUI(true);
//        mlp.buildClassifier(train);

        try {
            Instances trainingSet = getInstances_TEST();
//            mlp.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("NeuralNetworkService.init() - END");
    }

    public Instances getInstances_TEST() {
        ArrayList<Attribute> atts = new ArrayList<Attribute>();

        List<Symptom> symptoms = symptomRepository.findAll();
        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
        List<Patient> patients = patientRepository.findAll();
        for (int i = 0; i < symptoms.size(); i++) {
            atts.add(new Attribute("symptom_" + i, List.of("True", "False")));
        }
        List<String> diagnosisValues = new ArrayList<>(diagnoses.size());
        diagnoses.forEach(item -> {
            diagnosisValues.add(item.getName());
        });
        atts.add(new Attribute("diagnosis", diagnosisValues));
        save_diagnosisValues = diagnosisValues;

        Instances data = new Instances("test-relation", atts, 0);
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

        System.out.println(data);

        try(FileWriter writer = new FileWriter("MY.arff", false))
        {
            writer.write(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public Instances getInstances() throws Exception {
        ArrayList<Attribute>	atts;
        ArrayList<Attribute>	attsRel;
        ArrayList<String>       attVals;
        ArrayList<String>		attValsRel;
        Instances           data;
        Instances			dataRel;
        double[]			vals;
        double[]			valsRel;
        int				i;

        // 1. set up attributes
        atts = new ArrayList<Attribute>();
        // -- numeric
        atts.add(new Attribute("att1"));
        // -- nominal
        attVals = new ArrayList<String>();
        for (i = 0; i < 5; i++)
            attVals.add("val" + (i+1));
        atts.add(new Attribute("att2", attVals));
        // -- string
        atts.add(new Attribute("att3", (ArrayList<String>) null));
        // -- date
        atts.add(new Attribute("att4", "yyyy-MM-dd"));
        // -- relational
        attsRel = new ArrayList<Attribute>();
        // -- numeric
        attsRel.add(new Attribute("att5.1"));
        // -- nominal
        attValsRel = new ArrayList<String>();
        for (i = 0; i < 5; i++)
            attValsRel.add("val5." + (i+1));
        attsRel.add(new Attribute("att5.2", attValsRel));
        dataRel = new Instances("att5", attsRel, 0);
        atts.add(new Attribute("att5", dataRel, 0));

        // 2. create Instances object
        data = new Instances("MyRelation", atts, 0);

        // 3. fill with data
        // first instance
        vals = new double[data.numAttributes()];
        // - numeric
        vals[0] = Math.PI;
        // - nominal
        vals[1] = attVals.indexOf("val3");
        // - string
        vals[2] = data.attribute(2).addStringValue("This is a string!");
        // - date
        vals[3] = data.attribute(3).parseDate("2001-11-09");
        // - relational
        dataRel = new Instances(data.attribute(4).relation(), 0);
        // -- first instance
        valsRel = new double[2];
        valsRel[0] = Math.PI + 1;
        valsRel[1] = attValsRel.indexOf("val5.3");
        dataRel.add(new DenseInstance(1.0, valsRel));
        // -- second instance
        valsRel = new double[2];
        valsRel[0] = Math.PI + 2;
        valsRel[1] = attValsRel.indexOf("val5.2");
        dataRel.add(new DenseInstance(1.0, valsRel));
        vals[4] = data.attribute(4).addRelation(dataRel);
        // add
        data.add(new DenseInstance(1.0, vals));

        // second instance
        vals = new double[data.numAttributes()];  // important: needs NEW array!
        // - numeric
        vals[0] = Math.E;
        // - nominal
        vals[1] = attVals.indexOf("val1");
        // - string
        vals[2] = data.attribute(2).addStringValue("And another one!");
        // - date
        vals[3] = data.attribute(3).parseDate("2000-12-01");
        // - relational
        dataRel = new Instances(data.attribute(4).relation(), 0);
        // -- first instance
        valsRel = new double[2];
        valsRel[0] = Math.E + 1;
        valsRel[1] = attValsRel.indexOf("val5.4");
        dataRel.add(new DenseInstance(1.0, valsRel));
        // -- second instance
        valsRel = new double[2];
        valsRel[0] = Math.E + 2;
        valsRel[1] = attValsRel.indexOf("val5.1");
        dataRel.add(new DenseInstance(1.0, valsRel));
        vals[4] = data.attribute(4).addRelation(dataRel);
        // add
        data.add(new DenseInstance(1.0, vals));

        // 4. output data
        System.out.println(data);
        return data;
    }
}
