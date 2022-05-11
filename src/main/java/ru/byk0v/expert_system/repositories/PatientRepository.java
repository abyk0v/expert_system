package ru.byk0v.expert_system.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.byk0v.expert_system.models.Patient;

import java.util.Collection;
import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    @Query(value = "select id, name, surname, age, diagnosis " +
            "from Patient ",
            nativeQuery = true)
    public List<Patient> findAll();

    /*
        Пациенты с диагнозом Di (β1)
     */
    @Query(value = "SELECT id, name, surname, age " +
            "FROM patient JOIN patient_diagnosis " +
            "ON patient_diagnosis.id_p = patient.id AND patient_diagnosis.id_d = ?1",
            nativeQuery = true)
    public List<Patient> patientsWithDiagnosisById(int diagnosisId);

    /*
        Пациенты с диагнозом Di и с симптомом A (α1)
     */
    @Query(value = "SELECT id, name, surname, age " +
            "FROM patient JOIN patient_symptom " +
            "ON patient_symptom.id_p = patient.id AND patient_symptom.id_s = ?2 " +
            "WHERE patient.id IN ?1",
            nativeQuery = true)
    public List<Patient> specificPatientsWithSymptomById(Collection<Patient> patients, Integer symptomId);

    /*
        Пациенты БЕЗ диагноза Di (β2)
     */
    @Query(value = "SELECT id, name, surname, age " +
            "FROM patient JOIN patient_diagnosis " +
            "ON patient_diagnosis.id_p = patient.id AND patient_diagnosis.id_d <> ?1",
            nativeQuery = true)
    public List<Patient> patientsWithoutDiagnosisById(int diagnosisId);

    /*
        Пациенты БЕЗ диагноза Di, с симптомом A (α2)
     */
    @Query(value = "SELECT id, name, surname, age " +
            "FROM patient JOIN patient_symptom " +
            "ON patient_symptom.id_p = patient.id AND patient_symptom.id_s = ?2 " +
            "WHERE patient.id IN ?1",
            nativeQuery = true)
    public List<Patient> alpha2(Collection<Patient> patientsWithoutDiagnosis, Integer symptomId);
}
