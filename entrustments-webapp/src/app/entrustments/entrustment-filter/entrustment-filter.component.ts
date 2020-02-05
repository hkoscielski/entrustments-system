import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map, switchAll} from 'rxjs/operators';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {CourseInstructor, CourseInstructorService} from "../course-instructor.service";
import {Entrustment, EntrustmentService, ReversedStatus, Status} from "../entrustment.service";
import {Course, Faculty, FieldOfStudy, Semester, Specialty, StudyLevel, StudyPlanService} from "../study-plan.service";

@Component({
  selector: 'app-entrustment-filter',
  templateUrl: './entrustment-filter.component.html',
  styleUrls: ['./entrustment-filter.component.css']
})
export class EntrustmentFilterComponent implements OnInit {
  public foundEntrustments: Entrustment[];

  pickedFaculty: Faculty;
  faculties: Faculty[];

  pickedFieldOfStudy: FieldOfStudy;
  fieldsOfStudy: FieldOfStudy[];

  pickedAcademicYear: string;
  academicYears: string[];

  pickedSemester: Semester;
  semesters: Semester[];

  pickedStudyLevel: StudyLevel;
  studyLevels: StudyLevel[];

  pickedSpecialty: Specialty;
  specialties: Specialty[];

  pickedCourse: Course;
  courses: Course[];

  pickedCourseInstrucor: CourseInstructor;
  courseInstructors: CourseInstructor[];

  pickedStatus: Status;
  statuses: Status[];

  @Input() isCourseInstructor = false;

  @ViewChild('coursesTextBox', {static: true}) coursesTextBox: NgbTypeahead;
  focusCoursesTextBox$ = new Subject<string>();
  clickCoursesTextBox$ = new Subject<string>();

  @ViewChild('courseInstructorsTextBox', {static: true}) courseInstructorsTextBox: NgbTypeahead;
  focusCourseInstructorsTextBox$ = new Subject<string>();
  clickCourseInstructorsTextBox$ = new Subject<string>();

  searchCourses = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCoursesTextBox$.pipe(filter(() => !this.coursesTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCoursesTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.courses
        : this.courses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  }

  formatterCourse = (x: {code: string, name: string}) => x.code + ' ' + x.name;

  searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.courseInstructors
        : this.courseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  }

  formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private courseInstructorService: CourseInstructorService, private entrustmentService: EntrustmentService, private studyPlanService: StudyPlanService) { }

  ngOnInit() {
    // fieldsOfStudy: FieldOfStudy[];

    this.studyPlanService.findAllFaculties().subscribe(
      faculties => {
        this.faculties = faculties;
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
      }
    );

    this.statuses = Object.values(Status);
  }

  onSearchClicked() {
    // academicYear=2019/2020&semester=1&studyLevel=FIRST_DEGREE&specialty=IO&courseCode=INZ000W&entrustmentStatus=ACCEPTED&courseInstructorId=1
    console.log(JSON.stringify(this.pickedSemester));
    console.log(this.pickedSemester ? this.pickedSemester.semesterNumber : undefined);

    this.entrustmentService.findAllEntrustments(
      this.pickedAcademicYear,
      this.pickedSemester ? this.pickedSemester.semesterNumber : undefined,
      this.pickedStudyLevel ? this.pickedStudyLevel.code : undefined,
      this.pickedSpecialty ? this.pickedSpecialty.shortName : undefined,
      this.pickedCourse ? this.pickedCourse.code : undefined,
      ReversedStatus[this.pickedStatus],
      this.pickedCourseInstrucor ? this.pickedCourseInstrucor.id : undefined
      ).subscribe(
        entrustments => {
      this.foundEntrustments = entrustments;
      console.log(JSON.stringify(entrustments));
    });
  }

  onClearFiltersClicked() {
    if (this.shouldLockFieldOfStudy()) {
      this.pickedFaculty = undefined;
      this.pickedFieldOfStudy = undefined;
    }

    this.pickedAcademicYear = undefined;
    this.pickedSemester = undefined;
    this.pickedStudyLevel = undefined;
    this.pickedSpecialty = undefined;
    this.pickedCourse = undefined;
    this.pickedCourseInstrucor = undefined;
    this.pickedStatus = undefined;
  }

  shouldShowFieldOfStudy() {
    return true;
  }

  shouldLockFieldOfStudy() {
    return !this.isCourseInstructor;
  }
}
