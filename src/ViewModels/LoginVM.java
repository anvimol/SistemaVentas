/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.Encriptar;
import Library.Objetos;
import Models.Usuarios;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 *
 * @author avice
 */
public class LoginVM extends Consult {

    private ArrayList<JLabel> _label;
    private ArrayList<JTextField> _textfield;
    private List<Usuarios> listUsuarios;

    public LoginVM(ArrayList<JLabel> label, ArrayList<JTextField> textfield) {
        _label = label;
        _textfield = textfield;
    }

    public LoginVM() {
    }

    public Object[] login() throws SQLException {
        if (_textfield.get(0).getText().isEmpty()) {
            _label.get(0).setText("Ingrese el Usuario");
            _label.get(0).setForeground(Color.RED);
            _textfield.get(0).requestFocus();
        } else if (!Objetos.eventos.isEmail(_textfield.get(0).getText())) {
            _label.get(0).setText("Ingrese un email valido");
            _label.get(0).setForeground(Color.RED);
            _textfield.get(0).requestFocus();
        } else if (_textfield.get(1).getText().isEmpty()) {
            _label.get(1).setText("Ingrese la Contraseña");
            _label.get(1).setForeground(Color.RED);
            _textfield.get(1).requestFocus();
        } else {
            listUsuarios = usuarios().stream()
                    .filter(u -> u.getEmail().equals(_textfield.get(0).getText()))
                    .collect(Collectors.toList());
            if (!listUsuarios.isEmpty()) {
                String pass = listUsuarios.get(0).getPassword();
                String pass1 = "";
                try {
                    pass1 = Encriptar.decrypt(pass);
                } catch (Exception ex) {
                    Logger.getLogger(LoginVM.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (pass1.equals(_textfield.get(1).getText())) {
                    try {
                        Date date = new Date();
                        final QueryRunner qr = new QueryRunner(true);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                } else {
                    _label.get(1).setText("Contraseña incorrecta");
                    _label.get(1).setForeground(Color.RED);
                    _textfield.get(1).requestFocus();
                    listUsuarios.clear();
                }
            } else {
                _label.get(0).setText("El email no esta registrado");
                _label.get(0).setForeground(Color.RED);
                _textfield.get(0).requestFocus();
                listUsuarios.clear();
            }
        }
        Object[] objects = {listUsuarios};
        return objects;
    }

//    public Object[] verificar() {
//        listUsuarios = new ArrayList<>();
//        try {
//            String hdd = Ordenador.getSerialNumber('c');
//            var dataOrdenador = ordenadores().stream()
//                    .filter(o -> o.getOrdenador().equals(hdd)
//                    && o.isIsActive() == true)
//                    .collect(Collectors.toList());
//            if (!dataOrdenador.isEmpty()) {
//                listUsuarios = usuarios().stream()
//                        .filter(u -> u.getEmail().equals(dataOrdenador.get(0).getUsuario()))
//                        .collect(Collectors.toList());
//            }
//        } catch (Exception e) {
//        }
//        Object[] objects = {listUsuarios};
//        return objects;
//    }
//    public void close() throws Exception {
//        listUsuarios = new ArrayList<>();
//        final QueryRunner qr = new QueryRunner(true);
//        getConn().setAutoCommit(false);
//        try {
//            Date date = new Date();
//            var hdd = Ordenador.getSerialNumber('c');
//            var dataOrdenador = ordenadores().stream()
//                    .filter(o -> o.getOrdenador().equals(hdd))
//                    .collect(Collectors.toList());
//            listUsuarios = usuarios().stream()
//                        .filter(u -> u.getEmail().equals(dataOrdenador.get(0).getUsuario()))
//                        .collect(Collectors.toList());
//            Object[] usuario = {false};
//            String sql1 = "UPDATE tusuarios SET isActive=? WHERE idUsuario=" + 
//                    listUsuarios.get(0).getIdUsuario();
//            qr.update(getConn(), sql1, usuario);
//            
//            Object[] ordenador = {false, date};
//            String sql2 = "UPDATE tordenadores SET isActive=?, inFecha=? "
//                    + "WHERE idOrdenador=" + dataOrdenador.get(0).getIdOrdenador();
//            qr.update(getConn(), sql2, ordenador);
//            getConn().commit();
//        } catch (Exception e) {
//            getConn().rollback();
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
}
