import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {CourseInstructor} from "./course-instructor.service";

@Injectable({
  providedIn: 'root'
})
export class StudyPlanService {

  constructor(private httpClient: HttpClient) { }

  findAllFaculties(): Observable<Faculty[]> {
    return this.httpClient.get<Faculty[]>(`${environment.apiBaseUrl}/api/v1/faculties`);
  }

  findAllSemesters(): Observable<Semester[]> {
    return this.httpClient.get<Semester[]>(`${environment.apiBaseUrl}/api/v1/semesters`);
  }

  findAllSemestersByFieldOfStudyId(fieldOfStudyId: number): Observable<Semester[]> {
    return this.httpClient.get<Semester[]>(`${environment.apiBaseUrl}/api/v1/fields-of-study/${fieldOfStudyId}/semesters`);
  }

  findAllFieldsOfStudyByFacultySymbol(facultySymbol: string): Observable<FieldOfStudy[]> {
    return this.httpClient.get<FieldOfStudy[]>(`${environment.apiBaseUrl}/api/v1/faculties/${facultySymbol}/fields-of-study`);
  }
}

export class Faculty {
  id?: number;
  symbol: string;
  name: string;

  constructor(id?: number, symbol?: string, name?: string) {
    this.id = id;
    this.symbol = symbol;
    this.name = name;
  }
}

export class Semester {
  id?: number;
  academicYear: string;
  semesterName: string;
  semesterNumber: number;
  startAcademicYear: string;
  startSemesterName: string;
  fieldOfStudy: FieldOfStudy;
  specialty: Specialty;
  studyLevel: StudyLevel;
  formOfStudy: FormOfStudy;
  studyLanguage: StudyLanguage;
  courses: Course[];
  modules: Module[];

  constructor(id?: number, academicYear?: string, semesterName?: string, semesterNumber?: number, startAcademicYear?: string, startSemesterName?: string, fieldOfStudy?: FieldOfStudy, specialty?: Specialty, studyLevel?: StudyLevel, formOfStudy?: FormOfStudy, studyLanguage?: StudyLanguage, courses?: Course[], modules?: Module[]) {
    this.id = id;
    this.academicYear = academicYear;
    this.semesterName = semesterName;
    this.semesterNumber = semesterNumber;
    this.startAcademicYear = startAcademicYear;
    this.startSemesterName = startSemesterName;
    this.fieldOfStudy = fieldOfStudy;
    this.specialty = specialty;
    this.studyLevel = studyLevel;
    this.formOfStudy = formOfStudy;
    this.studyLanguage = studyLanguage;
    this.courses = courses;
    this.modules = modules;
  }
}

export class FieldOfStudy {
  id?: number;
  name: string;
  shortName: string;

  constructor(id?: number, name?: string, shortName?: string) {
    this.id = id;
    this.name = name;
    this.shortName = shortName;
  }
}

export class Specialty {
  name: string;
  shortName: string;

  constructor(name?: string, shortName?: string) {
    this.name = name;
    this.shortName = shortName;
  }
}

export class StudyLevel {
  code: string;
  name: string;

  constructor(code?: string, name?: string) {
    this.code = code;
    this.name = name;
  }
}

export class FormOfStudy {
  code: string;
  name: string;

  constructor(code?: string, name?: string) {
    this.code = code;
    this.name = name;
  }
}

export class StudyLanguage {
  code: string;
  name: string;

  constructor(code?: string, name?: string) {
    this.code = code;
    this.name = name;
  }
}

export class Course {
  id?: number;
  code: string;
  name: string;
  zzuHours: number;
  didacticForm: DidacticForm;

  constructor(id?: number, code?: string, name?: string, zzuHours?: number, didacticForm?: DidacticForm) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.zzuHours = zzuHours;
    this.didacticForm = didacticForm;
  }
}

export class DidacticForm {
  code: string;
  name: string;

  constructor(code?: string, name?: string) {
    this.code = code;
    this.name = name;
  }
}

export class Module {

}
