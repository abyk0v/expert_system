package ru.byk0v.expert_system.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.byk0v.expert_system.models.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    @Query(value = "select id, name, surname, age " +
            "from Patient ",
            nativeQuery = true)
    public List<Patient> findAll();
}
