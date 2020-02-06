import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {EntrustmentFilterComponent} from "../entrustment-filter/entrustment-filter.component";
import {EntrustmentListComponent} from "../entrustment-list/entrustment-list.component";
import {filter} from "rxjs/operators";
import {Course, Faculty, FieldOfStudy, Semester, Specialty, StudyLevel} from "../study-plan.service";
import {CourseInstructor} from "../course-instructor.service";
import {Status} from "../entrustment.service";

@Component({
  selector: 'app-cru-main-view',
  templateUrl: './cru-main-view.component.html',
  styleUrls: ['./cru-main-view.component.css']
})
export class CruMainViewComponent implements OnInit, AfterViewInit {

  @ViewChild(EntrustmentFilterComponent, {static: false}) entrustmentFilterComponent;
  @ViewChild(EntrustmentListComponent, {static: false}) entrustmentListComponent;

  filterOptionsString = '';

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.entrustmentFilterComponent.foundEntrustments.asObservable().subscribe(
      entrustments => {
        this.entrustmentListComponent.showList(entrustments);
        this.filterOptionsString = this.getFilterOptionsString();
      })
  }

  // shouldShowList() {
  //   if (this.entrustmentFilterComponent && this.entrustmentFilterComponent.foundEntrustments$)
  //     return this.entrustmentFilterComponent.foundEntrustments$.length > 0;
  //   return false;
  //   // return true;
  // }

  shouldShowInstructionsText() {
    return this.entrustmentFilterComponent && !this.entrustmentFilterComponent.showFoundHint;
  }

  shouldShowNoResultsText() {
    return this.entrustmentFilterComponent && this.entrustmentFilterComponent.showFoundHint && this.entrustmentFilterComponent.foundCount == 0;
  }

  shouldShowFilterOptionsLabel() {
    return this.filterOptionsString !== '';
  }

  getFilterOptionsString() {
    let filterStrings: string[] = [];
    if (this.entrustmentFilterComponent && this.entrustmentFilterComponent.filterOptions) {
      if (this.entrustmentFilterComponent.filterOptions.faculty) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.faculty.symbol);
      }
      if (this.entrustmentFilterComponent.filterOptions.fieldOfStudy) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.fieldOfStudy.shortName);
      }
      if (this.entrustmentFilterComponent.filterOptions.studyLevel) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.studyLevel.name);
      }
      if (this.entrustmentFilterComponent.filterOptions.semester) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.semester.semesterNumber + ' sem.');
      }
      if (this.entrustmentFilterComponent.filterOptions.specialty) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.specialty.shortName);
      }
      if (this.entrustmentFilterComponent.filterOptions.course) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.course.code + ' ' + this.entrustmentFilterComponent.filterOptions.course.name);
      }
      if (this.entrustmentFilterComponent.filterOptions.courseInstrucor) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.courseInstrucor.firstName + ' ' + this.entrustmentFilterComponent.filterOptions.courseInstrucor.surname);
      }
      if (this.entrustmentFilterComponent.filterOptions.status) {
        filterStrings.push(this.entrustmentFilterComponent.filterOptions.status);
      }
    }

    if (filterStrings.length == 0) {
      filterStrings.push('Brak filtrów');
    }

    return filterStrings.join(' - ').toString();
  }
}
