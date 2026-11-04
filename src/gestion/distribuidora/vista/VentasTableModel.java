package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla de ventas: adapta una lista de Venta a filas y columnas.
 */
public class VentasTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Fecha", "Cliente", "Estado", "Total"};

    private List<Venta> ventas = new ArrayList<>();

    /**
     * Reemplaza las ventas que se muestran y repinta la tabla.
     * @param ventas la nueva lista de ventas
     */
    public void actualizar(List<Venta> ventas) {
        this.ventas = ventas;
        fireTableDataChanged();
    }

    /**
     * Devuelve la venta de una fila.
     * @param fila numero de fila de la tabla
     * @return la venta que se muestra en esa fila
     */
    public Venta getVenta(int fila) {
        return ventas.get(fila);
    }

    @Override
    public int getRowCount() {
        return ventas.size();
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
        Venta v = ventas.get(fila);
        return switch (columna) {
            case 0 -> v.getId();
            case 1 -> Formato.fecha(v.getFecha());
            case 2 -> v.getCliente().getNombreApellido();
            case 3 -> v.getEstado().toString();
            case 4 -> Formato.moneda(v.getTotal());
            default -> null;
        };
    }
}
