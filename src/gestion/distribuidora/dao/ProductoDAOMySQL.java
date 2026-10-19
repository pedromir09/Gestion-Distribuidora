package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de ProductoDAO usando JDBC y MySQL.
 */
public class ProductoDAOMySQL implements ProductoDAO {

    private final String url = "jdbc:mysql://localhost:3306/distribuidora";
    private final String usuario = "root";
    private final String clave = "clave";

    @Override
    public void insert(Producto producto) throws DaoException {
        String sql = "INSERT INTO productos (codigo, nombre, stock, precio) VALUES (?, ?, ?, ?)";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getStock());
            ps.setDouble(4, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DaoException("Ya existe un producto con el codigo " + producto.getCodigo(), e);
        } catch (SQLException e) {
            throw new DaoException("No se pudo guardar el producto", e);
        }
    }

    @Override
    public Producto findById(String codigo) throws DaoException {
        String sql = "SELECT codigo, nombre, stock, precio FROM productos WHERE codigo = ?";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo buscar el producto con codigo " + codigo, e);
        }
    }

    @Override
    public List<Producto> findAll() throws DaoException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, stock, precio FROM productos ORDER BY nombre";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo listar los productos", e);
        }
        return productos;
    }

    @Override
    public void update(Producto producto) throws DaoException {
        String sql = "UPDATE productos SET nombre = ?, stock = ?, precio = ? WHERE codigo = ?";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getStock());
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getCodigo());
            if (ps.executeUpdate() == 0) {
                throw new DaoException("No existe un producto con el codigo " + producto.getCodigo());
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo actualizar el producto", e);
        }
    }

    @Override
    public void delete(String codigo) throws DaoException {
        String sql = "DELETE FROM productos WHERE codigo = ?";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            if (ps.executeUpdate() == 0) {
                throw new DaoException("No existe un producto con el codigo " + codigo);
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo eliminar el producto con codigo " + codigo, e);
        }
    }

    @Override
    public List<Producto> findByNombre(String nombre) throws DaoException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, stock, precio FROM productos WHERE nombre LIKE ? ORDER BY nombre";
        try (Connection cn = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo buscar productos por nombre", e);
        }
        return productos;
    }

    /** Arma un Producto con la fila actual del ResultSet. */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getInt("stock"),
                rs.getDouble("precio"));
    }
}
