
package venta.repository;

import venta.model.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    // Métodos específicos si se necesitan (ej. List<VentaDetalle> findByVentaId(Long ventaId);)
}
