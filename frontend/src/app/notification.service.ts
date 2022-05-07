import {Injectable} from "@angular/core";
import {ToastrService} from "ngx-toastr";

@Injectable()
export class NotificationService {

    constructor(private toastr: ToastrService) {
    }

    success(message: string): void {
        this.toastr.success(message);
        // this.toastr.warning('Test', 'Title');
        // this.toastr.error('Test');
        // this.toastr.info('Test');
        // this.toastr.show('Test');
    }

    error(message: string): void {
        this.toastr.error(message);
    }
}