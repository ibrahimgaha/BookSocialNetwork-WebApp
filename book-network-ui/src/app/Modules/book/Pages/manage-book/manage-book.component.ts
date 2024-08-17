import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { BookRequest } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.css']
})
export class ManageBookComponent implements OnInit {

  constructor(private bookService:BookService,private router:Router,private activatedRoute: ActivatedRoute) { }


  selectedPicture:string | undefined ;
  selectedBookCover:any;
  bookRequest:BookRequest={
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  }
    errorMsg:Array<string> =[];

    ngOnInit(): void {

      const bookId=this.activatedRoute.snapshot.params['bookId'];
      if(bookId){
        this.bookService.findBookById({'book-id':bookId}).subscribe({
          next:(book)=>{
            this.bookRequest={
              id : book.id,
              title : book.title as string,
              authorName : book.authorName as string,
              isbn : book.isbn as string,
              synopsis : book.synopsis as string,
              shareable : book.shareable
            }
            if(book.cover){
              this.selectedPicture = 'data:image/png;base64,'+book.cover;
            }
          }
        })
      }
    }
  


  onFileSelected(file:any){
    this.selectedBookCover = file.target.files[0];
    console.log(this.selectedBookCover);
    if(this.selectedBookCover){
      const reader = new FileReader();
      reader.onload = (e:any) => {
        this.selectedPicture = e.target.result as string;
      }
      reader.readAsDataURL(this.selectedBookCover);
    }
    
  }
  saveBook(){
    this.bookService.saveBook({
      body: this.bookRequest
    }).subscribe({
      next:(bookId)=>{
        this.bookService.uploadBookCoverPicture({'book-id':bookId,body:{
          file:this.selectedBookCover
        }}).subscribe({
          next:()=>{
            this.router.navigate(['/books/my-books'])
          }
        })
      },
      error:(err)=>{
        console.log(err);
        if(err.error.validationErrors){
          this.errorMsg= err.error.validationErrors
        }
      }
      
    })
  }
}
