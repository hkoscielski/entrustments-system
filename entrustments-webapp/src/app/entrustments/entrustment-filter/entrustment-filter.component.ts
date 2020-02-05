import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {BehaviorSubject, merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map, switchAll} from 'rxjs/operators';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {CourseInstructor, CourseInstructorService} from "../course-instructor.service";
import {Entrustment, EntrustmentService, ReversedStatus, Status} from "../entrustment.service";
import {Course, Faculty, FieldOfStudy, Semester, Specialty, StudyLevel, StudyPlanService} from "../study-plan.service";
import {identifierModuleUrl} from "@angular/compiler";

@Component({
  selector: 'app-entrustment-filter',
  templateUrl: './entrustment-filter.component.html',
  styleUrls: ['./entrustment-filter.component.css']
})
export class EntrustmentFilterComponent implements OnInit {
  public foundEntrustments: BehaviorSubject<Entrustment[]> = new BehaviorSubject<Entrustment[]>(undefined);
  public filterOptions = new FilterOptions();

  faculties: Faculty[];
  fieldsOfStudy: FieldOfStudy[];
  academicYears: string[];
  semesters: Semester[];
  studyLevels: StudyLevel[];
  specialties: Specialty[];
  courses: Course[];
  courseInstructors: CourseInstructor[];
  statuses: Status[];

  showFoundHint = false;
  foundCount = 0;

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
        faculties.forEach(x => this.studyPlanService.findAllFieldsOfStudyByFacultySymbol(x.symbol).subscribe(
          fieldsOfStudy => {
          if (this.fieldsOfStudy)
            this.fieldsOfStudy.concat(fieldsOfStudy);
          else
            this.fieldsOfStudy = fieldsOfStudy;
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
      }
    );

    this.statuses = Object.values(Status);
  }

  onSearchClicked() {
    this.entrustmentService.findAllEntrustments(
      this.filterOptions.academicYear,
      this.filterOptions.semester ? this.filterOptions.semester.semesterNumber : undefined,
      this.filterOptions.studyLevel ? this.filterOptions.studyLevel.code : undefined,
      this.filterOptions.specialty ? this.filterOptions.specialty.shortName : undefined,
      this.filterOptions.course ? this.filterOptions.course.code : undefined,
      ReversedStatus[this.filterOptions.status],
      this.filterOptions.courseInstrucor ? this.filterOptions.courseInstrucor.id : undefined
      ).subscribe(
        entrustments => {
          this.showFoundHint = true;
          this.foundCount = entrustments.length;
          this.foundEntrustments.next(entrustments);
      console.log(JSON.stringify(entrustments));
    });
  }

  onClearFiltersClicked() {
    let newFilterOptions = new FilterOptions();
    if (this.shouldLockFieldOfStudy()) {
      newFilterOptions.faculty = this.filterOptions.faculty;
      newFilterOptions.fieldOfStudy = this.filterOptions.fieldOfStudy;
    }

    this.filterOptions = newFilterOptions;

    // this.showFoundHint = false;
    // this.foundCount = 0;
  }

  shouldShowFieldOfStudy() {
    return true;
  }

  shouldLockFieldOfStudy() {
    return !this.isCourseInstructor;
  }

  areFilterOptionsEmpty() {
    return !this.filterOptions.faculty &&
      !this.filterOptions.fieldOfStudy &&
      !this.filterOptions.academicYear &&
      !this.filterOptions.semester &&
      !this.filterOptions.studyLevel &&
      !this.filterOptions.specialty &&
      !this.filterOptions.course &&
      !this.filterOptions.courseInstrucor &&
      !this.filterOptions.status;
  }
}

export class FilterOptions {
  faculty?: Faculty;
  fieldOfStudy?: FieldOfStudy;
  academicYear?: string;
  semester?: Semester;
  studyLevel?: StudyLevel;
  specialty?: Specialty;
  course?: Course;
  courseInstrucor?: CourseInstructor;
  status?: Status;

  constructor(faculty?: Faculty, fieldOfStudy?: FieldOfStudy, academicYear?: string, semester?: Semester, studyLevel?: StudyLevel, specialty?: Specialty, course?: Course, courseInstrucor?: CourseInstructor, status?: Status) {
    this.faculty = faculty;
    this.fieldOfStudy = fieldOfStudy;
    this.academicYear = academicYear;
    this.semester = semester;
    this.studyLevel = studyLevel;
    this.specialty = specialty;
    this.course = course;
    this.courseInstrucor = courseInstrucor;
    this.status = status;
  }
}
