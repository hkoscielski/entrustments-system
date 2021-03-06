import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {BehaviorSubject, merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map, switchAll} from 'rxjs/operators';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {CourseInstructor, CourseInstructorService} from "../course-instructor.service";
import {Entrustment, EntrustmentService, ReversedStatus, Status} from "../entrustment.service";
import {Course, Faculty, FieldOfStudy, Semester, Specialty, StudyLevel, StudyPlanService} from "../study-plan.service";
import {SharedDataService} from "../shared-data.service";

@Component({
  selector: 'app-entrustment-filter',
  templateUrl: './entrustment-filter.component.html',
  styleUrls: ['./entrustment-filter.component.css']
})
export class EntrustmentFilterComponent implements OnInit {
  foundEntrustments$: BehaviorSubject<Entrustment[]> = new BehaviorSubject<Entrustment[]>(undefined);

  // faculties: Faculty[];
  // fieldsOfStudy: FieldOfStudy[] = [];
  private filteredAcademicYears: string[] = [];
  private filteredSemesters: Semester[] = [];
  private filteredStudyLevels: StudyLevel[] = [];
  private filteredSpecialties: Specialty[] = [];
  private filteredCourses: Course[] = [];
  private filteredCourseInstructors: CourseInstructor[];
  private filteredStatuses: Status[] = [];

  private showFoundHint = false;
  private foundCount = 0;

  @ViewChild('coursesTextBox', {static: true}) private coursesTextBox: NgbTypeahead;
  private focusCoursesTextBox$ = new Subject<string>();
  private clickCoursesTextBox$ = new Subject<string>();

  @ViewChild('courseInstructorsTextBox', {static: true}) private courseInstructorsTextBox: NgbTypeahead;
  private focusCourseInstructorsTextBox$ = new Subject<string>();
  private clickCourseInstructorsTextBox$ = new Subject<string>();

  private searchCourses = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCoursesTextBox$.pipe(filter(() => !this.coursesTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCoursesTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.filteredCourses.slice(0, 10)
        : this.filteredCourses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  };

  private formatterCourse = (x: {code: string, name: string}) => x.code + ' ' + x.name;

  private searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;
 
    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.filteredCourseInstructors.slice(0, 10)
        : this.filteredCourseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  };

  private formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private courseInstructorService: CourseInstructorService, private entrustmentService: EntrustmentService, private studyPlanService: StudyPlanService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
    // fieldsOfStudy: FieldOfStudy[];
    // if (this.sharedDataService.actualCourseInstructor) {
    //   this.filterOptions.courseInstrucor = this.sharedDataService.actualCourseInstructor;
      // this.filterOptions.faculty = this.sharedDataService.actualFaculty;
      // this.filterOptions.fieldOfStudy = this.sharedDataService.actualFieldOfStudy;
    // }
    //
    // this.studyPlanService.findAllFaculties().subscribe(
    //   faculties => {
    //     this.faculties = faculties;
    //     this.filterOptions.faculty = this.faculties.find(f => f.id == this.sharedDataService.actualFaculty.id);
    //     faculties.forEach(x => this.studyPlanService.findAllFieldsOfStudyByFacultySymbol(x.symbol).subscribe(
    //       fieldsOfStudy => {
    //         this.fieldsOfStudy = this.fieldsOfStudy.concat(fieldsOfStudy);
    //         this.filterOptions.fieldOfStudy = this.fieldsOfStudy.find(f => f.id == this.sharedDataService.actualFieldOfStudy.id);
    //     }));
    //   }
    // );
    //
    // this.studyPlanService.findAllSemesters().subscribe(
    //   semesters => {
    //     this.academicYears = [...new Set(semesters.map(x => x.academicYear))].sort();
    //     this.semesters = semesters.sort(x => x.semesterNumber);
    //
    //     let allStudyLevels = semesters.map(x => x.studyLevel).filter(x => x);
    //     let uniqueStudyLevelNames = [...new Set(allStudyLevels.map(x => x.name))];
    //     this.studyLevels = uniqueStudyLevelNames.map(unique => allStudyLevels.find(all => unique == all.name))
    //       .sort((a, b) => a.name > b.name ? -1 : 1);
    //
    //     let allSpecialties = semesters.map(x => x.specialty).filter(x => x);
    //     let uniqueSpecialtyNames = [...new Set(allSpecialties.map(x => x.shortName))];
    //     this.specialties = uniqueSpecialtyNames.map(unique => allSpecialties.find(all => unique == all.shortName))
    //       .sort((a, b) => a.name > b.name ? -1 : 1);
    //
    //     let allCourses = semesters.map(x => x.courses).reduce((accum, next) => accum.concat(next), []);
    //     let uniqueCourseCodes = [...new Set(allCourses.map(x => x.code))];
    //     this.courses = uniqueCourseCodes.map(unique => allCourses.find(all => unique == all.code));
    //     this.courses.sort((a, b) => (a.name + a.code) >  (b.name + b.code) ? -1 : 1);
    //   }
    // );
    //
    // this.courseInstructorService.findAll().subscribe(
    //   instructors => {
    //     this.courseInstructors = instructors;
    //     this.filterOptions.courseInstrucor = this.sharedDataService.actualCourseInstructor;
    //   }
    // );
    //
    // this.statuses = Object.values(Status);
    if (this.sharedDataService.actualCourseInstructor) {
      this.onSearchClicked();
    }

    this.sharedDataService.onFilterOptionsChanged$.subscribe(x => this.onFilterOptionsChangedFilter(x));
    // this.filteredSemesters;
    //   this.sharedDataService.semesters.filter()
    // this.semesters = semesters.sort(x => x.semesterNumber);
    // let allSemesters = this.sharedDataService.semesters;
    // let uniqueSemesterNumbers = [...new Set(allSemesters.map(x => x.semesterNumber))];
    // this.filteredSemesters = uniqueSemesterNumbers.map(unique => allSemesters.find(all => unique == all.semesterNumber))
    //   .sort((a, b) => a.semesterNumber >= b.semesterNumber ? -1 : 1);
  }

  onFilterOptionsChangedFilter(newFilter: FilterOptions) {
    console.log('on filter options changed');
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
      this.filteredCourses = this.sharedDataService.courses.filter(x => newFilter.semester.courses.some(y => x.code == y.code));
    }
    else {
      this.filteredCourses = this.sharedDataService.courses;
    }

  }

  onSearchClicked() {
    // this.sharedDataService.onFilterOptionsChanged$.next(this.filterOptions);
    this.entrustmentService.findAllEntrustments(
      this.sharedDataService.actualFilterOptions.academicYear,
      this.sharedDataService.actualFilterOptions.semester ? this.sharedDataService.actualFilterOptions.semester.semesterNumber : undefined,
      this.sharedDataService.actualFilterOptions.studyLevel ? this.sharedDataService.actualFilterOptions.studyLevel.code : undefined,
      this.sharedDataService.actualFilterOptions.specialty ? this.sharedDataService.actualFilterOptions.specialty.shortName : undefined,
      this.sharedDataService.actualFilterOptions.course ? this.sharedDataService.actualFilterOptions.course.code : undefined,
      ReversedStatus[this.sharedDataService.actualFilterOptions.status],
      this.sharedDataService.actualFilterOptions.courseInstrucor ? this.sharedDataService.actualFilterOptions.courseInstrucor.id : undefined
      ).subscribe(
        entrustments => {
          this.showFoundHint = true;
          this.foundCount = entrustments.length;
          this.foundEntrustments$.next(entrustments);
      // console.log(JSON.stringify(entrustments));
    });
  }

  onClearFiltersClicked() {
    let newFilterOptions = new FilterOptions();
    if (this.shouldLockFieldOfStudy()) {
      newFilterOptions.faculty = this.sharedDataService.actualFaculty;
      newFilterOptions.fieldOfStudy = this.sharedDataService.actualFieldOfStudy;
    }

    if (this.sharedDataService.actualCourseInstructor) {
      newFilterOptions.courseInstrucor = this.sharedDataService.actualCourseInstructor;
    }

    this.sharedDataService.actualFilterOptions = newFilterOptions;

    // this.showFoundHint = false;
    // this.foundCount = 0;
  }

  shouldShowFieldOfStudy() {
    return true;
  }

  shouldLockFieldOfStudy() {
    return !this.sharedDataService.actualCourseInstructor;
  }

  areFilterOptionsEmpty() {
    return !this.sharedDataService.actualFilterOptions.faculty &&
      !this.sharedDataService.actualFilterOptions.fieldOfStudy &&
      !this.sharedDataService.actualFilterOptions.academicYear &&
      !this.sharedDataService.actualFilterOptions.semester &&
      !this.sharedDataService.actualFilterOptions.studyLevel &&
      !this.sharedDataService.actualFilterOptions.specialty &&
      !this.sharedDataService.actualFilterOptions.course &&
      !this.sharedDataService.actualFilterOptions.courseInstrucor &&
      !this.sharedDataService.actualFilterOptions.status;
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
