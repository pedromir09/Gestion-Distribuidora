package gestion.distribuidora.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Datos de conexion a la base MySQL, en un solo lugar para todos los DAO.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/distribuidora";
    private static final String USUARIO = "root";
    private static final String CLAVE = "clave";

    private ConexionBD() {
    }

    /**
     * Abre una conexion nueva a la base de datos.
     * @return la conexion abierta (hay que cerrarla despues de usarla)
     * @throws DaoException si no se puede conectar, con un mensaje que explica que revisar
     */
    public static Connection getConnection() throws DaoException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            throw new DaoException("No se pudo conectar con la base de datos. "
                    + "Revisá que MySQL esté iniciado y que los datos de ConexionBD sean correctos.", e);
        }
    }
}
