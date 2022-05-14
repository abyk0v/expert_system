package ru.byk0v.expert_system.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.byk0v.expert_system.models.Symptom;

import java.util.List;

@Repository
public interface SymptomRepository extends CrudRepository<Symptom, Integer> {

    @Query(value = "select id, description " +
            "from Symptom ",
            nativeQuery = true)
    public List<Symptom> findAll();

//    @Query(value = "select id, description " +
//            "from symptom inner join symptom.patient_symptom " +
//            "where symptom.patient_symptom.id_p = :patient_id",
//            nativeQuery = true)
//@Query("FROM Student s " +
//        "JOIN FETCH s.extras e " +
//        "JOIN FETCH e.address")
//
//    @Query("FROM Symptom s " +
//            "JOIN FETCH s.patients " +
//            "WHERE s.patients.")

    @Query(value = "select id, description " +
            "from symptom join patient_symptom " +
            "on patient_symptom.id_p = ?1 and symptom.id = patient_symptom.id_s",
            nativeQuery = true)
    public List<Symptom> findAllForPatient(Integer patient_id);

    @Query(value = "select id, description " +
            "from symptom join diagnosis_symptom " +
            "on diagnosis_symptom.id_d = ?1 and symptom.id = diagnosis_symptom.id_s",
            nativeQuery = true)
    public List<Symptom> findAllForDiagnosis(Integer diagnosis_id);
}
