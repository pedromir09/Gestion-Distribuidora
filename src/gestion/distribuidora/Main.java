package gestion.distribuidora;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import gestion.distribuidora.controlador.ProductoControlador;
import gestion.distribuidora.dao.ProductoDAOMySQL;
import gestion.distribuidora.vista.ListadoProductos;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada: arma el DAO, la vista y el controlador, y muestra la ventana.
 */
public class Main {

    public static void main(String[] args) {
        FlatLaf.setGlobalExtraDefaults(Map.of("@accentColor", "#22D3EE"));   // color de acento cyan
        FlatDarkLaf.setup();                                                 // tema oscuro
        SwingUtilities.invokeLater(() -> {
            ListadoProductos listado = new ListadoProductos();
            new ProductoControlador(new ProductoDAOMySQL(), listado);
            listado.setLocationRelativeTo(null);
            listado.setVisible(true);
        });
    }
}
