import {ChangeDetectorRef, Component} from "@angular/core";
import {RestService} from "../../RestService";
import {Patient} from "../../model/patient.model";

@Component({
    selector: 'home-page',
    styleUrls: ['./home-page.component.css'],
    templateUrl: './home-page.component.html',
    providers: [RestService]
})
export class HomePageComponent {
    patients = [];
    patientSymptoms = [];

    activeElement: number = -1;
    activePatientId: number = -1;
    isEditButtonDisabled: boolean = true;
    isCalculateButtonDisabled: boolean = true;

    constructor(private restService: RestService) {

        this.restService.getPatientList().subscribe((data) => {
            this.patients.splice(0); // empty the array, without reassigning it
            this.patients.push(...data);
            console.log(this.patients);
        })
    }

    click(index): void {
        console.log(index);
        this.activeElement = index;
        this.activePatientId = this.patients[index].id;

        this.restService.getSymptomsForPatientById(this.patients[index].id).subscribe((data) => {
            this.patientSymptoms.splice(0);
            this.patientSymptoms.push(...data);
        })

        this.isEditButtonDisabled = this.patients[index] == undefined;
        this.isCalculateButtonDisabled = this.patients[index] == undefined;
    }

    addPatient(): void {
        console.log("addPatient()");
        // this.restServce.getPatientList().subscribe((data) => {
        //     this.patients = data;
        //     console.log(this.patients);
        //     // this.patientsForRendering = this.listPatients;
        // })
    }

    patientDelete(): void {

    }
}