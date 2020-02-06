import {Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {Course, StudyPlanService} from "../study-plan.service";
import {CourseCardComponent} from "./course-card/course-card.component";
import {SharedDataService} from "../shared-data.service";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  filteredCourses: Course[] = [];
  // courses: Course[] = [];
  courseTextBoxValue: string;

  constructor(private studyPlanService: StudyPlanService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
    this.filteredCourses = this.sharedDataService.courses;

    // this.studyPlanService.findAllFaculties()
    //   .subscribe(faculties => {
    //     faculties.forEach(faculty => this.studyPlanService.findAllFieldsOfStudyByFacultySymbol(faculty.symbol)
    //       .subscribe(fieldsOfStudy => {
    //           fieldsOfStudy.forEach(fieldOfStudy => this.studyPlanService.findAllSemestersByFieldOfStudyId(fieldOfStudy.id)
    //             .subscribe(semesters => {
    //               semesters.forEach(s => {
    //                 this.courses = this.courses.concat(s.courses);
    //                 this.filteredCourses = this.courses;
    //               });
    //             })
    //           );
    //       })
    //     );
    //   });
  }

  onCourseFilterChanged() {
    this.filteredCourses = this.sharedDataService.courses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(this.courseTextBoxValue.toLowerCase()) > -1);
  }
}
