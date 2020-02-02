import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrustmentAddPanelComponent } from './entrustment-add-panel.component';

describe('EntrustmentAddPanelComponent', () => {
  let component: EntrustmentAddPanelComponent;
  let fixture: ComponentFixture<EntrustmentAddPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntrustmentAddPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrustmentAddPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
