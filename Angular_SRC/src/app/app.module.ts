import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Import ReactiveFormsModule
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
//import { CommonModule } from '@angular/common';
import { AppComponent } from './app.component';
import { provideHttpClient, withFetch } from '@angular/common/http'; // Import provideHttpClient

import { RegisterComponent } from './register/register.component';

import { LoginComponent } from './login/login.component'; // Import LoginComponent


// Import your standalone component
import { DashboardComponent } from './dashboard/dashboard.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent// Declare LoginComponent here
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    DashboardComponent,

    // Import standalone component here

  ],
  
  providers: [provideHttpClient(withFetch()) ],
  bootstrap: [AppComponent]
})
export class AppModule { }
