package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla de clientes: adapta una lista de Cliente a filas y columnas.
 */
public class ClientesTableModel extends AbstractTableModel {

    private static final String[] COLUMNAS = {"ID", "Nombre y Apellido", "Dirección", "Teléfono"};

    private List<Cliente> clientes = new ArrayList<>();

    /**
     * Reemplaza los clientes que se muestran y repinta la tabla.
     * @param clientes la nueva lista de clientes
     */
    public void actualizar(List<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
    }

    /**
     * Devuelve el cliente de una fila.
     * @param fila numero de fila de la tabla
     * @return el cliente que se muestra en esa fila
     */
    public Cliente getCliente(int fila) {
        return clientes.get(fila);
    }

    @Override
    public int getRowCount() {
        return clientes.size();
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
        Cliente c = clientes.get(fila);
        return switch (columna) {
            case 0 -> c.getId();
            case 1 -> c.getNombreApellido();
            case 2 -> c.getDireccion();
            case 3 -> c.getTelefono();
            default -> null;
        };
    }
}
