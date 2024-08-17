import { Component, OnInit } from '@angular/core';
import { BookResponse, BorrowedBookResponse, FeedBackRequest, PageResponseBorrowedBookResponse } from 'src/app/services/models';
import { BookService, FeedBackService } from 'src/app/services/services';

@Component({
  selector: 'app-borrowed-book-list',
  templateUrl: './borrowed-book-list.component.html',
  styleUrls: ['./borrowed-book-list.component.css']
})
export class BorrowedBookListComponent implements OnInit {
  
  constructor(private bookService:BookService, private feedBackService:FeedBackService) { }

  borrowedBooks: PageResponseBorrowedBookResponse = {};
  public page= 0;
  public size = 5;
  public selectedBook: BorrowedBookResponse | undefined= undefined;
  public feedBackRequest: FeedBackRequest = {
    bookId: 0,
    comment: ''
  }


  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }
  findAllBorrowedBooks() {
    this.bookService.findAllBorrowedBooks({page:this.page,size:this.size}).subscribe((res:PageResponseBorrowedBookResponse) => {
      this.borrowedBooks = res;
    }); 
  }


  goToFirstPage(){
    this.page=0;
    this.findAllBorrowedBooks();
    }
  goToPreviousPage(){
    this.page--;
    this.findAllBorrowedBooks();
  }

  goToPage(index: number){
    this.page=index;
    this.findAllBorrowedBooks();
  }

  goToNextPage(){
    this.page++;
    this.findAllBorrowedBooks();
  }

  goToLastPage(){
    this.page= this.borrowedBooks.totalPages as  number -1;
    this.findAllBorrowedBooks();
  }

  get isLastPage(): boolean{
    return this.page == this.borrowedBooks.totalPages as number - 1 ;
  }




  returnBorrowedBook(book:BorrowedBookResponse){

    this.selectedBook=book;
    this.feedBackRequest.bookId=book.id as number;
  }

  returnBook(withFeedBack:boolean){
    this.bookService.returnBorrowedBook({'book-id':this.selectedBook?.id as number}).subscribe({
      next:()=>{
        if(withFeedBack){
          this.giveFeedBack();
        }
        this.selectedBook=undefined;
        this.findAllBorrowedBooks();
      }
    })
  }
  giveFeedBack() {
      this.feedBackService.saveFeedBack({body:this.feedBackRequest}).subscribe({
        next:()=>{

        }
      })
  }

}
