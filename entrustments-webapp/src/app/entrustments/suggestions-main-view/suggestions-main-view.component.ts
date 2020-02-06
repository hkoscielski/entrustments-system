import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {EntrustmentFilterComponent} from "../entrustment-filter/entrustment-filter.component";
import {EntrustmentListComponent} from "../entrustment-list/entrustment-list.component";

@Component({
  selector: 'app-suggestions-main-view',
  templateUrl: './suggestions-main-view.component.html',
  styleUrls: ['./suggestions-main-view.component.css']
})
export class SuggestionsMainViewComponent implements OnInit, AfterViewInit {

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

  shouldShowInstructionsText() {
    return this.entrustmentFilterComponent && !this.entrustmentFilterComponent.showFoundHint;
  }

  shouldShowNoResultsText() {
    return this.entrustmentFilterComponent && this.entrustmentFilterComponent.showFoundHint && this.entrustmentFilterComponent.foundCount == 0;
  }
}
