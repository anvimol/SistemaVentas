/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.Encriptar;
import Library.GenerarNumeros;
import Library.Objetos;
import Library.TextFieldEvent;
import Library.UploadImage;
import Models.Clientes;
import datechooser.beans.DateChooserCombo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
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
public class ClienteVM extends Consult {

    private UploadImage _uploadimage = new UploadImage();
    private String _accion = "insert";
    private ArrayList<JLabel> _label;
    private ArrayList<JTextField> _textfield;
    private JTable _tablaClientes;
    private JRadioButton _radioActivo, _radioInactivo;
    private DateChooserCombo _dateChooserFecha;
    private DefaultTableModel modelo1;
    private TextFieldEvent evento = new TextFieldEvent();
    private static int idCliente;
    private Integer totalRegistros;
    private List<Clientes> clienteFilter;

    public ClienteVM() {
    }

    public ClienteVM(Object[] objects, ArrayList<JTextField> textfield, ArrayList<JLabel> label) {
        _accion = "insert";
        _label = label;
        _textfield = textfield;
        _radioActivo = (JRadioButton) objects[0];
        _radioInactivo = (JRadioButton) objects[1];
        _dateChooserFecha = (DateChooserCombo) objects[2];
        _tablaClientes = (JTable) objects[3];
        restablecer();
    }

    public void registrarClientes() {
        if (validarDatos()) {
            int count;
            List<Clientes> listEmail = clientes().stream()
                    .filter(u -> u.getEmail().equals(_textfield.get(8).getText()))
                    .collect(Collectors.toList());
            count = listEmail.size();
            List<Clientes> listTelefono = clientes().stream()
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
                        if (count == 3) {
                            if (listTelefono.get(0).getIdCliente() == idCliente
                                    && listEmail.get(0).getIdCliente() == idCliente) {
                                saveData();
                            } else {
                                if (listEmail.get(0).getIdCliente() != idCliente) {
                                    JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(8).requestFocus();
                                }
                                if (listTelefono.get(0).getIdCliente() != idCliente) {
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
                                    if (listEmail.get(0).getIdCliente() == idCliente) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(4).requestFocus();
                                    }
                                }
                                if (!listTelefono.isEmpty()) {
                                    if (listTelefono.get(0).getIdCliente() == idCliente) {
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
            }
        }
    }

    private void saveData() {
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
                image = Objetos.uploadImage.getTransFoto(_label.get(0));
            }
            switch (_accion) {

                case "insert":
                    String sqlCliente = "INSERT INTO clientes(nombre,apellidos,"
                            + "razonSocial,direccion,dni,cif,telefono,email,"
                            + "estado,codCliente,fecha,imagen)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

                    Object[] dataCliente = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        estado,
                        _textfield.get(8).getText(),
                        null,//new Calendario().getFecha(),
                        image,};
                    qr.insert(getConn(), sqlCliente, new ColumnListHandler(), dataCliente);
                    JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    restablecer();
                    break;

                case "update":
                    Object[] dataCli2 = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        _textfield.get(6).getText(),
                        _textfield.get(7).getText(),
                        estado,
                        _textfield.get(8).getText(),
                        null, //new Calendario().getFecha()
                        image
                    };
                    String sqlCli2 = "UPDATE clientes SET nombre=?, apellidos=?,"
                            + "razonSocial=?,direccion=?,dni=?,cif=?,telefono=?,"
                            + "email=?,estado=?,codCliente=?,fecha=?,imagen=?"
                            + " WHERE idCliente = " + idCliente;
                    qr.update(getConn(), sqlCli2, dataCli2);
                    break;
            }
            restablecer();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void searchClientes(String campo) {
        totalRegistros = 0;
        String[] titulos = {"Id", "Nombre", "Apellidos", "Razon social", "Dirección",
            "DNI", "CIF", "Teléfono", "Email", "Estado", "Cod. Cliente", "Fecha", "Imagen"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            clienteFilter = clientes().stream()
                    .collect(Collectors.toList());
        } else {
            clienteFilter = clientes().stream()
                    .filter(u -> u.getNombre().startsWith(campo) || u.getApellidos().startsWith(campo)
                    || u.getEmail().startsWith(campo) || u.getRazonSocial().startsWith(campo))
                    .collect(Collectors.toList());
        }
        if (!clienteFilter.isEmpty()) {
            clienteFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdCliente(),
                    item.getNombre(),
                    item.getApellidos(),
                    item.getRazonSocial(),
                    item.getDireccion(),
                    item.getDni(),
                    item.getCif(),
                    item.getTelefono(),
                    item.getEmail(),
                    item.getEstado(),
                    item.getCodCliente(),
                    item.getFecha(),
                    item.getImagen(),};
                totalRegistros = totalRegistros + 1;
                modelo1.addRow(registros);
            });
            _label.get(1).setText(String.valueOf(totalRegistros));
        }
        _tablaClientes.setModel(modelo1);
    }

    public void getClientes() {
        String estado;
        _accion = "update";
        int fila = _tablaClientes.getSelectedRow();
        idCliente = (Integer) modelo1.getValueAt(fila, 0);
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

        Objetos.uploadImage.byteImage(_label.get(0), (byte[]) modelo1.getValueAt(fila, 12),
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
        searchClientes("");
        numeros();
        crearTabla();
    }

    public void numeros() {
        int j;
        String c = null;
        // String SQL="select count(*) from productos";
        //String SQL = "SELECT MAX(codigo_cliente) AS cod_cli FROM cliente";
        //String SQL="SELECT @@identity AS ID";
        List<Clientes> cliente = clientes();
        if (cliente.isEmpty()) {
            _textfield.get(8).setText("CLI-00000001");
        } else {
            j = cliente.get(cliente.size() - 1).getIdCliente();
            c = cliente.get(j - 1).getCodCliente();
        }

        if (c == null) {
            _textfield.get(8).setText("CLI-00000001");
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
            gen.generar(var);

            _textfield.get(8).setText(gen.serie());

        }
    }

    private boolean validarDatos() {
        if (_textfield.get(0).getText().equals("") && _textfield.get(2).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre o la razón social del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(0).requestFocus();
            return false;
        } else if (!_textfield.get(0).getText().equals("") && _textfield.get(1).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese los apellidos del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(1).requestFocus();
            return false;
        } else if (_textfield.get(3).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la dirección del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(3).requestFocus();
            return false;
        } else if (_textfield.get(4).getText().equals("") && !_textfield.get(0).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el DNI del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(4).requestFocus();
            return false;
        } else if (_textfield.get(0).getText().equals("") && _textfield.get(5).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el CIF del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(5).requestFocus();
            return false;
        } else if (_textfield.get(6).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el teléfono del cliente",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(6).requestFocus();
            return false;
        } else if (_textfield.get(7).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el email del cliente",
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
        for (int i = 0; i < _tablaClientes.getColumnCount(); i++) {
            _tablaClientes.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        _tablaClientes.setAutoResizeMode(_tablaClientes.AUTO_RESIZE_OFF);

        // Encabezados
        _tablaClientes.getTableHeader().setFont(new Font("Cooper Black", 1, 14));
// cambia el fondo del encabezado de la _tablaUsuarios
        _tablaClientes.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaUsuarios
        _tablaClientes.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 150, 150, 200, 200, 100, 100, 100, 200, 100, 100, 100, 100};
        for (int i = 0; i < _tablaClientes.getColumnCount(); i++) {
            _tablaClientes.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        _tablaClientes.setRowHeight(30);
        ocultarColumnas();
    }

    private void ocultarColumnas() {
        _tablaClientes.setRowHeight(30);
        _tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tablaClientes.getColumnModel().getColumn(11).setMaxWidth(0);
        _tablaClientes.getColumnModel().getColumn(11).setMinWidth(0);
        _tablaClientes.getColumnModel().getColumn(11).setPreferredWidth(0);
        _tablaClientes.getColumnModel().getColumn(12).setMaxWidth(0);
        _tablaClientes.getColumnModel().getColumn(12).setMinWidth(0);
        _tablaClientes.getColumnModel().getColumn(12).setPreferredWidth(0);
    }
}
