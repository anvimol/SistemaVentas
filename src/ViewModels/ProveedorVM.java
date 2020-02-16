/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.GenerarNumeros;
import Library.Objetos;
import Library.TextFieldEvent;
import Library.UploadImage;
import Models.Clientes;
import Models.Persona;
import Models.Proveedores;
import datechooser.beans.DateChooserCombo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author avice
 */
public class ProveedorVM extends Consult {

    private UploadImage _uploadimage = new UploadImage();
    private String _accion = "insert";
    private ArrayList<JLabel> _label;
    private ArrayList<JTextField> _textfield;
    private JTable _tablaProveedores;
    private JRadioButton _radioActivo, _radioInactivo;
    private DateChooserCombo _dateChooserFecha;
    private DefaultTableModel modelo1;
    private TextFieldEvent evento = new TextFieldEvent();
    private static int idProveedor;
    private Integer totalRegistros;
    private List<Proveedores> proveedorFilter;

    public ProveedorVM() {
    }

    public ProveedorVM(Object[] objects, ArrayList<JTextField> textfield, ArrayList<JLabel> label) {
        _accion = "insert";
        _label = label;
        _textfield = textfield;
        _radioActivo = (JRadioButton) objects[0];
        _radioInactivo = (JRadioButton) objects[1];
        _dateChooserFecha = (DateChooserCombo) objects[2];
        _tablaProveedores = (JTable) objects[3];
        restablecer();
    }

    public void registrarProveedor() {
        if (validarDatos()) {
            int count;
            List<Proveedores> listEmail = proveedores().stream()
                    .filter(u -> u.getEmail().equals(_textfield.get(7).getText()))
                    .collect(Collectors.toList());
            count = listEmail.size();
            List<Proveedores> listTelefono = proveedores().stream()
                    .filter(u -> u.getTelefono().equals(_textfield.get(6).getText()))
                    .collect(Collectors.toList());
            count += listTelefono.size();
            try {
                switch (_accion) {
                    case "insert":
                        if (count == 0) {
                            saveData();
                        } else {
                            if (!listEmail.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(8).requestFocus();
                            }
                            if (!listTelefono.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este teléfono ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(6).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (count == 2) {
                            if (listTelefono.get(0).getId() == idProveedor
                                    && listEmail.get(0).getId() == idProveedor) {
                                saveData();
                            } else {
                                if (listEmail.get(0).getId() != idProveedor) {
                                    JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(8).requestFocus();
                                }
                                if (listTelefono.get(0).getId() != idProveedor) {
                                    JOptionPane.showMessageDialog(null, "Este teléfono ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(6).requestFocus();
                                }
                            }
                        } else {
                            if (count == 0) {
                                saveData();
                            } else {
                                if (!listEmail.isEmpty()) {
                                    if (listEmail.get(0).getId() == idProveedor) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(4).requestFocus();
                                    }
                                }
                                if (!listTelefono.isEmpty()) {
                                    if (listTelefono.get(0).getId() == idProveedor) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este teléfono ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(5).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (HeadlessException e) {
            } catch (SQLException ex) {
                Logger.getLogger(ProveedorVM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveData() throws SQLException {
        try {
            final QueryRunner qr = new QueryRunner(true);
            getConn().setAutoCommit(false);
            byte[] image = UploadImage.getImageByte();
            if (image == null) {
                image = Objetos.uploadImage.getTransFoto(_label.get(0));
            }
            String estado = "";
            if (_radioActivo.isSelected()) {
                estado = "ACTIVO";
            } else {
                estado = "INACTIVO";
            }
            switch (_accion) {

                case "insert":
                    String sqlPersona = "INSERT INTO personas(nombre,apellidos,"
                            + "razonSocial,direccion,dni,cif,telefono,email,imagen)"
                            + " VALUES(?,?,?,?,?,?,?,?,?)";

                    Object[] dataPersona = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        image,};
                    qr.insert(getConn(), sqlPersona, new ColumnListHandler(), dataPersona);

                    String sqlProv = "INSERT INTO proveedores(codProveedor,"
                            + "estado,persona_id) VALUES(?,?,?)";
                    List<Persona> persona = personas();

                    Object[] dataProv = {
                        _textfield.get(8).getText(),
                        estado,
                        persona.get(persona.size() - 1).getId(), // Ultima persona registrada
                    };
                    qr.insert(getConn(), sqlProv, new ColumnListHandler(), dataProv);

                    JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    restablecer();
                    break;

                case "update":
                    Object[] dataPer2 = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        image
                    };
                    String sqlPer2 = "UPDATE personas SET nombre=?, apellidos=?,"
                            + "razonSocial=?,direccion=?,dni=?,cif=?,telefono=?,"
                            + "email=?,imagen=? WHERE id = " + idProveedor;
                    qr.update(getConn(), sqlPer2, dataPer2);
                    
                    Object[] dataCli2 = {
                        estado,
                    };
                    String sqlCli2 = "UPDATE proveedores SET estado=? WHERE persona_id = " + idProveedor;
                    qr.update(getConn(), sqlCli2, dataCli2);
                    break;
            }
            getConn().commit();
            restablecer();
        } catch (Exception ex) {
            getConn().rollback();
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void searchProveedores(String campo) {
        totalRegistros = 0;
        String[] titulos = {"Id", "Nombre", "Apellidos", "Razon social", "Dirección",
            "DNI", "CIF", "Teléfono", "Email", "Estado", "Cod. Proveedor", "Imagen"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            proveedorFilter = proveedores().stream()
                    .collect(Collectors.toList());
        } else {
            proveedorFilter = proveedores().stream()
                    .filter(u -> u.getNombre().startsWith(campo) || u.getApellidos().startsWith(campo)
                    || u.getEmail().startsWith(campo) || u.getRazonSocial().startsWith(campo))
                    .collect(Collectors.toList());
        }
        if (!proveedorFilter.isEmpty()) {
            proveedorFilter.forEach(item -> {
                Object[] registros = {
                    item.getId(),
                    item.getNombre(),
                    item.getApellidos(),
                    item.getRazonSocial(),
                    item.getDireccion(),
                    item.getDni(),
                    item.getCif(),
                    item.getTelefono(),
                    item.getEmail(),
                    item.getEstado(),
                    item.getCodProveedor(),
                    item.getImagen(),};
                totalRegistros = totalRegistros + 1;
                modelo1.addRow(registros);
            });
            _label.get(1).setText(String.valueOf(totalRegistros));
        }
        _tablaProveedores.setModel(modelo1);
    }

    public void getProveedores() {
        String estado;
        _accion = "update";
        int fila = _tablaProveedores.getSelectedRow();
        idProveedor = (Integer) modelo1.getValueAt(fila, 0);
        _textfield.get(0).setText((String) modelo1.getValueAt(fila, 1));
        _textfield.get(1).setText((String) modelo1.getValueAt(fila, 2));
        _textfield.get(2).setText((String) modelo1.getValueAt(fila, 3));
        _textfield.get(3).setText((String) modelo1.getValueAt(fila, 4));
        _textfield.get(4).setText((String) modelo1.getValueAt(fila, 5));
        _textfield.get(5).setText((String) modelo1.getValueAt(fila, 6));
        _textfield.get(6).setText((String) modelo1.getValueAt(fila, 7));
        _textfield.get(7).setText((String) modelo1.getValueAt(fila, 8));
        _textfield.get(8).setText((String) modelo1.getValueAt(fila, 10));

        _textfield.get(8).setEnabled(false);

        estado = (String) modelo1.getValueAt(fila, 9);
        if (estado.equals("ACTIVO")) {
            _radioActivo.setSelected(true);
        } else {
            _radioInactivo.setSelected(true);
        }

        Objetos.uploadImage.byteImage(_label.get(0), (byte[]) modelo1.getValueAt(fila, 11),
                235, 318);
    }

    public void restablecer() {
        _accion = "insert";
        _textfield.get(0).setText("");
        _textfield.get(1).setText("");
        _textfield.get(2).setText("");
        _textfield.get(3).setText("");
        _textfield.get(4).setText("");
        _textfield.get(5).setText("");
        _textfield.get(6).setText("");
        _textfield.get(7).setText("");
        _textfield.get(8).setText("");
        _label.get(0).setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("Resources/usuario.jpg")));
        searchProveedores("");
        numeros();
        crearTabla();
    }

    public void numeros() {
        int j;
        String c = null;
        // String SQL="select count(*) from productos";
        //String SQL = "SELECT MAX(codigo_proveedor) AS cod_cli FROM proveedor";
        //String SQL="SELECT @@identity AS ID";
        List<Proveedores> proveedor = proveedores();
        if (proveedor.isEmpty()) {
            _textfield.get(8).setText("PRO-00000001");
        } else {
            j = proveedor.get(proveedor.size() - 1).getIdProveedor();
            c = proveedor.get(j - 1).getCodProveedor();
        }

        if (c == null) {
            _textfield.get(8).setText("PRO-00000001");
        } else {
            char r1 = c.charAt(4);
            char r2 = c.charAt(5);
            char r3 = c.charAt(6);
            char r4 = c.charAt(7);
            char r5 = c.charAt(8);
            char r6 = c.charAt(9);
            char r7 = c.charAt(10);
            char r8 = c.charAt(11);
            //System.out.print("" + r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8);
            String juntar = "" + r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8;
            int var = Integer.parseInt(juntar);
            //System.out.print("\n este lo que vale numericamente" + var);
            GenerarNumeros gen = new GenerarNumeros();
            gen.generarPro(var);

            _textfield.get(8).setText(gen.serie());

        }
    }

    private boolean validarDatos() {
        if (_textfield.get(0).getText().equals("") && _textfield.get(2).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre o la razón social del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(0).requestFocus();
            return false;
        } else if (!_textfield.get(0).getText().equals("") && _textfield.get(1).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese los apellidos del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(1).requestFocus();
            return false;
        } else if (_textfield.get(3).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la dirección del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(3).requestFocus();
            return false;
        } else if (_textfield.get(4).getText().equals("") && !_textfield.get(0).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el DNI del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(4).requestFocus();
            return false;
        } else if (_textfield.get(0).getText().equals("") && _textfield.get(5).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el CIF del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(5).requestFocus();
            return false;
        } else if (_textfield.get(6).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el teléfono del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(6).requestFocus();
            return false;
        } else if (_textfield.get(7).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el email del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(8).requestFocus();
            return false;
        } else if (!evento.isEmail(_textfield.get(7).getText())) {
            JOptionPane.showMessageDialog(null, "El email no es correcto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(8).requestFocus();
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
                if (column == 0) {
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
        for (int i = 0; i < _tablaProveedores.getColumnCount(); i++) {
            _tablaProveedores.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        _tablaProveedores.setAutoResizeMode(_tablaProveedores.AUTO_RESIZE_OFF);

        // Encabezados
        _tablaProveedores.getTableHeader().setFont(new Font("Cooper Black", 1, 14));
// cambia el fondo del encabezado de la _tablaUsuarios
        _tablaProveedores.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaUsuarios
        _tablaProveedores.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 150, 150, 200, 200, 100, 100, 100, 200, 100, 120, 100, 100};
        for (int i = 0; i < _tablaProveedores.getColumnCount(); i++) {
            _tablaProveedores.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        _tablaProveedores.setRowHeight(30);
        ocultarColumnas();
    }

    private void ocultarColumnas() {
        _tablaProveedores.setRowHeight(30);
        _tablaProveedores.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaProveedores.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tablaProveedores.getColumnModel().getColumn(11).setMaxWidth(0);
        _tablaProveedores.getColumnModel().getColumn(11).setMinWidth(0);
        _tablaProveedores.getColumnModel().getColumn(11).setPreferredWidth(0);
    }
}
