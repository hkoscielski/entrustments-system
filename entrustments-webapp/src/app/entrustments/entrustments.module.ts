import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EntrustmentListComponent } from './entrustment-list/entrustment-list.component';
import { EntrustmentCardComponent } from './entrustment-card/entrustment-card.component';
import { EntrustmentFilterComponent } from './entrustment-filter/entrustment-filter.component';
import { CruMainViewComponent } from './cru-main-view/cru-main-view.component';
import { EntrustmentAddPanelComponent } from './entrustment-add-panel/entrustment-add-panel.component';
import { EntrustmentPreviewPanelComponent } from './entrustment-preview-panel/entrustment-preview-panel.component';
import { SuggestionsMainViewComponent } from './suggestions-main-view/suggestions-main-view.component';
import {AppRoutingModule} from '../app-routing.module';
import {NgbAccordionModule, NgbDropdownModule, NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import { CourseListComponent } from './course-list/course-list.component';
import { CourseCardComponent } from './course-list/course-card/course-card.component';


@NgModule({
  declarations: [EntrustmentListComponent, EntrustmentCardComponent, EntrustmentFilterComponent, CruMainViewComponent, EntrustmentAddPanelComponent, EntrustmentPreviewPanelComponent, SuggestionsMainViewComponent, CourseListComponent, CourseCardComponent],
  exports: [
    EntrustmentFilterComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    NgbDropdownModule,
    FormsModule,
    NgbTypeaheadModule,
    NgbAccordionModule
  ]
})
export class EntrustmentsModule { }
