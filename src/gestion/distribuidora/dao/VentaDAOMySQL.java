package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Cliente;
import gestion.distribuidora.modelo.DetalleVenta;
import gestion.distribuidora.modelo.EstadoVenta;
import gestion.distribuidora.modelo.Producto;
import gestion.distribuidora.modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementacion de VentaDAO usando JDBC y MySQL.
 * Registrar y anular una venta tocan varias tablas, por eso se hacen en una
 * transaccion: o se guarda todo, o no se guarda nada.
 */
public class VentaDAOMySQL implements VentaDAO {

    private static final String SELECT_VENTAS =
            "SELECT v.id, v.fecha, v.estado, c.id AS c_id, c.nombre_apellido, c.direccion, c.cuit, c.telefono "
            + "FROM ventas v JOIN clientes c ON c.id = v.id_cliente ";

    private static final String SELECT_DETALLES =
            "SELECT d.id_venta, d.cantidad, d.precio_unitario, p.id, p.codigo, p.nombre, p.stock, p.precio "
            + "FROM detalle_venta d JOIN productos p ON p.id = d.id_producto ";

    /** Crea el DAO; cada operacion abre su propia conexion con {@link ConexionBD}. */
    public VentaDAOMySQL() {
    }

    /**
     * Registra la venta con sus renglones y descuenta el stock de cada producto.
     * @param venta la venta a guardar; al terminar queda con el id asignado
     * @throws DaoException si no se pudo guardar o si mientras tanto se quedo sin stock
     */
    @Override
    public void insert(Venta venta) throws DaoException {
        String sqlVenta = "INSERT INTO ventas (fecha, id_cliente, estado) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection cn = ConexionBD.getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psVenta = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = cn.prepareStatement(sqlStock)) {
                psVenta.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
                psVenta.setLong(2, venta.getCliente().getId());
                psVenta.setString(3, venta.getEstado().name());
                psVenta.executeUpdate();
                long idVenta;
                try (ResultSet keys = psVenta.getGeneratedKeys()) {
                    keys.next();
                    idVenta = keys.getLong(1);
                }
                for (DetalleVenta d : venta.getDetalles()) {
                    psDetalle.setLong(1, idVenta);
                    psDetalle.setLong(2, d.getProducto().getId());
                    psDetalle.setInt(3, d.getCantidad());
                    psDetalle.setDouble(4, d.getPrecioUnitario());
                    psDetalle.executeUpdate();

                    psStock.setInt(1, d.getCantidad());
                    psStock.setLong(2, d.getProducto().getId());
                    psStock.setInt(3, d.getCantidad());
                    if (psStock.executeUpdate() == 0) {
                        throw new DaoException("Stock insuficiente de " + d.getProducto().getCodigo());
                    }
                }
                cn.commit();
                venta.setId(idVenta);
            } catch (SQLException | DaoException e) {
                cn.rollback();   // si fallo algo, no queda nada a medias
                throw e;
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo registrar la venta", e);
        }
    }

    @Override
    public Venta findById(Long id) throws DaoException {
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_VENTAS + "WHERE v.id = ?")) {
            ps.setLong(1, id);
            List<Venta> ventas = leerVentas(cn, ps);
            return ventas.isEmpty() ? null : ventas.get(0);
        } catch (SQLException e) {
            throw new DaoException("No se pudo buscar la venta con id " + id, e);
        }
    }

    @Override
    public List<Venta> findAll() throws DaoException {
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_VENTAS + "ORDER BY v.fecha DESC, v.id DESC")) {
            return leerVentas(cn, ps);
        } catch (SQLException e) {
            throw new DaoException("No se pudo listar las ventas", e);
        }
    }

    @Override
    public List<Venta> findByCliente(Long idCliente) throws DaoException {
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_VENTAS + "WHERE c.id = ? ORDER BY v.fecha DESC, v.id DESC")) {
            ps.setLong(1, idCliente);
            return leerVentas(cn, ps);
        } catch (SQLException e) {
            throw new DaoException("No se pudo listar las ventas del cliente", e);
        }
    }

    /**
     * Guarda el estado de la venta. Es lo unico que se modifica de una venta ya registrada.
     * @param venta la venta con el estado nuevo
     * @throws DaoException si no existe o no se pudo actualizar
     */
    @Override
    public void update(Venta venta) throws DaoException {
        String sql = "UPDATE ventas SET estado = ? WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, venta.getEstado().name());
            ps.setLong(2, venta.getId());
            if (ps.executeUpdate() == 0) {
                throw new DaoException("No existe una venta con el id " + venta.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo actualizar la venta", e);
        }
    }

    /**
     * Anula la venta: devuelve el stock de cada producto y la marca como anulada.
     * La venta no se borra, para que quede el historial.
     * @param id el id de la venta a anular
     * @throws DaoException si no existe, no esta pendiente o no se pudo anular
     */
    @Override
    public void delete(Long id) throws DaoException {
        String sqlEstado = "SELECT estado FROM ventas WHERE id = ? FOR UPDATE";
        String sqlDevolver = "UPDATE productos p JOIN detalle_venta d ON d.id_producto = p.id "
                + "SET p.stock = p.stock + d.cantidad WHERE d.id_venta = ?";
        String sqlAnular = "UPDATE ventas SET estado = ? WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psEstado = cn.prepareStatement(sqlEstado);
                 PreparedStatement psDevolver = cn.prepareStatement(sqlDevolver);
                 PreparedStatement psAnular = cn.prepareStatement(sqlAnular)) {
                psEstado.setLong(1, id);
                try (ResultSet rs = psEstado.executeQuery()) {
                    if (!rs.next()) {
                        throw new DaoException("No existe una venta con el id " + id);
                    }
                    if (EstadoVenta.valueOf(rs.getString("estado")) != EstadoVenta.PENDIENTE) {
                        throw new DaoException("Solo se puede anular una venta pendiente");
                    }
                }
                psDevolver.setLong(1, id);
                psDevolver.executeUpdate();
                psAnular.setString(1, EstadoVenta.ANULADA.name());
                psAnular.setLong(2, id);
                psAnular.executeUpdate();
                cn.commit();
            } catch (SQLException | DaoException e) {
                cn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo anular la venta " + id, e);
        }
    }

    /** Arma las ventas que devuelve la consulta y les carga sus renglones. */
    private List<Venta> leerVentas(Connection cn, PreparedStatement ps) throws SQLException {
        Map<Long, Venta> ventas = new LinkedHashMap<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta v = mapearVenta(rs);
                ventas.put(v.getId(), v);
            }
        }
        if (!ventas.isEmpty()) {
            cargarDetalles(cn, ventas);
        }
        return new ArrayList<>(ventas.values());
    }

    private void cargarDetalles(Connection cn, Map<Long, Venta> ventas) throws SQLException {
        String marcas = String.join(",", Collections.nCopies(ventas.size(), "?"));
        try (PreparedStatement ps = cn.prepareStatement(SELECT_DETALLES + "WHERE d.id_venta IN (" + marcas + ")")) {
            int i = 1;
            for (Long id : ventas.keySet()) {
                ps.setLong(i++, id);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto(rs.getString("codigo"), rs.getString("nombre"),
                            rs.getInt("stock"), rs.getDouble("precio"));
                    p.setId(rs.getLong("id"));
                    DetalleVenta d = new DetalleVenta(p, rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                    ventas.get(rs.getLong("id_venta")).agregarDetalle(d);
                }
            }
        }
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(rs.getString("nombre_apellido"), rs.getString("direccion"),
                rs.getString("cuit"), rs.getString("telefono"));
        c.setId(rs.getLong("c_id"));
        Venta v = new Venta(c);
        v.setId(rs.getLong("id"));
        v.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        v.setEstado(EstadoVenta.valueOf(rs.getString("estado")));
        return v;
    }
}
