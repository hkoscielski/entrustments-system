import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Entrustment} from "../../entrustment.service";
import {Course} from "../../study-plan.service";
import {SharedDataService} from "../../shared-data.service";

@Component({
  selector: 'app-course-card',
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css']
})
export class CourseCardComponent implements OnInit {
  @Input() course: Course;

  constructor(private sharedDataService: SharedDataService) { }

  ngOnInit() {
  }

  onCardClicked() {
    this.sharedDataService.onPickedCourse.next(this.course);
  }

  isCardEditable() {
    return false;
  }
}
