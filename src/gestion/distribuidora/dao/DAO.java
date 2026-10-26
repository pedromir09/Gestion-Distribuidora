package gestion.distribuidora.dao;

import java.util.List;

/**
 * Contrato generico con las operaciones CRUD para cualquier entidad.
 * @param <T> tipo de la entidad (ej: Producto)
 * @param <ID> tipo del identificador de la entidad
 */
public interface DAO<T, ID> {

    /**
     * Guarda una entidad nueva.
     * @param entity la entidad a guardar
     * @throws DaoException si no se pudo guardar
     */
    void insert(T entity) throws DaoException;

    /**
     * Busca una entidad por su identificador.
     * @param id el identificador a buscar
     * @return la entidad encontrada, o null si no existe
     * @throws DaoException si no se pudo leer
     */
    T findById(ID id) throws DaoException;

    /**
     * Devuelve todas las entidades guardadas.
     * @return la lista de entidades (vacia si no hay ninguna)
     * @throws DaoException si no se pudo leer
     */
    List<T> findAll() throws DaoException;

    /**
     * Modifica una entidad que ya existe.
     * @param entity la entidad con los datos nuevos
     * @throws DaoException si no existe o no se pudo actualizar
     */
    void update(T entity) throws DaoException;

    /**
     * Borra la entidad con ese identificador.
     * @param id el identificador de la entidad a borrar
     * @throws DaoException si no existe o no se pudo borrar
     */
    void delete(ID id) throws DaoException;
}
