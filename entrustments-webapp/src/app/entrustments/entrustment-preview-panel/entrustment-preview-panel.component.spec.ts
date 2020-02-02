import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrustmentPreviewPanelComponent } from './entrustment-preview-panel.component';

describe('EntrustmentPreviewPanelComponent', () => {
  let component: EntrustmentPreviewPanelComponent;
  let fixture: ComponentFixture<EntrustmentPreviewPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntrustmentPreviewPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrustmentPreviewPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
