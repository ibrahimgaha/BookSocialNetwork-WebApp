import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerRequest: RegistrationRequest = {email: '', firstname: '', lastname: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(private router: Router, private authService: AuthenticationService) {}

  ngOnInit(): void {}

  register() {
    this.errorMsg = [];
    this.authService.register({body: this.registerRequest}).subscribe({
      next: () => {
        this.router.navigate(['/activate-account']);
      },
      error: (err) => {
        // Check if err and validationErrors exist before accessing them
        if (err && err.error && err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          // Handle the case where validationErrors is not available
          this.errorMsg = ['An unexpected error occurred. Please try again later.'];
          console.error('Unexpected error structure:', err);
        }
      }
    });
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }
}
