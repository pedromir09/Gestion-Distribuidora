package gestion.distribuidora.vista;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Formatos para mostrar importes y fechas en pantalla.
 */
public final class Formato {

    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(Locale.of("es", "AR"));
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Formato() {
    }

    /**
     * Da formato de pesos a un importe.
     * @param importe el importe
     * @return el importe con formato, por ejemplo "$ 2.500,00"
     */
    public static String moneda(double importe) {
        return MONEDA.format(importe);
    }

    /**
     * Da formato a una fecha y hora.
     * @param fecha la fecha
     * @return la fecha con formato dd/MM/yyyy HH:mm
     */
    public static String fecha(LocalDateTime fecha) {
        return fecha.format(FECHA);
    }
}
