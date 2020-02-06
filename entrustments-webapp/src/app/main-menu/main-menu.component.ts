import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {CourseInstructor, CourseInstructorService} from "../entrustments/course-instructor.service";
import {merge, Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, filter, map} from "rxjs/operators";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {SharedDataService} from "../entrustments/shared-data.service";
import {Router} from "@angular/router";
import {StudyPlanService} from "../entrustments/study-plan.service";

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {
  pickedCourseInstructor: CourseInstructor;
  courseInstructors: CourseInstructor[];

  @ViewChild('courseInstructorsTextBox', {static: true}) courseInstructorsTextBox: NgbTypeahead;
  focusCourseInstructorsTextBox$ = new Subject<string>();
  clickCourseInstructorsTextBox$ = new Subject<string>();

  searchCourseInstructors = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickCourseInstructorsTextBox$.pipe(filter(() => !this.courseInstructorsTextBox.isPopupOpen()));
    const inputFocus$ = this.focusCourseInstructorsTextBox$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => term === '' ? this.courseInstructors
        : this.courseInstructors.filter(v => v.academicDegree.toLowerCase().concat(' ', v.firstName.toLowerCase(), ' ', v.surname.toLowerCase()).indexOf(term.toLowerCase()) > -1).slice(0, 10)));
  }

  formatterCourseInstructor = (x: {academicDegree: string, firstName: string, surname: string}) => `${x.academicDegree} ${x.firstName} ${x.surname}`;

  constructor(private router: Router, private courseInstructorService: CourseInstructorService, private sharedDataService: SharedDataService, private studyPlanService: StudyPlanService) { }

  ngOnInit() {
    this.sharedDataService.resetActualAllData();
    // this.courseInstructorService.findAll().subscribe(
    //   instructors => {
    //     this.courseInstructors = instructors;
    //   }
    // );
    this.courseInstructors = this.sharedDataService.courseInstructors;
  }

  onCruClicked() {
    // this.studyPlanService.findAllFaculties().subscribe(
    //   faculties => {
    //     this.sharedDataService.actualFaculty = faculties[0];
    //     this.studyPlanService.findAllFieldsOfStudyByFacultySymbol(faculties[0].symbol).subscribe(
    //       fieldsOfStudy => {
    //         this.sharedDataService.actualFieldOfStudy = fieldsOfStudy[0];
    //         this.router.navigate(['/cru-main-view']);
    //       });
    //   }
    // );
    this.sharedDataService.actualFaculty = this.sharedDataService.faculties[0];
    this.sharedDataService.actualFieldOfStudy = this.sharedDataService.fieldsOfStudy.find(fos => fos.facultySymbol == this.sharedDataService.actualFaculty.symbol);
    this.sharedDataService.actualFilterOptions.faculty = this.sharedDataService.actualFaculty;
    this.sharedDataService.actualFilterOptions.fieldOfStudy = this.sharedDataService.actualFieldOfStudy;

    this.router.navigate(['/cru-main-view']);
  }

  onSuggestionsClicked() {
    this.sharedDataService.actualCourseInstructor = this.pickedCourseInstructor;
    this.sharedDataService.actualFilterOptions.courseInstrucor = this.pickedCourseInstructor;
    this.router.navigate(['/suggestions-main-view']);
  }
}
