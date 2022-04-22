import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule }   from '@angular/common/http';

import { AppComponent }   from './app.component';
import { HomePageComponent } from "./pages/home/home-page.component";
import {PatientManagementComponent} from "./pages/patient-management/patient-management.component";
import {RouterModule, Routes} from "@angular/router";

// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomePageComponent},
    { path: 'patient-management/:id', component: PatientManagementComponent},
    { path: 'patient-management', component: PatientManagementComponent},
    { path: '**', redirectTo: '/'}
];

@NgModule({
    imports:      [ BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(appRoutes) ],
    declarations: [ AppComponent, HomePageComponent, PatientManagementComponent ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }
