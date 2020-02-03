import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {NgbDropdown} from '@ng-bootstrap/ng-bootstrap';
import {Router} from "@angular/router";

@Component({
  selector: 'app-entrustment-card',
  templateUrl: './entrustment-card.component.html',
  styleUrls: ['./entrustment-card.component.css']
})
export class EntrustmentCardComponent implements OnInit {


  constructor(private router: Router) {
  }
  static x = 0;
  @ViewChild(NgbDropdown, {static: false})
  dropdown: NgbDropdown;
  actualX = 0;

  @Input() ddX = 0;
  @Input() ddY = 0;

  ngOnInit() {
    EntrustmentCardComponent.x += 1;
    this.actualX = EntrustmentCardComponent.x;
  }

  onCardClicked(event) {
    this.ddX = event.clientX;
    this.ddY = event.clientY;
    document.documentElement.style.setProperty('--ddX', event.clientX);
    document.documentElement.style.setProperty('--ddY', event.clientY);
    console.log('VALUE == ' + document.documentElement.style.getPropertyValue('--ddX'));
    if (this.isCardEditable()) {
      this.dropdown.toggle();
    }
  }

  onEditClicked() {
    this.router.navigate(['/entrustment-preview-panel']);
  }

  onAcceptClicked() {
  }

  onDiscardClicked() {
  }

  isCardEditable() {
    return this.actualX % 2 === 0;
  }
}
