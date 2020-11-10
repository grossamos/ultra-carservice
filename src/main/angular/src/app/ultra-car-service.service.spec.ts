import { TestBed } from '@angular/core/testing';

import { UltraCarServiceService } from './ultra-car-service.service';
import {Test} from 'tslint';
import {HttpClientModule} from '@angular/common/http';

describe('UltraCarServiceService', () => {
  let service: UltraCarServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    TestBed.configureTestingModule({
      imports : [HttpClientModule]
    });
    service = TestBed.inject(UltraCarServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
