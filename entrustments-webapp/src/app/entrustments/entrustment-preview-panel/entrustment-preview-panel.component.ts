import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import {EntrustmentService, Status} from "../entrustment.service";
import {SharedDataService} from "../shared-data.service";

@Component({
  selector: 'app-entrustment-preview-panel',
  templateUrl: './entrustment-preview-panel.component.html',
  styleUrls: ['./entrustment-preview-panel.component.css']
})
export class EntrustmentPreviewPanelComponent implements OnInit {
  pickedNumberOfHours: number;
  difference = 0;

  constructor(private location: Location, private entrustmentService: EntrustmentService, private sharedDataService: SharedDataService) { }

  ngOnInit() {
    this.pickedNumberOfHours = this.sharedDataService.actualCard.numberOfHours;
  }

  onBackClicked() {
    this.location.back();
  }

  onAcceptClicked() {
    this.entrustmentService.acceptEntrustment(0, this.sharedDataService.actualCard.id).subscribe(x => {
      this.sharedDataService.actualCard.status = Status.ACCEPTED;
      this.location.back();
    });
  }

  onRejectClicked() {
    this.entrustmentService.rejectEntrustment(0, this.sharedDataService.actualCard.id).subscribe(x => {
      this.sharedDataService.actualCard.status = Status.REJECTED;
      this.location.back();
    });
  }

  onSuggestClicked() {
    this.entrustmentService.modifyEntrustmentHours(0, this.sharedDataService.actualCard.id, this.pickedNumberOfHours).subscribe(x => {
      this.sharedDataService.actualCard = x;
      this.location.back();
    });
  }

  onUndoClicked() {
    this.pickedNumberOfHours = this.sharedDataService.actualCard.numberOfHours;
    this.onValueChanged();
  }

  onValueChanged() {
    this.difference = this.pickedNumberOfHours - this.sharedDataService.actualCard.numberOfHours;
  }

  wasChanged() {
    return this.pickedNumberOfHours !== this.sharedDataService.actualCard.numberOfHours;
  }
}
