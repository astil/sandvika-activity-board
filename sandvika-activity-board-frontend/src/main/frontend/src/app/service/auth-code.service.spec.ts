import { TestBed, inject } from '@angular/core/testing';

import { AuthCodeService } from './auth-code.service';

describe('AuthCodeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthCodeService]
    });
  });

  it('should be created', inject([AuthCodeService], (service: AuthCodeService) => {
    expect(service).toBeTruthy();
  }));
});
