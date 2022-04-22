package ru.byk0v.expert_system.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;

    @ManyToMany
    @JoinTable(name = "patient_diagnosis"
            , joinColumns = @JoinColumn(name = "id_p")
            , inverseJoinColumns = @JoinColumn(name = "id_d"))
    private List<Diagnosis> diagnoses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_symptom"
            , joinColumns = @JoinColumn(name = "id_p")
            , inverseJoinColumns = @JoinColumn(name = "id_s"))
    private List<Symptom> symptoms;

    public Patient(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public void addSymptom(Symptom symptom) {
        if (symptoms == null) {
            symptoms = new ArrayList<>();
        }

        symptoms.add(symptom);
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        if (diagnoses == null) {
            diagnoses = new ArrayList<>();
        }

        diagnoses.add(diagnosis);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", diagnoses=" + diagnoses +
                ", symptoms=" + symptoms +
                '}';
    }
}
