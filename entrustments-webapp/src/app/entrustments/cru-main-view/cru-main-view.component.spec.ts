import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CruMainViewComponent } from './cru-main-view.component';

describe('CruMainViewComponent', () => {
  let component: CruMainViewComponent;
  let fixture: ComponentFixture<CruMainViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CruMainViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CruMainViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
