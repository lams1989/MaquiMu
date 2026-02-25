export interface AppNotification {
  id: string;
  type: NotificationType;
  title: string;
  message: string;
  icon: string;
  iconColor: string;
  timestamp: Date;
  read: boolean;
  routerLink?: string;
}

export type NotificationType =
  | 'rental_pending'
  | 'rental_approved'
  | 'rental_rejected'
  | 'rental_active'
  | 'rental_finalized'
  | 'invoice_generated'
  | 'invoice_paid'
  | 'registration_pending'
  | 'extension_pending'
  | 'extension_approved'
  | 'extension_rejected'
  | 'password_reset_request'
  | 'system';
