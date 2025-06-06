import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Expense {
  id?: number;
  description: string;
  amount: number;
  expenseDate?: string;
  budget?: { id: number };
}

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  private url = 'http://localhost:8080/api/expenses';

  constructor(private http: HttpClient) { }

  getExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(this.url);
  }

  createExpense(expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(this.url, expense);
  }

  updateExpense(id: number, expense: Expense): Observable<Expense> {
    return this.http.put<Expense>(`${this.url}/${id}`, expense);
  }

  deleteExpense(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
