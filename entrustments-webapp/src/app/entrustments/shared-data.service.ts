import { Injectable } from '@angular/core';
import {Entrustment} from "./entrustment.service";
import {CourseInstructor} from "./course-instructor.service";
import {Course, Faculty, FieldOfStudy} from "./study-plan.service";
import {BehaviorSubject} from "rxjs";
import {FilterOptions} from "./entrustment-filter/entrustment-filter.component";

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  public actualCard: Entrustment;
  public actualCourseInstructor: CourseInstructor;
  public actualFaculty: Faculty;
  public actualFieldOfStudy: FieldOfStudy;
  public actualFilterOptions: FilterOptions;
  public onFilterOptionsChanged: BehaviorSubject<FilterOptions> = new BehaviorSubject<FilterOptions>(new FilterOptions());
  public onPickedCourse: BehaviorSubject<Course> = new BehaviorSubject<Course>(undefined);;

  constructor() {
    this.onFilterOptionsChanged.asObservable().subscribe(options => {
      this.actualFilterOptions = options;
    })
  }

  resetAllData() {
    this.actualCard = undefined;
    this.actualCourseInstructor = undefined;
    this.actualFaculty = undefined;
    this.actualFieldOfStudy = undefined;
  }
}
