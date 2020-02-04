import { TestBed } from '@angular/core/testing';

import { EntrustmentService } from './entrustment.service';

describe('EntrustmentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EntrustmentService = TestBed.get(EntrustmentService);
    expect(service).toBeTruthy();
  });
});
