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

  filterOptions = new FilterOptions();
  pickedNumberOfHours: number;

  academicYears: string[];
  semesters: Semester[];
  studyLevels: StudyLevel[];
  specialties: Specialty[];
  courses: Course[];
  courseInstructors: CourseInstructor[];

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
  };

  formatterCourse = (x: {code: string, name: string}) => x.code + ' ' + x.name;

  searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.courseInstructors
        : this.courseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  };

  formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private location: Location, private courseInstructorService: CourseInstructorService, private entrustmentService: EntrustmentService, private studyPlanService: StudyPlanService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
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
  }

  areAllOptionsPicked() {
    return this.filterOptions.academicYear &&
    this.filterOptions.semester &&
    this.filterOptions.studyLevel &&
    this.filterOptions.specialty &&
    this.filterOptions.course &&
    this.filterOptions.courseInstrucor &&
    this.pickedNumberOfHours;
  }

  onAddClicked() {
    if (!this.areAllOptionsPicked())
      return;

    if (this.filterOptions.semester.courses.some(c => c.code == this.filterOptions.course.code)) {

    }

    this.entrustmentService.addEntrustment(this.filterOptions.semester.id, this.filterOptions.courseInstrucor.id, this.pickedNumberOfHours, this.filterOptions.course.code)
      .subscribe(x => {
        this.location.back();
    });
  }

  onBackClicked() {
    this.location.back();
  }
}
