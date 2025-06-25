package venta.servicio_venta.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import venta.model.Venta;
import venta.model.VentaDetalle;
// import venta.repository.VentaDetalleRepository; // Ya no es necesario si no se usa el mock explícitamente
import venta.repository.VentaRepository;
import venta.service.impl.VentaServiceImpl;

import java.time.LocalDateTime; // Importar si se usa LocalDateTime.now()
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset; // ¡Nueva importación para 'offset'!
import static org.mockito.Mockito.*; // Se mantiene para verify, when, any, times

public class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarVenta() {
        VentaDetalle detalle = new VentaDetalle();
        detalle.setProductoId(10L);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(11900.0);

        Venta venta = new Venta();
        venta.setClienteId(1L);
        venta.setEmpleadoId(2L);
        venta.setTiendaId(3L);
        venta.setMetodoPago("Efectivo");
        venta.addDetalle(detalle); 


        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(1L); 
        ventaGuardada.setClienteId(1L);
        ventaGuardada.setEmpleadoId(2L);
        ventaGuardada.setTiendaId(3L);
        ventaGuardada.setMetodoPago("Efectivo");
        ventaGuardada.setFechaVenta(LocalDateTime.now()); 
     
        VentaDetalle detalleGuardado = new VentaDetalle();
        detalleGuardado.setId(1L); 
        detalleGuardado.setProductoId(10L);
        detalleGuardado.setCantidad(2);
        detalleGuardado.setPrecioUnitario(11900.0);
        detalleGuardado.setSubtotalProducto(23800.0);
        ventaGuardada.addDetalle(detalleGuardado);
        
        ventaGuardada.setTotal(23800.0);
        ventaGuardada.setSubtotal(20000.0);
        ventaGuardada.setImpuesto(3800.0);

        
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaGuardada);




        Venta resultado = ventaService.registrarVenta(venta);


        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getTotal()).isEqualTo(23800.0);

        assertThat(resultado.getSubtotal()).isCloseTo(20000.0, offset(0.01));
        assertThat(resultado.getImpuesto()).isCloseTo(3800.0, offset(0.01));
        
    
        verify(ventaRepository, times(1)).save(any(Venta.class));
        
      
    }

    @Test
    void testListarTodasLasVentas() {
        Venta venta1 = new Venta();
        venta1.setId(1L);

        Venta venta2 = new Venta();
        venta2.setId(2L);

        when(ventaRepository.findAll()).thenReturn(List.of(venta1, venta2));

        List<Venta> resultado = ventaService.listarTodasLasVentas();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(1).getId()).isEqualTo(2L);

        verify(ventaRepository, times(1)).findAll(); 
    }

    @Test
    void testListarPorCliente() {
        Long clienteId = 100L;

        Venta venta1 = new Venta();
        venta1.setId(1L);
        venta1.setClienteId(clienteId);

        when(ventaRepository.findByClienteId(clienteId)).thenReturn(List.of(venta1));

        List<Venta> resultado = ventaService.listarPorCliente(clienteId);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getClienteId()).isEqualTo(clienteId);

        verify(ventaRepository, times(1)).findByClienteId(clienteId); 
    }

    @Test
    void testListarPorTienda() {
        Long tiendaId = 50L;

        Venta venta1 = new Venta();
        venta1.setId(1L);
        venta1.setTiendaId(tiendaId);

        when(ventaRepository.findByTiendaId(tiendaId)).thenReturn(List.of(venta1));

        List<Venta> resultado = ventaService.listarPorTienda(tiendaId);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTiendaId()).isEqualTo(tiendaId);

        verify(ventaRepository, times(1)).findByTiendaId(tiendaId);
    }
}