package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla de productos: adapta una lista de Producto a filas y columnas.
 */
public class ProductosTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = {"ID", "Código", "Artículo", "Stock", "Precio"};

    private List<Producto> productos = new ArrayList<>();

    /**
     * Reemplaza los productos que se muestran y repinta la tabla.
     * @param productos la nueva lista de productos
     */
    public void actualizar(List<Producto> productos) {
        this.productos = productos;
        fireTableDataChanged();
    }

    /**
     * Devuelve el producto de una fila.
     * @param fila numero de fila de la tabla
     * @return el producto que se muestra en esa fila
     */
    public Producto getProducto(int fila) {
        return productos.get(fila);
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int columna) {
        return COLUMNAS[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Producto p = productos.get(fila);
        return switch (columna) {
            case 0 -> p.getId();
            case 1 -> p.getCodigo();
            case 2 -> p.getNombre();
            case 3 -> p.getStock();
            case 4 -> p.getPrecio();
            default -> null;
        };
    }
}
