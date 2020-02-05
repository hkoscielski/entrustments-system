import {Component, OnInit, ViewChild} from '@angular/core';
import {EntrustmentFilterComponent} from "../entrustment-filter/entrustment-filter.component";

@Component({
  selector: 'app-cru-main-view',
  templateUrl: './cru-main-view.component.html',
  styleUrls: ['./cru-main-view.component.css']
})
export class CruMainViewComponent implements OnInit {

  @ViewChild(EntrustmentFilterComponent, {static: false}) entrustmentFilterComponent;

  constructor() { }

  ngOnInit() {
  }

}
