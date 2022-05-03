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
@Table(name = "symptom")
@Getter
@Setter
@NoArgsConstructor
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "diagnosis_symptom"
            , joinColumns = @JoinColumn(name = "id_s")
            , inverseJoinColumns = @JoinColumn(name = "id_d"))
    private List<Diagnosis> diagnoses;

    @ManyToMany
    @JoinTable(name = "patient_symptom"
            , joinColumns = @JoinColumn(name = "id_s")
            , inverseJoinColumns = @JoinColumn(name = "id_p"))
    private List<Patient> patients;

    public Symptom(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Symptom)) {
            return false;
        }

        return this.id.equals(((Symptom) obj).getId());
    }
}
