package gestion.distribuidora.dao;

import gestion.distribuidora.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de ClienteDAO usando JDBC y MySQL.
 */
public class ClienteDAOMySQL implements ClienteDAO {

    private static final String COLUMNAS = "id, nombre_apellido, direccion, cuit, telefono";

    /** Crea el DAO; cada operacion abre su propia conexion con {@link ConexionBD}. */
    public ClienteDAOMySQL() {
    }

    @Override
    public void insert(Cliente cliente) throws DaoException {
        String sql = "INSERT INTO clientes (nombre_apellido, direccion, cuit, telefono) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombreApellido());
            ps.setString(2, cliente.getDireccion());
            ps.setString(3, cliente.getCuit());
            ps.setString(4, cliente.getTelefono());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cliente.setId(keys.getLong(1));
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DaoException("Ya existe un cliente con el CUIT " + cliente.getCuit(), e);
        } catch (SQLException e) {
            throw new DaoException("No se pudo guardar el cliente", e);
        }
    }

    @Override
    public Cliente findById(Long id) throws DaoException {
        String sql = "SELECT " + COLUMNAS + " FROM clientes WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo buscar el cliente con id " + id, e);
        }
    }

    @Override
    public List<Cliente> findAll() throws DaoException {
        String sql = "SELECT " + COLUMNAS + " FROM clientes ORDER BY nombre_apellido";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo listar los clientes", e);
        }
        return clientes;
    }

    @Override
    public void update(Cliente cliente) throws DaoException {
        String sql = "UPDATE clientes SET nombre_apellido = ?, direccion = ?, cuit = ?, telefono = ? WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombreApellido());
            ps.setString(2, cliente.getDireccion());
            ps.setString(3, cliente.getCuit());
            ps.setString(4, cliente.getTelefono());
            ps.setLong(5, cliente.getId());
            if (ps.executeUpdate() == 0) {
                throw new DaoException("No existe un cliente con el id " + cliente.getId());
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DaoException("Ya existe un cliente con el CUIT " + cliente.getCuit(), e);
        } catch (SQLException e) {
            throw new DaoException("No se pudo actualizar el cliente", e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DaoException("No existe un cliente con el id " + id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DaoException("No se puede eliminar el cliente porque tiene ventas registradas", e);
        } catch (SQLException e) {
            throw new DaoException("No se pudo eliminar el cliente con id " + id, e);
        }
    }

    @Override
    public List<Cliente> findByNombre(String texto) throws DaoException {
        String sql = "SELECT " + COLUMNAS + " FROM clientes WHERE nombre_apellido LIKE ? ORDER BY nombre_apellido";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("No se pudo buscar clientes por nombre", e);
        }
        return clientes;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(
                rs.getString("nombre_apellido"),
                rs.getString("direccion"),
                rs.getString("cuit"),
                rs.getString("telefono"));
        c.setId(rs.getLong("id"));
        return c;
    }
}
