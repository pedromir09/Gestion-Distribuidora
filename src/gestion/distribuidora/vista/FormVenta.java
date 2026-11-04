package gestion.distribuidora.vista;

import gestion.distribuidora.modelo.Cliente;
import gestion.distribuidora.modelo.Producto;
import gestion.distribuidora.modelo.Venta;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formulario para cargar un pedido (venta): se elige el cliente, se agregan
 * articulos con su cantidad y se confirma. Tambien sirve para ver el detalle
 * de una venta ya registrada.
 */
public class FormVenta extends JFrame {

    private final JComboBox<Cliente> cmbCliente = new JComboBox<>();
    private final JLabel lblFecha = new JLabel();
    private final JComboBox<Producto> cmbProducto = new JComboBox<>();
    private final JTextField txtCantidad = new JTextField("1", 5);
    private final JButton btnAgregar = new JButton("Agregar");
    private final DetallesVentaTableModel modeloDetalles = new DetallesVentaTableModel();
    private final JTable tablaDetalles = new JTable(modeloDetalles);
    private final JButton btnQuitar = new JButton("Quitar");
    private final JLabel lblTotal = new JLabel();
    private final JButton btnConfirmar = new JButton("Confirmar");
    private final JButton btnCancelar = new JButton("Cancelar");
    private final JPanel panelArticulos = new JPanel(new FlowLayout(FlowLayout.LEFT));

    /** Crea el formulario vacio para cargar un pedido nuevo. */
    public FormVenta() {
        super("Cargar pedido");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
        mostrarTotal(0);
        setSize(750, 480);
    }

    private void inicializarComponentes() {
        cmbCliente.setPreferredSize(new Dimension(320, cmbCliente.getPreferredSize().height));
        cmbProducto.setPreferredSize(new Dimension(370, cmbProducto.getPreferredSize().height));
        JPanel datos = new JPanel(new GridLayout(2, 1, 0, 4));
        datos.add(fila(etiqueta("Cliente:"), cmbCliente));
        datos.add(fila(etiqueta("Fecha:"), lblFecha));

        panelArticulos.add(etiqueta("Artículo:"));
        panelArticulos.add(cmbProducto);
        panelArticulos.add(new JLabel("Cantidad:"));
        panelArticulos.add(txtCantidad);
        panelArticulos.add(btnAgregar);

        tablaDetalles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDetalles.setRowHeight(26);
        tablaDetalles.getTableHeader().setFont(tablaDetalles.getTableHeader().getFont().deriveFont(Font.BOLD));
        tablaDetalles.getColumnModel().getColumn(1).setPreferredWidth(220);
        DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
        derecha.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int col = 2; col <= 4; col++) {
            tablaDetalles.getColumnModel().getColumn(col).setCellRenderer(derecha);
        }

        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16f));
        JPanel pieTabla = new JPanel(new BorderLayout());
        pieTabla.add(btnQuitar, BorderLayout.WEST);
        pieTabla.add(lblTotal, BorderLayout.EAST);

        JPanel centro = new JPanel(new BorderLayout(6, 6));
        centro.add(panelArticulos, BorderLayout.NORTH);
        centro.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);
        centro.add(pieTabla, BorderLayout.SOUTH);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnCancelar);
        botones.add(btnConfirmar);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        contenido.add(datos, BorderLayout.NORTH);
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(botones, BorderLayout.SOUTH);
        setContentPane(contenido);
    }

    private JLabel etiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setPreferredSize(new Dimension(70, etiqueta.getPreferredSize().height));
        return etiqueta;
    }

    private JPanel fila(JLabel etiqueta, Component componente) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        fila.add(etiqueta);
        fila.add(componente);
        return fila;
    }

    /**
     * Carga los clientes en el desplegable.
     * @param clientes los clientes a mostrar
     */
    public void cargarClientes(List<Cliente> clientes) {
        cmbCliente.removeAllItems();
        for (Cliente c : clientes) {
            cmbCliente.addItem(c);
        }
    }

    /**
     * Carga los productos en el desplegable.
     * @param productos los productos a mostrar
     */
    public void cargarProductos(List<Producto> productos) {
        cmbProducto.removeAllItems();
        for (Producto p : productos) {
            cmbProducto.addItem(p);
        }
    }

    /**
     * Devuelve el cliente elegido, o null si no hay ninguno.
     * @return el cliente elegido, o null si no hay ninguno
     */
    public Cliente getClienteSeleccionado() {
        return (Cliente) cmbCliente.getSelectedItem();
    }

    /**
     * Devuelve el producto elegido, o null si no hay ninguno.
     * @return el producto elegido, o null si no hay ninguno
     */
    public Producto getProductoSeleccionado() {
        return (Producto) cmbProducto.getSelectedItem();
    }

    /**
     * Devuelve el campo Cantidad.
     * @return el campo Cantidad
     */
    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    /**
     * Devuelve la posicion del renglon elegido en la tabla, o -1 si no hay ninguno.
     * @return la posicion del renglon elegido en la tabla, o -1 si no hay ninguno
     */
    public int getFilaDetalleSeleccionada() {
        return tablaDetalles.getSelectedRow();
    }

    /**
     * Devuelve el modelo de la tabla de renglones.
     * @return el modelo de la tabla de renglones
     */
    public DetallesVentaTableModel getModeloDetalles() {
        return modeloDetalles;
    }

    /**
     * Devuelve el boton Agregar.
     * @return el boton Agregar
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Devuelve el boton Quitar.
     * @return el boton Quitar
     */
    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    /**
     * Devuelve el boton Confirmar.
     * @return el boton Confirmar
     */
    public JButton getBtnConfirmar() {
        return btnConfirmar;
    }

    /**
     * Devuelve el boton Cancelar (o Cerrar, al ver el detalle).
     * @return el boton Cancelar (o Cerrar, al ver el detalle)
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Muestra la fecha de la venta.
     * @param fecha la fecha a mostrar
     */
    public void mostrarFecha(LocalDateTime fecha) {
        lblFecha.setText(Formato.fecha(fecha));
    }

    /**
     * Muestra el total de la venta.
     * @param total el importe total
     */
    public void mostrarTotal(double total) {
        lblTotal.setText("Total: " + Formato.moneda(total));
    }

    /**
     * Deja el formulario solo para mirar una venta ya registrada.
     * @param venta la venta a mostrar
     */
    public void mostrarVenta(Venta venta) {
        setTitle("Venta N° " + venta.getId() + " - " + venta.getEstado());
        cmbCliente.removeAllItems();
        cmbCliente.addItem(venta.getCliente());
        cmbCliente.setEnabled(false);
        mostrarFecha(venta.getFecha());
        modeloDetalles.actualizar(venta.getDetalles());
        mostrarTotal(venta.getTotal());
        panelArticulos.setVisible(false);
        btnQuitar.setVisible(false);
        btnConfirmar.setVisible(false);
        btnCancelar.setText("Cerrar");
    }

    /**
     * Muestra un mensaje de error sobre el formulario.
     * @param mensaje el texto del error
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
