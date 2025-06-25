// src/main/java/venta/model/VentaDetalle.java
package venta.model;

import jakarta.persistence.*;
import lombok.Data; // Asumo que sigues usando Lombok
import lombok.ToString; // Para excluir 'venta' del toString y evitar bucles en logs

import com.fasterxml.jackson.annotation.JsonBackReference; // ¡ASEGÚRATE DE ESTA IMPORTACIÓN!

@Entity
@Table(name = "venta_detalles")
@Data // Lombok para getters, setters, etc.
@ToString(exclude = "venta") // Importante para evitar StackOverflowError en logs
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonBackReference // ¡Esta anotación aquí!
    private Venta venta; // Referencia a la Venta padre

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "descripcion_producto")
    private String descripcionProducto; 
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "precio_unitario")
    private Double precioUnitario; // Precio unitario del producto CON IVA INCLUIDO

    @Column(name = "subtotal_producto")
    private Double subtotalProducto; // Subtotal de esta línea CON IVA (precioUnitario * cantidad)

    // Constructor, etc. (Lombok @Data ya los maneja en general)
}