package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Venta;
import java.util.List;

/**
 * DAO especifico para Venta. El identificador es el id (Long) que genera la base.
 * En una venta, update guarda solo el estado y delete la anula (si esta pendiente) devolviendo el stock.
 */
public interface VentaDAO extends DAO<Venta, Long> {

    /**
     * Devuelve las ventas de un cliente.
     * @param idCliente el id del cliente
     * @return sus ventas, de la mas nueva a la mas vieja (vacia si no tiene)
     * @throws DaoException si no se pudo leer
     */
    List<Venta> findByCliente(Long idCliente) throws DaoException;
}
