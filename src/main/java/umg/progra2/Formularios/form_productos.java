package umg.progra2.Formularios;

import umg.progra2.DataBase.Model.ProductoModel;
import umg.progra2.DataBase.Service.ProductoService;
import umg.progra2.Reportes.PdfReporte;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class form_productos {
    private JPanel form_producto;
    private JLabel lbl_titulo;
    private JLabel lb_id;
    private JTextField textField1_id_producto;
    private JLabel lb_nombre;
    private JTextField textField1_nombre;
    private JLabel lb_origen;
    private JComboBox comboBox1_origen;
    private JLabel lb_precio;
    private JLabel lb_existencia;
    private JTextField textField1_precio;
    private JTextField textField1_existencia;
    private JButton button1_crear;
    private JButton Button1_buscar;
    private JButton button1_actualizar;
    private JButton button1_eliminar;
    private JLabel lb_reportes;
    private JComboBox comboBox1_reportes;
    private JButton button1_generar_reporte;
    private JLabel lb_reportess;
    private JCheckBox checkBox1_grupos;

    private ProductoService productoService;


    public static void main(String[] args) {

        JFrame frame = new JFrame("form_productos");
        frame.setContentPane(new form_productos().form_producto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void limpiar() {
        textField1_id_producto.setText("");
        textField1_nombre.setText("");
        textField1_existencia.setText("");
        textField1_precio.setText("");
        comboBox1_origen.setSelectedIndex(0);
        comboBox1_reportes.setSelectedIndex(0);
    }


    public form_productos() {

        productoService = new ProductoService();

        //Boton para actualizar un producto =============================================================================
        button1_actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int idProducto = Integer.parseInt(textField1_id_producto.getText());
                    String descripcion = textField1_nombre.getText();
                    String origen = comboBox1_origen.getSelectedItem().toString();
                    int existencia = Integer.parseInt(textField1_existencia.getText());
                    double precio = Double.parseDouble(textField1_precio.getText());

                    ProductoModel producto = new ProductoModel();
                    producto.setIdProducto(idProducto);
                    producto.setDescripcion(descripcion);
                    producto.setOrigen(origen);
                    producto.setExistencia(existencia);
                    producto.setPrecio(precio);

                    productoService.actualizarProducto(producto.getIdProducto(), producto.getDescripcion(), producto.getOrigen(), producto.getPrecio(), producto.getExistencia());
                    JOptionPane.showMessageDialog(null, "El producto se actualizó exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error, producto no actualizado: " + ex.getMessage());
                }
            }
        });

        //Boton para eliminar un producto =============================================================================
        button1_eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idProducto = Integer.parseInt(textField1_id_producto.getText());
                    ProductoModel producto = productoService.obtenerProductoPorId(idProducto);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que deseas eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (producto.getPrecio() == 0) {
                            productoService.eliminarProducto(idProducto);
                            JOptionPane.showMessageDialog(null, "El producto se eliminó exitosamente");
                            limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: El producto no se pued eliminar a menos que su precio sea 0.00");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
                }
            }
        });

        //Boton para crear un producto =============================================================================
        button1_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Guardar producto
                String descripcion = textField1_nombre.getText();
                String origen = comboBox1_origen.getSelectedItem().toString();
                int existencias = Integer.parseInt(textField1_existencia.getText());
                double precio = Double.parseDouble(textField1_precio.getText());

                ProductoModel producto = new ProductoModel();
                producto.setDescripcion(descripcion);
                producto.setOrigen(origen);
                producto.setExistencia(existencias);
                producto.setPrecio(precio);

                try {
                    productoService.agregarProducto(producto.getDescripcion(), producto.getOrigen(), producto.getPrecio(), producto.getExistencia());
                    JOptionPane.showMessageDialog(null, "El producto se guardo de manera exitosa");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage());
                }

            }
        });

        //Boton para buscar un producto =============================================================================
        Button1_buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Buscar producto por ID
                try {
                    int idProducto = Integer.parseInt(textField1_id_producto.getText());
                    ProductoModel eeproducto = productoService.obtenerProductoPorId(idProducto);

                    if (eeproducto != null) {
                        textField1_nombre.setText(eeproducto.getDescripcion());
                        comboBox1_origen.setSelectedItem(eeproducto.getOrigen());
                        textField1_existencia.setText(String.valueOf(eeproducto.getExistencia()));
                        textField1_precio.setText(String.valueOf(eeproducto.getPrecio()));
                    } else {
                        JOptionPane.showMessageDialog(null, "El producto no se encontró");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + ex.getMessage());
                }

            }
        });

        //Boton para generar un reporte =============================================================================
        button1_generar_reporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean eeagrupados = checkBox1_grupos.isSelected();
                    int reporteSeleccionado = comboBox1_reportes.getSelectedIndex();
                    List<ProductoModel> eeproductos;

                    if (eeagrupados && reporteSeleccionado != 0) {
                        JOptionPane.showMessageDialog(null, "La función de Grupos funciona solamente con 'Reporte General'.");
                        return;
                    }

                    switch (reporteSeleccionado) {
                        case 0:

                            if (eeagrupados) {
                                eeproductos = productoService.obtenerTodosLosProductos();
                            } else {
                                eeproductos = productoService.obtenerTodosLosProductosID();
                            }
                            break;
                        case 1:

                            eeproductos = productoService.obtenerGenericos("existencias < 20");

                            break;
                        case 2:

                            String eepais = comboBox1_origen.getSelectedItem().toString();
                            eeproductos = productoService.obtenerGenericos("origen = '" + eepais + "'");


                            break;
                        case 3:

                            eeproductos = productoService.obtenerGenericos("precio > 2000");

                            break;
                        case 4:

                            eeproductos = productoService.obtenerGenericos("1=1 ORDER BY precio DESC");

                            break;
                        case 5:

                            eeproductos = productoService.obtenerGenericos("1=1 ORDER BY precio ASC");

                            break;
                        default:
                            eeproductos = null;
                    }

                    // Generar el reporte si hay productos
                    if (eeproductos != null) {

                        new PdfReporte().generateProductReport(eeproductos, "C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados\\reporte.pdf", eeagrupados);
                        JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Users\\GMG\\Desktop\\PROGRAMACIÓN (Programas)\\0. JAVA-AA\\0. PDF Guardados\\reporte.pdf");
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay productos para generar el reporte.");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + exception.getMessage());
                }
            }
        });



        //Combo box de origen =============================================================
        String[] origenop = { "Colombia", "Italia", "Japón", "Suiza", "México", "España", "Estados Unidos", "China", "Alemania", "Escocia", "Corea del Sur", "Vietnam", "Argentina", "Noruega", "Suecia", "Francia", "Irlanda",};

        for (String opcion : origenop) {
            comboBox1_origen.addItem(opcion);
        }

        //Combo box de reportes =============================================================
        String[] opcionesReportes = {"Reporte General", "Existencias menores a 20 unidades", "País específico ", "Precios mayores a 2000", "Precio mayor a menor", "Precio menor a mayor"};

        for (String opcion : opcionesReportes) {
            comboBox1_reportes.addItem(opcion);
        }
    }


}
