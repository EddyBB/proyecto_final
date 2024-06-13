import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { AdminFooterComponent } from '../admin-footer/admin-footer.component';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, AdminHeaderComponent, AdminFooterComponent]
})
export class AdminPanelComponent {
  constructor(private router: Router) {}

  navigateTo(route: string) {
    this.router.navigate([`/admin/${route}`]);
  }
}
