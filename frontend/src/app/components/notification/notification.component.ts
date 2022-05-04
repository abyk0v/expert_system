import {Component, Input} from "@angular/core";

@Component({
    selector: 'notification',
    styleUrls: ['./notification.component.css'],
    templateUrl: './notification.component.html',
})
export class NotificationComponent {

    @Input() message: string = '';
}