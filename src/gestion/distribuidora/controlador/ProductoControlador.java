package gestion.distribuidora.controlador;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;
import gestion.distribuidora.modelo.casosdeuso.AltaProducto;
import gestion.distribuidora.modelo.casosdeuso.BuscarProducto;
import gestion.distribuidora.modelo.casosdeuso.EliminarProducto;
import gestion.distribuidora.modelo.casosdeuso.ListarProductos;
import gestion.distribuidora.modelo.casosdeuso.ModificarProducto;
import gestion.distribuidora.vista.FormProducto;
import gestion.distribuidora.vista.ListadoProductos;

/**
 * Controlador de Producto: escucha los botones de las ventanas y llama
 * al caso de uso que corresponde, pasandole el DAO.
 */
public class ProductoControlador {

    private final ProductoDAO dao;
    private final ListadoProductos listado;

    private final AltaProducto altaProducto = new AltaProducto();
    private final ModificarProducto modificarProducto = new ModificarProducto();
    private final EliminarProducto eliminarProducto = new EliminarProducto();
    private final ListarProductos listarProductos = new ListarProductos();
    private final BuscarProducto buscarProducto = new BuscarProducto();

    /**
     * Conecta los botones del listado y carga la tabla.
     * @param dao acceso a los datos de productos
     * @param listado ventana principal con la tabla de productos
     */
    public ProductoControlador(ProductoDAO dao, ListadoProductos listado) {
        this.dao = dao;
        this.listado = listado;
        listado.getBtnNuevo().addActionListener(e -> abrirFormularioNuevo());
        listado.getBtnEditar().addActionListener(e -> abrirFormularioEditar());
        listado.getBtnEliminar().addActionListener(e -> eliminarSeleccionado());
        actualizarTabla();
    }

    private void actualizarTabla() {
        try {
            listado.getModeloTabla().actualizar(listarProductos.ejecutar(dao));
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void abrirFormularioNuevo() {
        FormProducto form = new FormProducto();
        form.setTitle("Nuevo artículo");
        form.getRootPane().setDefaultButton(form.getBtnGuardar());
        form.getBtnGuardar().addActionListener(e -> guardarNuevo(form));
        form.getBtnCancelar().addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(listado);
        form.setVisible(true);
    }

    private void guardarNuevo(FormProducto form) {
        try {
            altaProducto.ejecutar(dao, leerFormulario(form));
            form.dispose();
            actualizarTabla();
        } catch (IllegalArgumentException | DaoException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void abrirFormularioEditar() {
        Producto seleccionado = listado.getProductoSeleccionado();
        if (seleccionado == null) {
            listado.mostrarError("Selecciona un producto de la tabla");
            return;
        }
        try {
            Producto producto = buscarProducto.ejecutar(dao, seleccionado.getId());
            if (producto == null) {
                listado.mostrarError("El producto ya no existe");
                actualizarTabla();
                return;
            }
            FormProducto form = new FormProducto();
            form.setTitle("Editar artículo " + producto.getCodigo());
            form.getRootPane().setDefaultButton(form.getBtnGuardar());
            form.getTxtCodigo().setText(producto.getCodigo());
            form.getTxtNombre().setText(producto.getNombre());
            form.getTxtStock().setText(String.valueOf(producto.getStock()));
            form.getTxtPrecio().setText(String.format("%.2f", producto.getPrecio()));
            form.getBtnGuardar().addActionListener(e -> guardarEdicion(form, producto.getId()));
            form.getBtnCancelar().addActionListener(e -> form.dispose());
            form.setLocationRelativeTo(listado);
            form.setVisible(true);
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void guardarEdicion(FormProducto form, Long id) {
        try {
            Producto producto = leerFormulario(form);
            producto.setId(id);
            modificarProducto.ejecutar(dao, producto);
            form.dispose();
            actualizarTabla();
        } catch (IllegalArgumentException | DaoException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void eliminarSeleccionado() {
        Producto seleccionado = listado.getProductoSeleccionado();
        if (seleccionado == null) {
            listado.mostrarError("Selecciona un producto de la tabla");
            return;
        }
        if (!listado.confirmar("¿Eliminar el articulo " + seleccionado.getCodigo() + "?")) {
            return;
        }
        try {
            eliminarProducto.ejecutar(dao, seleccionado.getId());
            actualizarTabla();
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    /**
     * Arma un Producto con lo que escribio el usuario en el formulario.
     * @throws IllegalArgumentException si el stock o el precio no son numeros
     */
    private Producto leerFormulario(FormProducto form) {
        String codigo = form.getTxtCodigo().getText().trim();
        String nombre = form.getTxtNombre().getText().trim();
        try {
            int stock = Integer.parseInt(form.getTxtStock().getText().trim());
            double precio = Double.parseDouble(form.getTxtPrecio().getText().trim().replace(',', '.'));
            return new Producto(codigo, nombre, stock, precio);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El stock debe ser un numero entero y el precio un numero");
        }
    }
}
