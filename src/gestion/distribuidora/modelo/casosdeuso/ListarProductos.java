package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.modelo.Producto;
import java.util.List;

/**
 * Caso de uso: listar todos los productos.
 */
public class ListarProductos {

    public List<Producto> ejecutar(ProductoDAO dao) throws DaoException {
        return dao.findAll();
    }
}
