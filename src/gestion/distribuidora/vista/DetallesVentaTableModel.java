package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.DetalleVenta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla con los renglones de una venta.
 */
public class DetallesVentaTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = {"Código", "Artículo", "Cantidad", "Precio unit.", "Subtotal"};

    private List<DetalleVenta> detalles = new ArrayList<>();

    /**
     * Reemplaza los renglones que se muestran y repinta la tabla.
     * @param detalles los renglones de la venta
     */
    public void actualizar(List<DetalleVenta> detalles) {
        this.detalles = detalles;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return detalles.size();
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
        DetalleVenta d = detalles.get(fila);
        return switch (columna) {
            case 0 -> d.getProducto().getCodigo();
            case 1 -> d.getProducto().getNombre();
            case 2 -> d.getCantidad();
            case 3 -> Formato.moneda(d.getPrecioUnitario());
            case 4 -> Formato.moneda(d.getSubtotal());
            default -> null;
        };
    }
}
