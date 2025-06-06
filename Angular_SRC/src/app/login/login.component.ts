import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service.service';

@Component({
  selector: 'app-login',
  standalone:false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  error: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    this.submitted = true;

    // Stop here if the form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    // Call the login method from AuthService
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe({
      next: () => {
        // Navigate to the dashboard or another route upon successful login
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        console.error('Login error:', err); // Log the error for debugging

        this.error = 'Login failed. Please check your credentials.';
      }
    });
  }
}
