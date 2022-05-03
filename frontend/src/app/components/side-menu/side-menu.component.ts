import {Component, Input, OnInit} from "@angular/core";

@Component({
    selector: 'side-menu',
    styleUrls: ['./side-menu.component.css'],
    templateUrl: './side-menu.component.html',
})
export class SideMenuComponent {

    @Input() activePage: string = '';

    mainBlockClick(): void {

    }

    addAndEditPatientBlockClick(): void {

    }

    addAndEditDiagnosisAndSympomBlockClick(): void {

    }

    helpBlockClick(): void {

    }
}