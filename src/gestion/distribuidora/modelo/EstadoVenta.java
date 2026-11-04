package gestion.distribuidora.modelo;

/**
 * Estados por los que pasa una venta.
 */
public enum EstadoVenta {
    /** Registrada, todavia sin entregar. */
    PENDIENTE("Pendiente"),
    /** Ya entregada al cliente. */
    ENTREGADA("Entregada"),
    /** Anulada: el stock se devolvio. */
    ANULADA("Anulada");

    private final String descripcion;

    EstadoVenta(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el nombre del estado para mostrar en pantalla.
     * @return el nombre del estado para mostrar en pantalla
     */
    @Override
    public String toString() {
        return descripcion;
    }
}
