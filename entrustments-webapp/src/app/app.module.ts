import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { CruMainViewComponent } from './entrustments/cru-main-view/cru-main-view.component';
import { SuggestionsMainViewComponent } from './entrustments/suggestions-main-view/suggestions-main-view.component';
import { EntrustmentsModule } from './entrustments/entrustments.module';
import {EntrustmentFilterComponent} from './entrustments/entrustment-filter/entrustment-filter.component';

@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    CruMainViewComponent,
    SuggestionsMainViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule
    // ,EntrustmentsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
