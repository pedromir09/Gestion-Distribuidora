package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ClienteDAO;

/**
 * Caso de uso: eliminar un cliente.
 */
public class EliminarCliente {

    /**
     * Borra el cliente.
     * @param dao acceso a los datos de clientes
     * @param id el id del cliente a borrar
     * @throws DaoException si no existe, tiene ventas o no se pudo borrar
     */
    public void ejecutar(ClienteDAO dao, Long id) throws DaoException {
        dao.delete(id);
    }
}
