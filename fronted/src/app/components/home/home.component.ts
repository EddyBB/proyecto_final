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
  selectedDate: Date | null = null;

  constructor(private eventService: EventService) {}

  ngOnInit() {
    this.loadEvents();
  }

  loadEvents() {
    if (this.selectedDate) {
        const date = new Date(this.selectedDate.getTime() - (this.selectedDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0];
        console.log("Selected Date:", date); // Añadido para depuración
        this.eventService.getEventsByDate(date).subscribe((data: Event[]) => {
            this.events = data;
        });
    } else {
        this.eventService.getEventsByDate(null).subscribe((data: Event[]) => {
            this.events = data;
        });
    }
}


  onDateChange(event: any) {
    this.selectedDate = event.value;
    this.loadEvents();
  }
  
  
}
