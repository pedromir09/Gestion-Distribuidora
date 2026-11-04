package gestion.distribuidora.modelo.casosdeuso;

import gestion.distribuidora.dao.DaoException;
import gestion.distribuidora.dao.ProductoDAO;
import gestion.distribuidora.dao.VentaDAO;
import gestion.distribuidora.modelo.DetalleVenta;
import gestion.distribuidora.modelo.Producto;
import gestion.distribuidora.modelo.StockInsuficienteException;
import gestion.distribuidora.modelo.Venta;

/**
 * Caso de uso: registrar una venta y descontar el stock de los productos.
 */
public class RegistrarVenta {

    /**
     * Valida la venta, controla que haya stock de cada producto y la guarda.
     * @param ventaDAO acceso a los datos de ventas
     * @param productoDAO acceso a los datos de productos, para ver el stock actual
     * @param venta la venta a registrar
     * @throws StockInsuficienteException si algun producto no tiene stock suficiente
     * @throws DaoException si no se pudo leer o guardar
     * @throws IllegalArgumentException si la venta no tiene cliente o productos
     */
    public void ejecutar(VentaDAO ventaDAO, ProductoDAO productoDAO, Venta venta)
            throws StockInsuficienteException, DaoException {
        venta.validar();
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto actual = productoDAO.findById(detalle.getProducto().getId());
            if (actual == null) {
                throw new DaoException("El producto " + detalle.getProducto().getCodigo() + " ya no existe");
            }
            if (actual.getStock() < detalle.getCantidad()) {
                throw new StockInsuficienteException(actual, detalle.getCantidad());
            }
        }
        ventaDAO.insert(venta);
    }
}
