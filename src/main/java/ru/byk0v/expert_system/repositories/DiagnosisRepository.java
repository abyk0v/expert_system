package ru.byk0v.expert_system.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.byk0v.expert_system.models.Diagnosis;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {
    @Query(value = "select id, description " +
            "from diagnosis ",
            nativeQuery = true)
    public List<Diagnosis> findAll();

    @Query(value = "select id, description " +
            "from diagnosis join patient_diagnosis " +
            "on patient_diagnosis.id_p = ?1 and diagnosis.id = patient_diagnosis.id_d",
            nativeQuery = true)
    public Optional<Diagnosis> getDiagnosisForPatient(Integer patient_id);
}
