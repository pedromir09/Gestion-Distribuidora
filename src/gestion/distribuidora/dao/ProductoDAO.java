package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Producto;
import java.util.List;

/**
 * DAO especifico para Producto. El identificador es el id (Long) que genera la base.
 */
public interface ProductoDAO extends DAO<Producto, Long> {

    /**
     * Busca los productos cuyo nombre contenga el texto indicado.
     * @param nombre texto a buscar dentro del nombre
     * @return los productos que coinciden (vacia si no hay ninguno)
     * @throws DaoException si no se pudo leer
     */
    List<Producto> findByNombre(String nombre) throws DaoException;
}
