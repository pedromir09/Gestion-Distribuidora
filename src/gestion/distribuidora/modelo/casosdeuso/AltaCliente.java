package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.modelo.Cliente;

/**
 * Caso de uso: dar de alta un cliente nuevo.
 */
public class AltaCliente {

    /**
     * Valida el cliente y lo guarda.
     * @param dao acceso a los datos de clientes
     * @param cliente el cliente nuevo
     * @throws DaoException si no se pudo guardar (por ejemplo, CUIT repetido)
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void ejecutar(ClienteDAO dao, Cliente cliente) throws DaoException {
        cliente.validar();
        dao.insert(cliente);
    }
}
