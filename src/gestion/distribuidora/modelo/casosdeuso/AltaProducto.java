package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: dar de alta un producto nuevo.
 */
public class AltaProducto {

    /**
     * Valida el producto y lo guarda.
     * @param dao acceso a los datos de productos
     * @param producto el producto nuevo
     * @throws DaoException si no se pudo guardar (por ejemplo, codigo repetido)
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void ejecutar(ProductoDAO dao, Producto producto) throws DaoException {
        producto.validar();
        dao.insert(producto);
    }
}
