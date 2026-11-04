package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.Venta;

/**
 * Caso de uso: marcar como entregada una venta pendiente.
 */
public class MarcarVentaEntregada {

    /**
     * Marca la venta como entregada y lo guarda.
     * @param dao acceso a los datos de ventas
     * @param venta la venta a entregar
     * @throws DaoException si no se pudo guardar
     * @throws IllegalStateException si la venta no esta pendiente
     */
    public void ejecutar(VentaDAO dao, Venta venta) throws DaoException {
        venta.marcarEntregada();
        dao.update(venta);
    }
}
