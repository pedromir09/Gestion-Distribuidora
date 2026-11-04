package gestion.distribuidora.reportes;

/**
 * Se lanza cuando no se pudo imprimir un comprobante.
 */
public class ImpresionException extends Exception {

    /**
     * Crea la excepcion guardando el error original de la impresora.
     * @param message mensaje claro para el usuario
     * @param cause el error original
     */
    public ImpresionException(String message, Throwable cause) {
        super(message, cause);
    }
}
