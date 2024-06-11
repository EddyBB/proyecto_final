// src/app/models/evento.model.ts
export interface eventos {
    idEvento: number;
    nombre: string;
    descripcion: string;
    aforo: number;
    entradasDisponibles: number;
    fecha: Date;
    precio: number;
    imagenUrl: string;
  }
  