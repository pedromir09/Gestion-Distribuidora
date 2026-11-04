package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: buscar un producto por su id.
 */
public class BuscarProducto {

    /**
     * Busca el producto.
     * @param dao acceso a los datos de productos
     * @param id el id a buscar
     * @return el producto, o null si no existe
     * @throws DaoException si no se pudo leer
     */
    public Producto ejecutar(ProductoDAO dao, Long id) throws DaoException {
        return dao.findById(id);
    }
}
