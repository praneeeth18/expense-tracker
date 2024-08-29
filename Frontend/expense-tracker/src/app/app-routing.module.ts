import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './modules/expenses/dashboard/dashboard.component';
import { AddExpenseFormComponent } from './modules/expenses/add-expense-form/add-expense-form.component';

const routes: Routes = [
  
    { path: '', component: DashboardComponent }, 
   {path:'add-expense-form',component:AddExpenseFormComponent},
    { path: '**', redirectTo: '' } //404
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
