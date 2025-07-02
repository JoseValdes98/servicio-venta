package venta.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import venta.controller.VentaControllerV2;
import venta.model.Venta;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta, //
                 linkTo(methodOn(VentaControllerV2.class).listarTodasLasVentas()).withRel("ventas"),
                linkTo(methodOn(VentaControllerV2.class).registrarVenta(venta)).withRel("crear_venta"), 
                linkTo(methodOn(VentaControllerV2.class).listarPorCliente(venta.getClienteId())).withRel("ventas_por_cliente"), 
                linkTo(methodOn(VentaControllerV2.class).listarPorTienda(venta.getTiendaId())).withRel("ventas_por_tienda"), 
                linkTo(methodOn(VentaControllerV2.class).eliminarVenta(venta.getId())).withRel("eliminar_venta") 
        );
    }
}