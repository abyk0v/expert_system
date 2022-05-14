import { Injectable } from "@angular/core";
import { Diagnosis } from "./model/diagnosis.model";
import { HttpService } from "./http.service";

@Injectable()
export class StoreService {
    private isInitLoading: boolean  = true;
    private diagnoses: Diagnosis[] = [];

    constructor(private httpService: HttpService) {
    }

    public init(): void {
        this.httpService.getDiagnosesList().subscribe((data) => {
            this.diagnoses.splice(0); // empty the array, without reassigning it
            this.diagnoses.push(...data);
        })
    }

    public getIsInitLoading() : boolean {
        if (this.isInitLoading) {
            this.isInitLoading = false;
        }
        return this.isInitLoading;
    }

    public getDiagnoses(): Diagnosis[] {
        return this.diagnoses;
    }
}