import { Component, inject, Input, OnInit } from '@angular/core';
import { BudgetsStore } from '../../../stores/bugdets.store';

@Component({
  selector: 'app-budgets',
  templateUrl: './budgets.component.html',
})
export class BudgetsComponent implements OnInit {
  private readonly budgetStore = inject(BudgetsStore);

  budgets = this.budgetStore.budgets;

  ngOnInit(): void {
    this.budgetStore.getAll();
  }
}
