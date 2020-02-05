import {Component, Input, OnInit} from '@angular/core';
import {Entrustment} from "../entrustment.service";
import {Observable} from "rxjs";
import {EntrustmentFilterComponent} from "../entrustment-filter/entrustment-filter.component";

@Component({
  selector: 'app-entrustment-list',
  templateUrl: './entrustment-list.component.html',
  styleUrls: ['./entrustment-list.component.css']
})
export class EntrustmentListComponent implements OnInit {
  actualList: Entrustment[];

  constructor() { }

  ngOnInit() {
  }

  showList(entrustments: Entrustment[]) {
    this.actualList = entrustments;
  }
}
