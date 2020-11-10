import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileEditComponent } from './automobile-edit.component';
import {HttpClientModule} from '@angular/common/http';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';

describe('AutomobileEditComponent', () => {
  let component: AutomobileEditComponent;
  let fixture: ComponentFixture<AutomobileEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileEditComponent ],
      imports : [HttpClientModule, FormsModule, ReactiveFormsModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutomobileEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
