import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuggestionsMainViewComponent } from './suggestions-main-view.component';

describe('SuggestionsMainViewComponent', () => {
  let component: SuggestionsMainViewComponent;
  let fixture: ComponentFixture<SuggestionsMainViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuggestionsMainViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuggestionsMainViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
