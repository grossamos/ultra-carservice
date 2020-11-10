import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileListComponent } from './automobile-list.component';
import {Observable, of} from 'rxjs';
import {Automobile} from '../automobile';
import {HttpClientModule} from '@angular/common/http';
import {UltraCarServiceService} from '../ultra-car-service.service';
import {RouterTestingModule} from '@angular/router/testing';
import {By} from '@angular/platform-browser';
import {Component} from '@angular/core';

describe('AutomobileListComponent', () => {
  let component: AutomobileListComponent;
  let fixture: ComponentFixture<AutomobileListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileListComponent ],
      imports : [
        HttpClientModule,
        RouterTestingModule,
        RouterTestingModule.withRoutes(
          [
            {path: 'list-all/', component: DummyComponent}
          ]
        )
      ],
      providers : [
        {provide: UltraCarServiceService, useClass: MockUltraCarServiceService}
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutomobileListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain  delete buttons', () => {
    expect(fixture.debugElement.query(By.css('button'))).toBeDefined();
  });

  it('#ngOnInit should populate myAutomobiles', () => {
    expect(component.myAutomobiles.length).toBeGreaterThan(0);
  });

  class MockUltraCarServiceService {
    getListOfCars(): Observable<any> {
      return of([new Automobile(), new Automobile(), new Automobile()]);
    }

    deleteSomething(id: number): void{
    }
  }

  @Component({ template: '' })
  class DummyComponent{}
});
