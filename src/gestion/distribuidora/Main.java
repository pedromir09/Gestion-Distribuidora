package gestion.distribuidora;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import gestion.distribuidora.controlador.MenuControlador;
import gestion.distribuidora.vista.MenuPrincipal;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada: aplica el estilo visual y abre el menu principal.
 */
public class Main {

    private Main() {
    }

    /**
     * Arranca el sistema.
     * @param args no se usan
     */
    public static void main(String[] args) {
        FlatLaf.setGlobalExtraDefaults(Map.of("@accentColor", "#22D3EE"));   // color de acento cyan
        FlatDarkLaf.setup();                                                 // tema oscuro
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            new MenuControlador(menu);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }
}
