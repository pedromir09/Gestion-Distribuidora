package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: modificar los datos de un producto existente.
 */
public class ModificarProducto {

    public void ejecutar(ProductoDAO dao, Producto producto) throws DaoException {
        producto.validar();
        dao.update(producto);
    }
}
