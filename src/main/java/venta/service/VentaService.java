
package venta.service;

import venta.model.Venta; 

import java.util.List;

public interface VentaService {

    Venta registrarVenta(Venta venta);


    List<Venta> listarTodasLasVentas();


    List<Venta> listarPorCliente(Long clienteId);


    List<Venta> listarPorTienda(Long tiendaId);

    void eliminarVenta(Long id);
}