import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EventoService } from '../../services/evento.service';


@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule]
})
export class EventosComponent {
  eventos: any[] = [];

  constructor(private eventoService: EventoService) {}

  ngOnInit(): void {
    this.eventoService.getEventos().subscribe((data: any) => {
      this.eventos = data;
    });
  }
}
