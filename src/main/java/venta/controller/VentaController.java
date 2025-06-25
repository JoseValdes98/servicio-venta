// src/main/java/venta/controller/VentaController.java
package venta.controller;

// Eliminamos las importaciones de DTOs
// import venta.dto.VentaRequestDTO;
// import venta.dto.VentaResponseDTO;
import venta.model.Venta; // Importamos la entidad Venta
import venta.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    // Cambiamos VentaRequestDTO a Venta y VentaResponseDTO a Venta
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.registrarVenta(venta));
    }

    @GetMapping
    // Cambiamos List<VentaResponseDTO> a List<Venta>
    public ResponseEntity<List<Venta>> listarTodasLasVentas() {
        return ResponseEntity.ok(ventaService.listarTodasLasVentas());
    }

    @GetMapping("/cliente/{clienteId}")
    // Cambiamos List<VentaResponseDTO> a List<Venta>
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.listarPorCliente(clienteId));
    }

    @GetMapping("/tienda/{tiendaId}")
    // Cambiamos List<VentaResponseDTO> a List<Venta>
    public ResponseEntity<List<Venta>> listarPorTienda(@PathVariable Long tiendaId) {
        return ResponseEntity.ok(ventaService.listarPorTienda(tiendaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}