package gestion.distribuidora.controlador;

import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.dao.ClienteDAOMySQL;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.dao.ProductoDAOMySQL;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.dao.VentaDAOMySQL;
import gestion.distribuidora.vista.ListadoClientes;
import gestion.distribuidora.vista.ListadoProductos;
import gestion.distribuidora.vista.ListadoVentas;
import gestion.distribuidora.vista.MenuPrincipal;

/**
 * Controlador del menu principal: crea los DAO y abre la ventana de cada
 * entidad con su controlador.
 */
public class MenuControlador {

    private final MenuPrincipal menu;
    private final ProductoDAO productoDAO = new ProductoDAOMySQL();
    private final ClienteDAO clienteDAO = new ClienteDAOMySQL();
    private final VentaDAO ventaDAO = new VentaDAOMySQL();

    /**
     * Conecta los botones del menu.
     * @param menu la ventana del menu principal
     */
    public MenuControlador(MenuPrincipal menu) {
        this.menu = menu;
        menu.getBtnProductos().addActionListener(e -> abrirProductos());
        menu.getBtnClientes().addActionListener(e -> abrirClientes());
        menu.getBtnVentas().addActionListener(e -> abrirVentas());
    }

    private void abrirProductos() {
        ListadoProductos listado = new ListadoProductos();
        new ProductoControlador(productoDAO, listado);
        listado.setLocationRelativeTo(menu);
        listado.setVisible(true);
    }

    private void abrirClientes() {
        ListadoClientes listado = new ListadoClientes();
        new ClienteControlador(clienteDAO, listado);
        listado.setLocationRelativeTo(menu);
        listado.setVisible(true);
    }

    private void abrirVentas() {
        ListadoVentas listado = new ListadoVentas();
        new VentaControlador(ventaDAO, clienteDAO, productoDAO, listado);
        listado.setLocationRelativeTo(menu);
        listado.setVisible(true);
    }
}
