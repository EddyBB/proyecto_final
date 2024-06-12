import { Component, OnInit } from '@angular/core';
import { Event } from '../../models/event.model';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [RouterModule, CommonModule, HeaderComponent, FooterComponent]
})
export class HomeComponent implements OnInit {
  events: Event[] = [];

  constructor(private eventService: EventService) {}

  ngOnInit() {
    this.eventService.getUpcomingEvents().subscribe((data: Event[]) => {
      this.events = data;
    });
  }
}
