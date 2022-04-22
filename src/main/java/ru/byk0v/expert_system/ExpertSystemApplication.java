package ru.byk0v.expert_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.byk0v.expert_system.models.Patient;
import ru.byk0v.expert_system.models.Symptom;
import ru.byk0v.expert_system.repositories.PatientRepository;
import ru.byk0v.expert_system.repositories.SymptomRepository;

import java.util.List;

@SpringBootApplication
public class ExpertSystemApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ExpertSystemApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ExpertSystemApplication.class, args);

//		PatientRepository patientRepository = context.getBean(PatientRepository.class);
//		SymptomRepository symptomRepository = context.getBean(SymptomRepository.class);
//
//		Patient patient = new Patient("Aleskandr", "Byk0v", 26L);
//		Symptom symptom = new Symptom("Усталость");
//
//		symptomRepository.save(symptom);
//
//		patient.addSymptom(symptom);
//		patientRepository.save(patient);
//
//		List<Patient> patients = patientRepository.findAll();
//		if (patients.size() > 0) {
//			for (Patient item : patients) {
//				System.out.println(item);
//			}
//		} else {
//			System.out.println("patient.size == 0");
//		}
	}

}
