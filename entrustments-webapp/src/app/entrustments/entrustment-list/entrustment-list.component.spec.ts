import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrustmentListComponent } from './entrustment-list.component';

describe('EntrustmentListComponent', () => {
  let component: EntrustmentListComponent;
  let fixture: ComponentFixture<EntrustmentListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntrustmentListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrustmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
