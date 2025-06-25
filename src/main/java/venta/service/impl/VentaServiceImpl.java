
package venta.service.impl;

import venta.model.Venta;
import venta.model.VentaDetalle;
import venta.repository.VentaRepository;
import venta.service.VentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; 
import java.math.RoundingMode; 
import java.time.LocalDateTime;
import java.util.ArrayList; 
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    private static final double IVA_RATE = 0.19; 

    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional
    public Venta registrarVenta(Venta venta) {
        venta.setFechaVenta(LocalDateTime.now());

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un detalle de producto.");
        }

        double totalVentaConIvaCalculado = 0.0;

        for (VentaDetalle detalle : venta.getDetalles()) {
            if (detalle.getVenta() == null) {
                detalle.setVenta(venta);
            }
            
            detalle.setSubtotalProducto(round(detalle.getPrecioUnitario() * detalle.getCantidad(), 2));
            totalVentaConIvaCalculado += detalle.getSubtotalProducto();
        }

        
        totalVentaConIvaCalculado = round(totalVentaConIvaCalculado, 2);
        venta.setTotal(totalVentaConIvaCalculado);

        double subtotalVentaSinIva = totalVentaConIvaCalculado / (1 + IVA_RATE);
        double impuestoVentaCalculado = totalVentaConIvaCalculado - subtotalVentaSinIva;

        
        venta.setSubtotal(round(subtotalVentaSinIva, 2));
        venta.setImpuesto(round(impuestoVentaCalculado, 2));

        Venta savedVenta = ventaRepository.save(venta);
        
        return savedVenta;
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Venta> listarTodasLasVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        ventas.forEach(venta -> {
            if (venta.getDetalles() != null) {
                venta.getDetalles().size(); 
            }
        });
        return ventas;
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Venta> listarPorCliente(Long clienteId) {
        List<Venta> ventas = ventaRepository.findByClienteId(clienteId);
        ventas.forEach(venta -> {
            if (venta.getDetalles() != null) {
                venta.getDetalles().size();
            }
        });
        return ventas;
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Venta> listarPorTienda(Long tiendaId) {
        List<Venta> ventas = ventaRepository.findByTiendaId(tiendaId);
        ventas.forEach(venta -> {
            if (venta.getDetalles() != null) {
                venta.getDetalles().size();
            }
        });
        return ventas;
    }

    @Override
    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }


    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP); // Redondeo tradicional
        return bd.doubleValue();
    }
}
