import { Component, OnInit } from '@angular/core';
import { AdminEventService } from '../../services/admin-event.service';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { EventDialogComponent } from '../event-dialog/event-dialog.component';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { AdminFooterComponent } from '../admin-footer/admin-footer.component';
import { FormsModule } from '@angular/forms'; // Import FormsModule

@Component({
  selector: 'app-admin-event-list',
  templateUrl: './admin-event-list.component.html',
  styleUrls: ['./admin-event-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    AdminHeaderComponent,
    AdminFooterComponent,
    FormsModule // Add FormsModule to imports
  ]
})
export class AdminEventListComponent implements OnInit {
  events: any[] = [];
  discotecas: any[] = [];
  salas: any[] = [];
  displayedColumns: string[] = ['nombre', 'descripcion', 'aforo', 'entradasDisponibles', 'fecha', 'precio', 'discoteca', 'ubicacion', 'sala', 'acciones'];
  discotecaNames: { [key: number]: string } = {};
  discotecaUbicaciones: { [key: number]: string } = {};
  salaNames: { [key: number]: string } = {};

  constructor(private eventService: AdminEventService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadDiscotecas();
    this.loadSalas();
  }

  loadDiscotecas(): void {
    this.eventService.getDiscotecas().subscribe({
      next: (data) => {
        console.log('Discotecas loaded:', data);
        this.discotecas = data;
        this.loadEvents();
      },
      error: (err) => {
        console.error('Error loading discotecas', err);
      }
    });
  }

  loadSalas(): void {
    this.eventService.getSalas().subscribe({
      next: (data) => {
        console.log('Salas loaded:', data);
        this.salas = data;
        this.loadEvents();
      },
      error: (err) => {
        console.error('Error loading salas', err);
      }
    });
  }

  loadEvents(): void {
    this.eventService.getEvents().subscribe({
      next: (data) => {
        console.log('Events from backend:', data);
        this.events = data.map((event: { idEvento: any; }) => {
          const sala = this.salas.find(s => s.idEvento === event.idEvento);
          const discoteca = sala ? this.discotecas.find(d => d.idDiscoteca === sala.idDiscoteca) : null;
          return {
            ...event,
            discotecaNombre: discoteca ? discoteca.nombre : 'Not found',
            ubicacion: discoteca ? discoteca.ubicacion : 'Not found',
            salaNombre: sala ? sala.nombre : 'Not found',
            discotecaId: discoteca ? discoteca.idDiscoteca : null, // Añadir el ID de la discoteca
            salaId: sala ? sala.idSala : null // Añadir el ID de la sala
          };
        });
        console.log('Events loaded:', this.events);
      },
      error: (err) => {
        console.error('Error loading events', err);
      }
    });
  }

  editEvent(event: any): void {
    console.log('Editing event:', event); // Log para verificar los datos del evento
    const dialogRef = this.dialog.open(EventDialogComponent, {
      data: {
        event: {
          ...event,
          discoteca: {
            idDiscoteca: event.discotecaId,
            nombre: event.discotecaNombre,
            ubicacion: event.ubicacion
          },
          sala: {
            idSala: event.salaId,
            nombre: event.salaNombre
          }
        }
      }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadEvents();
        this.loadDiscotecas();
        this.loadSalas();
      }
    });
  }

  deleteEvent(id: number): void {
    this.eventService.deleteEvent(id).subscribe({
      next: () => {
        this.loadEvents();
      },
      error: (err) => {
        console.error('Error deleting event', err);
      }
    });
  }

  createEvent(): void {
    const dialogRef = this.dialog.open(EventDialogComponent, {
      data: { event: null }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadEvents();
      }
    });
  }

  saveDiscoteca(eventId: number): void {
    const discotecaName = this.discotecaNames[eventId];
    const ubicacion = this.discotecaUbicaciones[eventId];
    const newDiscoteca = { nombre: discotecaName, ubicacion: ubicacion } as any;

    this.eventService.createDiscoteca(newDiscoteca).subscribe({
      next: (createdDiscoteca) => {
        console.log('Discoteca guardada:', createdDiscoteca);
        this.events = this.events.map(event => {
          if (event.idEvento === eventId) {
            return {
              ...event,
              discotecaNombre: createdDiscoteca.nombre,
              ubicacion: createdDiscoteca.ubicacion,
              discotecaId: createdDiscoteca.idDiscoteca, // Guardar el ID de la discoteca
              salaNombre: 'Not found'
            };
          }
          return event;
        });
        this.discotecaNames[eventId] = '';
        this.discotecaUbicaciones[eventId] = '';
        // Actualizar la lista de discotecas sin perder los datos actuales
        this.discotecas.push(createdDiscoteca);
      },
      error: (err) => {
        console.error('Error saving discoteca', err);
      }
    });
  }

  saveSala(eventId: number): void {
    const event = this.events.find(e => e.idEvento === eventId);
    console.log('Evento encontrado:', event);
    if (!event) {
      console.error('No existe evento');
      return;
    }
    const discoteca = this.discotecas.find(d => d.idDiscoteca === event.discotecaId); // Usar el ID de la discoteca almacenado en el evento
    console.log('Discoteca encontrada:', discoteca);
    if (!discoteca) {
      console.error('No existe discoteca afiliada');
      return;
    }
    const salaName = this.salaNames[eventId];
    console.log('Nombre de sala:', salaName);
    if (!salaName) {
      console.error('Nombre de sala no proporcionado');
      return;
    }

    const newSala = { nombre: salaName, idDiscoteca: discoteca.idDiscoteca, idEvento: eventId } as any;

    console.log('Creando sala con:', newSala); // Log para ver el ID de la discoteca y otros detalles

    this.eventService.createSala(newSala).subscribe({
      next: (createdSala) => {
        this.events = this.events.map(event => {
          if (event.idEvento === eventId) {
            return {
              ...event,
              salaNombre: createdSala.nombre
            };
          }
          return event;
        });
        this.salaNames[eventId] = '';
        window.location.reload(); // Recargar la página después de guardar la sala
      },
      error: (err) => {
        console.error('Error saving sala', err);
      }
    });
  }
  
  
  
}
