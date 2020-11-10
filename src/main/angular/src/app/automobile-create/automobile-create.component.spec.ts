import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileCreateComponent } from './automobile-create.component';
import {HttpClientModule} from '@angular/common/http';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';

describe('AutomobileCreateComponent', () => {
  let component: AutomobileCreateComponent;
  let fixture: ComponentFixture<AutomobileCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileCreateComponent ],
      imports : [HttpClientModule, FormsModule, ReactiveFormsModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutomobileCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
