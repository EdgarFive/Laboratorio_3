package umg.progra2.DataBase.Model;

public class ProductoModel {

    private int id;
    private String descripcion;
    private String origen;
    private double precio;
    private int existencias;

    public ProductoModel(int idProducto, String descripcion, String origen, double precio, int existencia) {
        this.id = idProducto;
        this.descripcion = descripcion;
        this.origen = origen;
        this.precio = precio;
        this.existencias = existencia;
    }

    public ProductoModel() {}


    public int getIdProducto() {
        return id;
    }

    public void setIdProducto(int idProducto) {
        this.id = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public double getPrecio() {  // Cambiado a double
        return precio;
    }

    public void setPrecio(double precio) {  // Cambiado a double
        this.precio = precio;
    }

    public int getExistencia() {
        return existencias;
    }

    public void setExistencia(int existencia) {
        this.existencias = existencia;
    }

}
