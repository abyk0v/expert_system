import {HttpClient} from '@angular/common/http';
import {Patient} from "./model/patient.model";
import {Injectable} from "@angular/core";
import {Symptom} from "./model/symptom.model";
import {Observable} from "rxjs";
import {Diagnosis} from "./model/diagnosis.model";
import {NewPatientToServer} from "./model/new-patient.model";
import {Response} from "./model/response.model";
import {CalculateRequestModel} from "./model/calculate-request.model";
import {CalculateResponceModel} from "./model/calculate-responce.model";

@Injectable()
export class HttpService {

    protected apiUrls = {
        base: 'http://localhost:8080',
        patientList: '/patients',
        patientById: '/patient',
        symptomList: '/symptoms',
        diagnosisList: '/diagnoses',
        symptomsForPatientById: '/symptoms-for-patient-by-id',
        diagnosisForPatientById: '/diagnosis-for-patient-by-id',

        patient: '/patient',
        calculate: '/calculate'
    };

    constructor(private http: HttpClient){}

    ngOnInit() {
        console.log('HttpService.ngOnInit()');
    }

    getPatientById(patientId: number): Observable<Patient> {
        return this.http.get<Patient>(this.apiUrls.base + this.apiUrls.patientById +
            '?patient_id=' + patientId);
    }

    getPatientList(): Observable<Patient[]> {
        return this.http.get<Patient[]>(this.apiUrls.base + this.apiUrls.patientList);
    }

    getSymptomsForPatientById(patientId: number): Observable<Symptom[]> {
        return this.http.get<Symptom[]>(this.apiUrls.base + this.apiUrls.symptomsForPatientById +
            '?patient_id=' + patientId);
    }

    getSymptomsList(): Observable<Symptom[]> {
        return this.http.get<Symptom[]>(this.apiUrls.base + this.apiUrls.symptomList);
    }

    getDiagnosesList(): Observable<Diagnosis[]> {
        return this.http.get<Diagnosis[]>(this.apiUrls.base + this.apiUrls.diagnosisList);
    }

    // TODO проверить актуальность метода
    getDiagnosisForPatientById(patientId: number): Observable<Diagnosis> {
        return this.http.get<Diagnosis>(this.apiUrls.base + this.apiUrls.diagnosisForPatientById +
            '?patient_id=' + patientId);
    }

    postPatient(savePatient: NewPatientToServer): Observable<NewPatientToServer> {
        return this.http.post<NewPatientToServer>(this.apiUrls.base + this.apiUrls.patient, savePatient);
    }

    deletePatientById(patientId: number): Observable<any> {
        return this.http.delete<Response>(this.apiUrls.base + this.apiUrls.patientById +
            '?patient_id=' + patientId);
    }

    calculate(patientId: number, symptoms: Symptom[]): Observable<CalculateResponceModel> {
        let request = new CalculateRequestModel(patientId, symptoms);
        return this.http.post<CalculateResponceModel>(this.apiUrls.base + this.apiUrls.calculate, request);
    }
}