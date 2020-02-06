import { Injectable } from '@angular/core';
import {Entrustment} from "./entrustment.service";
import {CourseInstructor} from "./course-instructor.service";
import {Faculty, FieldOfStudy} from "./study-plan.service";

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  public actualCard: Entrustment;
  public actualCourseInstructor: CourseInstructor;
  public actualFaculty: Faculty;
  public actualFieldOfStudy: FieldOfStudy;

  constructor() { }

  resetAllData() {
    this.actualCard = undefined;
    this.actualCourseInstructor = undefined;
    this.actualFaculty = undefined;
    this.actualFieldOfStudy = undefined;
  }
}
