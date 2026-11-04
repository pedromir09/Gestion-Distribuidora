package gestion.distribuidora.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Ventana principal: desde aca se entra a cada parte del sistema.
 * Es la unica que cierra el programa al cerrarse.
 */
public class MenuPrincipal extends JFrame {

    private final JButton btnProductos = new JButton("Artículos");
    private final JButton btnClientes = new JButton("Clientes");
    private final JButton btnVentas = new JButton("Ventas / Pedidos");

    /** Crea el menu con un boton por cada entidad. */
    public MenuPrincipal() {
        super("Distribuidora de Ferretería");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        setSize(360, 300);
    }

    private void inicializarComponentes() {
        JLabel titulo = new JLabel("Distribuidora de Ferretería", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JPanel botones = new JPanel(new GridLayout(3, 1, 0, 10));
        botones.add(btnProductos);
        botones.add(btnClientes);
        botones.add(btnVentas);

        JPanel contenido = new JPanel(new BorderLayout(0, 16));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 24, 30));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(botones, BorderLayout.CENTER);
        setContentPane(contenido);
    }

    /**
     * Devuelve el boton para entrar a Articulos.
     * @return el boton para entrar a Articulos
     */
    public JButton getBtnProductos() {
        return btnProductos;
    }

    /**
     * Devuelve el boton para entrar a Clientes.
     * @return el boton para entrar a Clientes
     */
    public JButton getBtnClientes() {
        return btnClientes;
    }

    /**
     * Devuelve el boton para entrar a Ventas.
     * @return el boton para entrar a Ventas
     */
    public JButton getBtnVentas() {
        return btnVentas;
    }
}
