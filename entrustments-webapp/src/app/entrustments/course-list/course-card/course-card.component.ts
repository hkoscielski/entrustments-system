import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Entrustment} from "../../entrustment.service";
import {Course, FieldOfStudy, Semester, Specialty} from "../../study-plan.service";
import {SharedDataService} from "../../shared-data.service";

@Component({
  selector: 'app-course-card',
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css']
})
export class CourseCardComponent implements OnInit {
  @Input() course: Course;

  // foundSemester: Semester = new Semester();
  // foundFacultyName: string = '';
  // foundFieldOfStudyName: string = '';
  // foundSpecialtyName: string = '';
  // foundStudyLevelName: string = '';

  constructor(private sharedDataService: SharedDataService) {
    // this.foundSemester = this.sharedDataService.semesters.find(s => s.id == this.course.id);
  }

  ngOnInit() {
    // console.log(JSON.stringify(this.sharedDataService.semesters.map(s => s.id)));
    // console.log('course ID = ' + this.course.semesterId);
    // this.foundSemester = this.sharedDataService.semesters.find(s => s.id == this.course.semesterId);
    // // console.log(JSON.stringify(this.foundSemester.id));
    // this.foundFacultyName = this.foundSemester.fieldOfStudy.facultySymbol;
    // this.foundFieldOfStudyName = this.foundSemester.fieldOfStudy.name;
    // this.foundSpecialtyName = this.foundSemester.specialty.name;
    // this.foundStudyLevelName = this.foundSemester.studyLevel.name;
  }

  onCardClicked() {
    this.sharedDataService.onPickedCourse.next(this.course);
  }

  isCardEditable() {
    return false;
  }
}
