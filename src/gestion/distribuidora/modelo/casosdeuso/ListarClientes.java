package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.modelo.Cliente;
import java.util.List;

/**
 * Caso de uso: listar todos los clientes.
 */
public class ListarClientes {

    /**
     * Devuelve todos los clientes, ordenados por nombre.
     * @param dao acceso a los datos de clientes
     * @return la lista de clientes
     * @throws DaoException si no se pudo leer
     */
    public List<Cliente> ejecutar(ClienteDAO dao) throws DaoException {
        return dao.findAll();
    }
}
