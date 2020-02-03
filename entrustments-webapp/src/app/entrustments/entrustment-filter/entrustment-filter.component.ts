import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-entrustment-filter',
  templateUrl: './entrustment-filter.component.html',
  styleUrls: ['./entrustment-filter.component.css']
})
export class EntrustmentFilterComponent implements OnInit {
  facultySelectId: Selection;
  @Input() isCourseInstructor = false;

  constructor() { }

  ngOnInit() {
  }

  onSearchClicked() {
  }

  onClearFiltersClicked() {
  }

  shouldShowFaculties() {
    return true;
  }

  shouldLockFaculties() {
    return !this.isCourseInstructor;
  }
}
