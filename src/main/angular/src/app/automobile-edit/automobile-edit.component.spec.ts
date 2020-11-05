import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileEditComponent } from './automobile-edit.component';

describe('AutomobileEditComponent', () => {
  let component: AutomobileEditComponent;
  let fixture: ComponentFixture<AutomobileEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileEditComponent ]
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
