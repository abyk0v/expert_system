import {Symptom} from "./symptom.model";

export class CalculateRequestModel {
    patientId: number;
    symptoms: Symptom[];

    constructor(patientId: number, symptoms: Symptom[]) {
        this.patientId = patientId;
        this.symptoms = symptoms;
    }
}