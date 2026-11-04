package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.Venta;

/**
 * Caso de uso: buscar una venta por su id, con su detalle.
 */
public class BuscarVenta {

    /**
     * Busca la venta.
     * @param dao acceso a los datos de ventas
     * @param id el id a buscar
     * @return la venta con sus renglones, o null si no existe
     * @throws DaoException si no se pudo leer
     */
    public Venta ejecutar(VentaDAO dao, Long id) throws DaoException {
        return dao.findById(id);
    }
}
