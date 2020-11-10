import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileSearchComponent } from './automobile-search.component';
import {HttpClientModule} from '@angular/common/http';

describe('AutomobileSearchComponent', () => {
  let component: AutomobileSearchComponent;
  let fixture: ComponentFixture<AutomobileSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileSearchComponent ],
      imports : [HttpClientModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutomobileSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
