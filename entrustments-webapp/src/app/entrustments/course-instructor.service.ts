import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CourseInstructorService {

  constructor(private httpClient: HttpClient) { }

  findAllByFieldOfStudyId(fieldOfStudyId: number): Observable<CourseInstructor[]> {
    return this.httpClient.get<CourseInstructor[]>(`${environment.apiBaseUrl}/api/v1/fields-of-study/${fieldOfStudyId}/course-instructors`);
  }

  findAll(): Observable<CourseInstructor[]> {
    return this.httpClient.get<CourseInstructor[]>(`${environment.apiBaseUrl}/api/v1/course-instructors`);
  }
}

export class CourseInstructor {
  id?: number;
  firstName: string;
  surname: string;
  academicDegree: string;
  agreements?: Agreement[];
  courseInstructorType: CourseInstructorType;
  entrustedHours: number;
  additionalAttributes: AdditionalAttributes;

  constructor(id?: number, firstName?: string, surname?: string, academicDegree?: string, agreements?: Agreement[], courseInstructorType?: CourseInstructorType, entrustedHours?: number, additionalAttributes?: AdditionalAttributes) {
    this.id = id;
    this.firstName = firstName;
    this.surname = surname;
    this.academicDegree = academicDegree;
    this.agreements = agreements;
    this.courseInstructorType = courseInstructorType;
    this.entrustedHours = entrustedHours;
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
  pensum?: number;
  position?: string;
  timeBasis?: number;

  constructor(pensum?: number, position?: string, timeBasis?: number) {
    this.pensum = pensum;
    this.position = position;
    this.timeBasis = timeBasis;
  }
}
