import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule }   from '@angular/common/http';

import { AppComponent }   from './app.component';
import { HomePageComponent } from "./pages/home/home-page.component";
import {PatientManagementComponent} from "./pages/patient-management/patient-management.component";
import {RouterModule, Routes} from "@angular/router";
import {CalculateModalComponent} from "./modals/calculate-diagnosis/calculate-modal.component";
import {HttpService} from "./http.service";
import {ModalService} from "./modal.service";
import {SideMenuComponent} from "./components/side-menu/side-menu.component";
import {NotificationComponent} from "./components/notification/notification.component";
import {NotificationService} from "./notification.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {StoreService} from "./store.service";

// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomePageComponent},
    { path: 'patient-management/:id', component: PatientManagementComponent},
    { path: 'patient-management', component: PatientManagementComponent},
    { path: '**', redirectTo: '/'}
];

@NgModule({
    imports:      [ BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(appRoutes),
        BrowserAnimationsModule, ToastrModule.forRoot() ],
    declarations: [ AppComponent, HomePageComponent, PatientManagementComponent, CalculateModalComponent,
        SideMenuComponent, NotificationComponent ],
    bootstrap:    [ AppComponent ],
    providers:    [ StoreService, HttpService, ModalService, NotificationService]
})
export class AppModule { }
