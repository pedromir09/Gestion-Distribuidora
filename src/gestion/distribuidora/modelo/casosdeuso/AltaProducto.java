package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: dar de alta un producto nuevo.
 */
public class AltaProducto {

    public void ejecutar(ProductoDAO dao, Producto producto) throws DaoException {
        producto.validar();
        dao.insert(producto);
    }
}
