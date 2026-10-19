package gestion.distribuidora.dao;

import java.util.List;

/**
 * Contrato generico con las operaciones CRUD para cualquier entidad.
 * @param <T> tipo de la entidad (ej: Producto)
 * @param <ID> tipo del identificador de la entidad
 */
public interface DAO<T, ID> {

    void insert(T entity) throws DaoException;

    T findById(ID id) throws DaoException;

    List<T> findAll() throws DaoException;

    void update(T entity) throws DaoException;

    void delete(ID id) throws DaoException;
}
