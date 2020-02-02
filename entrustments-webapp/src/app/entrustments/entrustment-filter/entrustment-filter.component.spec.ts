import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrustmentFilterComponent } from './entrustment-filter.component';

describe('EntrustmentFilterComponent', () => {
  let component: EntrustmentFilterComponent;
  let fixture: ComponentFixture<EntrustmentFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntrustmentFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrustmentFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
