import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Budget {
  id?: number;
  name: string;
  amount: number;
  startDate?: string;
  endDate?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  private url = 'http://localhost:9001/api/budgets';

  constructor(private http: HttpClient) { }

  getBudgets(): Observable<Budget[]> {
    return this.http.get<Budget[]>(this.url);
  }

  createBudget(budget: Budget): Observable<Budget> {
    return this.http.post<Budget>(this.url, budget);
  }

  updateBudget(id: number, budget: Budget): Observable<Budget> {
    return this.http.put<Budget>(`${this.url}/${id}`, budget);
  }

  deleteBudget(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
