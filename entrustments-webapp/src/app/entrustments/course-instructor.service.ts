import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CourseInstructorService {

  constructor(private httpClient: HttpClient) { }

  findAll(fieldOfStudyId: number): Observable<CourseInstructor[]> {
    // const header = new HttpHeaders().append('Access-Control-Allow-Origin', null)
    return this.httpClient.get<CourseInstructor[]>(`${environment.apiBaseUrl}/api/v1/fields-of-study/${fieldOfStudyId}/course-instructors/`);
  }

  // findAll(): Observable<CourseInstructor[]> {
  //   return this.httpClient.get<CourseInstructor[]>(`${environment.apiBaseUrl}/api/v1/fields-of-study/${fieldOfStudyId}/course-instructors/`);
  // }
}

export class CourseInstructor {
  id?: number;
  firstName: string;
  surname: string;
  academicDegree: string;
  agreements?: Agreement[];
  courseInstructorType: string;
  additionalAttributes?: AdditionalAttributes;

  constructor(id?: number, firstName?: string, surname?: string, academicDegree?: string, agreements?: Agreement[], courseInstructorType?: string, additionalAttributes?: AdditionalAttributes) {
    this.id = id;
    this.firstName = firstName;
    this.surname = surname;
    this.academicDegree = academicDegree;
    this.agreements = agreements;
    this.courseInstructorType = courseInstructorType;
    this.additionalAttributes = additionalAttributes;
  }
}

export enum CourseInstructorType {
  Teacher = "Teacher",
  DoctoralStudent = "DoctoralStudent",
  Specialist = "Specialist"
}

export class Agreement {
  startDate: string;
  endDate: string;
  didacticForm: string;

  constructor(startDate?: string, endDate?: string, didacticForm?: string) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.didacticForm = didacticForm;
  }
}

export class AdditionalAttributes {
  pensum?: string;
  position?: string;
  timeBasis?: string;

  constructor(pensum?: string, position?: string, timeBasis?: string) {
    this.pensum = pensum;
    this.position = position;
    this.timeBasis = timeBasis;
  }
}
