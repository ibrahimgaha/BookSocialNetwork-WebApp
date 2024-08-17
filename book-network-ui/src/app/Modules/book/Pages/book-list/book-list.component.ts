import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookResponse, PageResponseBookResponse } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';
import Swal from 'sweetalert2'
@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  bookResponse:PageResponseBookResponse = {};
  public page= 0;
  public size = 4;
  public message = "";

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

  borrowBook(book: BookResponse) {
    this.message = "";
    this.bookService.borrowBook({'book_id': book.id as number }).subscribe({
      next: () => {
        Swal.fire({
          title: "You Borrowed this Book",
          width: 600,
          padding: "3em",
          color: "#716add",
          background: "#fff url(/images/trees.png)",
          backdrop: `
            rgba(0,0,123,0.4)
            url("https://media1.tenor.com/m/TI4Iyy-5pGoAAAAC/pile-of-books-i-want-to-read-all-the-books.gif")
            left top
            no-repeat
          `
        });
      },
      error: (err) => {
        Swal.fire({
          title: "You can not Borrow this Book(already borrowed)",
          width: 600,
          padding: "3em",
          color: "#000",
          background: "#ff9c84 url(/images/trees.png)",
          backdrop: `
            rgba(0,0,123,0.4)
            url("https://media.tenor.com/rPiHMPFCs_kAAAAi/incoludido-say-no.gif")
            left top
            no-repeat
          `
        });
      }
    });
  }
  
  }

