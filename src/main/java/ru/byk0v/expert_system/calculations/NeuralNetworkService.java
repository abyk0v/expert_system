package ru.byk0v.expert_system.calculations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.byk0v.expert_system.models.*;
import ru.byk0v.expert_system.repositories.DiagnosisRepository;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NeuralNetworkService {

    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private DatasetService datasetService;

    // Если при создании передавать этот список, то так не работает
    private List<String> symptomValues;
    private List<String> save_diagnosisValues;

    private MultilayerPerceptron multilayerPerceptron;
    private List<Diagnosis> diagnoses;

    @PostConstruct
    private void init() {
        System.out.println("NeuralNetworkService.init()");
        symptomValues = List.of("False", "True");
        diagnoses = diagnosisRepository.findAll();

        //Instance of NN
        multilayerPerceptron = new MultilayerPerceptron();
//        Setting Parameters
//        mlp.setLearningRate(0.3);
//        mlp.setMomentum(0.3);
//        mlp.setTrainingTime(2000);
        multilayerPerceptron.setTrainingTime(1000);
        multilayerPerceptron.setHiddenLayers("3,3");
//        mlp.setGUI(true);
//        mlp.buildClassifier(train);

        try {
//            Instances trainingSet = getInstances_TEST();
            Instances trainingSet = datasetService.getDataset();
            multilayerPerceptron.buildClassifier(trainingSet);

            var test = trainingSet.get(0);
            System.out.println(test.attribute(25));
            double[] rrr =  multilayerPerceptron.distributionForInstance(test);

            System.out.print("rrr: ");
            for (int i = 0; i < rrr.length; i++) {
                System.out.print(rrr[i] + " ");
            }
            System.out.println();

            // 2
            double eval =  multilayerPerceptron.classifyInstance(test);
            System.out.println("eval: " + eval);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("NeuralNetworkService.init() - END");
    }

//    public Instances getInstances_TEST() {
//        ArrayList<Attribute> atts = new ArrayList<Attribute>();
//
//        List<Symptom> symptoms = symptomRepository.findAll();
////        List<Diagnosis> diagnoses = diagnosisRepository.findAll();
//        List<Patient> patients = patientRepository.findAll();
//        for (int i = 0; i < symptoms.size(); i++) {
//            atts.add(new Attribute("symptom_" + i, List.of("False", "True")));
//        }
//        List<String> diagnosisValues = new ArrayList<>(diagnoses.size());
//        diagnoses.forEach(item -> {
//            diagnosisValues.add(item.getName());
//        });
//        atts.add(new Attribute("diagnosis", diagnosisValues));
//        save_diagnosisValues = diagnosisValues;
//
//        data = new Instances("symptom-diagnosis-relation", atts, 0);
//        System.out.println("data.numAttributes(): " + data.numAttributes());
//        for (int i = 0; i < patients.size(); i++) {
//            double[] vals = new double[data.numAttributes()];
//            for (int j = 0; j < data.numAttributes()-1; j++) {
//                if (patients.get(i).getSymptoms().contains(new Symptom(j))) {
//                    vals[j] = symptomValues.indexOf("True");
//                } else {
//                    vals[j] = symptomValues.indexOf("False");
//                }
//            }
//            vals[data.numAttributes()-1] = save_diagnosisValues.indexOf(patients.get(i).getDiagnosis().getName());
//            data.add(new DenseInstance(1.0, vals));
//        }
//
//        // Нужно обязательно установить атрибут класса. Данный атрибут будет выходами НС
//        data.setClassIndex(data.numAttributes() - 1);
//
//        System.out.println(data);
//
//        try(FileWriter writer = new FileWriter("MY.arff", false))
//        {
//            writer.write(data.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return data;
//    }

    public CalculateResponce calculate(CalculateRequest request) {
        List<Symptom> symptoms = symptomRepository.findAll();

        double[] vals = new double[symptoms.size() + 1];
        for (int i = 0; i < vals.length-1; i++) {
            if (request.getSymptoms().contains(symptoms.get(i))) {
                vals[i] = symptomValues.indexOf("True");
            } else {
                vals[i] = symptomValues.indexOf("False");
            }
        }
        vals[vals.length-1] = 200.0;
        Instance instance = new DenseInstance(1.0, vals);

        double[] results = new double[2];
        double results2 = -22.0;
        try {
            results = multilayerPerceptron.distributionForInstance(instance);
//            results2 = mlp.classifyInstance(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("results: " + results);
        System.out.println("results2: " + results2);
        System.out.print("results: ");
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
