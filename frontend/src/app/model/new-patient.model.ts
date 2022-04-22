import {Patient} from "./patient.model";
import {Symptom} from "./symptom.model";
import {Diagnosis} from "./diagnosis.model";

export class NewPatientToServer{
    patient: Patient;
    diagnosis: Diagnosis;
    symptoms: Symptom[];
}