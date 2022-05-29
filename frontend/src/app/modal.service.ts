import {Injectable} from "@angular/core";
import {NgbModal, NgbModalOptions, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {from, Observable} from "rxjs";
import {CalculateModalComponent} from "./modals/calculate-diagnosis/calculate-modal.component";
import {Symptom} from "./model/symptom.model";
import {Diagnosis} from "./model/diagnosis.model";

@Injectable()
export class ModalService {

    private defaultOptions: NgbModalOptions = {
        size: 'md',
    };

    constructor(
        private ngbModal: NgbModal
    ) {}

    calculateDialog(type: string, patientId: number, symptoms: Symptom[]): Observable<Diagnosis[]> {
        const modalRef: NgbModalRef = this.ngbModal.open(CalculateModalComponent, this.defaultOptions);
        modalRef.componentInstance.type = type;
        modalRef.componentInstance.patientId = patientId;
        modalRef.componentInstance.symptoms = symptoms;

        return from(modalRef.result);
    }

}
