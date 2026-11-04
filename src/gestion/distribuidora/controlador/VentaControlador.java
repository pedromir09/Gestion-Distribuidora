package gestion.distribuidora.controlador;

import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.Cliente;
import gestion.distribuidora.modelo.DetalleVenta;
import gestion.distribuidora.modelo.Producto;
import gestion.distribuidora.modelo.StockInsuficienteException;
import gestion.distribuidora.modelo.Venta;
import gestion.distribuidora.modelo.casosdeuso.AnularVenta;
import gestion.distribuidora.modelo.casosdeuso.BuscarVenta;
import gestion.distribuidora.modelo.casosdeuso.ListarClientes;
import gestion.distribuidora.modelo.casosdeuso.ListarProductos;
import gestion.distribuidora.modelo.casosdeuso.ListarVentas;
import gestion.distribuidora.modelo.casosdeuso.MarcarVentaEntregada;
import gestion.distribuidora.modelo.casosdeuso.RegistrarVenta;
import gestion.distribuidora.reportes.ComprobanteVenta;
import gestion.distribuidora.reportes.ImpresionException;
import gestion.distribuidora.vista.FormVenta;
import gestion.distribuidora.vista.ListadoVentas;
import java.util.List;

/**
 * Controlador de Venta: escucha los botones de las ventanas de ventas y llama
 * al caso de uso que corresponde, pasandole los DAO.
 */
public class VentaControlador {

    private final VentaDAO ventaDAO;
    private final ClienteDAO clienteDAO;
    private final ProductoDAO productoDAO;
    private final ListadoVentas listado;

    private final RegistrarVenta registrarVenta = new RegistrarVenta();
    private final ListarVentas listarVentas = new ListarVentas();
    private final BuscarVenta buscarVenta = new BuscarVenta();
    private final MarcarVentaEntregada marcarVentaEntregada = new MarcarVentaEntregada();
    private final AnularVenta anularVenta = new AnularVenta();
    private final ListarClientes listarClientes = new ListarClientes();
    private final ListarProductos listarProductos = new ListarProductos();

    /**
     * Conecta los botones del listado y carga la tabla.
     * @param ventaDAO acceso a los datos de ventas
     * @param clienteDAO acceso a los datos de clientes, para elegir el cliente
     * @param productoDAO acceso a los datos de productos, para elegir articulos y ver el stock
     * @param listado ventana con la tabla de ventas
     */
    public VentaControlador(VentaDAO ventaDAO, ClienteDAO clienteDAO, ProductoDAO productoDAO, ListadoVentas listado) {
        this.ventaDAO = ventaDAO;
        this.clienteDAO = clienteDAO;
        this.productoDAO = productoDAO;
        this.listado = listado;
        listado.getBtnNueva().addActionListener(e -> abrirFormularioNuevo());
        listado.getBtnVerDetalle().addActionListener(e -> verDetalle());
        listado.getBtnEntregada().addActionListener(e -> marcarEntregada());
        listado.getBtnAnular().addActionListener(e -> anular());
        listado.getBtnComprobante().addActionListener(e -> imprimirComprobante());
        listado.alVolverALaVentana(this::actualizarTabla);
        actualizarTabla();
    }

    private void actualizarTabla() {
        try {
            listado.getModeloTabla().actualizar(listarVentas.ejecutar(ventaDAO));
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void abrirFormularioNuevo() {
        List<Cliente> clientes;
        List<Producto> productos;
        try {
            clientes = listarClientes.ejecutar(clienteDAO);
            productos = listarProductos.ejecutar(productoDAO);
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
            return;
        }
        if (clientes.isEmpty() || productos.isEmpty()) {
            listado.mostrarError("Para cargar un pedido tiene que haber al menos un cliente y un artículo");
            return;
        }
        Venta venta = new Venta(null);   // el cliente se toma del desplegable al confirmar
        FormVenta form = new FormVenta();
        form.cargarClientes(clientes);
        form.cargarProductos(productos);
        form.mostrarFecha(venta.getFecha());
        form.getRootPane().setDefaultButton(form.getBtnAgregar());
        form.getBtnAgregar().addActionListener(e -> agregarArticulo(form, venta));
        form.getBtnQuitar().addActionListener(e -> quitarArticulo(form, venta));
        form.getBtnConfirmar().addActionListener(e -> confirmar(form, venta));
        form.getBtnCancelar().addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(listado);
        form.setVisible(true);
    }

    private void agregarArticulo(FormVenta form, Venta venta) {
        Producto producto = form.getProductoSeleccionado();
        try {
            int cantidad = leerCantidad(form);
            venta.agregarDetalle(new DetalleVenta(producto, cantidad, producto.getPrecio()));
            mostrarRenglones(form, venta);
            form.getTxtCantidad().setText("1");
        } catch (IllegalArgumentException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void quitarArticulo(FormVenta form, Venta venta) {
        int fila = form.getFilaDetalleSeleccionada();
        if (fila == -1) {
            form.mostrarError("Selecciona un artículo de la tabla");
            return;
        }
        venta.quitarDetalle(fila);
        mostrarRenglones(form, venta);
    }

    private void confirmar(FormVenta form, Venta venta) {
        if (venta.getId() != null) {
            return;   // doble clic: la venta ya se registro
        }
        venta.setCliente(form.getClienteSeleccionado());
        try {
            registrarVenta.ejecutar(ventaDAO, productoDAO, venta);
            form.dispose();
            actualizarTabla();
            listado.seleccionarVenta(venta.getId());
            listado.mostrarMensaje("Pedido registrado con el número " + venta.getId());
        } catch (IllegalArgumentException | StockInsuficienteException | DaoException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void verDetalle() {
        Venta venta = buscarSeleccionada();
        if (venta == null) {
            return;
        }
        FormVenta form = new FormVenta();
        form.mostrarVenta(venta);
        form.getBtnCancelar().addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(listado);
        form.setVisible(true);
    }

    private void marcarEntregada() {
        Venta venta = buscarSeleccionada();
        if (venta == null) {
            return;
        }
        try {
            marcarVentaEntregada.ejecutar(ventaDAO, venta);
            actualizarTabla();
            listado.seleccionarVenta(venta.getId());
        } catch (IllegalStateException | DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void anular() {
        Venta seleccionada = listado.getVentaSeleccionada();
        if (seleccionada == null) {
            listado.mostrarError("Selecciona una venta de la tabla");
            return;
        }
        if (!listado.confirmar("¿Anular la venta N° " + seleccionada.getId() + "? Se devuelve el stock de los artículos.")) {
            return;
        }
        try {
            anularVenta.ejecutar(ventaDAO, seleccionada.getId());
            actualizarTabla();
            listado.seleccionarVenta(seleccionada.getId());
        } catch (IllegalStateException | DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void imprimirComprobante() {
        Venta venta = buscarSeleccionada();
        if (venta == null) {
            return;
        }
        try {
            new ComprobanteVenta(venta).imprimir();
        } catch (ImpresionException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    /** Trae de la base la venta elegida en la tabla, o devuelve null avisando por que. */
    private Venta buscarSeleccionada() {
        Venta seleccionada = listado.getVentaSeleccionada();
        if (seleccionada == null) {
            listado.mostrarError("Selecciona una venta de la tabla");
            return null;
        }
        try {
            Venta venta = buscarVenta.ejecutar(ventaDAO, seleccionada.getId());
            if (venta == null) {
                listado.mostrarError("La venta ya no existe");
                actualizarTabla();
            }
            return venta;
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
            return null;
        }
    }

    private void mostrarRenglones(FormVenta form, Venta venta) {
        form.getModeloDetalles().actualizar(venta.getDetalles());
        form.mostrarTotal(venta.getTotal());
    }

    private int leerCantidad(FormVenta form) {
        try {
            return Integer.parseInt(form.getTxtCantidad().getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad tiene que ser un número entero");
        }
    }
}
