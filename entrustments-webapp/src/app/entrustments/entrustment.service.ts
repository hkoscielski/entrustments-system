import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EntrustmentService {

  constructor(private httpClient: HttpClient) { }

  findAll(): Observable<Entrustment[]> {
    return this.httpClient.get<Entrustment[]>(`${environment.apiBaseUrl}/api/v1/entrustments/`);
  }
}

export class Entrustment {
  id?: number;
  numberOfHours: number;
  course: Course;
  courseInstructor: EntrustmentCourseInstructor;
  status: Status;

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
}

export class EntrustmentCourseInstructor {
  id?: number;
  firstName: string;
  surname: string;
  academicDegree: string;
}

export enum Status {
  Accepted = "Zaakceptowane",
  Suggested = "Zaproponowane",
  Declined = "Odrzucone"
}
