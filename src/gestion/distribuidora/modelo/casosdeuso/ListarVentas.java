package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.Venta;
import java.util.List;

/**
 * Caso de uso: listar todas las ventas.
 */
public class ListarVentas {

    /**
     * Devuelve todas las ventas, de la mas nueva a la mas vieja.
     * @param dao acceso a los datos de ventas
     * @return la lista de ventas con sus renglones
     * @throws DaoException si no se pudo leer
     */
    public List<Venta> ejecutar(VentaDAO dao) throws DaoException {
        return dao.findAll();
    }
}
