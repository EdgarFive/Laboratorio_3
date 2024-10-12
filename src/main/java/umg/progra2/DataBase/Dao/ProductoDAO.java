package umg.progra2.DataBase.Dao;

import umg.progra2.DataBase.Conexion.DataBaseConnection;
import umg.progra2.DataBase.Model.ProductoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {


    public void actualizarProducto(int idProducto, String descripcion, String origen, double precio, int existencia) throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "UPDATE tb_producto SET descripcion = ?, origen = ?, precio = ?, existencias = ? WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);
        ps.setDouble(3, precio); // Usando setDouble para el precio
        ps.setInt(4, existencia);
        ps.setInt(5, idProducto);

        ps.executeUpdate();
        connection.close();
    }

    public void eliminarProducto(int idProducto) throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "DELETE FROM tb_producto WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ps.executeUpdate();
        connection.close();
    }

    public List<ProductoModel> obtenerTodosLosProductos() throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto ORDER BY origen";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ProductoModel> productos = new ArrayList<>();

        while (rs.next()) {
            ProductoModel producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setExistencia(rs.getInt("existencias"));
            productos.add(producto);
        }

        connection.close();
        return productos;
    }

    public void agregarProducto(String descripcion, String origen, double precio, int existencias) throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "INSERT INTO tb_producto (descripcion, origen, precio, existencias) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);
        ps.setDouble(3, precio);
        ps.setInt(4, existencias);

        ps.executeUpdate();
        connection.close();
    }

    public ProductoModel obtenerProductoPorId(int idProducto) throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ResultSet rs = ps.executeQuery();
        ProductoModel producto = null;
        if (rs.next()) {
            producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio")); // Usando getDouble para el precio
            producto.setExistencia(rs.getInt("existencias"));
        }
        connection.close();
        return producto;
    }

    public List<ProductoModel> obtenerTodosLosProductosID() throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto ORDER BY id";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ProductoModel> productos = new ArrayList<>();

        while (rs.next()) {
            ProductoModel producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setExistencia(rs.getInt("existencias"));
            productos.add(producto);
        }

        connection.close();
        return productos;
    }

    public List<ProductoModel> obtenerGenericos(String condicion) throws Exception {
        Connection connection = DataBaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto WHERE " + condicion;

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ProductoModel> productos = new ArrayList<>();

        while (rs.next()) {
            ProductoModel producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setExistencia(rs.getInt("existencias"));
            productos.add(producto);
        }

        connection.close();
        return productos;
    }


}
