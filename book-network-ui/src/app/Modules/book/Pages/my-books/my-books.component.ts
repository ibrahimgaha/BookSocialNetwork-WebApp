import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookResponse, PageResponseBookResponse } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css']
})
export class MyBooksComponent implements OnInit {

  bookResponse:PageResponseBookResponse = {};
  public page= 0;
  public size = 5;
  

  constructor(private bookService : BookService,private router:Router) { }

  ngOnInit(): void {
    this.findALLBooks();
  }
  
  findALLBooks() {
    this.bookService.findAllBooksByOwner({page:this.page,size:this.size}).subscribe({
      next:(books)=>{
        this.bookResponse=books;
      }
    }
      
    )
  }

  goToFirstPage(){
    this.page=0;
    this.findALLBooks();
    }
  goToPreviousPage(){
    this.page--;
    this.findALLBooks();
  }

  goToPage(index: number){
    this.page=index;
    this.findALLBooks();
  }

  goToNextPage(){
    this.page++;
    this.findALLBooks();
  }

  goToLastPage(){
    this.page= this.bookResponse.totalPages as  number -1;
    this.findALLBooks();
  }

  get isLastPage(): boolean{
    return this.page == this.bookResponse.totalPages as number - 1 ;
  }


archiveBook(book:BookResponse){
  this.bookService.updateArchivedStatus({'book-id':book.id as number}).subscribe({
    next:()=>{
      book.archived = !book.archived
    }
  })
}

 shareBook(book:BookResponse){
  this.bookService.updateShareableStatus({'book-id':book.id as number}).subscribe({
    next:()=>{
      book.shareable = !book.shareable
    }
  })
 }

editBook(book:BookResponse){
  this.router.navigate(['books','manage',book.id]);
}

  

}
