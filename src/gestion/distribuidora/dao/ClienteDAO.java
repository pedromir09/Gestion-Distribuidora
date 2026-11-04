package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Cliente;
import java.util.List;

/**
 * DAO especifico para Cliente. El identificador es el id (Long) que genera la base.
 */
public interface ClienteDAO extends DAO<Cliente, Long> {

    /**
     * Busca los clientes cuyo nombre contenga el texto indicado.
     * @param texto texto a buscar dentro del nombre y apellido
     * @return los clientes que coinciden (vacia si no hay ninguno)
     * @throws DaoException si no se pudo leer
     */
    List<Cliente> findByNombre(String texto) throws DaoException;
}
