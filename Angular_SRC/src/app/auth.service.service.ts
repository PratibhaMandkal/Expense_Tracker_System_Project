import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
//import { BehaviorSubject, Observable, Subject, map } from 'rxjs';
//import { catchError, throwError } from 'rxjs/operators';
import { BehaviorSubject, Observable, map, catchError, throwError } from 'rxjs';


export interface User {
  id: number;
  username: string;
  email: string;
  token: string;
}
export interface AuthResponse {
  id: number;
  username: string;
  email: string;
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:9001/api/auth';
  private currentUser = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUser .asObservable();

    constructor(private http: HttpClient) {
    // Check if running in a browser environment
    if (typeof window !== 'undefined' && window.localStorage) {
      const savedUser  = localStorage.getItem('currentUser ');
      if (savedUser ) {
        this.currentUser .next(JSON.parse(savedUser ));
      }
    }
  }


  login(username: string, password: string): Observable<User> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/signin`, { username, password }).pipe(
      map(user => {
        if (user && user.token) {
          localStorage.setItem('currentUser ', JSON.stringify(user));
          this.currentUser .next(user);
        }
        return user;
      }),
          catchError((error: HttpErrorResponse) => {
        console.error('Login error:', error); // Log the error for debugging
        let errorMessage = 'An unknown error occurred!';
        if (error.error instanceof ErrorEvent) {
          // Client-side error
          errorMessage = error.error.message;
        } else {
          // Server-side error
          errorMessage = `Error ${error.status}: ${error.message}`;
        }
        return throwError(() => new Error(errorMessage)); // Return a user-friendly error
      })

    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, { username, email, password });
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUser .next(null);
  }


  getToken(): string | null {
    const user = this.currentUser .value;
    return user?.token || null;
  }

  get isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
