package venta.model;

import jakarta.persistence.*;
import lombok.Data; 
import lombok.ToString; 

import com.fasterxml.jackson.annotation.JsonBackReference; 


@Entity
@Table(name = "venta_detalles")
@Data 
@ToString(exclude = "venta") 
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonBackReference 
    private Venta venta; 

    @Column(name = "producto_id")
    private Long productoId;

    @Column(name = "descripcion_producto")
    private String descripcionProducto; 
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "precio_unitario")
    private Double precioUnitario; 
 

    @Column(name = "subtotal_producto")
    private Double subtotalProducto;

    
}
