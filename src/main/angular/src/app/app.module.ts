import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, FormArray } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AutomobileListComponent } from './automobile-list/automobile-list.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AutomobileSearchComponent } from './automobile-search/automobile-search.component';
import { AutomobileCreateComponent } from './automobile-create/automobile-create.component';
import { AutomobileEditComponent } from './automobile-edit/automobile-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    AutomobileListComponent,
    DashboardComponent,
    AutomobileSearchComponent,
    AutomobileCreateComponent,
    AutomobileEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
