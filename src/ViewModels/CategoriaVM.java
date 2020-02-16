/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Models.Categorias;
import Models.Departamentos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JButton;
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
public class CategoriaVM extends Consult {

    private String _accion = "insert";
    private final ArrayList<JLabel> _label;
    private final ArrayList<JTextField> _textfield;
    private final JRadioButton _radioDpto, _radioCat;
    private final JTable _tablaDepartamentos, _tablaCategorias;
    private int idCategoria = 0, idDepartamento = 0;
    private DefaultTableModel modelo, modelo1;

    private List<Departamentos> departamentoFilter;
    private List<Categorias> categoriaFilter;

    public CategoriaVM(Object[] objects, ArrayList<JLabel> label,
            ArrayList<JTextField> textfield) {
        _accion = "insert";
        _label = label;
        _textfield = textfield;
        _radioDpto = (JRadioButton) objects[0];
        _radioCat = (JRadioButton) objects[1];
        _tablaDepartamentos = (JTable) objects[2];
        _tablaCategorias = (JTable) objects[3];
        restablecer();
    }

    public void registrarCategoria() {
        departamentoFilter = departamentos().stream()
                .filter(u -> u.getDepartamento().equals(_textfield.get(0).getText()))
                .collect(Collectors.toList());
        categoriaFilter = categorias().stream()
                .filter(u -> u.getCategoria().equals(_textfield.get(1).getText()))
                .collect(Collectors.toList());

        if (_radioDpto.isSelected()) {
            try {
                switch (_accion) {
                    case "insert":
                        if (departamentoFilter.size() == 0) {
                            saveData("Dpto");
                        } else {
                            if (!departamentoFilter.isEmpty()) { // 0 < listEmail.size()
                                _label.get(0).setText("El Dpto ya esta registrado");
                                _label.get(0).setForeground(Color.RED);
                                _textfield.get(0).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (departamentoFilter.size() == 1) {
                            if (departamentoFilter.get(0).getIdDpto() == idDepartamento) {
                                saveData("Dpto");
                            } else {
                                if (departamentoFilter.get(0).getIdDpto() != idDepartamento) {
                                    _label.get(0).setText("El Dpto ya esta registrado");
                                    _label.get(0).setForeground(Color.RED);
                                    _textfield.get(0).requestFocus();
                                }
                            }
                        } else {
                            if (departamentoFilter.size() == 0) {
                                saveData("Dpto");
                            } else {
                                if (!departamentoFilter.isEmpty()) {
                                    if (departamentoFilter.get(0).getIdDpto() == idDepartamento) {
                                        saveData("Dpto");
                                    } else {
                                        _label.get(0).setText("El Dpto ya esta registrado");
                                        _label.get(0).setForeground(Color.RED);
                                        _textfield.get(0).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        if (_radioCat.isSelected()) {
            try {
                switch (_accion) {
                    case "insert":
                        if (categoriaFilter.isEmpty()) {
                            saveData("Cat");
                        } else {
                            if (!categoriaFilter.isEmpty()) { // 0 < listEmail.size()
                                _label.get(1).setText("La Categoria ya esta registrada");
                                _label.get(1).setForeground(Color.RED);
                                _textfield.get(1).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (categoriaFilter.size() == 1) {
                            if (categoriaFilter.get(0).getIdCat() == idCategoria) {
                                saveData("Cat");
                            } else {
                                if (categoriaFilter.get(0).getIdCat() != idCategoria) {
                                    _label.get(1).setText("La Categoria ya esta registrada");
                                    _label.get(1).setForeground(Color.RED);
                                    _textfield.get(1).requestFocus();
                                }
                            }
                        } else {
                            if (categoriaFilter.isEmpty()) {
                                saveData("Cat");
                            } else {
                                if (!categoriaFilter.isEmpty()) {
                                    if (categoriaFilter.get(0).getIdCat() == idCategoria) {
                                        saveData("Cat");
                                    } else {
                                        _label.get(1).setText("La Categoria ya esta registrada");
                                        _label.get(1).setForeground(Color.RED);
                                        _textfield.get(1).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void saveData(String type) {
        try {
            final QueryRunner qr = new QueryRunner(true);
            if (_radioDpto.isSelected() && type.equals("Dpto")) {
                switch (_accion) {
                    case "insert":
                        String sqlDep = "INSERT INTO departamentos(departamento) VALUES(?)";
                        Object[] dataDept = {
                            _textfield.get(0).getText()
                        };
                        qr.insert(getConn(), sqlDep, new ColumnListHandler(), dataDept);
                        JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                                "Exito", JOptionPane.INFORMATION_MESSAGE);
                        restablecer();
                        break;
                    case "update":
                        Object[] dataDep2 = {
                            _textfield.get(0).getText()
                        };
                        String sqlDep2 = "UPDATE departamentos SET departamento=?"
                                + " WHERE idDpto = " + idDepartamento;
                        qr.update(getConn(), sqlDep2, dataDep2);
                        JOptionPane.showMessageDialog(null, "El registro se ha modificado correctamente",
                                "Exito", JOptionPane.INFORMATION_MESSAGE);
                        restablecer();
                        break;
                }

            } else {
                switch (_accion) {
                    case "insert":
                        String sqlCateg = "INSERT INTO categorias(categoria,dpto_id) VALUES(?,?)";
                        Object[] dataCateg = {
                            _textfield.get(1).getText(),
                            idDepartamento
                        };
                        qr.insert(getConn(), sqlCateg, new ColumnListHandler(), dataCateg);
                        JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                                "Exito", JOptionPane.INFORMATION_MESSAGE);
                        restablecer();
                        break;
                    case "update":
                        Object[] dataCateg2 = {
                            _textfield.get(1).getText(),
                            idDepartamento
                        };
                        String sqlCateg2 = "UPDATE categorias SET categoria=?, dpto_id=?"
                                + " WHERE idCat = " + idCategoria;
                        qr.update(getConn(), sqlCateg2, dataCateg2);
                        JOptionPane.showMessageDialog(null, "El registro se ha modificado correctamente",
                                "Exito", JOptionPane.INFORMATION_MESSAGE);
                        restablecer();
                        break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaVM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public boolean insertDptoCat(String dptocat, int idDpto, String type) {
//        boolean valor = false;
//        if (type.equals("dpto")) {
//            departamentoFilter = departamentos().stream()
//                    .filter(d -> d.getDepartamento().equals(dptocat))
//                    .collect(Collectors.toList());
//            if (departamentoFilter.isEmpty()) {
//                sql = "INSERT INTO departamentos(departamento) VALUES(?)";
//                object = new Object[]{dptocat};
//                insert(sql, object);
//                valor = true;
//            }
//            departamentoFilter.clear();
//        } else {
//            categoriaFilter = categorias().stream()
//                    .filter(c -> c.getCategoria().equals(dptocat))
//                    .collect(Collectors.toList());
//            if (categoriaFilter.isEmpty()) {
//                sql = "INSERT INTO categorias(categoria, dpto_id) VALUES(?,?)";
//                object = new Object[]{dptocat, idDpto};
//                insert(sql, object);
//                valor = true;
//            }
//            categoriaFilter.clear();
//        }
//        return valor;
//    }
    public void searchDepartamento(String campo) {
        String[] titulos = {"ID", "Departamento"};
        modelo = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            departamentoFilter = departamentos().stream()
                    .collect(Collectors.toList());
        } else {
            departamentoFilter = departamentos().stream()
                    .filter(C -> C.getDepartamento().startsWith(campo))
                    .collect(Collectors.toList());
        }
        if (!departamentoFilter.isEmpty()) {
            departamentoFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdDpto(),
                    item.getDepartamento()
                };
                modelo.addRow(registros);
            });
        }
        _tablaDepartamentos.setModel(modelo);
        _tablaDepartamentos.getTableHeader().setFont(new Font("Cooper Black", 1, 16));
        _tablaDepartamentos.getTableHeader().setBackground(new Color(0, 51, 102));
        _tablaDepartamentos.getTableHeader().setForeground(Color.white);
        _tablaDepartamentos.setRowHeight(30);
        _tablaDepartamentos.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaDepartamentos.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaDepartamentos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void searchCategoria(String campo, int idDpto) {
        List<Categorias> categoriaFilter;
        String[] titulos = {"ID", "Categoria"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.equals("")) {
            categoriaFilter = categorias().stream()
                    .filter(c -> c.getDpto_id() == idDpto)
                    .collect(Collectors.toList());
        } else {
            categoriaFilter = categorias().stream()
                    .filter(C -> C.getCategoria().startsWith(campo))
                    .collect(Collectors.toList());
        }
        if (!categoriaFilter.isEmpty()) {
            categoriaFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdCat(),
                    item.getCategoria()
                };
                modelo1.addRow(registros);
            });
        }
        _tablaCategorias.setModel(modelo1);
        _tablaCategorias.getTableHeader().setFont(new Font("Cooper Black", 1, 16));
        _tablaCategorias.getTableHeader().setBackground(new Color(0, 51, 102));
        _tablaCategorias.getTableHeader().setForeground(Color.white);
        _tablaCategorias.setRowHeight(30);
        _tablaCategorias.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaCategorias.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void datosDpto() {
        if (_radioDpto.isSelected()) {
            _accion = "update";
        }

        int fila = _tablaDepartamentos.getSelectedRow();
        idDepartamento = (Integer) modelo.getValueAt(fila, 0);
        _textfield.get(0).setText((String) modelo.getValueAt(fila, 1));

        if (_radioCat.isSelected()) {
            _accion = "insert";
            _label.get(0).setText("Departamento");
            _label.get(0).setForeground(new Color(0, 153, 51));
        }
        _label.get(0).setText("Departamento");
        _label.get(0).setForeground(new Color(0, 153, 51));
        _textfield.get(1).setText("");
        searchCategoria("", idDepartamento);

    }
    
    public void datosCategoria() {
        if (_radioCat.isSelected()) {
            _accion = "update";
        }

        int fila = _tablaCategorias.getSelectedRow();
        idCategoria = (Integer) modelo1.getValueAt(fila, 0);
        _textfield.get(1).setText((String) modelo1.getValueAt(fila, 1));

        if (_radioDpto.isSelected()) {
            _accion = "insert";
            _label.get(1).setText("Categoria");
            _label.get(1).setForeground(new Color(0, 153, 51));
        }
        _label.get(1).setText("Categoria");
        _label.get(1).setForeground(new Color(0, 153, 51));
        searchCategoria("", idDepartamento);

    }

//    public void getDepartamentos() {
//        _accion = "update";
//        int fila = _tablaDepartamentos.getSelectedRow();
//        idDepartamento = (Integer) modelo.getValueAt(fila, 0);
//        _textfield.get(0).setText((String) modelo.getValueAt(fila, 1));
//    }
//
//    public void getCategorias() {
//        _accion = "update";
//        int fila = _tablaCategorias.getSelectedRow();
//        idCategoria = (Integer) modelo1.getValueAt(fila, 0);
//        _textfield.get(1).setText((String) modelo1.getValueAt(fila, 1));
//    }
    public final void restablecer() {
        _accion = "insert";
        idCategoria = 0;
        idDepartamento = 0;
        _textfield.get(0).setText("");
        _textfield.get(1).setText("");
        _textfield.get(0).requestFocus();
        _label.get(0).setForeground(new Color(70, 106, 124));
        _label.get(1).setForeground(new Color(70, 106, 124));
        searchDepartamento("");
        searchCategoria("", 0);
        //crearTabla();
    }

    public void crearTabla() {
        //--------------------PRESENTACION DE JTABLE----------------------

        TableCellRenderer render = new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //Determinar Alineaciones   
                if (column == 1) {
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
        for (int i = 0; i < _tablaCategorias.getColumnCount(); i++) {
            _tablaCategorias.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        _tablaCategorias.setAutoResizeMode(_tablaCategorias.AUTO_RESIZE_OFF);

        // Encabezados
        _tablaCategorias.getTableHeader().setFont(new Font("Cooper Black", 1, 16));
// cambia el fondo del encabezado de la _tablaCategorias
        _tablaCategorias.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaCategorias
        _tablaCategorias.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 812};
        for (int i = 0; i < _tablaCategorias.getColumnCount(); i++) {
            _tablaCategorias.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        _tablaCategorias.setRowHeight(30);
        ocultarColumnas();
    }

    private void ocultarColumnas() {
        _tablaCategorias.getColumnModel().getColumn(0).setMaxWidth(0);
        _tablaCategorias.getColumnModel().getColumn(0).setMinWidth(0);
        _tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
