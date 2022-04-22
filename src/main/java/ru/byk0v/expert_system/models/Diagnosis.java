package ru.byk0v.expert_system.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "diagnosis")
@Getter
@Setter
@NoArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "patient_diagnosis"
                , joinColumns = @JoinColumn(name = "id_d")
                , inverseJoinColumns = @JoinColumn(name = "id_p"))
    private List<Patient> patients;

    @ManyToMany
    @JoinTable(name = "diagnosis_symptom"
            , joinColumns = @JoinColumn(name = "id_d")
            , inverseJoinColumns = @JoinColumn(name = "id_s"))
    private List<Symptom> symptoms;

    public Diagnosis(String description) {
        this.description = description;
    }
}
