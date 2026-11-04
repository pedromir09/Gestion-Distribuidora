package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.Venta;

/**
 * Caso de uso: anular una venta pendiente y devolver el stock.
 */
public class AnularVenta {

    /**
     * Anula la venta si todavia esta pendiente.
     * @param dao acceso a los datos de ventas
     * @param id el id de la venta a anular
     * @throws DaoException si no existe o no se pudo anular
     * @throws IllegalStateException si la venta ya se entrego o ya estaba anulada
     */
    public void ejecutar(VentaDAO dao, Long id) throws DaoException {
        Venta venta = dao.findById(id);
        if (venta == null) {
            throw new DaoException("No existe una venta con el id " + id);
        }
        venta.anular();
        dao.delete(id);
    }
}
