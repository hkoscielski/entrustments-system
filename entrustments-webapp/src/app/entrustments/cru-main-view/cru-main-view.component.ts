import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {EntrustmentFilterComponent} from "../entrustment-filter/entrustment-filter.component";
import {EntrustmentListComponent} from "../entrustment-list/entrustment-list.component";

@Component({
  selector: 'app-cru-main-view',
  templateUrl: './cru-main-view.component.html',
  styleUrls: ['./cru-main-view.component.css']
})
export class CruMainViewComponent implements OnInit, AfterViewInit {

  @ViewChild(EntrustmentFilterComponent, {static: false}) entrustmentFilterComponent;
  @ViewChild(EntrustmentListComponent, {static: false}) entrustmentListComponent;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.entrustmentFilterComponent.foundEntrustments.asObservable().subscribe(
      entrustments => {
        this.entrustmentListComponent.showList(entrustments);
      })
  }

  // shouldShowList() {
  //   if (this.entrustmentFilterComponent && this.entrustmentFilterComponent.foundEntrustments)
  //     return this.entrustmentFilterComponent.foundEntrustments.length > 0;
  //   return false;
  //   // return true;
  // }

  shouldShowInstructionsText() {
    return this.entrustmentFilterComponent && !this.entrustmentFilterComponent.showFoundHint;
  }

  shouldShowNoResultsText() {
    return this.entrustmentFilterComponent && this.entrustmentFilterComponent.showFoundHint && this.entrustmentFilterComponent.foundCount == 0;
  }
}
