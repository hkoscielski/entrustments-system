import {Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {Course, Semester, Specialty, StudyLevel, StudyPlanService} from "../study-plan.service";
import {CourseCardComponent} from "./course-card/course-card.component";
import {SharedDataService} from "../shared-data.service";
import {FilterOptions} from "../entrustment-filter/entrustment-filter.component";
import {CourseInstructor} from "../course-instructor.service";
import {Status} from "../entrustment.service";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  filteredCourses: Course[] = [];
  // courses: Course[] = [];
  courseTextBoxValue: string;

  filteredAcademicYears: string[] = [];
  filteredSemesters: Semester[] = [];
  filteredStudyLevels: StudyLevel[] = [];
  filteredSpecialties: Specialty[] = [];
  prefilteredCourses: Course[] = [];
  filteredCourseInstructors: CourseInstructor[];
  filteredStatuses: Status[] = [];

  constructor(private studyPlanService: StudyPlanService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
    this.prefilteredCourses = this.sharedDataService.courses;
    this.filteredCourses = this.prefilteredCourses;

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
    this.sharedDataService.onFilterOptionsChanged.asObservable().subscribe(x => this.onFilterOptionsChanged(x));
  }

  onCourseFilterChanged() {
    this.filteredCourses = this.prefilteredCourses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(this.courseTextBoxValue.toLowerCase()) > -1);
  }

  onFilterOptionsChanged(newFilter: FilterOptions) {
    // this.onClearFiltersClicked();
    // this.sharedDataService.actualFilterOptions = newFilter;
    this.filteredAcademicYears = [...new Set(this.sharedDataService.semesters.map(x => x.academicYear))].sort();
    if (newFilter.academicYear) {
      this.filteredSemesters = this.sharedDataService.semesters.filter(s => s.academicYear == newFilter.academicYear);
    }
    else {
      this.filteredSemesters = this.sharedDataService.semesters;
    }

    this.filteredCourseInstructors = this.sharedDataService.courseInstructors;
    this.filteredSpecialties = this.sharedDataService.specialties;
    this.filteredStatuses = this.sharedDataService.statuses;
    this.filteredStudyLevels = this.sharedDataService.studyLevels;

    if (newFilter.semester) {
      this.prefilteredCourses = this.sharedDataService.courses.filter(x => newFilter.semester.courses.some(y => x.code == y.code));
    }
    else {
      this.prefilteredCourses = this.sharedDataService.courses;
    }
    this.filteredCourses = this.prefilteredCourses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(this.courseTextBoxValue.toLowerCase()) > -1);
  }
}
