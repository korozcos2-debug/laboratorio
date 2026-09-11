package datos;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDao {

    public boolean crear(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty() ||
            p.getCategoria() == null || p.getCategoria().trim().isEmpty() ||
            p.getPrecio() <= 0 || p.getStock() < 0) {
            System.err.println("Error de validación: Revise que el nombre/categoría no estén vacíos, precio > 0 y stock >= 0.");
            return false;
        }

        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getCategoria());
            pstmt.setDouble(3, p.getPrecio());
            pstmt.setInt(4, p.getStock());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("No se pudo crear el producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, categoria, precio, stock FROM productos";

        try (Connection conn = Conexion.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }

    public Optional<Producto> buscarPorId(int id) {
        String sql = "SELECT id, nombre, categoria, precio, stock FROM productos WHERE id = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean actualizar(Producto p) {
        if (p.getPrecio() <= 0 || p.getStock() < 0) {
            System.err.println("Error de validación: El precio debe ser mayor a 0 y el stock no puede ser negativo.");
            return false;
        }

        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getCategoria());
            pstmt.setDouble(3, p.getPrecio());
            pstmt.setInt(4, p.getStock());
            pstmt.setInt(5, p.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("No se pudo actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                System.out.println("No existe un producto con ese id");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("No se pudo eliminar el producto: " + e.getMessage());
            return false;
        }
    }
}