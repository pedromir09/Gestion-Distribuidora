package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.Venta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Ventana de ventas: lista los pedidos y permite cargar, ver, marcar como entregado,
 * anular e imprimir el comprobante.
 */
public class ListadoVentas extends JFrame {

    private final VentasTableModel modeloTabla = new VentasTableModel();
    private final JTable tablaVentas = new JTable(modeloTabla);
    private final JButton btnNueva = new JButton("Cargar pedido");
    private final JButton btnVerDetalle = new JButton("Ver detalle");
    private final JButton btnEntregada = new JButton("Marcar entregada");
    private final JButton btnAnular = new JButton("Anular");
    private final JButton btnComprobante = new JButton("Imprimir comprobante");

    /** Crea la ventana con la tabla vacia; la carga el controlador. */
    public ListadoVentas() {
        super("Ventas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
        setSize(720, 420);
    }

    private void inicializarComponentes() {
        tablaVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVentas.setRowHeight(28);
        tablaVentas.getTableHeader().setFont(tablaVentas.getTableHeader().getFont().deriveFont(Font.BOLD));
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(45);
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(220);
        tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(90);
        tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(120);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tablaVentas.getColumnModel().getColumn(0).setCellRenderer(centrado);
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        tablaVentas.getColumnModel().getColumn(4).setCellRenderer(derecha);

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        arriba.add(btnNueva);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnVerDetalle);
        abajo.add(btnEntregada);
        abajo.add(btnAnular);
        abajo.add(btnComprobante);

        JPanel contenido = new JPanel(new BorderLayout(8, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contenido.add(arriba, BorderLayout.NORTH);
        contenido.add(new JScrollPane(tablaVentas), BorderLayout.CENTER);
        contenido.add(abajo, BorderLayout.SOUTH);
        setContentPane(contenido);

        tablaVentas.getSelectionModel().addListSelectionListener(e -> habilitarBotones());
        habilitarBotones();
    }

    /** Entregar y anular solo se habilitan si la venta elegida esta pendiente. */
    private void habilitarBotones() {
        Venta elegida = getVentaSeleccionada();
        boolean hayElegida = elegida != null;
        boolean pendiente = hayElegida && elegida.estaPendiente();
        btnVerDetalle.setEnabled(hayElegida);
        btnComprobante.setEnabled(hayElegida);
        btnEntregada.setEnabled(pendiente);
        btnAnular.setEnabled(pendiente);
    }

    /**
     * Devuelve el boton para cargar un pedido nuevo.
     * @return el boton para cargar un pedido nuevo
     */
    public JButton getBtnNueva() {
        return btnNueva;
    }

    /**
     * Devuelve el boton Ver detalle.
     * @return el boton Ver detalle
     */
    public JButton getBtnVerDetalle() {
        return btnVerDetalle;
    }

    /**
     * Devuelve el boton Marcar entregada.
     * @return el boton Marcar entregada
     */
    public JButton getBtnEntregada() {
        return btnEntregada;
    }

    /**
     * Devuelve el boton Anular.
     * @return el boton Anular
     */
    public JButton getBtnAnular() {
        return btnAnular;
    }

    /**
     * Devuelve el boton Imprimir comprobante.
     * @return el boton Imprimir comprobante
     */
    public JButton getBtnComprobante() {
        return btnComprobante;
    }

    /**
     * Devuelve el modelo de la tabla, para cargarle las ventas.
     * @return el modelo de la tabla, para cargarle las ventas
     */
    public VentasTableModel getModeloTabla() {
        return modeloTabla;
    }

    /**
     * Devuelve la venta elegida en la tabla.
     * @return la venta seleccionada, o null si no hay ninguna fila elegida
     */
    public Venta getVentaSeleccionada() {
        int fila = tablaVentas.getSelectedRow();
        return fila == -1 ? null : modeloTabla.getVenta(fila);
    }

    /**
     * Vuelve a seleccionar una venta en la tabla (por ejemplo, despues de recargarla).
     * @param id el id de la venta a seleccionar
     */
    public void seleccionarVenta(Long id) {
        for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
            if (modeloTabla.getVenta(fila).getId().equals(id)) {
                tablaVentas.setRowSelectionInterval(fila, fila);
                return;
            }
        }
    }

    /**
     * Muestra un mensaje de error sobre la ventana.
     * @param mensaje el texto del error
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje informativo sobre la ventana.
     * @param mensaje el texto a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Ventas", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pide confirmacion al usuario.
     * @param mensaje la pregunta a mostrar
     * @return true si el usuario respondio que si
     */
    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Ejecuta una accion cada vez que el usuario vuelve a esta ventana desde otra
     * (por ejemplo, para recargar la tabla). La fila elegida se mantiene.
     * @param accion lo que hay que hacer al volver
     */
    public void alVolverALaVentana(Runnable accion) {
        RecargaAlVolver.instalar(this, accion, () -> {
            Venta elegido = getVentaSeleccionada();
            return elegido == null ? null : elegido.getId();
        }, this::seleccionarVenta);
    }
}
