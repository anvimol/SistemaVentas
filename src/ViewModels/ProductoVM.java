/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.FormatDecimal;
import Library.Objetos;
import Library.PaintLabel;
import Library.UploadImage;
import Models.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Interleaved25;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author avice
 */
public class ProductoVM extends Consult {

    private String _accion = "insert";
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textfield;
    private final ArrayList<JTextArea> _textarea;
    private final JRadioButton _radioCodigo, _radioNombre, _radioCategoria;
    private final JRadioButton _radioActivo, _radioInactivo;
    private final JTable _tablaProductos;
    private final JComboBox _comboCategoria, _comboDepartamentos;
    private Integer idProducto, totalRegistros;
    private DefaultTableModel modelo1;
    private DefaultComboBoxModel modelCombo;
    public JBarcodeBean barcode = new JBarcodeBean();
    public List<Departamentos> departamentos = new ArrayList<>();
    private List<Categorias> categorias = new ArrayList<>();
    private List<Productos> productoFilter;
    public FormatDecimal formato = new FormatDecimal();
    private Departamentos dpt = null;
    private int idDpto;
    private Categorias cat = null;

    public ProductoVM(Object[] objects, ArrayList<JLabel> label,
            ArrayList<JTextField> textfield, ArrayList<JTextArea> textarea) {
        _accion = "insert";
        _label = label;
        _textfield = textfield;
        _textarea = textarea;
        _radioCodigo = (JRadioButton) objects[0];
        _radioNombre = (JRadioButton) objects[1];
        _radioCategoria = (JRadioButton) objects[2];
        _tablaProductos = (JTable) objects[3];
        _comboDepartamentos = (JComboBox) objects[4];
        _comboCategoria = (JComboBox) objects[5];
        _radioActivo = (JRadioButton) objects[6];
        _radioInactivo = (JRadioButton) objects[7];
        restablecer();
    }

    public void registrarProducto() {
        if (validarDatos()) {
            int count;
            List<Productos> listCodigo = productos().stream()
                    .filter(u -> u.getCodigo().equals(_textfield.get(1).getText()))
                    .collect(Collectors.toList());
            count = listCodigo.size();
            List<Productos> listProducto = productos().stream()
                    .filter(u -> u.getNombre().equals(_textfield.get(2).getText()))
                    .collect(Collectors.toList());
            count += listProducto.size();

            try {
                switch (_accion) {
                    case "insert":
                        if (count == 0) {
                            saveData();
                        } else {
                            if (!listCodigo.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este código ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(1).requestFocus();
                            }
                            if (!listProducto.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este producto ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(1).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (count == 2) {
                            if (listCodigo.get(0).getId() == idProducto
                                    && listProducto.get(0).getId() == idProducto) {
                                saveData();
                            } else {
                                if (listCodigo.get(0).getId() != idProducto) {
                                    JOptionPane.showMessageDialog(null, "Este código ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(0).requestFocus();
                                }
                                if (listProducto.get(0).getId() != idProducto) {
                                    JOptionPane.showMessageDialog(null, "Este producto ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(1).requestFocus();
                                }
                            }
                        } else {
                            if (count == 0) {
                                saveData();
                            } else {
                                if (!listCodigo.isEmpty()) {
                                    if (listCodigo.get(0).getId() == idProducto) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este código ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(0).requestFocus();
                                    }
                                }
                                if (!listProducto.isEmpty()) {
                                    if (listProducto.get(0).getId() == idProducto) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este producto ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(1).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (Exception e) {
            }
        }
    }

    private void saveData() {
        dpt = (Departamentos) _comboDepartamentos.getSelectedItem();
        String departa = dpt.getDepartamento();
        cat = (Categorias) _comboCategoria.getSelectedItem();
        String categ = cat.getCategoria();
        String estado = "";
        if (_radioActivo.isSelected()) {
            estado = "ACTIVO";
        } else {
            estado = "INACTIVO";
        }
        try {
            final QueryRunner qr = new QueryRunner(true);
            // getConn().setAutoCommit(false);
            byte[] image = UploadImage.getImageByte();
            if (image == null) {
                image = Objetos.uploadImage.getTransFoto(_label.get(1));
            }
            switch (_accion) {

                case "insert":
                    String sqlProd = "INSERT INTO productos(codigo,nombre,descripcion,"
                            + "stock,stockMin,precioCosto,precioVenta,utilidad,"
                            + "estado,departamento,categoria,imagen)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

                    Object[] dataProd = {
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textarea.get(0).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        estado,
                        departa,
                        categ,
                        image
                    };
                    qr.insert(getConn(), sqlProd, new ColumnListHandler(), dataProd);
                    JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    restablecer();
                    break;

                case "update":
                    Object[] dataProd2 = {
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textarea.get(0).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        estado,
                        departa,
                        categ,
                        image
                    };
                    String sqlProd2 = "UPDATE productos SET codigo=?, nombre=?,"
                            + "descripcion=?, stock=?, stockMin=?, precioCosto=?,"
                            + "precioVenta=?, utilidad=?, estado=?, departamento=?,"
                            + "categoria=?, imagen=? WHERE id = " + idProducto;
                    qr.update(getConn(), sqlProd2, dataProd2);
                    break;
            }
            restablecer();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void searchProducto(String campo) {
        String[] titulos = {"Id", "Código", "Nombre", "Descripcion", "Stock", "StockMin",
            "Precio costo", "Precio venta", "Utilidad", "Estado", "Departamento",
            "Categoria", "Imagen"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            productoFilter = productos().stream()
                    .collect(Collectors.toList());
        } else {
            buscarPorCampo(campo);
//            productoFilter = productos().stream()
//                    .filter(C -> C.getCodigo().startsWith(campo) || C.getNombre().startsWith(campo)
//                    || C.getDepartamento().startsWith(campo) || C.getCategoria().startsWith(campo))
//                    .collect(Collectors.toList());
        }
        if (!productoFilter.isEmpty()) {
            productoFilter.forEach(item -> {
                Object[] registros = {
                    item.getId(),
                    item.getCodigo(),
                    item.getNombre(),
                    item.getDescripcion(),
                    item.getStock(),
                    item.getStockMin(),
                    item.getPrecioCosto(),
                    item.getPrecioVenta(),
                    item.getUtilidad(),
                    item.getEstado(),
                    item.getDepartamento(),
                    item.getCategoria(),
                    item.getImagen()
                };
                modelo1.addRow(registros);
            });
        }
        _tablaProductos.setModel(modelo1);
//        _tablaProductos.setRowHeight(30);
//        _tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
//        _tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
//        _tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
//        _tablaProductos.getColumnModel().getColumn(12).setMaxWidth(0);
//        _tablaProductos.getColumnModel().getColumn(12).setMinWidth(0);
//        _tablaProductos.getColumnModel().getColumn(12).setPreferredWidth(0);
        //_tablaProductos.getColumnModel().getColumn(7).setCellRenderer(new Render_CheckBox());
    }

    private void buscarPorCampo(String campo) {
        if (_radioCodigo.isSelected()) {
            productoFilter = productos().stream()
                    .filter(C -> C.getCodigo().startsWith(campo))
                    .collect(Collectors.toList());
        } else if (_radioNombre.isSelected()) {
            productoFilter = productos().stream()
                    .filter(C -> C.getNombre().startsWith(campo))
                    .collect(Collectors.toList());
        } else if (_radioCategoria.isSelected()) {
            productoFilter = productos().stream()
                    .filter(C -> C.getCategoria().startsWith(campo))
                    .collect(Collectors.toList());
        }
    }

    public void getProducto() {
        String departamento, categoria, estado;
        _accion = "update";
        int fila = _tablaProductos.getSelectedRow();
        idProducto = (Integer) modelo1.getValueAt(fila, 0);
        _textfield.get(1).setText((String) modelo1.getValueAt(fila, 1));
        _textfield.get(2).setText((String) modelo1.getValueAt(fila, 2));
        _textarea.get(0).setText((String) modelo1.getValueAt(fila, 3));
        _textfield.get(3).setText((String) modelo1.getValueAt(fila, 4));
        _textfield.get(4).setText((String) modelo1.getValueAt(fila, 5));
        _textfield.get(5).setText((String) modelo1.getValueAt(fila, 6));
        _textfield.get(6).setText((String) modelo1.getValueAt(fila, 7));
        _textfield.get(7).setText((String) modelo1.getValueAt(fila, 8));

        estado = (String) modelo1.getValueAt(fila, 9);
        if (estado.equals("ACTIVO")) {
            _radioActivo.setSelected(true);
        } else {
            _radioInactivo.setSelected(true);
        }
        departamento = (String) modelo1.getValueAt(fila, 10);
        categoria = (String) modelo1.getValueAt(fila, 11);

        dpt = getDepartamento(_comboDepartamentos, departamento);
        _comboDepartamentos.setSelectedItem(dpt);
        cat = getCategorias(_comboDepartamentos, _comboCategoria,
                dpt.getIdDpto(), categoria);
        _comboCategoria.setSelectedItem(cat);

        Objetos.uploadImage.byteImage(_label.get(1), (byte[]) modelo1.getValueAt(fila, 12),
                260, 300);
        codigo(_label.get(0), _textfield.get(1).getText());
    }

    public Departamentos getDepartamento(JComboBox combo, String departa) {
        modelCombo = new DefaultComboBoxModel();
        departamentos = departamentos();
        if (0 < departamentos.size()) {
            departamentos.forEach(item -> {
                modelCombo.addElement(item);
                if (departa.equals(item.getDepartamento())) {
                    dpt = item;
                }
            });
            combo.setModel(modelCombo);
            modelCombo = new DefaultComboBoxModel();
        }
        return dpt;
    }

    public Categorias getCategorias(JComboBox combo1, JComboBox combo2,
            int idDptos, String categori) {
        modelCombo = new DefaultComboBoxModel();
        if (idDptos == 0) {
            Departamentos dpto = (Departamentos) combo1.getSelectedItem();
            idDpto = dpto.getIdDpto();
        } else {
            idDpto = idDptos;
        }
        categorias = categorias();
        if (0 < categorias.size()) {
            List<Categorias> categoria = categorias().stream()
                    .filter(c -> c.getDpto_id() == idDpto)
                    .collect(Collectors.toList());
            categoria.forEach(item -> {
                modelCombo.addElement(item);
                if (categori.equals(item.getCategoria())) {
                    cat = item;
                }
            });
            combo2.setModel(modelCombo);
        }
        return cat;
    }

    public final void restablecer() {
        _accion = "insert";
        _textfield.get(0).setText("");
        _textfield.get(1).setText("");
        _textfield.get(2).setText("");
        _textfield.get(3).setText("");
        _textfield.get(4).setText("");
        _textfield.get(5).setText("");
        _textfield.get(6).setText("");
        _textfield.get(7).setText("");
        _textarea.get(0).setText("");
        getDepartamento(_comboDepartamentos, "");
        dpt = (Departamentos) _comboDepartamentos.getSelectedItem();
        getCategorias(_comboDepartamentos, _comboCategoria,
                dpt.getIdDpto(), "");
        codigo(_label.get(0), "0000000000000");
        _label.get(1).setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("Resources/product.png")));
        searchProducto("");
        crearTabla();
    }

    private boolean validarDatos() {
        if (_textfield.get(1).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el código de barras del producto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(1).requestFocus();
            return false;
        } else if (_textfield.get(2).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del producto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(2).requestFocus();
            return false;
        }
        return true;
    }

    public void crearTabla() {
        //--------------------PRESENTACION DE JTABLE----------------------

        TableCellRenderer render = new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //Determinar Alineaciones   
                if (column == 4 || column == 5 || column == 6 || column == 7 || column == 8) {
                    l.setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    l.setHorizontalAlignment(SwingConstants.LEFT);
                }

                //Colores en Jtable        
                if (isSelected) {
                    //l.setBackground(new Color(203, 159, 41));
                    //l.setBackground(new Color(168, 198, 238));
                    l.setBackground(new Color(153, 204, 255));
                    l.setForeground(Color.WHITE);
                } else {
                    l.setForeground(Color.BLACK);
                    if (row % 2 == 0) {
                        l.setBackground(Color.WHITE);
                    } else {
                        l.setBackground(new Color(232, 232, 232));
                        //l.setBackground(new Color(254, 227, 152));
                    }
                }
                return l;
            }
        };
        //Agregar Render
        for (int i = 0; i < _tablaProductos.getColumnCount(); i++) {
            _tablaProductos.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        _tablaProductos.setAutoResizeMode(_tablaProductos.AUTO_RESIZE_OFF);

        // Encabezados
        _tablaProductos.getTableHeader().setFont(new Font("Cooper Black", 1, 14));
// cambia el fondo del encabezado de la _tablaProductos
        _tablaProductos.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaProductos
        _tablaProductos.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 135, 200, 200, 100, 100, 100, 100, 100, 100, 150, 150, 100};
        for (int i = 0; i < _tablaProductos.getColumnCount(); i++) {
            _tablaProductos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        _tablaProductos.setRowHeight(30);
        ocultarColumnas();
    }

    private void ocultarColumnas() {
        _tablaProductos.setRowHeight(30);
        _tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tablaProductos.getColumnModel().getColumn(12).setMaxWidth(0);
        _tablaProductos.getColumnModel().getColumn(12).setMinWidth(0);
        _tablaProductos.getColumnModel().getColumn(12).setPreferredWidth(0);
    }

    public void codigo(JLabel label, String codeBarra) {
        // muestro que tipo de código de barra
        barcode.setCodeType(new Interleaved25());
        barcode.setCode(codeBarra);
        barcode.setCheckDigit(true);
        BufferedImage bufferedImage = barcode.draw(new BufferedImage(250, 110,//160, 80, 
                BufferedImage.TYPE_INT_RGB));
        PaintLabel p = new PaintLabel(bufferedImage);
        label.removeAll();
        label.add(p);
        label.repaint();
    }

    public static void codeBarras(String codigo, String titulo) throws DocumentException {
        try {
            Document doc = new Document();
            PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(titulo));
            doc.open();

            // COD 39
//            Barcode39 code = new Barcode39();
//            code.setCode(codigo);
//            com.itextpdf.text.Image img = code.createImageWithBarcode(pdf.getDirectContent(),
//                    BaseColor.BLACK, BaseColor.BLACK);
//            doc.add(img);
//            doc.add(new Paragraph(" "));
//            
//            // COD 128
            Barcode128 code128 = new Barcode128();
            code128.setCode(codigo);
            com.itextpdf.text.Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(),
                    BaseColor.BLACK, BaseColor.BLACK);
            // Tamaño
            img128.scalePercent(200);
            doc.add(img128);

            doc.close();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex);
        }
    }
}
