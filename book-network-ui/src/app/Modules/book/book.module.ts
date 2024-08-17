import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './Pages/main/main.component';
import { MenuComponent } from './Components/menu/menu.component';
import { BookListComponent } from './Pages/book-list/book-list.component';
import { BookCardComponent } from './Components/book-card/book-card.component';
import { RatingComponent } from './Components/rating/rating.component';
import { MyBooksComponent } from './Pages/my-books/my-books.component';
import { ManageBookComponent } from './Pages/manage-book/manage-book.component';
import { FormsModule } from '@angular/forms';
import { BorrowedBookListComponent } from './Pages/borrowed-book-list/borrowed-book-list.component';
import { ReturnedBooksComponent } from './Pages/returned-books/returned-books.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    RatingComponent,
    MyBooksComponent,
    ManageBookComponent,
    BorrowedBookListComponent,
    ReturnedBooksComponent
  ],
  imports: [
    CommonModule,
    BookRoutingModule,
    FormsModule,

  ]
})
export class BookModule { }
