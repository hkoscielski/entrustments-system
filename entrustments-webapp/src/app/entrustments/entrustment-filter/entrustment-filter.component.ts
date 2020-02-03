import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {debounceTime, map} from 'rxjs/operators';

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
  readonly courseInstructors: {name: string, hours: number, pensum: number}[] = [
    { name: 'inż. Konrad Jakubowski', hours: 50, pensum: 100 },
    { name: 'inż. Hubert Kościelski', hours: 90, pensum: 90 },
    { name: 'dr inż. Bogumiła Hnatkowska', hours: 120, pensum: 240 },
    { name: 'dr inż. Artur Wilczek', hours: 180, pensum: 120 }
  ];
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

  facultySelectId: Selection;
  @Input() isCourseInstructor = false;

  public modelCourse;
  public modelCourseInstructor;

  searchCourses = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term === '' ? []
        : this.courses.filter(v => v.code.toLowerCase().concat(' ', v.name.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10))
    )

  formatterCourse = (x: {code: string, name: string}) => x.code + ' ' + x.name;

  searchCourseInstructors = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term === '' ? []
        : this.courseInstructors.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    )

  formatterCourseInstructor = (x: {name: string}) => x.name;

  constructor() { }

  ngOnInit() {
  }

  onSearchClicked() {
  }

  onClearFiltersClicked() {
  }

  shouldShowFaculties() {
    return true;
  }

  shouldLockFaculties() {
    return !this.isCourseInstructor;
  }
}
