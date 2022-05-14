import {Component} from "@angular/core";
import {HttpService} from "../../http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Patient} from "../../model/patient.model";
import {Symptom} from "../../model/symptom.model";
import {NewPatientToServer} from "../../model/new-patient.model";
import {ModalService} from "../../modal.service";
import {NotificationService} from "../../notification.service";
import {StoreService} from "../../store.service";
import {Diagnosis} from "../../model/diagnosis.model";

@Component({
    selector: 'patient-management',
    styleUrls: ['./patient-management.component.css'],
    templateUrl: './patient-management.component.html',
    providers: [HttpService, ModalService]
})
export class PatientManagementComponent {
    activePatientId: number;
    symptoms: Symptom[] = [];
    diagnoses: Diagnosis[] = [];

    // Индекс активного диагноза
    activeDiagnosisIndex: number = -1;

    editingPatient: Patient = undefined;
    editingPatientSymptoms = new Map<number, Symptom>();
    // Данные для ввода
    surname: string = '';
    name: string = '';
    age: number;

    constructor(private storeService: StoreService,
                private activateRoute: ActivatedRoute,
                private router: Router,
                private restService: HttpService,
                private modalService: ModalService,
                private notificationService: NotificationService) {
        this.activePatientId = activateRoute.snapshot.params['id'];

        if (this.storeService.getIsInitLoading()) {
            this.notificationService.success("Приложение готово к работе");
        }

        this.diagnoses = storeService.getDiagnoses();
        // this.restService.getDiagnosesList().subscribe((data) => {
        //     this.diagnoses.splice(0);
        //     this.diagnoses.push(...data);
        // })

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

                this.activeDiagnosisIndex = this.diagnoses.findIndex((item) => {
                    return item.id === this.editingPatient.diagnosis_id;
                })
            })

            restService.getSymptomsForPatientById(this.activePatientId).subscribe((data) => {
                data.forEach(item => {
                    this.editingPatientSymptoms.set(item.id, item);
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

        let saveSymptoms = this.getCheckedSymptoms();

        let newPatientToServer = new NewPatientToServer();
        newPatientToServer.patient = savePatient;
        newPatientToServer.diagnosis = this.diagnoses[this.activeDiagnosisIndex];
        newPatientToServer.symptoms = saveSymptoms;

        this.restService.postPatient(newPatientToServer).subscribe((data) => {
            if (this.activePatientId != undefined) {
                this.notificationService.success('Пациент успешно обновлен')
            } else {
                this.notificationService.success('Пациент успешно добавлен')
            }
            // Вернемся на главный экран
            this.router.navigate(['']);

        }, error => {
            this.notificationService.error('Что-то пошло не так')
        })
    }

    diagnosisChecked(index: number): void {
        console.log('diagnosisChecked()');

        if (this.activeDiagnosisIndex === index) {
            this.activeDiagnosisIndex = -1;
        } else {
            this.activeDiagnosisIndex = index;
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

        this.modalService.calculateDialog(this.activePatientId, this.getCheckedSymptoms()).subscribe(data => {
            // TODO добавить больше данных
            if (data !== null && data !== undefined) {
                let idСalculatedDiagnosis = data[0].id;
                this.activeDiagnosisIndex = this.diagnoses.findIndex((element, index, array) => {
                    return element.id === idСalculatedDiagnosis;
                })
            } else {
                // console.log('modalService.calculateDialog(): ' + null);
            }
        })
    }

    // Получить выбранные симптомы
    private getCheckedSymptoms(): Symptom[] {
        let saveSymptoms: Symptom[] = [];
        for (let item of this.editingPatientSymptoms.values()) {
            saveSymptoms.push(item);
        }
        return saveSymptoms;
    }
}