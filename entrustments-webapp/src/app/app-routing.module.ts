import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CruMainViewComponent } from './entrustments/cru-main-view/cru-main-view.component';
import { SuggestionsMainViewComponent } from './entrustments/suggestions-main-view/suggestions-main-view.component';
import { MainMenuComponent } from './main-menu/main-menu.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'menu',
    pathMatch: 'full'
  },
  {
    path: 'cru-main-view',
    component: CruMainViewComponent
  },
  {
    path: 'suggestions-main-view',
    component: SuggestionsMainViewComponent
  },
  {
    path: 'menu',
    component: MainMenuComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

