import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/services';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent implements OnInit {


  message: string='';
  isOkay: boolean =true;
  submitted: boolean =false;

  constructor(private router:Router,private authService:AuthenticationService) { }
  ngOnInit(): void {
  }


 
  
  onCodeCompleted(token: string) {
    this.confirmAccount(token)
    // Handle code completion
  }
  confirmAccount(token: string) {
    this.authService.confirm({token}).subscribe({
      next:()=>{
        this.message = 'Your account has been activated.\n You can now log in.';
        this.submitted = true;
        this.isOkay = true;

      },
      error:()=>{
        this.message = 'The token has been expired.\n ';
        this.isOkay = false;
        this.submitted = true;
    }})
  }

  redirectTologin(){
    this.router.navigate(['/login']);
  }

}
