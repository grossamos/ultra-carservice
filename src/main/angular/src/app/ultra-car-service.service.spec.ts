import { TestBed } from '@angular/core/testing';

import { UltraCarServiceService } from './ultra-car-service.service';

describe('UltraCarServiceService', () => {
  let service: UltraCarServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UltraCarServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
