package gestion.distribuidora.modelo;

/**
 * Un renglon de una venta: que producto, cuantas unidades y a que precio.
 */
public class DetalleVenta {

    private final Producto producto;
    private int cantidad;
    private final double precioUnitario;   // el precio del dia de la venta

    /**
     * Crea un renglon de venta.
     * @param producto el producto vendido
     * @param cantidad las unidades vendidas
     * @param precioUnitario el precio de cada unidad al momento de la venta
     */
    public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    /**
     * Devuelve el producto vendido.
     * @return el producto vendido
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Devuelve las unidades vendidas.
     * @return las unidades vendidas
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Asigna las nuevas unidades vendidas.
     * @param cantidad las nuevas unidades vendidas
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el precio de cada unidad al momento de la venta.
     * @return el precio de cada unidad al momento de la venta
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Devuelve la cantidad por el precio unitario.
     * @return la cantidad por el precio unitario
     */
    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
