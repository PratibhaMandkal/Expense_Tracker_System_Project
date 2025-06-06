import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service.service';


@Component({
  selector: 'app-register',
  standalone:false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  submitted = false;
  error = '';
  success = '';

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  // Returns form controls explicitly typed as any for template flexibility
  get f(): { [key: string]: any } {
    return this.registerForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.error = '';
    this.success = '';

    if (this.registerForm.invalid) {
      return;
    }

    const { username, email, password } = this.registerForm.value;

    this.authService.register(username, email, password).subscribe({
      next: () => {
        this.success = 'Registration successful! Redirecting to login page...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err: { error: string; }) => {
        this.error = err.error || 'Registration failed. Please try again.';
      }
    });
  }
}
