package gestion.distribuidora.dao;

/**
 * Excepcion propia de la capa de acceso a datos.
 * Envuelve los errores tecnicos (ej: SQLException) con un mensaje mas claro.
 */
public class DaoException extends Exception {

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }
}
