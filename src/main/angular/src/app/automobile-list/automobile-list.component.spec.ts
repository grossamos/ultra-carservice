import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutomobileListComponent } from './automobile-list.component';

describe('AutomobileListComponent', () => {
  let component: AutomobileListComponent;
  let fixture: ComponentFixture<AutomobileListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutomobileListComponent ]
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
});
