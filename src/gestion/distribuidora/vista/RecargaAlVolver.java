package gestion.distribuidora.vista;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.JFrame;

/**
 * Hace que un listado se recargue cuando el usuario vuelve a el desde otra ventana,
 * por ejemplo, para ver el stock actualizado despues de registrar una venta.
 * Despues de recargar, vuelve a seleccionar la fila que estaba elegida.
 */
public final class RecargaAlVolver {

    private RecargaAlVolver() {
    }

    /**
     * Instala la recarga en una ventana.
     * @param ventana el listado
     * @param recargar lo que hay que hacer para recargar la tabla
     * @param idElegido devuelve el id de la fila elegida, o null si no hay ninguna
     * @param seleccionar vuelve a seleccionar la fila con ese id
     */
    public static void instalar(JFrame ventana, Runnable recargar, Supplier<Long> idElegido, Consumer<Long> seleccionar) {
        ventana.addWindowListener(new WindowAdapter() {
            private boolean primeraVez = true;   // al abrirse, la tabla ya viene cargada

            @Override
            public void windowActivated(WindowEvent e) {
                if (primeraVez) {
                    primeraVez = false;
                    return;
                }
                Window otra = e.getOppositeWindow();
                if (otra != null && otra.getOwner() == ventana) {
                    return;   // vuelve de un mensaje de esta misma ventana: no hace falta recargar
                }
                Long id = idElegido.get();
                recargar.run();
                if (id != null) {
                    seleccionar.accept(id);
                }
            }
        });
    }
}
