export interface clientes {
    idCliente: number;
    nombre: string;
    apellidos: string;
    email: string;
    telefono: string;
    password: string;
    rol: {
      idRol: number;
      tipoRol: string;
    };
  }
  