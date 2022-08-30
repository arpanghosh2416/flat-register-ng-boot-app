import { TestBed } from '@angular/core/testing';

import { DeauthGuard } from './deauth.guard';

describe('DeauthGuard', () => {
  let guard: DeauthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(DeauthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
