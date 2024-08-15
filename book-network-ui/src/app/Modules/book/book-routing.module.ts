import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './Pages/main/main.component';
import { BookListComponent } from './Pages/book-list/book-list.component';

const routes: Routes = [
  {path: '', component:MainComponent, children:[
    {path: '',component:BookListComponent}
  ]},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
