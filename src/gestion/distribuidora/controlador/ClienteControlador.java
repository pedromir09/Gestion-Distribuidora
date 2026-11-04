package gestion.distribuidora.controlador;

import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.modelo.Cliente;
import gestion.distribuidora.modelo.casosdeuso.AltaCliente;
import gestion.distribuidora.modelo.casosdeuso.BuscarCliente;
import gestion.distribuidora.modelo.casosdeuso.EliminarCliente;
import gestion.distribuidora.modelo.casosdeuso.ListarClientes;
import gestion.distribuidora.modelo.casosdeuso.ModificarCliente;
import gestion.distribuidora.vista.FormCliente;
import gestion.distribuidora.vista.ListadoClientes;

/**
 * Controlador de Cliente: escucha los botones de las ventanas y llama
 * al caso de uso que corresponde, pasandole el DAO.
 */
public class ClienteControlador {

    private final ClienteDAO dao;
    private final ListadoClientes listado;

    private final AltaCliente altaCliente = new AltaCliente();
    private final ModificarCliente modificarCliente = new ModificarCliente();
    private final EliminarCliente eliminarCliente = new EliminarCliente();
    private final ListarClientes listarClientes = new ListarClientes();
    private final BuscarCliente buscarCliente = new BuscarCliente();

    /**
     * Conecta los botones del listado y carga la tabla.
     * @param dao acceso a los datos de clientes
     * @param listado ventana con la tabla de clientes
     */
    public ClienteControlador(ClienteDAO dao, ListadoClientes listado) {
        this.dao = dao;
        this.listado = listado;
        listado.getBtnNuevo().addActionListener(e -> abrirFormularioNuevo());
        listado.getBtnEditar().addActionListener(e -> abrirFormularioEditar());
        listado.getBtnEliminar().addActionListener(e -> eliminarSeleccionado());
        listado.alVolverALaVentana(this::actualizarTabla);
        actualizarTabla();
    }

    private void actualizarTabla() {
        try {
            listado.getModeloTabla().actualizar(listarClientes.ejecutar(dao));
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void abrirFormularioNuevo() {
        FormCliente form = new FormCliente();
        form.setTitle("Nuevo cliente");
        form.getRootPane().setDefaultButton(form.getBtnGuardar());
        form.getBtnGuardar().addActionListener(e -> guardarNuevo(form));
        form.getBtnCancelar().addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(listado);
        form.setVisible(true);
    }

    private void guardarNuevo(FormCliente form) {
        if (!form.isDisplayable()) {
            return;   // doble clic: el formulario ya se guardo y se cerro
        }
        try {
            altaCliente.ejecutar(dao, leerFormulario(form));
            form.dispose();
            actualizarTabla();
        } catch (IllegalArgumentException | DaoException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void abrirFormularioEditar() {
        Cliente seleccionado = listado.getClienteSeleccionado();
        if (seleccionado == null) {
            listado.mostrarError("Selecciona un cliente de la tabla");
            return;
        }
        try {
            Cliente cliente = buscarCliente.ejecutar(dao, seleccionado.getId());
            if (cliente == null) {
                listado.mostrarError("El cliente ya no existe");
                actualizarTabla();
                return;
            }
            FormCliente form = new FormCliente();
            form.setTitle("Editar cliente " + cliente.getNombreApellido());
            form.getRootPane().setDefaultButton(form.getBtnGuardar());
            form.getTxtId().setText(String.valueOf(cliente.getId()));
            form.getTxtNombreApellido().setText(cliente.getNombreApellido());
            form.getTxtDireccion().setText(cliente.getDireccion());
            form.getTxtCuit().setText(cliente.getCuit());
            form.getTxtTelefono().setText(cliente.getTelefono());
            form.getBtnGuardar().addActionListener(e -> guardarEdicion(form, cliente.getId()));
            form.getBtnCancelar().addActionListener(e -> form.dispose());
            form.setLocationRelativeTo(listado);
            form.setVisible(true);
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private void guardarEdicion(FormCliente form, Long id) {
        if (!form.isDisplayable()) {
            return;   // doble clic: el formulario ya se guardo y se cerro
        }
        try {
            Cliente cliente = leerFormulario(form);
            cliente.setId(id);
            modificarCliente.ejecutar(dao, cliente);
            form.dispose();
            actualizarTabla();
        } catch (IllegalArgumentException | DaoException e) {
            form.mostrarError(e.getMessage());
        }
    }

    private void eliminarSeleccionado() {
        Cliente seleccionado = listado.getClienteSeleccionado();
        if (seleccionado == null) {
            listado.mostrarError("Selecciona un cliente de la tabla");
            return;
        }
        if (!listado.confirmar("¿Eliminar el cliente " + seleccionado.getNombreApellido() + "?")) {
            return;
        }
        try {
            eliminarCliente.ejecutar(dao, seleccionado.getId());
            actualizarTabla();
        } catch (DaoException e) {
            listado.mostrarError(e.getMessage());
        }
    }

    private Cliente leerFormulario(FormCliente form) {
        return new Cliente(
                form.getTxtNombreApellido().getText().trim(),
                form.getTxtDireccion().getText().trim(),
                form.getTxtCuit().getText().trim(),
                form.getTxtTelefono().getText().trim());
    }
}
