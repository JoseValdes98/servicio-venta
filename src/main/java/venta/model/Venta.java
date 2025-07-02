

package venta.model;

import jakarta.persistence.*;
import lombok.Data; 
import lombok.Data; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference; 

@Entity
@Table(name = "ventas")
@Data 
@Data 
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "empleado_id")
    private Long empleadoId;

    @Column(name = "tienda_id")
    private Long tiendaId;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "descuento")
    private String descuento;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "impuesto")
    private Double impuesto;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // ¡Esta anotación aquí!
    private List<VentaDetalle> detalles = new ArrayList<>();

 

    public void addDetalle(VentaDetalle detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<>();
        }
        detalles.add(detalle);
        detalle.setVenta(this); 
        detalle.setVenta(this); 
    }

    public void removeDetalle(VentaDetalle detalle) {
        if (this.detalles != null) {
            detalles.remove(detalle);
            detalle.setVenta(null);
        }
    }
}
