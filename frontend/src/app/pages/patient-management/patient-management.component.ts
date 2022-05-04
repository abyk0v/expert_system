import {Component} from "@angular/core";
import {HttpService} from "../../http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Patient} from "../../model/patient.model";
import {Symptom} from "../../model/symptom.model";
import {NewPatientToServer} from "../../model/new-patient.model";
import {ModalService} from "../../modal.service";
import {NotificationService} from "../../notification.service";
import {StoreService} from "../../store.service";

@Component({
    selector: 'patient-management',
    styleUrls: ['./patient-management.component.css'],
    templateUrl: './patient-management.component.html',
    providers: [HttpService, ModalService]
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

        let saveSymptoms = this.getCheckedSymptoms();

        let newPatientToServer = new NewPatientToServer();
        newPatientToServer.patient = savePatient;
        newPatientToServer.diagnosis = this.diagnoses[this.activeDiagnosis];
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

        this.modalService.calculateDialog(this.activePatientId, this.getCheckedSymptoms()).subscribe(data => {
            // TODO добавить больше данных
            if (data !== null && data !== undefined) {
                // console.log('modalService.calculateDialog()');
                // data.forEach(item => {
                //     console.log(item);
                // })
                let idСalculatedDiagnosis = data[0].id;
                this.activeDiagnosis = this.diagnoses.findIndex((element, index, array) => {
                    if (element.id === idСalculatedDiagnosis) {
                        return true;
                        console.log('new index for active diagnosis: ' + index);
                    }
                    return false;
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