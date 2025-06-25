
package venta.controller;


import venta.model.Venta; 
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
  
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.registrarVenta(venta));
    }

    @GetMapping
    
    public ResponseEntity<List<Venta>> listarTodasLasVentas() {
        return ResponseEntity.ok(ventaService.listarTodasLasVentas());
    }

    @GetMapping("/cliente/{clienteId}")
    
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.listarPorCliente(clienteId));
    }

    @GetMapping("/tienda/{tiendaId}")
    
    public ResponseEntity<List<Venta>> listarPorTienda(@PathVariable Long tiendaId) {
        return ResponseEntity.ok(ventaService.listarPorTienda(tiendaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
