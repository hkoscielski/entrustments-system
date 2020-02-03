import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';

@Component({
  selector: 'app-entrustment-preview-panel',
  templateUrl: './entrustment-preview-panel.component.html',
  styleUrls: ['./entrustment-preview-panel.component.css']
})
export class EntrustmentPreviewPanelComponent implements OnInit {
  numberOfHours = 90;
  initialNumberOfHours = 90;
  difference = 0;

  constructor(private location: Location) { }

  ngOnInit() {
  }

  onBackClicked() {
    this.location.back();
  }

  onAcceptClicked() {
    // this.location.back();
  }

  onDiscardClicked() {
    // this.location.back();
  }

  onSuggestClicked() {
    // this.location.back();
  }

  onUndoClicked() {
    this.numberOfHours = this.initialNumberOfHours;
    this.onValueChanged();
  }

  onValueChanged() {
    this.difference = this.numberOfHours - this.initialNumberOfHours;
  }

  wasChanged() {
    return this.numberOfHours !== this.initialNumberOfHours;
  }
}
