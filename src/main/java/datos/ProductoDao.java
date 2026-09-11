package datos;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDao {

    public boolean crear(Producto prod) {
        if (prod.getNombre() == null || prod.getNombre().trim().isEmpty() ||
            prod.getCategoria() == null || prod.getCategoria().trim().isEmpty() ||
            prod.getPrecio() <= 0 || prod.getStock() < 0) {
            System.err.println("Validación fallida: Verifique que los campos no estén vacíos, precio > 0 y stock >= 0.");
            return false;
        }

        String queryInsert = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        
        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = Conexion.obtenerConexion();
            ps = conexion.prepareStatement(queryInsert);
            
            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getCategoria());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            System.err.println("Error al insertar el producto: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();
        String querySelect = "SELECT id, nombre, categoria, precio, stock FROM productos";

        try (Connection conexion = Conexion.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(querySelect)) {

            while (rs.next()) {
                Producto item = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                resultado.add(item);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar la lista de productos: " + ex.getMessage());
        }
        return resultado;
    }

    public Optional<Producto> buscarPorId(int identificador) {
        String queryId = "SELECT id, nombre, categoria, precio, stock FROM productos WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(queryId)) {

            ps.setInt(1, identificador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto encontrado = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                    );
                    return Optional.of(encontrado);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar el producto por ID: " + ex.getMessage());
        }
        return Optional.empty();
    }

    public boolean actualizar(Producto prod) {
        if (prod.getPrecio() <= 0 || prod.getStock() < 0) {
            System.err.println("Validación fallida: El precio debe superar 0 y el stock no puede ser menor a 0.");
            return false;
        }

        String queryUpdate = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(queryUpdate)) {

            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getCategoria());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar el registro: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int identificador) {
        String queryDelete = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(queryDelete)) {

            ps.setInt(1, identificador);
            int filasModificadas = ps.executeUpdate();
            
            if (filasModificadas == 0) {
                System.out.println("No existe un producto registrado con ese identificador.");
                return false;
            }
            return true;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar el producto: " + ex.getMessage());
            return false;
        }
    }
}