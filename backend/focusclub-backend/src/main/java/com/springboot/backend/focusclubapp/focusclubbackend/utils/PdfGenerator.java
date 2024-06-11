package com.springboot.backend.focusclubapp.focusclubbackend.utils;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static ByteArrayOutputStream generatePdf(Entrada entrada) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText("Detalles de la Entrada");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("ID Entrada: " + entrada.getIdEntrada());
                contentStream.newLine();
                contentStream.showText("Cliente: " + entrada.getCliente().getNombre() + " " + entrada.getCliente().getApellidos());
                contentStream.newLine();
                contentStream.showText("Evento: " + entrada.getEvento().getNombre());
                contentStream.newLine();
                contentStream.showText("Fecha de Compra: " + entrada.getFechaCompra());
                contentStream.newLine();
                contentStream.showText("Precio Total: " + entrada.getPrecioTotal());
                contentStream.newLine();
                contentStream.showText("Cantidad de Entradas: " + entrada.getCantidadEntradas());
                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream;
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
