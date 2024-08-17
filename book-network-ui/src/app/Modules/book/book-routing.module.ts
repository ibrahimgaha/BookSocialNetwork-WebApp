import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './Pages/main/main.component';
import { BookListComponent } from './Pages/book-list/book-list.component';
import { MyBooksComponent } from './Pages/my-books/my-books.component';
import { ManageBookComponent } from './Pages/manage-book/manage-book.component';
import { BorrowedBookListComponent } from './Pages/borrowed-book-list/borrowed-book-list.component';
import { ReturnedBooksComponent } from './Pages/returned-books/returned-books.component';
import { AuthGuard } from 'src/app/services/guard/auth.guard';

const routes: Routes = [
  {path: '', component:MainComponent, children:[
    {path: '',component:BookListComponent ,canActivate:[AuthGuard] },
    {path: 'my-books',component:MyBooksComponent,canActivate:[AuthGuard]},
    {path: 'manage',component:ManageBookComponent,canActivate:[AuthGuard]},
    {path: 'my-borrowed-books',component:BorrowedBookListComponent,canActivate:[AuthGuard]},

    {path: 'manage/:bookId',component:ManageBookComponent,canActivate:[AuthGuard]},

    {path: 'my-returned-books',component:ReturnedBooksComponent,canActivate:[AuthGuard]},


  ]},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
