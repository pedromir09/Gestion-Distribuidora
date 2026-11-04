package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.modelo.Cliente;

/**
 * Caso de uso: buscar un cliente por su id.
 */
public class BuscarCliente {

    /**
     * Busca el cliente.
     * @param dao acceso a los datos de clientes
     * @param id el id a buscar
     * @return el cliente, o null si no existe
     * @throws DaoException si no se pudo leer
     */
    public Cliente ejecutar(ClienteDAO dao, Long id) throws DaoException {
        return dao.findById(id);
    }
}
