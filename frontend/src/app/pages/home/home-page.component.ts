import { Component } from "@angular/core";
import { HttpService } from "../../http.service";
import { Diagnosis } from "../../model/diagnosis.model";
import { NotificationService } from "../../notification.service";
import { StoreService } from "../../store.service";
import { Patient } from "../../model/patient.model";
import { Symptom } from "../../model/symptom.model";

@Component({
    selector: 'home-page',
    styleUrls: ['./home-page.component.css'],
    templateUrl: './home-page.component.html',
    providers: [HttpService]
})
export class HomePageComponent {
    patients: Patient[] = [];
    patientSymptoms: Symptom[] = [];
    diagnoses: Diagnosis[] = [];
    activeDiagnosisIndex: number = -1;
    activePatientDiagnosis: Diagnosis;

    activeElement: number = -1;
    activePatientId: number = -1;
    isEditButtonDisabled: boolean = true;

    constructor(private storeService: StoreService,
                private restService: HttpService,
                private notificationService: NotificationService) {

        this.restService.getPatientList().subscribe(
            (data) => {
            this.patients.splice(0); // empty the array, without reassigning it
            this.patients.push(...data);
            console.log(this.patients);

            if (this.storeService.getIsInitLoading()) {
                this.notificationService.success("Приложение готово к работе");
            }
        },
            error => {
                this.notificationService.error("Нет связи с сервером");
            })

        this.diagnoses = storeService.getDiagnoses();
    }

    click(index): void {
        console.log(index);
        this.activeElement = index;
        this.activePatientId = this.patients[index].id;

        this.restService.getSymptomsForPatientById(this.patients[index].id).subscribe((data) => {
            this.patientSymptoms.splice(0);
            this.patientSymptoms.push(...data);
        })

        this.activeDiagnosisIndex = this.diagnoses.findIndex((item) => {
            return item.id == this.patients[index].diagnosis_id;
        })

        this.isEditButtonDisabled = this.patients[index] == undefined;
    }

    patientDelete(): void {
        this.restService.deletePatientById(this.activePatientId).subscribe((data) => {
            let delIndex = this.patients.findIndex(item => {
                return item.id === this.activePatientId
            });
            this.patients.splice(delIndex, 1);

            this.activeElement = -1;
            this.activePatientId = undefined;
            this.activePatientDiagnosis = undefined;
            this.patientSymptoms = [];
            this.isEditButtonDisabled = true;

            this.notificationService.success('Пациент удален');
        }, error => {
            this.notificationService.success('Ошибка при удалении пациента');
        })
    }
}