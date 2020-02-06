import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {NgbDropdown} from '@ng-bootstrap/ng-bootstrap';
import {Router} from "@angular/router";
import {Entrustment, EntrustmentService, Status} from "../entrustment.service";
import {SharedDataService} from "../shared-data.service";

@Component({
  selector: 'app-entrustment-card',
  templateUrl: './entrustment-card.component.html',
  styleUrls: ['./entrustment-card.component.css']
})
export class EntrustmentCardComponent implements OnInit {
  constructor(private router: Router, private entrustmentService: EntrustmentService, private sharedDataService: SharedDataService) {
  }

  static x = 0;
  @ViewChild(NgbDropdown, {static: false})
  dropdown: NgbDropdown;
  actualX = 0;

  @Input() ddX = 0;
  @Input() ddY = 0;
  @Input() entrustment: Entrustment;

  ngOnInit() {
    EntrustmentCardComponent.x += 1;
    this.actualX = EntrustmentCardComponent.x;
  }

  onCardClicked(event) {
    this.ddX = event.clientX;
    this.ddY = event.clientY;
    document.documentElement.style.setProperty('--ddX', event.clientX);
    document.documentElement.style.setProperty('--ddY', event.clientY);
    // console.log('VALUE == ' + document.documentElement.style.getPropertyValue('--ddX'));
    if (this.isCardEditable()) {
      this.dropdown.toggle();
    }
  }

  onEditClicked() {
    this.sharedDataService.actualCard = this.entrustment;
    this.router.navigate(['/entrustment-preview-panel']);
  }

  onAcceptClicked() {
    this.entrustmentService.acceptEntrustment(0, this.entrustment.id).subscribe(x => {
      this.entrustment.status = Status.ACCEPTED;
    });
  }

  onRejectClicked() {
    this.entrustmentService.rejectEntrustment(0, this.entrustment.id).subscribe(x => {
      this.entrustment.status = Status.REJECTED;
    });
  }

  isCardEditable() {
    return this.canBeModified() || this.canBeAccepted() || this.canBeRejected();
  }

  getStatusColor() {
    switch (this.entrustment.status) {
      case Status.ACCEPTED: return 'green';
      case Status.MODIFIED: return 'orange';
      case Status.PROPOSED: return 'orange';
      case Status.REJECTED: return 'red';
    }
  }

  canBeAccepted() {
    return this.entrustment.status == Status.PROPOSED || this.entrustment.status == Status.MODIFIED;
  }

  canBeModified() {
    return this.entrustment.status == Status.PROPOSED;
  }

  canBeRejected() {
    return this.entrustment.status == Status.PROPOSED || this.entrustment.status == Status.MODIFIED;
  }
}
