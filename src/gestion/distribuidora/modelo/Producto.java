package gestion.distribuidora.modelo;

/**
 * Producto que vende la distribuidora.
 */
public class Producto {

    private Long id;   // lo genera la base de datos
    private String codigo;
    private String nombre;
    private int stock;
    private double precio;

    /**
     * Crea un producto nuevo, todavia sin id.
     * @param codigo codigo interno del producto (no se repite)
     * @param nombre nombre del articulo
     * @param stock unidades disponibles
     * @param precio precio de venta
     */
    public Producto(String codigo, String nombre, int stock, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
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
     * Devuelve el codigo del producto.
     * @return el codigo del producto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Asigna el nuevo codigo.
     * @param codigo el nuevo codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el nombre del articulo.
     * @return el nombre del articulo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nuevo nombre.
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve las unidades disponibles.
     * @return las unidades disponibles
     */
    public int getStock() {
        return stock;
    }

    /**
     * Asigna las nuevas unidades disponibles.
     * @param stock las nuevas unidades disponibles
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve el precio de venta.
     * @return el precio de venta
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Asigna el nuevo precio de venta.
     * @param precio el nuevo precio de venta
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Verifica que el producto cumpla las reglas de la distribuidora.
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void validar() {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    /**
     * Devuelve el codigo, el nombre y el stock, como se muestra en los desplegables.
     * @return por ejemplo "FER-0001 (stock 30) - Martillo"
     */
    @Override
    public String toString() {
        return codigo + " (stock " + stock + ") - " + nombre;
    }
}
