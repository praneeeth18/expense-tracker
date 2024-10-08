import { Component, OnInit } from '@angular/core';
import { ExpenseResponseDTO } from '../../../models/expense-response-dto';
import { ExpenseService } from '../../../services/expense.service';
import { UserService } from '../../../services/user.service';
import { GridApi, GridReadyEvent } from 'ag-grid-community';

@Component({
  selector: 'app-view-expenses',
  templateUrl: './view-expenses.component.html',
  styleUrl: './view-expenses.component.css'
})
export class ViewExpensesComponent implements OnInit {

  private gridApi!: GridApi<any>;

  users: any[] = [];
  expenses: ExpenseResponseDTO[] = [];
  columnDefs: any[];
  rowData: ExpenseResponseDTO[] = [];
  gridOptions: any;

  constructor(private expenseService: ExpenseService, private userService: UserService) {
    this.columnDefs = [
      { headerName: 'Expense ID', field: 'expenseId', sortable: true, filter: true },
      { headerName: 'Category', field: 'category', sortable: true, filter: true },
      { headerName: 'Description', field: 'description', sortable: true, filter: true },
      { headerName: 'Date', field: 'createdDate', sortable: true, filter: true, cellRenderer: this.formatDate },
      { headerName: 'Amount', field: 'amount', sortable: true, filter: true },
      {
        headerName: 'Receipt', field: 'receipt', cellRenderer: (params: any) => {
          return params.value ? `<button class="btn btn-primary">Download Receipt</button>` : '';
        },
        onCellClicked: (params: any) => {
          if (params.data.receipt) {
            this.downloadReceipt(params.data.expenseId);
          }
        }
      }
    ];

    this.gridOptions = {
      pagination: true,
      paginationPageSize: 10,
      defaultColDef: {
        flex: 1,
        minWidth: 100,
        sortable: true,
        filter: true
      }
    };
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  onBtExport() {
    this.gridApi.exportDataAsCsv();
  }

  onGridReady(event: GridReadyEvent<any>) {
    this.gridApi = event.api;
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (response) => {
        this.users = response;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      }
    });
  }

  onUserSelect(event: Event): void {
    const selectedUserId = (event.target as HTMLSelectElement).value;
    if (selectedUserId) {
      this.expenseService.getExpensesByUserId(Number(selectedUserId)).subscribe({
        next: (response) => {
          this.rowData = response;  
        },
        error: (error) => {
          console.error('Error fetching expenses:', error);
        }
      });
    }
  }

  formatDate(params: any): string {
    const date = new Date(params.value);
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
  }

  downloadReceipt(expenseId: number): void {
    this.expenseService.downloadReceipt(expenseId).subscribe({
      next: (response) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `receipt-${expenseId}.pdf`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error downloading receipt:', error);
      }
    });
  }
}