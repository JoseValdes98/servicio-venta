package venta.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import venta.assemblers.VentaModelAssembler;
import venta.model.Venta;
import venta.service.VentaService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/ventas") 
public class VentaControllerV2 {

    private final VentaService ventaService;
    private final VentaModelAssembler ventaAssembler;
    
    
    public VentaControllerV2(VentaService ventaService, VentaModelAssembler ventaAssembler) {
        this.ventaService = ventaService;
        this.ventaAssembler = ventaAssembler;
    }

    @GetMapping
    
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> listarTodasLasVentas() {
        List<EntityModel<Venta>> ventas = ventaService.listarTodasLasVentas().stream()
                .map(ventaAssembler::toModel) 
                .collect(Collectors.toList());

        
        return ResponseEntity.ok(
                CollectionModel.of(ventas, linkTo(methodOn(VentaControllerV2.class).listarTodasLasVentas()).withSelfRel()));
    }


    @PostMapping
    public ResponseEntity<EntityModel<Venta>> registrarVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.registrarVenta(venta);
        return ResponseEntity.ok(ventaAssembler.toModel(nuevaVenta));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> listarPorCliente(@PathVariable Long clienteId) {
        List<EntityModel<Venta>> ventas = ventaService.listarPorCliente(clienteId).stream()
                .map(ventaAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(ventas,
                        linkTo(methodOn(VentaControllerV2.class).listarPorCliente(clienteId)).withSelfRel()));
    }

    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> listarPorTienda(@PathVariable Long tiendaId) {
        List<EntityModel<Venta>> ventas = ventaService.listarPorTienda(tiendaId).stream()
                .map(ventaAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(ventas,
                        linkTo(methodOn(VentaControllerV2.class).listarPorTienda(tiendaId)).withSelfRel()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}