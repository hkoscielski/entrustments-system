import { Component } from '@angular/core';
import {SharedDataService} from "./entrustments/shared-data.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'entrustments-webapp';

  constructor(private sharedDataService: SharedDataService) {
  }
}
