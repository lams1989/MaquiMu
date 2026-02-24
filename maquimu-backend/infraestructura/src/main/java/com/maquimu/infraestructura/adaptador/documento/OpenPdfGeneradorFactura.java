package com.maquimu.infraestructura.adaptador.documento;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.salida.GeneradorFacturaPort;
import com.maquimu.dominio.modelo.Maquinaria;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Adaptador de infraestructura que implementa la generación de facturas en PDF
 * usando la librería OpenPDF.
 */
@Component
public class OpenPdfGeneradorFactura implements GeneradorFacturaPort {

    private static final Color COLOR_PRIMARIO = new Color(13, 110, 253);   // Bootstrap primary
    private static final Color COLOR_GRIS_CLARO = new Color(248, 249, 250);
    private static final Color COLOR_BLANCO = Color.WHITE;

    private static final Font FONT_TITULO = new Font(Font.HELVETICA, 22, Font.BOLD, COLOR_PRIMARIO);
    private static final Font FONT_SUBTITULO = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.GRAY);
    private static final Font FONT_SECCION = new Font(Font.HELVETICA, 12, Font.BOLD, COLOR_PRIMARIO);
    private static final Font FONT_LABEL = new Font(Font.HELVETICA, 9, Font.BOLD, Color.DARK_GRAY);
    private static final Font FONT_VALOR = new Font(Font.HELVETICA, 9, Font.NORMAL, Color.BLACK);
    private static final Font FONT_HEADER_TABLA = new Font(Font.HELVETICA, 9, Font.BOLD, COLOR_BLANCO);
    private static final Font FONT_TOTAL = new Font(Font.HELVETICA, 14, Font.BOLD, COLOR_PRIMARIO);
    private static final Font FONT_FOOTER = new Font(Font.HELVETICA, 8, Font.ITALIC, Color.GRAY);

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final NumberFormat FORMATO_MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    @Override
    public byte[] generarFacturaPdf(Factura factura, Alquiler alquiler, Cliente cliente, Maquinaria maquinaria) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.LETTER, 50, 50, 40, 40);
            PdfWriter.getInstance(document, baos);
            document.open();

            // === ENCABEZADO ===
            agregarEncabezado(document, factura);

            document.add(new Paragraph(" "));

            // === DATOS DEL CLIENTE ===
            agregarSeccionCliente(document, cliente);

            document.add(new Paragraph(" "));

            // === DETALLE DEL ALQUILER ===
            agregarSeccionAlquiler(document, alquiler, maquinaria);

            document.add(new Paragraph(" "));

            // === RESUMEN FINANCIERO ===
            agregarResumenFinanciero(document, factura);

            document.add(new Paragraph(" "));

            // === ESTADO DE PAGO ===
            agregarEstadoPago(document, factura);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // === PIE DE PAGINA ===
            agregarPieDePagina(document);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF de la factura: " + e.getMessage(), e);
        }
    }

    private void agregarEncabezado(Document document, Factura factura) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{60, 40});

        // Columna izquierda: Logo / Nombre empresa
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(0);
        leftCell.addElement(new Paragraph("MaquiMu", FONT_TITULO));
        leftCell.addElement(new Paragraph("Sistema de Gestión de Alquiler de Maquinaria", FONT_SUBTITULO));
        headerTable.addCell(leftCell);

        // Columna derecha: Datos de factura
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(0);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph facturaInfo = new Paragraph();
        facturaInfo.setAlignment(Element.ALIGN_RIGHT);
        facturaInfo.add(new Chunk("FACTURA\n", new Font(Font.HELVETICA, 18, Font.BOLD, COLOR_PRIMARIO)));
        facturaInfo.add(new Chunk("N° " + String.format("%06d", factura.getFacturaId()) + "\n",
                new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK)));
        facturaInfo.add(new Chunk("Fecha: " + factura.getFechaEmision().format(FORMATO_FECHA),
                new Font(Font.HELVETICA, 9, Font.NORMAL, Color.GRAY)));
        rightCell.addElement(facturaInfo);
        headerTable.addCell(rightCell);

        document.add(headerTable);

        // Línea separadora
        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorder(0);
        lineCell.setBorderWidthBottom(2);
        lineCell.setBorderColorBottom(COLOR_PRIMARIO);
        lineCell.setFixedHeight(5);
        lineTable.addCell(lineCell);
        document.add(lineTable);
    }

    private void agregarSeccionCliente(Document document, Cliente cliente) throws DocumentException {
        document.add(new Paragraph("Datos del Cliente", FONT_SECCION));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{50, 50});

        agregarCampo(table, "Nombre:", cliente.getNombreCliente());
        agregarCampo(table, "Identificación:", cliente.getIdentificacion());
        agregarCampo(table, "Teléfono:", cliente.getTelefono());
        agregarCampo(table, "Email:", cliente.getEmail());
        agregarCampo(table, "Dirección:", cliente.getDireccion());

        document.add(table);
    }

    private void agregarSeccionAlquiler(Document document, Alquiler alquiler, Maquinaria maquinaria) throws DocumentException {
        document.add(new Paragraph("Detalle del Servicio", FONT_SECCION));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{35, 20, 20, 25});

        // Header
        agregarCeldaHeader(table, "Maquinaria");
        agregarCeldaHeader(table, "Fecha Inicio");
        agregarCeldaHeader(table, "Fecha Fin");
        agregarCeldaHeader(table, "Tarifa/Día");

        // Datos
        String nombreMaquinaria = maquinaria.getNombreEquipo() + " - " + maquinaria.getMarca() + " " + maquinaria.getModelo();
        agregarCeldaDato(table, nombreMaquinaria);
        agregarCeldaDato(table, alquiler.getFechaInicio().format(FORMATO_FECHA_HORA));
        agregarCeldaDato(table, alquiler.getFechaFin().format(FORMATO_FECHA_HORA));
        agregarCeldaDato(table, FORMATO_MONEDA.format(maquinaria.getTarifaPorDia()));

        document.add(table);
    }

    private void agregarResumenFinanciero(Document document, Factura factura) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidths(new float[]{50, 50});

        // Total
        PdfPCell labelCell = new PdfPCell(new Phrase("TOTAL:", FONT_SECCION));
        labelCell.setBorder(0);
        labelCell.setBorderWidthTop(1);
        labelCell.setBorderColorTop(COLOR_PRIMARIO);
        labelCell.setPadding(8);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(labelCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(FORMATO_MONEDA.format(factura.getMonto()), FONT_TOTAL));
        valorCell.setBorder(0);
        valorCell.setBorderWidthTop(1);
        valorCell.setBorderColorTop(COLOR_PRIMARIO);
        valorCell.setPadding(8);
        valorCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valorCell);

        document.add(table);
    }

    private void agregarEstadoPago(Document document, Factura factura) throws DocumentException {
        String estadoTexto = "Estado de Pago: " + factura.getEstadoPago().name();
        Color colorEstado;
        switch (factura.getEstadoPago()) {
            case PAGADO: colorEstado = new Color(25, 135, 84); break;    // green
            case ANULADO: colorEstado = new Color(220, 53, 69); break;   // red
            default: colorEstado = new Color(255, 193, 7); break;        // yellow/warning
        }
        Font fontEstado = new Font(Font.HELVETICA, 11, Font.BOLD, colorEstado);
        Paragraph p = new Paragraph(estadoTexto, fontEstado);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);
    }

    private void agregarPieDePagina(Document document) throws DocumentException {
        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setBorder(0);
        lineCell.setBorderWidthTop(1);
        lineCell.setBorderColorTop(Color.LIGHT_GRAY);
        lineCell.setFixedHeight(5);
        lineTable.addCell(lineCell);
        document.add(lineTable);

        Paragraph footer = new Paragraph(
                "Documento generado automáticamente por MaquiMu — Sistema de Gestión de Alquiler de Maquinaria.",
                FONT_FOOTER);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        Paragraph footer2 = new Paragraph(
                "Este documento no requiere firma y es válido como comprobante de facturación.",
                FONT_FOOTER);
        footer2.setAlignment(Element.ALIGN_CENTER);
        document.add(footer2);
    }

    // === HELPERS ===

    private void agregarCampo(PdfPTable table, String label, String valor) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, FONT_LABEL));
        labelCell.setBorder(0);
        labelCell.setPadding(3);
        labelCell.setBackgroundColor(COLOR_GRIS_CLARO);
        table.addCell(labelCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor != null ? valor : "N/A", FONT_VALOR));
        valorCell.setBorder(0);
        valorCell.setPadding(3);
        table.addCell(valorCell);
    }

    private void agregarCeldaHeader(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONT_HEADER_TABLA));
        cell.setBackgroundColor(COLOR_PRIMARIO);
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void agregarCeldaDato(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FONT_VALOR));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(COLOR_GRIS_CLARO);
        table.addCell(cell);
    }
}
