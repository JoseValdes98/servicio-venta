package venta.servicio_venta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import venta.controller.VentaController;
import venta.model.Venta;
import venta.model.VentaDetalle;
import venta.service.VentaService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VentaService ventaService;

    private Venta venta1;
    private Venta venta2;
    private VentaDetalle detalle1;
    private VentaDetalle detalle2;

    @BeforeEach
    void setUp() {
        // Configuración de la primera venta
        venta1 = new Venta();
        venta1.setId(1L);
        venta1.setClienteId(101L);
        venta1.setEmpleadoId(201L);
        venta1.setTiendaId(301L);
        venta1.setFechaVenta(LocalDateTime.now());
        venta1.setMetodoPago("Tarjeta");
        venta1.setDescuento("10%");
        venta1.setSubtotal(90.0);
        venta1.setImpuesto(9.0);
        venta1.setTotal(99.0);

        detalle1 = new VentaDetalle();
        detalle1.setId(1001L);
        detalle1.setProductoId(1L);
        detalle1.setDescripcionProducto("Laptop");
        detalle1.setCantidad(1);
        detalle1.setPrecioUnitario(900.0);
        detalle1.setSubtotalProducto(900.0);
        venta1.addDetalle(detalle1);

        // Configuración de la segunda venta
        venta2 = new Venta();
        venta2.setId(2L);
        venta2.setClienteId(102L);
        venta2.setEmpleadoId(202L);
        venta2.setTiendaId(302L);
        venta2.setFechaVenta(LocalDateTime.now().minusDays(1));
        venta2.setMetodoPago("Efectivo");
        venta2.setDescuento("0%");
        venta2.setSubtotal(50.0);
        venta2.setImpuesto(5.0);
        venta2.setTotal(55.0);

        detalle2 = new VentaDetalle();
        detalle2.setId(1002L);
        detalle2.setProductoId(2L);
        detalle2.setDescripcionProducto("Mouse");
        detalle2.setCantidad(2);
        detalle2.setPrecioUnitario(25.0);
        detalle2.setSubtotalProducto(50.0);
        venta2.addDetalle(detalle2);
    }

    @Test
    void registrarVenta_shouldReturnCreatedVenta() throws Exception {
        when(ventaService.registrarVenta(any(Venta.class))).thenReturn(venta1);

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(venta1.getId()))
                .andExpect(jsonPath("$.clienteId").value(venta1.getClienteId()))
                .andExpect(jsonPath("$.detalles[0].id").value(detalle1.getId()))
                .andExpect(jsonPath("$.detalles[0].descripcionProducto").value(detalle1.getDescripcionProducto()));
    }

    @Test
    void listarTodasLasVentas_shouldReturnListOfVentas() throws Exception {
        List<Venta> ventas = Arrays.asList(venta1, venta2);
        when(ventaService.listarTodasLasVentas()).thenReturn(ventas);

        mockMvc.perform(get("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(venta1.getId()))
                .andExpect(jsonPath("$[1].id").value(venta2.getId()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void listarPorCliente_shouldReturnListOfVentasForClient() throws Exception {
        List<Venta> ventasCliente = Collections.singletonList(venta1);
        when(ventaService.listarPorCliente(101L)).thenReturn(ventasCliente);

        mockMvc.perform(get("/api/v1/ventas/cliente/{clienteId}", 101L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(venta1.getId()))
                .andExpect(jsonPath("$[0].clienteId").value(101L))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listarPorTienda_shouldReturnListOfVentasForStore() throws Exception {
        List<Venta> ventasTienda = Collections.singletonList(venta1);
        when(ventaService.listarPorTienda(301L)).thenReturn(ventasTienda);

        mockMvc.perform(get("/api/v1/ventas/tienda/{tiendaId}", 301L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(venta1.getId()))
                .andExpect(jsonPath("$[0].tiendaId").value(301L))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void eliminarVenta_shouldReturnNoContent() throws Exception {
        doNothing().when(ventaService).eliminarVenta(1L);

        mockMvc.perform(delete("/api/v1/ventas/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}