import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {merge, Observable, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {CourseInstructor, CourseInstructorService} from "../course-instructor.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-entrustment-filter',
  templateUrl: './entrustment-filter.component.html',
  styleUrls: ['./entrustment-filter.component.css']
})
export class EntrustmentFilterComponent implements OnInit {
  readonly courses: { code: string, name: string }[] = [
    { code: 'INZ002419P', name: 'Przetwarzanie dużych zb.danych' },
    { code: 'INZ003854P', name: 'Projektowanie sys. informat.' },
    { code: 'INZ002418L', name: 'Bezpieczeństwo sys.web.i mob.' },
    { code: 'INZ002418W', name: 'Bezpieczeństwo sys.web.i mob.' },
    { code: 'INZ002418S', name: 'Bezpieczeństwo sys.web.i mob.' },
    { code: 'INZ003854W', name: 'Projektowanie sys. informat.' }
  ];
  courseInstructors: CourseInstructor[];
  // courseInstructors: {name: string, hours: number, pensum: number}[] = [
  //   { name: 'inż. Konrad Jakubowski', hours: 50, pensum: 100 },
  //   { name: 'inż. Hubert Kościelski', hours: 90, pensum: 90 },
  //   { name: 'dr inż. Bogumiła Hnatkowska', hours: 120, pensum: 240 },
  //   { name: 'dr inż. Artur Wilczek', hours: 180, pensum: 120 }
  // ];
  pickedAcademicYear: string;
  readonly academicYears: {name: string}[] = [
    {name: '2014/15'},
    {name: '2015/16'},
    {name: '2016/17'},
    {name: '2017/18'},
    {name: '2018/19'},
    {name: '2019/20'}
  ];
  readonly faculties: {symbol: string, name: string}[] = [
    {symbol: 'W08', name: 'Wydział Informatyki i Zarządzania'},
    {symbol: 'W04', name: 'Wydział Elektroniki'}
  ];
  readonly fieldsOfStudy: {name: string, facultySymbol: string}[] = [
    {name: 'Informatyka', facultySymbol: 'W04'},
    {name: 'Automatyka i Robotyka', facultySymbol: 'W04'},
    {name: 'Informatyka', facultySymbol: 'W08'},
    {name: 'Zarządzanie', facultySymbol: 'W08'},
    {name: 'Inżynieria Systemów', facultySymbol: 'W08'}
  ];

  pickedSemester: number;
  readonly semesters: {number: number}[] = [
    {number: 1},
    {number: 2},
    {number: 3},
    {number: 4},
    {number: 5},
    {number: 6},
    {number: 7}
  ];

  facultySelectId: Selection;
  @Input() isCourseInstructor = false;

  modelCourse: any;
  modelCourseInstructor: any;

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

  // searchCourseInstructors = (text$: Observable<string>) => {
  //   const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
  //   const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
  //   const inputFocus$ = this.focusCourseInstructorsTextBox$;
  //
  //   return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
  //     map(term => term === '' ? this.courseInstructors
  //       : this.courseInstructors.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  // }

  searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.courseInstructors
        : this.courseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  }

  formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private courseInstructorService: CourseInstructorService) { }

  ngOnInit() {
    this.courseInstructorService.findAll(1).subscribe(
      instructors => {
        console.log("SUBSCRIBED");
        this.courseInstructors = instructors;
        console.log(this.courseInstructors.map(x => `${x.academicDegree} ${x.firstName} ${x.surname}`).toString());
      }
    );
  }

  onSearchClicked() {
  }

  onClearFiltersClicked() {
    this.pickedAcademicYear = undefined;
  }

  shouldShowFaculties() {
    return true;
  }

  shouldLockFaculties() {
    return !this.isCourseInstructor;
  }
}
