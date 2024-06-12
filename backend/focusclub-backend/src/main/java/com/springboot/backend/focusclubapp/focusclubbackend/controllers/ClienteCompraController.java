package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.CompraService;
import com.springboot.backend.focusclubapp.focusclubbackend.security.CustomUserDetails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/mis-compras")
public class ClienteCompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraDTO>> getCompras() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clienteId = userDetails.getCliente().getIdCliente();

        List<CompraDTO> compras = compraService.findByClienteId(clienteId);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCompra(@PathVariable Long id) {
        Optional<CompraDTO> optionalCompra = compraService.findById(id);
        if (optionalCompra.isPresent()) {
            CompraDTO compra = optionalCompra.get();
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25, 750);
                    contentStream.showText("Detalle de la Compra");
                    contentStream.newLine();
                    contentStream.showText("ID Compra: " + compra.getIdCompra());
                    contentStream.newLine();
                    contentStream.showText("Cliente: " + compra.getClienteId());
                    contentStream.newLine();
                    contentStream.showText("ID Evento: " + compra.getEventoId());
                    contentStream.newLine();
                    contentStream.showText("Cantidad de Entradas: " + compra.getCantidadEntradas());
                    contentStream.newLine();
                    contentStream.showText("Precio Total: " + compra.getPrecioTotal());
                    contentStream.newLine();
                    contentStream.showText("Fecha de Compra: " + compra.getFechaCompra());
                    contentStream.endText();
                }

                document.save(out);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=compra_" + compra.getIdCompra() + ".pdf");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                        .body(out.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
