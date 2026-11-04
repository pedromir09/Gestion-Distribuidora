package gestion.distribuidora.modelo;

/**
 * Se lanza cuando se quiere vender mas unidades de las que hay en stock.
 */
public class StockInsuficienteException extends Exception {

    private final Producto producto;
    private final int cantidadPedida;

    /**
     * Crea la excepcion con el producto y la cantidad que no alcanzo.
     * @param producto el producto sin stock suficiente
     * @param cantidadPedida las unidades que se quisieron vender
     */
    public StockInsuficienteException(Producto producto, int cantidadPedida) {
        super("Stock insuficiente de " + producto.getCodigo() + " (" + producto.getNombre() + "): "
                + "se pidieron " + cantidadPedida + " y hay " + producto.getStock());
        this.producto = producto;
        this.cantidadPedida = cantidadPedida;
    }

    /**
     * Devuelve el producto sin stock suficiente.
     * @return el producto sin stock suficiente
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Devuelve las unidades que se quisieron vender.
     * @return las unidades que se quisieron vender
     */
    public int getCantidadPedida() {
        return cantidadPedida;
    }
}
