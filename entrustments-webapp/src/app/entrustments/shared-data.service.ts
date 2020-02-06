import { Injectable } from '@angular/core';
import {Entrustment, Status} from "./entrustment.service";
import {CourseInstructor, CourseInstructorService} from "./course-instructor.service";
import {Course, Faculty, FieldOfStudy, Semester, Specialty, StudyLevel, StudyPlanService} from "./study-plan.service";
import {BehaviorSubject} from "rxjs";
import {FilterOptions} from "./entrustment-filter/entrustment-filter.component";

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  public actualEntrustment: Entrustment;
  public actualCourseInstructor: CourseInstructor;
  public actualFaculty: Faculty;
  public actualFieldOfStudy: FieldOfStudy;
  public actualFilterOptions: FilterOptions = new FilterOptions();
  public onFilterOptionsChanged: BehaviorSubject<FilterOptions> = new BehaviorSubject<FilterOptions>(new FilterOptions());
  public onPickedCourse: BehaviorSubject<Course> = new BehaviorSubject<Course>(undefined);

  faculties: Faculty[];
  fieldsOfStudy: FieldOfStudy[] = [];
  academicYears: string[];
  semesters: Semester[];
  studyLevels: StudyLevel[];
  specialties: Specialty[];
  courses: Course[];
  courseInstructors: CourseInstructor[];
  statuses: Status[];

  constructor(private studyPlanService: StudyPlanService, private courseInstructorService: CourseInstructorService) {
    // this.onFilterOptionsChanged.asObservable().subscribe(options => {
    //   this.actualFilterOptions = options;
    // })
    this.downloadData();
  }

  downloadData() {
    console.log("DOWNLOADS DATA");
    this.studyPlanService.findAllFaculties().subscribe(
      faculties => {
        this.faculties = faculties;
        // this.actualFilterOptions.faculty = this.faculties.find(f => f.id == this.actualFaculty.id);
        faculties.forEach(x => this.studyPlanService.findAllFieldsOfStudyByFacultySymbol(x.symbol).subscribe(
          fieldsOfStudy => {
            fieldsOfStudy.forEach(fos => fos.facultySymbol = x.symbol);
            this.fieldsOfStudy = this.fieldsOfStudy.concat(fieldsOfStudy);
            // this.filterOptions.fieldOfStudy = this.fieldsOfStudy.find(f => f.id == this.sharedDataService.actualFieldOfStudy.id);
          }));
      }
    );

    this.studyPlanService.findAllSemesters().subscribe(
      semesters => {
        this.academicYears = [...new Set(semesters.map(x => x.academicYear))].sort();
        this.semesters = semesters.sort(x => x.semesterNumber);

        let allStudyLevels = semesters.map(x => x.studyLevel).filter(x => x);
        let uniqueStudyLevelNames = [...new Set(allStudyLevels.map(x => x.name))];
        this.studyLevels = uniqueStudyLevelNames.map(unique => allStudyLevels.find(all => unique == all.name))
          .sort((a, b) => a.name > b.name ? -1 : 1);

        let allSpecialties = semesters.map(x => x.specialty).filter(x => x);
        let uniqueSpecialtyNames = [...new Set(allSpecialties.map(x => x.shortName))];
        this.specialties = uniqueSpecialtyNames.map(unique => allSpecialties.find(all => unique == all.shortName))
          .sort((a, b) => a.name > b.name ? -1 : 1);

        let allCourses = semesters.map(x => x.courses).reduce((accum, next) => accum.concat(next), []);
        let uniqueCourseCodes = [...new Set(allCourses.map(x => x.code))];
        this.courses = uniqueCourseCodes.map(unique => allCourses.find(all => unique == all.code));
        this.courses.sort((a, b) => (a.name + a.code) >  (b.name + b.code) ? -1 : 1);
      }
    );

    this.courseInstructorService.findAll().subscribe(
      instructors => {
        this.courseInstructors = instructors;
        // this.filterOptions.courseInstrucor = this.sharedDataService.actualCourseInstructor;
      }
    );

    this.statuses = Object.values(Status);
  }

  resetActualAllData() {
    this.actualEntrustment = undefined;
    this.actualCourseInstructor = undefined;
    this.actualFaculty = undefined;
    this.actualFieldOfStudy = undefined;
  }
}
//
// export class Data {
//   faculties: Faculty[];
//   fieldsOfStudy: FieldOfStudy[] = [];
//   academicYears: string[];
//   semesters: Semester[];
//   studyLevels: StudyLevel[];
//   specialties: Specialty[];
//   courses: Course[];
//   courseInstructors: CourseInstructor[];
//   statuses: Status[];
// }
