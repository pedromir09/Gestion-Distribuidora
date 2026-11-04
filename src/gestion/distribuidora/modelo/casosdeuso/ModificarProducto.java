package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;

/**
 * Caso de uso: modificar los datos de un producto existente.
 */
public class ModificarProducto {

    /**
     * Valida el producto y guarda los cambios.
     * @param dao acceso a los datos de productos
     * @param producto el producto con los datos nuevos
     * @throws DaoException si no existe o no se pudo actualizar
     * @throws IllegalArgumentException si algun dato no es valido
     */
    public void ejecutar(ProductoDAO dao, Producto producto) throws DaoException {
        producto.validar();
        dao.update(producto);
    }
}
