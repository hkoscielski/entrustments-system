import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {CourseInstructor} from "./course-instructor.service";
import {Faculty, FieldOfStudy, Semester, Specialty, StudyLevel} from "./study-plan.service";

@Injectable({
  providedIn: 'root'
})
export class EntrustmentService {

  constructor(private httpClient: HttpClient) { }
  // academicYear=2019/2020&semester=1&studyLevel=FIRST_DEGREE&specialty=IO&courseCode=INZ000W&entrustmentStatus=ACCEPTED&courseInstructorId=1
  findAllEntrustments(academicYear?: string, semester?: number, studyLevel?: string, specialty?: string, courseCode?: string, entrustmentStatus?: string, courseInstructorId?: number): Observable<Entrustment[]> {
    let params = new HttpParams();
    if (academicYear) params = params.set('academicYear', academicYear);
    if (semester) params = params.set('semester', semester.toString());
    if (studyLevel) params = params.set('studyLevel', studyLevel);
    if (specialty) params = params.set('specialty', specialty);
    if (courseCode) params = params.set('courseCode', courseCode);
    if (entrustmentStatus) params = params.set('entrustmentStatus', entrustmentStatus);
    if (courseInstructorId) params = params.set('courseInstructorId', courseInstructorId.toString());
    // console.log("Headers: " + params.toString());
    return this.httpClient.get<Entrustment[]>(`${environment.apiBaseUrl}/api/v1/entrustments`, { params: params});
  }

  findAll(courseInstructorId: number): Observable<number> {
    return this.httpClient.get<number>(`${environment.apiBaseUrl}/api/v1/course-instructors/${courseInstructorId}/entrustments/hours`);
  }

  acceptEntrustment(semesterId: number, entrustmentId: number) {
    return this.httpClient.patch(`${environment.apiBaseUrl}/api/v1/semesters/${semesterId}/entrustments/${entrustmentId}/accept`, { description: "Accepted entrustment" });
  }

  rejectEntrustment(semesterId: number, entrustmentId: number) {
    return this.httpClient.patch(`${environment.apiBaseUrl}/api/v1/semesters/${semesterId}/entrustments/${entrustmentId}/reject`, { description: "Rejected entrustment" });
  }

  addEntrustment(semesterId: number, courseInstructorId: number, numberOfHours: number, courseCode: string): Observable<Entrustment>  {
    let body = {
        "courseInstructorId": courseInstructorId,
        "numberOfHours": numberOfHours,
        "courseCode": courseCode
      };
    return this.httpClient.post<Entrustment>(`${environment.apiBaseUrl}/api/v1/semesters/${semesterId}/entrustments`, body);
  }

  modifyEntrustmentHours(semesterId: number, entrustmentId: number, numberOfHours: number): Observable<Entrustment> {
    let body = {
      "numberOfHours": numberOfHours
    };
    return this.httpClient.patch<Entrustment>(`${environment.apiBaseUrl}/api/v1/semesters/${semesterId}/entrustments/${entrustmentId}`, body);
  }
}

export class Entrustment {
  id?: number;
  numberOfHours: number;
  course: Course;
  courseInstructor: EntrustmentCourseInstructor;
  status: Status;
  semester?: Semester;
  faculty?: Faculty;
  studyLevel?: StudyLevel;
  fieldOfStudy?: FieldOfStudy;
  specialty?: Specialty;

  constructor(id?: number, numberOfHours?: number, course?: Course, courseInstructor?: EntrustmentCourseInstructor, status?: Status) {
    this.id = id;
    this.numberOfHours = numberOfHours;
    this.course = course;
    this.courseInstructor = courseInstructor;
    this.status = status;
  }
}

export class Course {
  code: string;
  name: string;

  constructor(code?: string, name?: string) {
    this.code = code;
    this.name = name;
  }
}

export class EntrustmentCourseInstructor {
  id?: number;
  firstName: string;
  surname: string;
  academicDegree: string;

  constructor(id?: number, firstName?: string, surname?: string, academicDegree?: string) {
    this.id = id;
    this.firstName = firstName;
    this.surname = surname;
    this.academicDegree = academicDegree;
  }
}

export enum Status {
  PROPOSED = "Zaproponowane",
  MODIFIED = "Zmodyfikowane",
  ACCEPTED = "Zaakceptowane",
  REJECTED = "Odrzucone"
}

export enum ReversedStatus {
  Zaproponowane = "PROPOSED",
  Zmodyfikowane = "MODIFIED",
  Zaakceptowane = "ACCEPTED",
  Odrzucone = "REJECTED"
}
