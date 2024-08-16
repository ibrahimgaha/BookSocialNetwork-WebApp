import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BookResponse } from 'src/app/services/models';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.css']
})
export class BookCardComponent implements OnInit {

  constructor() { }

  public _book : BookResponse ={};
  public _manage: Boolean = false;  // Explicitly set an initial value
  public _bookCover : String | undefined ;

  ngOnInit(): void {
  }

  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(book: BookResponse) {
    this._book = book;
  }

  get bookCover(): String | undefined {
    if(this._book.cover){
      return 'data:image.jpg;base64,' + this._book.cover;
    }
    return 'https://images.unsplash.com/photo-1461988320302-91bde64fc8e4?ixid=2yJhcHBfaWQiOjEyMDd9';
  }


  get manage(): Boolean {
    return this._manage;
  }

  set manage(value: Boolean) {
  console.log('Setting manage to:', value);
  this._manage = value;
}


  @Output() private share : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();
  @Output() private addToWaitingList : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();
  @Output() private borrow : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();
  @Output() private edit : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();
  @Output() private details : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();
  @Output() private archive : EventEmitter<BookResponse> =new EventEmitter <BookResponse>();


  onShowDetails(){
    this.details.emit(this._book)
  }
  onAddToWatingList(){
    this.addToWaitingList.emit(this._book)

  }

  onBorrow(){
    this.borrow.emit(this._book)

  }

  onEdit(){
    this.edit.emit(this._book)

  }

  onShare(){
    this.share.emit(this._book)

  }

  onArchive(){
    this.archive.emit(this._book)

  }

}
