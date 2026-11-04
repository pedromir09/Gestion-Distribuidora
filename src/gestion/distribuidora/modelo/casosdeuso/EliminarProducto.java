package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;

/**
 * Caso de uso: eliminar un producto.
 */
public class EliminarProducto {

    /**
     * Borra el producto.
     * @param dao acceso a los datos de productos
     * @param id el id del producto a borrar
     * @throws DaoException si no existe, tiene ventas o no se pudo borrar
     */
    public void ejecutar(ProductoDAO dao, Long id) throws DaoException {
        dao.delete(id);
    }
}
