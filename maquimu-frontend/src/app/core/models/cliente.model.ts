export interface Cliente {
  clienteId: number;
  usuarioId?: number;
  nombreCliente: string;
  apellido?: string;
  identificacion: string;
  telefono?: string;
  email: string;
  direccion?: string;
  fechaRegistro: string;
}

export interface CrearClienteRequest {
  nombreCliente: string;
  apellido?: string;
  identificacion: string;
  telefono?: string;
  email: string;
  direccion?: string;
}

export interface ActualizarClienteRequest {
  nombreCliente?: string;
  apellido?: string;
  identificacion?: string;
  telefono?: string;
  email?: string;
  direccion?: string;
}

export interface CrearClienteResponse {
  clienteId: number;
}
