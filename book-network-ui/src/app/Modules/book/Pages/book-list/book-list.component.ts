import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageResponseBookResponse } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  bookResponse:PageResponseBookResponse = {};
  public page= 0;
  public size: 5;

  constructor(private bookService : BookService,private router:Router) { }

  ngOnInit(): void {
    this.findALLBooks();
  }
  
  findALLBooks() {
    this.bookService.findAllBooks({page:this.page,size:this.size}).subscribe({
      next:(books)=>{
        this.bookResponse=books;
      }
    }
      
    )
  }

}
