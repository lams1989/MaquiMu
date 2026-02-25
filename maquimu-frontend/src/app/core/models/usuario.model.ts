export interface UsuarioPendiente {
  usuarioId: number;
  nombreCompleto: string;
  email: string;
  rol: string;
  estado: string;
  fechaCreacion: string;
}

export interface RechazarUsuarioRequest {
  motivo: string;
}
