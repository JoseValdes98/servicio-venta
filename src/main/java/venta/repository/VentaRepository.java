// src/main/java/venta/repository/VentaRepository.java
package venta.repository;

import venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByTiendaId(Long tiendaId);
}