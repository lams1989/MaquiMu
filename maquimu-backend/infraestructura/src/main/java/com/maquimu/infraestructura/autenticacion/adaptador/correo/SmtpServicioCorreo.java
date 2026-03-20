package com.maquimu.infraestructura.autenticacion.adaptador.correo;

import com.maquimu.dominio.autenticacion.servicio.ServicioCorreo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpServicioCorreo implements ServicioCorreo {

    private final JavaMailSender mailSender;

    @Override
    public void enviarPasswordTemporal(String destinatario, String nombreUsuario, String passwordTemporal) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("MaquiMu - Contraseña Temporal");
            helper.setText(buildHtmlContent(nombreUsuario, passwordTemporal), true);

            mailSender.send(message);
            log.info("Email de contraseña temporal enviado exitosamente a: {}", destinatario);
        } catch (Exception e) {
            log.warn("No se pudo enviar email de contraseña temporal a: {}. La contraseña fue asignada correctamente.", destinatario, e);
        }
    }

    private String buildHtmlContent(String nombreUsuario, String passwordTemporal) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 500px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <div style="text-align: center; margin-bottom: 20px;">
                        <h2 style="color: #2563eb; margin: 0;">🔧 MaquiMu</h2>
                        <p style="color: #6b7280; font-size: 14px;">Sistema de Alquiler de Maquinaria</p>
                    </div>
                    <hr style="border: none; border-top: 1px solid #e5e7eb;">
                    <p style="color: #374151;">Hola <strong>%s</strong>,</p>
                    <p style="color: #374151;">Se ha asignado una contraseña temporal para tu cuenta. Utilízala para iniciar sesión:</p>
                    <div style="background: #f0f9ff; border: 2px solid #2563eb; border-radius: 8px; padding: 15px; text-align: center; margin: 20px 0;">
                        <p style="font-size: 12px; color: #6b7280; margin: 0 0 5px 0;">Tu contraseña temporal:</p>
                        <p style="font-size: 22px; font-weight: bold; color: #2563eb; margin: 0; letter-spacing: 2px;">%s</p>
                    </div>
                    <p style="color: #374151;">Te recomendamos cambiar esta contraseña después de iniciar sesión, desde la opción <strong>"Cambiar Contraseña"</strong> en tu perfil.</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb;">
                    <p style="color: #9ca3af; font-size: 12px; text-align: center;">Este es un mensaje automático, por favor no responder.</p>
                </div>
            </body>
            </html>
            """.formatted(nombreUsuario, passwordTemporal);
    }
}
