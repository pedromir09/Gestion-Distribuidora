package gestion.distribuidora.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Venta (pedido) de un cliente: combina un Cliente con uno o mas Producto.
 */
public class Venta {

    private Long id;   // lo genera la base de datos
    private LocalDateTime fecha;
    private Cliente cliente;
    private EstadoVenta estado;
    private final List<DetalleVenta> detalles = new ArrayList<>();

    /**
     * Crea una venta nueva para un cliente, con la fecha de hoy y estado pendiente.
     * @param cliente el cliente que hace el pedido
     */
    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoVenta.PENDIENTE;
    }

    /**
     * Devuelve el id que asigno la base, o null si todavia no se guardo.
     * @return el id que asigno la base, o null si todavia no se guardo
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id que asigno la base.
     * @param id el id que asigno la base
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha y hora de la venta.
     * @return la fecha y hora de la venta
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha y hora de la venta.
     * @param fecha la fecha y hora de la venta
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve el cliente de la venta.
     * @return el cliente de la venta
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Asigna el cliente de la venta.
     * @param cliente el cliente de la venta
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve el estado actual de la venta.
     * @return el estado actual de la venta
     */
    public EstadoVenta getEstado() {
        return estado;
    }

    /**
     * Asigna el nuevo estado.
     * @param estado el nuevo estado
     */
    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    /**
     * Devuelve los renglones de la venta (solo lectura).
     * @return los renglones de la venta (solo lectura)
     */
    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    /**
     * Agrega un producto a la venta. Si el producto ya estaba, suma las cantidades.
     * @param detalle el renglon a agregar
     * @throws IllegalArgumentException si la cantidad no es mayor a cero
     */
    public void agregarDetalle(DetalleVenta detalle) {
        if (detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad tiene que ser mayor a cero");
        }
        for (DetalleVenta existente : detalles) {
            if (existente.getProducto().getId().equals(detalle.getProducto().getId())) {
                existente.setCantidad(existente.getCantidad() + detalle.getCantidad());
                return;
            }
        }
        detalles.add(detalle);
    }

    /**
     * Saca un renglon de la venta.
     * @param posicion la posicion del renglon en la lista
     */
    public void quitarDetalle(int posicion) {
        detalles.remove(posicion);
    }

    /**
     * Devuelve la suma de los subtotales de todos los renglones.
     * @return la suma de los subtotales de todos los renglones
     */
    public double getTotal() {
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    /**
     * Indica si la venta todavia no se entrego ni se anulo.
     * @return true si la venta esta pendiente
     */
    public boolean estaPendiente() {
        return estado == EstadoVenta.PENDIENTE;
    }

    /**
     * Marca la venta como entregada. Solo se puede si esta pendiente.
     * @throws IllegalStateException si la venta ya se entrego o esta anulada
     */
    public void marcarEntregada() {
        if (estado == EstadoVenta.ENTREGADA) {
            throw new IllegalStateException("La venta N° " + id + " ya está entregada");
        }
        if (estado == EstadoVenta.ANULADA) {
            throw new IllegalStateException("La venta N° " + id + " está anulada: no se puede entregar");
        }
        estado = EstadoVenta.ENTREGADA;
    }

    /**
     * Anula la venta. Solo se puede si esta pendiente: una venta entregada ya no se anula.
     * @throws IllegalStateException si la venta ya se entrego o ya estaba anulada
     */
    public void anular() {
        if (estado == EstadoVenta.ENTREGADA) {
            throw new IllegalStateException("La venta N° " + id + " ya se entregó: no se puede anular");
        }
        if (estado == EstadoVenta.ANULADA) {
            throw new IllegalStateException("La venta N° " + id + " ya estaba anulada");
        }
        estado = EstadoVenta.ANULADA;
    }

    /**
     * Verifica que la venta se pueda registrar.
     * @throws IllegalArgumentException si falta el cliente o no tiene productos
     */
    public void validar() {
        if (cliente == null) {
            throw new IllegalArgumentException("Elegi un cliente");
        }
        if (detalles.isEmpty()) {
            throw new IllegalArgumentException("Agrega al menos un articulo a la venta");
        }
        for (DetalleVenta detalle : detalles) {
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("Las cantidades tienen que ser mayores a cero");
            }
        }
    }
}
