import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/login/pages/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input'; 
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button'; 
import {MatCardModule} from '@angular/material/card';
import { RegistrationComponent } from './features/registration/pages/registration/registration.component'; 
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {MatSnackBarModule} from '@angular/material/snack-bar'; 
import { DashboardComponent } from './features/dashboard/pages/dashboard/dashboard.component';

@NgModule({ declarations: [
        AppComponent,
        LoginComponent,
        RegistrationComponent,
        DashboardComponent,
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule,
        MatDatepickerModule,
        MatSnackBarModule,
        MatNativeDateModule], providers: [
        MatDatepickerModule,
        { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
        { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' },
        provideHttpClient(withInterceptorsFromDi()),
    ] })
export class AppModule { }
