import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service.service';
import { CommonModule } from '@angular/common'; // Import CommonModule

interface Expense {
  month: string;
  amount: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  standalone: true, // Mark this component as standalone
  imports: [CommonModule] // Import CommonModule here

})
export class DashboardComponent implements OnInit {

  username: string = '';
  email: string = '';
  message: string = '';
  error: string = '';
  expenses: Expense[] = [];

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadUserData();
    this.loadUserExpenses();
  }

  loadUserData(): void {
    this.http.get<any>('http://localhost:9001/api/user/dashboard').subscribe({
      next: data => {
        this.username = data.username;
        this.email = data.email;
        this.message = data.message;
      },
      error: err => {
        this.error = 'Failed to load user data.';
        if (err.status === 401) {
          this.authService.logout();
          this.router.navigate(['/login']);
        }
      }
    });
  }

  loadUserExpenses(): void {
    this.http.get<Expense[]>('http://localhost:9001/api/user/expenses').subscribe({
      next: data => {
        this.expenses = data;
      },
      error: err => {
        this.error = 'Failed to load expenses.';
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
