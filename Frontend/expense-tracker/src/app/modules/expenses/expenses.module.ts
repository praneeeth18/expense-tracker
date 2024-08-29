import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddExpenseFormComponent } from './add-expense-form/add-expense-form.component'; // Adjust the path as necessary
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
  declarations: [
    AddExpenseFormComponent,
    DashboardComponent
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
