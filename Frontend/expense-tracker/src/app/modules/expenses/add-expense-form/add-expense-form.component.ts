import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ExpenseService } from '../../../services/expense.service';
import { ExpenseRequestDTO } from '../../../models/expense-request-dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-expense-form',
  templateUrl: './add-expense-form.component.html',
  styleUrl: './add-expense-form.component.css'
})
export class AddExpenseFormComponent {
  expenseForm: FormGroup;

  constructor(private fb: FormBuilder, private expenseService: ExpenseService,private router: Router) {
    this.expenseForm = this.fb.group({
      category: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0)]],
      createdDate: ['', Validators.required],
      description: [''],
      receipt: ['']
    });
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.expenseForm.patchValue({
        receipt: file
      });
    }
  }

  onSubmit(): void {
    if (this.expenseForm.valid) {
      const expenseValues = this.expenseForm.value;
  
      // Convert createdDate to a Date object if it's not already one
      let createdDate: Date | null = null;
      if (expenseValues.createdDate) {
        createdDate = new Date(expenseValues.createdDate);
      }
  
      // Prepare the DTO object with the necessary fields
      const expenseRequest: ExpenseRequestDTO = {
        amount: expenseValues.amount,
        description: expenseValues.description,
        category: expenseValues.category,
        createdDate: createdDate, // Pass the converted Date object
        userId: 1 // Assuming you're hardcoding userId for now
      };
  
      // Call the service method to create the expense
      this.expenseService.createExpense(expenseRequest, expenseValues.receipt).subscribe({
        next: (response) => {
          console.log('Expense created successfully!', response);
          alert('Entry added successfully');
          this.router.navigate(['/']); // Redirect to home or another page upon success
        },
        error: (error) => {
          console.error('Error creating expense!', error);
          // Handle error response here
        },
        complete: () => {
          console.log('Expense creation process completed.');
        }
      });
    }
  }
  
  
}
