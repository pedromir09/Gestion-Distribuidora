package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ClienteDAO;
import gestion.distribuidora.modelo.Cliente;

/**
 * Caso de uso: modificar los datos de un cliente existente.
 */
public class ModificarCliente {

    /**
     * Valida el cliente y guarda los cambios.
     * @param dao acceso a los datos de clientes
     * @param cliente el cliente con los datos nuevos
     * @throws DaoException si no existe o no se pudo actualizar
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void ejecutar(ClienteDAO dao, Cliente cliente) throws DaoException {
        cliente.validar();
        dao.update(cliente);
    }
}
