import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddExpenseFormComponent } from './add-expense-form/add-expense-form.component'; // Adjust the path as necessary
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewExpensesComponent } from './view-expenses/view-expenses.component';

@NgModule({
  declarations: [
    AddExpenseFormComponent,
    DashboardComponent,
    ViewExpensesComponent
    // Add other components related to expenses here
  ],
  imports: [ReactiveFormsModule,
    CommonModule
  ],
  exports: [
    AddExpenseFormComponent ,
   DashboardComponent
  ]
})
export class ExpensesModule { }
