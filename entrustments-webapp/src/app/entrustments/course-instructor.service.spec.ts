import { TestBed } from '@angular/core/testing';

import { CourseInstructorService } from './course-instructor.service';

describe('CourseInstructorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CourseInstructorService = TestBed.get(CourseInstructorService);
    expect(service).toBeTruthy();
  });
});
