import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AdminEventService } from '../../services/admin-event.service';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { SimpleEvent } from '../../models/simple-event.model';
import { Discoteca } from '../../models/discoteca.model';
import { Sala } from '../../models/sala.model';

@Component({
  selector: 'app-event-dialog',
  templateUrl: './event-dialog.component.html',
  styleUrls: ['./event-dialog.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule
  ]
})
export class EventDialogComponent implements OnInit {
  eventForm: FormGroup;
  isEventSaved = false;
  originalDiscoteca: Discoteca | null = null;
  originalSala: Sala | null = null;

  constructor(
    private fb: FormBuilder,
    private eventService: AdminEventService,
    private dialogRef: MatDialogRef<EventDialogComponent>,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.eventForm = this.fb.group({
      idEvento: [null],
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      aforo: ['', Validators.required],
      entradasDisponibles: ['', Validators.required],
      fecha: ['', Validators.required],
      precio: ['', Validators.required],
      imagenUrl: [''],
      discotecaNombre: [''],
      ubicacion: [''],
      salaNombre: ['']
    });
  }

  ngOnInit(): void {
    if (this.data.event) {
      this.eventForm.patchValue(this.data.event);
      this.isEventSaved = true;

      // Guardar los valores originales de discoteca y sala para verificar cambios
      this.originalDiscoteca = this.data.event.discoteca ? { ...this.data.event.discoteca } : null;
      this.originalSala = this.data.event.sala ? { ...this.data.event.sala } : null;

      // Verificar los valores originales
      console.log('Original Discoteca:', this.originalDiscoteca);
      console.log('Original Sala:', this.originalSala);
    }
  }

  saveEvent() {
    if (this.eventForm.valid) {
      const formValue = this.eventForm.value;
      const event: SimpleEvent = {
        idEvento: formValue.idEvento,
        nombre: formValue.nombre,
        descripcion: formValue.descripcion,
        fecha: formValue.fecha,
        precio: formValue.precio,
        imagenUrl: formValue.imagenUrl,
        aforo: formValue.aforo,
        entradasDisponibles: formValue.entradasDisponibles
      };

      if (this.data.event) {
        this.updateEntities(formValue, event);
      } else {
        this.createEvent(event);
      }
    }
  }

  updateEntities(formValue: any, event: SimpleEvent) {
    console.log('Updating event with data:', event);
    this.eventService.updateEvent(this.data.event.idEvento, event).subscribe({
      next: () => {
        console.log('Event updated');
        this.updateDiscoteca(formValue);
      },
      error: (err) => {
        console.error('Error updating event', err);
      }
    });
  }

  updateDiscoteca(formValue: any) {
    console.log('Before updating discoteca:', this.originalDiscoteca);
    if (formValue.discotecaNombre || formValue.ubicacion) {
      if (this.originalDiscoteca && this.originalDiscoteca.idDiscoteca !== null) {
        const discoteca: Discoteca = {
          idDiscoteca: this.originalDiscoteca.idDiscoteca!,
          nombre: formValue.discotecaNombre,
          ubicacion: formValue.ubicacion
        };
        console.log('Updating discoteca with data:', discoteca);
        this.eventService.updateDiscoteca(discoteca.idDiscoteca!, discoteca).subscribe({
          next: () => {
            console.log('Discoteca updated');
            this.updateSala(formValue, discoteca.idDiscoteca);
          },
          error: (err) => {
            console.error('Error updating discoteca', err);
          }
        });
      } else {
        console.log('ID Discoteca no disponible, no se puede actualizar.');
      }
    } else {
      this.updateSala(formValue, this.originalDiscoteca?.idDiscoteca ?? null);
    }
  }

  createDiscoteca(formValue: any) {
    const discoteca: Discoteca = {
      idDiscoteca: null,
      nombre: formValue.discotecaNombre,
      ubicacion: formValue.ubicacion
    };
    this.eventService.createDiscoteca(discoteca).subscribe({
      next: (createdDiscoteca) => {
        console.log('Discoteca created:', createdDiscoteca);
        this.updateSala(formValue, createdDiscoteca.idDiscoteca);
      },
      error: (err) => {
        console.error('Error creating discoteca', err);
      }
    });
  }

  updateSala(formValue: any, discotecaId: number | null) {
    console.log('Before updating sala:', this.originalSala);
    if (formValue.salaNombre) {
      if (this.originalSala && this.originalSala.idSala !== null) {
        const sala: Sala = {
          idSala: this.originalSala.idSala!,
          nombre: formValue.salaNombre,
          idDiscoteca: discotecaId ?? this.originalDiscoteca?.idDiscoteca!,
          idEvento: formValue.idEvento
        };
        console.log('Updating sala with data:', sala);
        this.eventService.updateSala(sala.idSala!, sala).subscribe({
          next: () => {
            console.log('Sala updated');
            this.dialogRef.close(true);
          },
          error: (err) => {
            console.error('Error updating sala', err);
          }
        });
      } else {
        console.log('ID Sala no disponible, no se puede actualizar.');
      }
    } else {
      this.dialogRef.close(true);
    }
  }

  createSala(formValue: any, discotecaId: number) {
    const sala: Sala = {
      idSala: null,
      nombre: formValue.salaNombre,
      idDiscoteca: discotecaId,
      idEvento: formValue.idEvento
    };
    this.eventService.createSala(sala).subscribe({
      next: (createdSala) => {
        console.log('Sala created:', createdSala);
        this.dialogRef.close(true);
      },
      error: (err) => {
        console.error('Error creating sala', err);
      }
    });
  }

  createEvent(event: SimpleEvent) {
    console.log('Creating event with data:', event);
    this.eventService.createEvent(event).subscribe({
      next: () => {
        console.log('Event created');
        this.dialogRef.close(true);
      },
      error: (err) => {
        console.error('Error creating event', err);
      }
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
