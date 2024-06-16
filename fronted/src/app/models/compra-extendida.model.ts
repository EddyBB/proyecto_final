export interface CompraExtendida {
    idCompra: number;
    clienteId: number;
    clienteNombre: string;
    eventoId: number;
    eventoNombre: string;
    fechaCompra: Date;
    precioEntrada: number;
    cantidadEntradas: number;
    precioTotal: number;
}