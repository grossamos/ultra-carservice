import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AutomobileListComponent} from './automobile-list/automobile-list.component';
import {AutomobileSearchComponent} from './automobile-search/automobile-search.component';
import {AutomobileCreateComponent} from './automobile-create/automobile-create.component';
import {AutomobileEditComponent} from './automobile-edit/automobile-edit.component';

const routes: Routes = [
  { path: 'list-all', component: AutomobileListComponent },
  { path: 'search', component: AutomobileSearchComponent },
  { path: 'create', component: AutomobileCreateComponent },
  { path: 'edit', component: AutomobileEditComponent },
  {path: '',   redirectTo: '/list-all', pathMatch: 'full' },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
