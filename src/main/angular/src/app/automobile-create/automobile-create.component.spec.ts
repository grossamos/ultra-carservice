import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileCreateComponent } from './automobile-create.component';

describe('AutomobileCreateComponent', () => {
  let component: AutomobileCreateComponent;
  let fixture: ComponentFixture<AutomobileCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileCreateComponent ]
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
