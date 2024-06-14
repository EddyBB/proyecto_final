package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.CompraService;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.EmailService;
import com.springboot.backend.focusclubapp.focusclubbackend.security.CustomUserDetails;
import com.springboot.backend.focusclubapp.focusclubbackend.utils.PdfGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/compras")
    public ResponseEntity<List<CompraDTO>> getCompras() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long clienteId = userDetails.getCliente().getIdCliente();

        List<CompraDTO> compras = compraService.findByClienteId(clienteId);
        return ResponseEntity.ok(compras);
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> realizarCompra(@RequestBody CompraDTO compraDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        compraDTO.setClienteId(userDetails.getCliente().getIdCliente());
    
        if (compraDTO.getEventoId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del evento es requerido.");
        }
    
        try {
            CompraDTO savedCompra = compraService.save(compraDTO);
    
            ByteArrayOutputStream pdfBytes = PdfGenerator.generatePdf(compraService.convertToEntity(savedCompra));
            emailService.sendEmailWithAttachment(
                    userDetails.getCliente().getEmail(),
                    "Confirmación de compra",
                    "Adjunto encontrarás el PDF con los detalles de tu compra.",
                    pdfBytes.toByteArray(),
                    "compra.pdf"
            );
    
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCompra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException | com.google.zxing.WriterException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el PDF o enviar el correo.");
        }
    }
    
}
