export interface Cliente {
  clienteId: number;
  nombreCliente: string;
  identificacion: string;
  telefono: string;
  email: string;
  direccion?: string;
  fechaRegistro: string;
}

export interface CrearClienteRequest {
  nombreCliente: string;
  identificacion: string;
  telefono: string;
  email: string;
  direccion?: string;
}

export interface ActualizarClienteRequest {
  nombreCliente?: string;
  identificacion?: string;
  telefono?: string;
  email?: string;
  direccion?: string;
}

export interface CrearClienteResponse {
  clienteId: number;
}
