import {Component} from "@angular/core";
import {RestService} from "../../RestService";
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/patient.model";
import {Symptom} from "../../model/symptom.model";
import {NewPatientToServer} from "../../model/new-patient.model";

@Component({
    selector: 'patient-management',
    styleUrls: ['./patient-management.component.css'],
    templateUrl: './patient-management.component.html',
    providers: [RestService]
})
export class PatientManagementComponent {
    activePatientId: number;
    symptoms = [];
    diagnoses = [];

    // Индекс активного диагноза
    activeDiagnosis: number = -1;

    editingPatient: Patient = undefined;
    editingPatientSymptoms = new Map<number, Symptom>();
    // Данные для ввода
    surname: string = '';
    name: string = '';
    age: number;

    constructor(private activateRoute: ActivatedRoute,
                private restService: RestService) {
        this.activePatientId = activateRoute.snapshot.params['id'];

        this.restService.getDiagnosesList().subscribe((data) => {
            this.diagnoses.splice(0);
            this.diagnoses.push(...data);
        })

        this.restService.getSymptomsList().subscribe((data) => {
            this.symptoms.splice(0);
            this.symptoms.push(...data);
        })

        if (this.activePatientId !== undefined) {
            restService.getPatientById(this.activePatientId).subscribe(patient => {
                this.editingPatient = patient;
                this.surname = patient.surname;
                this.name = patient.name;
                this.age = patient.age;
            })

            restService.getSymptomsForPatientById(this.activePatientId).subscribe((data) => {
                data.forEach(item => {
                    this.editingPatientSymptoms.set(item.id, item);
                })
            })

            restService.getDiagnosisForPatientById(this.activePatientId).subscribe((data) => {
                this.diagnoses.forEach((item, index) => {
                    if (item.id === data.id) {
                        this.activeDiagnosis = index;
                        console.log('this.activeDiagnosis:', this.activeDiagnosis);
                        console.log('index:', index);
                        console.log('item.id:', item.id);
                        let tmp = new Symptom();
                        this.symptoms.push(tmp);
                        this.symptoms.pop();
                        console.log('activeDiagnosis:' + index);
                    }
                })
            })
        }
    }

    addPatient(): void {
        let savePatient;
        if (this.activePatientId !== undefined) {
            savePatient = new Patient(this.activePatientId, this.name, this.surname, this.age);
        } else {
            savePatient = new Patient(null, this.name, this.surname, this.age);
        }

        // Запоминаем симптомы
        let saveSymptoms: Symptom[] = [];
        for (let item of this.editingPatientSymptoms.values()) {
            saveSymptoms.push(item);
        }
        console.log(saveSymptoms);

        let newPatientToServer = new NewPatientToServer();
        newPatientToServer.patient = savePatient;
        newPatientToServer.diagnosis = this.diagnoses[this.activeDiagnosis];
        newPatientToServer.symptoms = saveSymptoms;

        console.log(newPatientToServer);

        console.log('this.restService.postPatient(newPatientToServer);');
        this.restService.postPatient(newPatientToServer).subscribe((data) => {
            console.log('SUCCESS!!');
            console.log(data);
        })
    }

    diagnosisChecked(index: number): void {
        console.log('diagnosisChecked()');

        if (this.activeDiagnosis === index) {
            this.activeDiagnosis = -1;
        } else {
            this.activeDiagnosis = index;
        }
    }

    checkedSymptom(symptom: Symptom, checked: Boolean): void {
        if (checked) {
            this.editingPatientSymptoms.set(symptom.id, symptom);
        } else {
            this.editingPatientSymptoms.delete(symptom.id);
        }
    }

    isCalculateDisabled(): boolean {
        return this.editingPatientSymptoms.size == 0;
    }

    calculate(): void {
        console.log('calculate(): void {}')
        if (this.symptoms.length == 0) {
            console.log('Список симптомов пуст. Выберите симптомы!');
            return;
        }
    }
}