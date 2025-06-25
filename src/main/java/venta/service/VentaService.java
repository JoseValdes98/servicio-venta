// src/main/java/venta/service/VentaService.java
package venta.service;

// Eliminamos las importaciones de DTOs
// import venta.dto.VentaRequestDTO;
// import venta.dto.VentaResponseDTO;
import venta.model.Venta; // Importamos la entidad Venta

import java.util.List;

public interface VentaService {

    Venta registrarVenta(Venta venta);


    List<Venta> listarTodasLasVentas();


    List<Venta> listarPorCliente(Long clienteId);


    List<Venta> listarPorTienda(Long tiendaId);

    void eliminarVenta(Long id);
}