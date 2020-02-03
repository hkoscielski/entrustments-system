import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrustmentCardComponent } from './entrustment-card.component';

describe('EntrustmentCardComponent', () => {
  let component: EntrustmentCardComponent;
  let fixture: ComponentFixture<EntrustmentCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntrustmentCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrustmentCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
