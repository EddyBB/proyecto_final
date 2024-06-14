import { Component, OnInit } from '@angular/core';
import { Event } from '../../models/event.model';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { EventService } from '../../services/event.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { EventDetailsComponent } from '../../event-details/event-details.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    HeaderComponent,
    FooterComponent,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule
  ]
})
export class HomeComponent implements OnInit {
  events: Event[] = [];
  filteredEvents: Event[] = [];
  selectedDate: Date | null = null;
  searchQuery: string = '';

  constructor(private eventService: EventService, public dialog: MatDialog) {}

  ngOnInit() {
    this.loadEvents();
  }

  loadEvents() {
    if (this.selectedDate) {
        const date = new Date(this.selectedDate.getTime() - (this.selectedDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0];
        this.eventService.getEventsByDate(date).subscribe((data: Event[]) => {
            this.events = data;
            this.filteredEvents = this.filterEvents();
        });
    } else {
        this.eventService.getEventsByDate(null).subscribe((data: Event[]) => {
            this.events = data;
            this.filteredEvents = this.filterEvents();
        });
    }
  }

  onDateChange(event: any) {
    this.selectedDate = event.value;
    this.loadEvents();
  }

  filterEvents(): Event[] {
    if (!this.searchQuery) {
      return this.events;
    }
    return this.events.filter(event => event.nombreDiscoteca.toLowerCase().includes(this.searchQuery.toLowerCase()));
  }

  onSearchQueryChange(query: string) {
    this.searchQuery = query;
    this.filteredEvents = this.filterEvents();
  }

  openEventDetails(event: Event): void {
    const dialogRef = this.dialog.open(EventDetailsComponent, {
      data: event
    });

    dialogRef.componentInstance.eventoComprado.subscribe(() => {
      this.loadEvents(); // Recargar eventos después de cerrar el modal
    });
  }
}
