import { Component, OnInit } from '@angular/core';
import { ExpenseResponseDTO } from '../../../models/expense-response-dto';
import { ExpenseService } from '../../../services/expense.service';
import { UserService } from '../../../services/user.service';
import * as Highcharts from 'highcharts';
import { NotificationServiceService } from '../../../services/notification-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  users: any[] = [];
  selectedUserId: number | null = null;
  selectedMonth: string = 'All'; 
  totalExpenses: number = 0;
  donutChartOptions: any;
  lineChartOptions: any;
  Highcharts: typeof Highcharts = Highcharts;
  expenses: ExpenseResponseDTO[] = [];
  months: string[] = ['All', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

  showNotifications = false;
  notifications: any[] = [];

  constructor(private expenseService: ExpenseService, private userService: UserService, private notificationService: NotificationServiceService) { }
  

  ngOnInit(): void {
    this.loadUsers();

    this.notificationService.getNotifications().subscribe((data) => {
      this.notifications = data;
    });
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (response) => {
        this.users = response;
        if (this.users.length > 0) {
          this.selectedUserId = this.users[0].id;
          this.loadUserExpenses(this.selectedUserId);
        }
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      }
    });
  }

  loadUserExpenses(userId: number | null): void {
    if (userId !== null) { 
      this.expenseService.getExpensesByUserId(userId).subscribe((expenses: ExpenseResponseDTO[]) => {
        this.totalExpenses = expenses.reduce((sum, expense) => sum + expense.amount, 0);
        this.expenses = expenses; 
        this.updateDonutChart(); 
        this.updateLineChart(expenses);
      });
    }
  }

  onUserChange(event: any): void {
    this.selectedUserId = +event.target.value;  
    if (this.selectedUserId) {
      this.loadUserExpenses(this.selectedUserId);
    }
  }

  onMonthChange(event: any): void {
    this.selectedMonth = event.target.value; 
    if (this.selectedUserId !== null) {   
      this.updateDonutChart(); // Update the pie chart with filtered data
    }
  }

  updateDonutChart(): void {
    const filteredExpenses = this.selectedMonth === 'All' ? this.expenses :
      this.expenses.filter(expense => new Date(expense.createdDate).getMonth() === this.months.indexOf(this.selectedMonth) - 1);
  
    const categories = filteredExpenses.reduce((acc: { [key: string]: number }, expense) => {
      acc[expense.category] = (acc[expense.category] || 0) + expense.amount;
      return acc;
    }, {} as { [key: string]: number });
  
    const data = Object.keys(categories).map(category => ({
      name: category,
      y: categories[category]
    }));
  
    const totalExpenseForSelectedMonth = filteredExpenses.reduce((sum, expense) => sum + expense.amount, 0);
  
    const totalColor = totalExpenseForSelectedMonth > 5000 ? '#FF0000' : '#0000FF';

    this.donutChartOptions = {
      chart: {
        type: 'pie'
      },
      title: {
       
        useHTML: true, 
        text: `<div style="text-align: center;">
        <span style="font-size: 16px; color: #888;">Overall / Monthly Expenses</span><br>
        <br>
        <span style="font-size: 36px; color: ${totalColor}; font-weight: bold;">₹${totalExpenseForSelectedMonth}</span>
      </div>`,
        align: 'center',
        verticalAlign: 'middle',
        
        style: {
          fontSize: '12px',
          fontWeight: 'bold'
        }
      },
      plotOptions: {
        pie: {
          innerSize: '88%',
          dataLabels: {
            enabled: true 
          },
          animation: {
            duration: 2000, 
            easing: 'easeOutBounce'
          }
        }
      },
      series: [{
        name: 'Amount',
        data: data
      }]
    };
  }
  
  updateLineChart(expenses: ExpenseResponseDTO[]): void {
    const monthlyExpenses = Array(12).fill(0);

    expenses.forEach(expense => {
      const month = new Date(expense.createdDate).getMonth();
      monthlyExpenses[month] += expense.amount;
    });

    this.lineChartOptions = {
      chart: {
        type: 'line'
      },
      title: {
        text: 'Monthly Expenses'
      },
      xAxis: {
        categories: this.months.slice(1) 
      },
      yAxis: {
        title: {
          text: 'Amount'
        },
        plotLines: [{
          color: 'red',
          dashStyle: 'dash',
          value: 5000,
          width: 2
        }]
      },
      series: [{
        name: 'Expenses',
        data: monthlyExpenses
      }]
    };
  }
}
 
