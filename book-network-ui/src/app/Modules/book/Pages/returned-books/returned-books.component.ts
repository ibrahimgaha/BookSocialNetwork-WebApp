import { Component, OnInit } from '@angular/core';
import { BorrowedBookResponse, PageResponseBorrowedBookResponse } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';

@Component({
  selector: 'app-returned-books',
  templateUrl: './returned-books.component.html',
  styleUrls: ['./returned-books.component.css']
})
export class ReturnedBooksComponent implements OnInit {

  returnedBooks: PageResponseBorrowedBookResponse = {};
  public page= 0;
  public size = 5;
  message : string =""
  level= 'success';
 
  constructor(private bookService:BookService) { }


  ngOnInit(): void {
    this.findAllReturnedBooks();
  }
  findAllReturnedBooks() {
    this.bookService.findAllReturnedBooks({ page: this.page, size: this.size })
      .subscribe({
        next: (res: PageResponseBorrowedBookResponse) => {
          this.returnedBooks = res;
          console.log(this.returnedBooks); // Debugging line
        },
        error: (err) => {
          console.error('Error fetching returned books:', err);
        }
      });
  }
  
  


  goToFirstPage(){
    this.page=0;
    this.findAllReturnedBooks();
    }
  goToPreviousPage(){
    this.page--;
    this.findAllReturnedBooks();
  }

  goToPage(index: number){
    this.page=index;
    this.findAllReturnedBooks();
  }

  goToNextPage(){
    this.page++;
    this.findAllReturnedBooks();
  }

  goToLastPage(){
    this.page= this.returnedBooks.totalPages as  number -1;
    this.findAllReturnedBooks();
  }

  get isLastPage(): boolean{
    return this.page == this.returnedBooks.totalPages as number - 1 ;
  }

  approveBookRetrun(book:BorrowedBookResponse){
    if(!book.returned){
      this.level='error';
      this.message="Book not returned ";
    }
    this.bookService.approveReturnBorrowBook({'book-id':book.id as number}).subscribe({
      next:()=>{
        this.level='success';
        this.message="Book return successfully approved";
        this.findAllReturnedBooks();
      }
    })
  }
 }
