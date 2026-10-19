package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Producto;
import java.util.List;

/**
 * DAO especifico para Producto. El identificador es el codigo (String).
 */
public interface ProductoDAO extends DAO<Producto, String> {

    /** Busca los productos cuyo nombre contenga el texto indicado. */
    List<Producto> findByNombre(String nombre) throws DaoException;
}
