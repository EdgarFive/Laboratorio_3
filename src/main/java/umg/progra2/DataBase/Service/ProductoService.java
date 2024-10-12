package umg.progra2.DataBase.Service;

import umg.progra2.DataBase.Dao.ProductoDAO;
import umg.progra2.DataBase.Model.ProductoModel;

import java.util.List;

public class ProductoService {

    private ProductoDAO productoDao;

    public ProductoService() {
        productoDao = new ProductoDAO();
    }

    public void actualizarProducto(int idProducto, String descripcion, String origen, double precio, int existencias) throws Exception {
        productoDao.actualizarProducto(idProducto, descripcion, origen, precio, existencias);
    }

    public void eliminarProducto(int idProducto) throws Exception {
        productoDao.eliminarProducto(idProducto);
    }

    public List<ProductoModel> obtenerTodosLosProductos() throws Exception {
        return productoDao.obtenerTodosLosProductos();
    }

    public void agregarProducto(String descripcion, String origen, double precio, int existencia) throws Exception {
        productoDao.agregarProducto(descripcion, origen, precio, existencia);
    }

    public ProductoModel obtenerProductoPorId(int idProducto) throws Exception {
        return productoDao.obtenerProductoPorId(idProducto);
    }

    public List<ProductoModel> obtenerGenericos(String condicion) throws Exception {
        return productoDao.obtenerGenericos(condicion);
    }

    public List<ProductoModel> obtenerTodosLosProductosID() throws Exception {
        return productoDao.obtenerTodosLosProductosID();
    }

}
