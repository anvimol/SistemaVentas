/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.Calendario;
import Library.Encriptar;
import Library.Objetos;
import Library.TextFieldEvent;
import Library.UploadImage;
import Models.Roles;
import Models.Usuarios;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
public class UsuariosVM extends Consult {

    private static Usuarios _dataUsuario;
    private static JLabel _nombrePerfil, _usuarioPerfil;
    private static JLabel _rolePerfil;
    private UploadImage _uploadimage = new UploadImage();
    private String _accion = "insert";
    private ArrayList<JLabel> _label;
    private ArrayList<JTextField> _textfield;
    private JTable _tablaUsuarios;
    private JComboBox _comboRol;
    private JRadioButton _radioActivo, _radioInactivo;
    private JCheckBox _checkPassword;
    private Integer idProducto, totalRegistros;
    private DefaultTableModel modelo1;
    private DefaultComboBoxModel modelCombo;
    private DateChooserCombo _dateChooserFecha;
    private TextFieldEvent evento = new TextFieldEvent();
    private static int idUsuario;
    private Roles rol = null;
    private List<Roles> roles = new ArrayList<>();
    private List<Usuarios> usuarioFilter;

    public UsuariosVM(Object[] objects, ArrayList<JLabel> label,
            ArrayList<JTextField> textfield) {
        _accion = "insert";
        _label = label;
        _textfield = textfield;
        _comboRol = (JComboBox) objects[0];
        _radioActivo = (JRadioButton) objects[1];
        _radioInactivo = (JRadioButton) objects[2];
        _dateChooserFecha = (DateChooserCombo) objects[3];
        _tablaUsuarios = (JTable) objects[4];
        _checkPassword = (JCheckBox) objects[5];
        restablecer();
    }

    public UsuariosVM(Usuarios dataUsuario, Object[] perfil) {
        _dataUsuario = dataUsuario;
        _nombrePerfil = (JLabel) perfil[0];
        _usuarioPerfil = (JLabel) perfil[1];
        _rolePerfil = (JLabel) perfil[2];
        Perfil();
    }

    private void Perfil() {
        _nombrePerfil.setText(_dataUsuario.getNombre());
        _usuarioPerfil.setText(_dataUsuario.getUsuario());
        _rolePerfil.setText(_dataUsuario.getRole());
    }

    public void registrarUsuarios() {
        if (validarDatos()) {
            int count;
            List<Usuarios> listEmail = usuarios().stream()
                    .filter(u -> u.getEmail().equals(_textfield.get(4).getText()))
                    .collect(Collectors.toList());
            count = listEmail.size();
            List<Usuarios> listUsers = usuarios().stream()
                    .filter(u -> u.getUsuario().equals(_textfield.get(5).getText()))
                    .collect(Collectors.toList());
            count += listUsers.size();
            try {
                switch (_accion) {
                    case "insert":
                        if (count == 0) {
                            saveData();
                        } else {
                            if (!listEmail.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(4).requestFocus();
                            }
                            if (!listUsers.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este usuario ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(5).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (count == 2) {
                            if (listUsers.get(0).getIdUsuario() == idUsuario
                                    && listEmail.get(0).getIdUsuario() == idUsuario) {
                                saveData();
                            } else {
                                if (listEmail.get(0).getIdUsuario() != idUsuario) {
                                    JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(4).requestFocus();
                                }
                                if (listUsers.get(0).getIdUsuario() != idUsuario) {
                                    JOptionPane.showMessageDialog(null, "Este usuario ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(5).requestFocus();
                                }
                            }
                        } else {
                            if (count == 0) {
                                saveData();
                            } else {
                                if (!listEmail.isEmpty()) {
                                    if (listEmail.get(0).getIdUsuario() == idUsuario) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este email ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(4).requestFocus();
                                    }
                                }
                                if (!listUsers.isEmpty()) {
                                    if (listUsers.get(0).getIdUsuario() == idUsuario) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este usuario ya esta registrado",
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
        rol = (Roles) _comboRol.getSelectedItem();
        String role = rol.getDescripcion();

        int estado = 0;
        if (_radioActivo.isSelected()) {
            estado = 1;
        } else {
            estado = 0;
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
                    String sqlUser = "INSERT INTO usuarios(nombre,apellidos,"
                            + "direccion,telefono,email,usuario,password,"
                            + "role,imagen,estado,fecha)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

                    Object[] dataUser = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        Encriptar.encrypt(_textfield.get(6).getText()),
                        role,
                        image,
                        estado,
                        null//new Calendario().getFecha()
                    };
                    qr.insert(getConn(), sqlUser, new ColumnListHandler(), dataUser);
                    JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    restablecer();
                    break;

                case "update":
                    Object[] dataUser2 = {
                        _textfield.get(0).getText(),
                        _textfield.get(1).getText(),
                        _textfield.get(2).getText(),
                        _textfield.get(3).getText(),
                        _textfield.get(4).getText(),
                        _textfield.get(5).getText(),
                        Encriptar.encrypt(_textfield.get(6).getText()),
                        role,
                        image,
                        estado,
                        null //new Calendario().getFecha()
                    };
                    String sqlUser2 = "UPDATE usuarios SET nombre=?, apellidos=?,"
                            + "direccion=?, telefono=?, email=?, usuario=?,"
                            + "password=?, role=?, imagen=?, estado=?, fecha=?"
                            + " WHERE idUsuario = " + idUsuario;
                    qr.update(getConn(), sqlUser2, dataUser2);
                    break;
            }
            restablecer();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void searchUsuarios(String campo) {
        totalRegistros = 0;
        String[] titulos = {"Id", "Nombre", "Apellidos", "Dirección", "Teléfono",
            "Email", "Usuario", "Contraseña", "Role", "Imagen", "Estado", "Fecha"
        };
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            usuarioFilter = usuarios().stream()
                    .collect(Collectors.toList());
        } else {
            usuarioFilter = usuarios().stream()
                    .filter(u -> u.getNombre().startsWith(campo) || u.getApellidos().startsWith(campo)
                    || u.getEmail().startsWith(campo) || u.getUsuario().startsWith(campo))
                    .collect(Collectors.toList());
        }
        if (!usuarioFilter.isEmpty()) {
            usuarioFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdUsuario(),
                    item.getNombre(),
                    item.getApellidos(),
                    item.getDireccion(),
                    item.getTelefono(),
                    item.getEmail(),
                    item.getUsuario(),
                    item.getPassword(),
                    item.getRole(),
                    item.getImagen(),
                    item.isEstado() == true ? "ACTIVO" : "INACTIVO",
                    item.getFecha()
                };
                totalRegistros = totalRegistros + 1;
                modelo1.addRow(registros);
            });
            _label.get(1).setText(String.valueOf(totalRegistros));
        }
        _tablaUsuarios.setModel(modelo1);
    }

    public void getUsuarios() {
        String role, estado;
        _accion = "update";
        int fila = _tablaUsuarios.getSelectedRow();
        idUsuario = (Integer) modelo1.getValueAt(fila, 0);
        _textfield.get(0).setText((String) modelo1.getValueAt(fila, 1));
        _textfield.get(1).setText((String) modelo1.getValueAt(fila, 2));
        _textfield.get(2).setText((String) modelo1.getValueAt(fila, 3));
        _textfield.get(3).setText((String) modelo1.getValueAt(fila, 4));
        _textfield.get(4).setText((String) modelo1.getValueAt(fila, 5));
        _textfield.get(5).setText((String) modelo1.getValueAt(fila, 6));
        try {
            _textfield.get(6).setText(Encriptar.decrypt((String) modelo1.getValueAt(fila, 7)));
        } catch (Exception ex) {
            Logger.getLogger(UsuariosVM.class.getName()).log(Level.SEVERE, null, ex);
        }
        _textfield.get(6).setEnabled(false);

        estado = (String) modelo1.getValueAt(fila, 10);
        if (estado.equals("ACTIVO")) {
            _radioActivo.setSelected(true);
        } else {
            _radioInactivo.setSelected(true);
        }
        role = (String) modelo1.getValueAt(fila, 8);

        rol = getRoles(_comboRol, role);
        _comboRol.setSelectedItem(rol);

        Objetos.uploadImage.byteImage(_label.get(0), (byte[]) modelo1.getValueAt(fila, 9),
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
        _label.get(0).setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource("Resources/usuario.jpg")));
        getRoles(_comboRol, "");
        searchUsuarios("");
        crearTabla();
    }

    public void activarCampoPass() {
        if (_checkPassword.isSelected()) {
            _textfield.get(6).setEnabled(true);
        } else {
            _textfield.get(6).setEnabled(false);
        }
    }

    public Roles getRoles(JComboBox combo, String role) {
        modelCombo = new DefaultComboBoxModel();
        roles = roles();
        if (0 < roles.size()) {
            roles.forEach(item -> {
                modelCombo.addElement(item);
                if (role.equals(item.getDescripcion())) {
                    rol = item;
                }
            });
            combo.setModel(modelCombo);
            modelCombo = new DefaultComboBoxModel();
        }
        return rol;
    }

    private boolean validarDatos() {
        if (_textfield.get(0).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(0).requestFocus();
            return false;
        } else if (_textfield.get(1).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese los apellidos del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(1).requestFocus();
            return false;
        } else if (_textfield.get(2).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la dirección del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(2).requestFocus();
            return false;
        } else if (_textfield.get(3).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el teléfono del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(3).requestFocus();
            return false;
        } else if (_textfield.get(4).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el email del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(4).requestFocus();
            return false;
        } else if (!evento.isEmail(_textfield.get(4).getText())) {
            JOptionPane.showMessageDialog(null, "El email no es correcto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(4).requestFocus();
            return false;
        } else if (_textfield.get(5).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el login del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(5).requestFocus();
            return false;
        } else if (_textfield.get(6).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la contraseña del usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(6).requestFocus();
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
        for (int i = 0; i < _tablaUsuarios.getColumnCount(); i++) {
            _tablaUsuarios.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        _tablaUsuarios.setAutoResizeMode(_tablaUsuarios.AUTO_RESIZE_OFF);

        // Encabezados
        _tablaUsuarios.getTableHeader().setFont(new Font("Cooper Black", 1, 14));
// cambia el fondo del encabezado de la _tablaUsuarios
        _tablaUsuarios.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaUsuarios
        _tablaUsuarios.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 150, 150, 175, 100, 175, 150, 100, 100, 100, 100, 100};
        for (int i = 0; i < _tablaUsuarios.getColumnCount(); i++) {
            _tablaUsuarios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        _tablaUsuarios.setRowHeight(30);
        ocultarColumnas();
    }

    private void ocultarColumnas() {
        _tablaUsuarios.setRowHeight(30);
        _tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(7).setMaxWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(7).setMinWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(7).setPreferredWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(9).setMaxWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(9).setMinWidth(0);
        _tablaUsuarios.getColumnModel().getColumn(9).setPreferredWidth(0);
    }
}
