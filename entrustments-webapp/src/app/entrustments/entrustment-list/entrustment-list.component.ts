import {Component, Input, OnInit} from '@angular/core';
import {Entrustment} from "../entrustment.service";

@Component({
  selector: 'app-entrustment-list',
  templateUrl: './entrustment-list.component.html',
  styleUrls: ['./entrustment-list.component.css']
})
export class EntrustmentListComponent implements OnInit {

  // @Input() foundEntrustments: Entrustment[];

  constructor() { }

  ngOnInit() {
  }
}
