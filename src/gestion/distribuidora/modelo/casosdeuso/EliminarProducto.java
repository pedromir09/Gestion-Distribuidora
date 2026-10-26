package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;

/**
 * Caso de uso: eliminar un producto.
 */
public class EliminarProducto {

    public void ejecutar(ProductoDAO dao, Long id) throws DaoException {
        dao.delete(id);
    }
}
