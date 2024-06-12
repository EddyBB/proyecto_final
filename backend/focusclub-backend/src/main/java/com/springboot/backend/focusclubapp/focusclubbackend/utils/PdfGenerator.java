package com.springboot.backend.focusclubapp.focusclubbackend.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static ByteArrayOutputStream generatePdf(Compra compra) throws IOException, WriterException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText("Detalles de la Compra");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("ID Compra: " + compra.getId());
                contentStream.newLine();
                contentStream.showText("Cliente: " + compra.getCliente().getNombre() + " " + compra.getCliente().getApellidos());
                contentStream.newLine();
                contentStream.showText("Evento: " + compra.getEvento().getNombre());
                contentStream.newLine();
                contentStream.showText("Fecha de Compra: " + compra.getFechaCompra());
                contentStream.newLine();
                contentStream.showText("Precio Total: " + compra.getPrecioTotal());
                contentStream.newLine();
                contentStream.showText("Cantidad de Entradas: " + compra.getCantidadEntradas());
                contentStream.endText();

                // Generar el código QR
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode("Compra ID: " + compra.getId(), BarcodeFormat.QR_CODE, 200, 200);
                BufferedImage qrImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < 200; x++) {
                    for (int y = 0; y < 200; y++) {
                        qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, qrImage);
                contentStream.drawImage(pdImage, 400, 650, 150, 150);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream;
        }
    }
}
