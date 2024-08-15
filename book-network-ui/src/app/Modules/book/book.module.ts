import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './Pages/main/main.component';
import { MenuComponent } from './Components/menu/menu.component';
import { BookListComponent } from './Pages/book-list/book-list.component';
import { BookCardComponent } from './Components/book-card/book-card.component';
import { RatingComponent } from './Components/rating/rating.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    RatingComponent
  ],
  imports: [
    CommonModule,
    BookRoutingModule
  ]
})
export class BookModule { }
