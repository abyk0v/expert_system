import { Component } from '@angular/core';
import {HttpService} from "./http.service";
import {StoreService} from "./store.service";

@Component({
    selector: 'purchase-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    providers: [HttpService]
})
export class AppComponent {

    constructor(private storeService: StoreService) {
        storeService.init();
    }
}