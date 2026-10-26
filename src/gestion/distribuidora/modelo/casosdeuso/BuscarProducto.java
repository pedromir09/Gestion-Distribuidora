package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: buscar un producto por su id.
 */
public class BuscarProducto {

    public Producto ejecutar(ProductoDAO dao, Long id) throws DaoException {
        return dao.findById(id);
    }
}
