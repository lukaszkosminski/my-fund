import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Budget, Expense, Income, Summary } from '../models/Budget.model';

const jsonPayloadHttpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class BudgetsService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Budget[]>(`/api/budgets`);
  }

  get(id: string) {
    return this.http.get<Budget>(`/api/budgets/${id}`);
  }

  create(budget: Budget): Observable<Budget> {
    return this.http.post<Budget>(
      `/api/budgets`,
      { ...budget },
      jsonPayloadHttpOptions
    );
  }

  addExpense(budgetId: string, expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(
      `/api/budgets/${budgetId}/expenses`,
      { ...expense },
      jsonPayloadHttpOptions
    );
  }

  addIncome(budgetId: string, income: Income): Observable<Income> {
    return this.http.post<Income>(
      `/api/budgets/${budgetId}/incomes`,
      { ...income },
      jsonPayloadHttpOptions
    );
  }

  getSummary(budgetId: string): Observable<Summary> {
    return this.http.get<Summary>(`/api/budgets/${budgetId}/expenses/summary`);
  }
}
