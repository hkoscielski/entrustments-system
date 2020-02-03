import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-entrustment-add-panel',
  templateUrl: './entrustment-add-panel.component.html',
  styleUrls: ['./entrustment-add-panel.component.css']
})
export class EntrustmentAddPanelComponent implements OnInit {

  constructor(private location: Location) { }

  ngOnInit() {
  }

  onBackClicked() {
    this.location.back();
  }

}
