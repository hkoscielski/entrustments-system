import {Component, OnInit, ViewChild} from '@angular/core';
import { Location } from '@angular/common';
import {Course, Semester, Specialty, StudyLevel, StudyPlanService} from "../study-plan.service";
import {CourseInstructor, CourseInstructorService} from "../course-instructor.service";
import {EntrustmentService, Status} from "../entrustment.service";
import {FilterOptions} from "../entrustment-filter/entrustment-filter.component";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {merge, Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, filter, map} from "rxjs/operators";
import {SharedDataService} from "../shared-data.service";

@Component({
  selector: 'app-entrustment-add-panel',
  templateUrl: './entrustment-add-panel.component.html',
  styleUrls: ['./entrustment-add-panel.component.css']
})
export class EntrustmentAddPanelComponent implements OnInit {

  // filterOptions = new FilterOptions();
  pickedNumberOfHours: number;

  // academicYears: string[];
  // semesters: Semester[];
  // studyLevels: StudyLevel[];
  // specialties: Specialty[];
  // courses: Course[];
  // courseInstructors: CourseInstructor[];

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
      map(term => term === '' ? this.filteredCourses.slice(0, 10)
        : this.filteredCourses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  };

  formatterCourse = (x: {code: string, name: string}) => x.code + ' ' + x.name;

  searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.filteredCourseInstructors.slice(0, 10)
        : this.filteredCourseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  };

  formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private location: Location, private courseInstructorService: CourseInstructorService, private entrustmentService: EntrustmentService, private studyPlanService: StudyPlanService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
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
    //   }
    // );
    this.sharedDataService.onFilterOptionsChanged$.subscribe(x => this.onFilterOptionsChanged(x));
    this.sharedDataService.onPickedCourse$.subscribe(x => {
        this.sharedDataService.actualFilterOptions.course = x;
        this.sharedDataService.actualFilterOptions.semester = this.sharedDataService.semesters.find(s => s.id = x.semesterId);
        this.sharedDataService.actualFilterOptions.academicYear = this.sharedDataService.actualFilterOptions.semester.academicYear;
        this.sharedDataService.actualFilterOptions.studyLevel = this.sharedDataService.actualFilterOptions.semester.studyLevel;
        this.sharedDataService.actualFilterOptions.specialty = this.sharedDataService.actualFilterOptions.semester.specialty;
        this.sharedDataService.onFilterOptionsChanged$.next(this.sharedDataService.actualFilterOptions);
        // this.onFilterOptionsChanged$(this.sharedDataService.actualFilterOptions);
    });

    console.log("Observers");
    console.log(JSON.stringify(this.sharedDataService.onFilterOptionsChanged$.observers.map(x => x.toString()).toString()));
  }

  filteredAcademicYears: string[] = [];
  filteredSemesters: Semester[] = [];
  filteredStudyLevels: StudyLevel[] = [];
  filteredSpecialties: Specialty[] = [];
  filteredCourses: Course[] = [];
  filteredCourseInstructors: CourseInstructor[];
  filteredStatuses: Status[] = [];

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
      this.filteredCourses = this.sharedDataService.courses.filter(x => newFilter.semester.courses.some(y => x.code == y.code));
    }
    else {
      this.filteredCourses = this.sharedDataService.courses;
    }
  }

  areAllOptionsPicked() {
    return this.sharedDataService.actualFilterOptions.academicYear &&
    this.sharedDataService.actualFilterOptions.semester &&
    this.sharedDataService.actualFilterOptions.studyLevel &&
    this.sharedDataService.actualFilterOptions.specialty &&
    this.sharedDataService.actualFilterOptions.course &&
    this.sharedDataService.actualFilterOptions.courseInstrucor &&
    this.pickedNumberOfHours;
  }

  onAddClicked() {
    if (!this.areAllOptionsPicked())
      return;

    if (this.sharedDataService.actualFilterOptions.semester.courses.some(c => c.code == this.sharedDataService.actualFilterOptions.course.code) && this.pickedNumberOfHours > this.sharedDataService.actualFilterOptions.course.hoursToEntrust) {
      return;
    }

    this.entrustmentService.addEntrustment(this.sharedDataService.actualFilterOptions.semester.id, this.sharedDataService.actualFilterOptions.courseInstrucor.id, this.pickedNumberOfHours, this.sharedDataService.actualFilterOptions.course.code)
      .subscribe(x => {
        let cor = this.sharedDataService.semesters.find(s => s.id == this.sharedDataService.actualFilterOptions.semester.id).courses.find(c => c.code == this.sharedDataService.actualFilterOptions.course.code);
        cor.hoursToEntrust -=  this.pickedNumberOfHours;
        this.location.back();
    });
  }

  onBackClicked() {
    this.location.back();
  }
}
