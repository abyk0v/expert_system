import {Component, Input, OnInit} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../http.service";
import {Symptom} from "../../model/symptom.model";
import {Diagnosis} from "../../model/diagnosis.model";

@Component({
    selector: 'calculate-modal',
    styleUrls: ['./calculate-modal.component.css'],
    templateUrl: './calculate-modal.component.html',
})
export class CalculateModalComponent implements OnInit {

    @Input() patientId: number;
    @Input() symptoms: Symptom[];

    isloading: boolean = true;
    calculateDiagnoses: Diagnosis[];

    isDetail: boolean = false
    detailButtonTextFull: string = "Подробнее ▼";
    detailButtonTextCollapsing: string = "Подробнее ▲";
    detailRenderText: string = this.detailButtonTextFull;

    constructor(
        private activeModal: NgbActiveModal,
        private httpService: HttpService
    ) {
    }

    ngOnInit() {
        this.httpService.calculate(this.patientId, this.symptoms).subscribe((data) => {
            this.isloading = false;
            // сортанем
            data.diagnoses.sort((a, b) => {
                if (a.probability > b.probability) {
                    return -1;
                }
                if (a.probability < b.probability) {
                    return 1;
                }
                // a должно быть равным b
                return 0;
            })

            this.calculateDiagnoses = data.diagnoses;
            // data.diagnoses.forEach(item => {
            //     console.log('CalculateModalComponent.ngOnInit(): ' + item.description + ', ' + item.probability);
            // })
        })
    }

    accept(): void {
        console.log('CalculateModalComponent: accept()')
        this.activeModal.close(this.calculateDiagnoses);
    }

    cancel(): void {
        console.log('CalculateModalComponent: cancel()');
        this.activeModal.close(null);
    }

    isAcceptDisabled(): boolean {
        return this.isloading;
    }

    detail(): void {
        if (this.isDetail) {
            this.detailRenderText = this.detailButtonTextCollapsing;
        } else {
            this.detailRenderText = this.detailButtonTextFull;
        }
        this.isDetail = !this.isDetail;
    }
}