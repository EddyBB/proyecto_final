export interface Event {
  idEvento: number;
  nombre: string;
  descripcion: string;
  fecha: Date;
  precio: number;
  imagenUrl: string;
  aforo: number;
  entradasDisponibles: number;
  nombreDiscoteca: string;
}