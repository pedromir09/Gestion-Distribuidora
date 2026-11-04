package gestion.distribuidora.dao;

/**
 * Excepcion propia de la capa de acceso a datos.
 * Envuelve los errores tecnicos (ej: SQLException) con un mensaje mas claro.
 */
public class DaoException extends Exception {

    /**
     * Crea la excepcion guardando el error tecnico original.
     * @param message mensaje claro para el usuario
     * @param cause el error original (por ejemplo, la SQLException)
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea la excepcion solo con un mensaje.
     * @param message mensaje claro para el usuario
     */
    public DaoException(String message) {
        super(message);
    }
}
