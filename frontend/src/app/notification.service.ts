import {Injectable} from "@angular/core";
import {SnotifyService} from "ng-snotify";

@Injectable()
export class NotificationService {

    constructor(private snotifyService: SnotifyService) {
    }

    success(message: string): void {
        this.snotifyService.success(message);
    }

    error(message: string): void {
        this.snotifyService.error(message);
    }
}