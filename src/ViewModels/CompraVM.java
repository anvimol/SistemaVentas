/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.Calendario;
import Library.FormatDecimal;
import Library.GenerarNumeros;
import Models.*;
import Views.ComprasForm;
import Views.PrincipalForm;
import datechooser.beans.DateChooserCombo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class CompraVM extends Consult {

    private ArrayList<JTextField> _textfield;
    private JTable _tablaCompra;
    private DateChooserCombo _dateChooserFecha;
    private JCheckBox _checkBox;
    private DefaultTableModel modelo1, modelo2, modelo3;
    public FormatDecimal formato = new FormatDecimal();
    public List<TempoCompras> numTempoCompras = new ArrayList<>();
    public List<Compras> compras = new ArrayList<>();
    private DateChooserCombo dateChooserInicio, dateChooserFinal;
    private String sql, codigoCompra;
    private Object[] object;
    private double deuda = 0, iva = 0, total;
    private String _accion = "insert";
    private static int idCompra;
    private final SimpleDateFormat formateador;

    public CompraVM() {

        this.formateador = new SimpleDateFormat("dd/MM/yyyy");
    }

    public CompraVM(Object[] objects, ArrayList<JTextField> textfield) {
        _dateChooserFecha = (DateChooserCombo) objects[0];
        _checkBox = (JCheckBox) objects[1];
        _tablaCompra = (JTable) objects[2];
        _textfield = textfield;

        this.formateador = new SimpleDateFormat("dd/MM/yyyy");
        restablecer();
    }

    public void guardarTempoCompra(String codigo, String producto, int cant,
            String precio, String stock) {

        final QueryRunner qr = new QueryRunner(true);
        double precio1 = formato.reconstruir(precio);
        String precio2 = formato.decimal(precio1);

        try {
            numTempoCompras = tempoCompras().stream()
                    .filter(t -> t.getCodigo_pro().equals(codigo)
                    && t.getProducto().equals(producto)
                    && t.getPrecio().equals(precio2))
                    .collect(Collectors.toList());
            if (0 < numTempoCompras.size()) {

                int cant1;
                String importe1;
                double importe2, importe3;
                importe2 = precio1 * cant;
                importe1 = numTempoCompras.get(0).getTotal();
                importe3 = formato.reconstruir(importe1);
                importe2 = importe2 + importe3;
                cant1 = cant + numTempoCompras.get(0).getCantidad();
                sql = "UPDATE tempo_compras SET codigo_pro=?, producto=?, precio=?, cantidad=?, "
                        + "total=?, stock=? WHERE id=" + numTempoCompras.get(0).getId();
                object = new Object[]{
                    codigo,
                    producto,
                    formato.decimal(precio1),
                    cant1,
                    formato.decimal(importe2),
                    stock
                };
                qr.update(getConn(), sql, object);

            } else {
                double importe;
                importe = precio1 * cant;
                sql = "INSERT INTO tempo_compras(codigo_pro,producto,precio,"
                        + "cantidad,total,stock) VALUES(?,?,?,?,?,?)";
                object = new Object[]{
                    codigo,
                    producto,
                    formato.decimal(precio1),
                    cant,
                    formato.decimal(importe),
                    stock
                };
                qr.insert(getConn(), sql, new ColumnListHandler(), object);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTempoCompra(int idCompra, String codigo, String producto, int cant,
            String precio) {
        try {
            final QueryRunner qr = new QueryRunner(true);
            double precio1, importe;
            precio1 = formato.reconstruir(precio);
            importe = precio1 * cant;
            sql = "UPDATE tempo_compras SET codigo_pro=?, producto=?, precio=?, cantidad=?, "
                    + "total=? WHERE id=" + idCompra;
            object = new Object[]{
                codigo,
                producto,
                formato.decimal(precio1),
                cant,
                formato.decimal(importe)
            };
            qr.update(getConn(), sql, object);
        } catch (SQLException ex) {
            Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchTempoCompras(JTable table, String campo) {
        String[] registros = new String[7];
        String[] titulos = {"ID", "Codigo", "Descripción", "Precio compra", "Cantidad", "Importe", "Stock"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.isEmpty()) {
            numTempoCompras = tempoCompras().stream()
                    .collect(Collectors.toList());
        } else {
            numTempoCompras = tempoCompras().stream()
                    .filter(t -> t.getProducto().startsWith(campo))
                    .collect(Collectors.toList());
        }
        numTempoCompras.forEach(item -> {
            registros[0] = String.valueOf(item.getId());
            registros[1] = item.getCodigo_pro();
            registros[2] = item.getProducto();
            registros[3] = item.getPrecio();
            registros[4] = String.valueOf(item.getCantidad());
            registros[5] = item.getTotal();
            registros[6] = item.getStock();
            modelo1.addRow(registros);
        });
        table.setModel(modelo1);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        //table.setDefaultRenderer(Object.class, new RenderCelda(4,0));
    }

    public DefaultTableModel getModelo() {
        return modelo1;
    }

    public void importesTempo(JTextField textfield1, JTextField textfield2,
            JTextField textfield3) {
        deuda = 0;
        iva = 0;
        numTempoCompras = tempoCompras().stream()
                .collect(Collectors.toList());
        if (!numTempoCompras.isEmpty()) {
            numTempoCompras.forEach(item -> {
                double importe = formato.reconstruir(item.getTotal());
                deuda += importe;
            });
            iva = Double.parseDouble(textfield2.getText().replace("%", "")) / 100;
            double subTotalIva = deuda * iva;
            total = deuda + subTotalIva;

            textfield1.setText(formato.decimal(deuda));
            textfield3.setText(formato.decimal(total));

        } else {
            textfield1.setText("0,00");
        }
    }

    public void deleteCompras(int idCompra) {
        if (0 < idCompra) {
            sql = "DELETE FROM tempo_compras WHERE id LIKE ?";
            delete(sql, idCompra);
        } else {
            sql = "DELETE FROM tempo_compras";
            delete(sql, 0);
        }
    }

    public void registrarCompra() {
        if (validarDatos()) {
            int count;
            List<Compras> listCod = compras().stream()
                    .filter(u -> u.getCodigoCompra().equals(_textfield.get(8).getText()))
                    .collect(Collectors.toList());
            count = listCod.size();
            try {
                switch (_accion) {
                    case "insert":
                        if (count == 0) {
                            saveData();
                        } else {
                            if (!listCod.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Este Código de compra ya esta registrado",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                _textfield.get(8).requestFocus();
                            }
                        }
                        break;
                    case "update":
                        if (count == 1) {
                            if (listCod.get(0).getIdCompra() == idCompra) {
                                saveData();
                            } else {
                                if (listCod.get(0).getIdCompra() != idCompra) {
                                    JOptionPane.showMessageDialog(null, "Este Código de compra ya esta registrado",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    _textfield.get(8).requestFocus();
                                }
                            }
                        } else {
                            if (count == 0) {
                                saveData();
                            } else {
                                if (!listCod.isEmpty()) {
                                    if (listCod.get(0).getIdCompra() == idCompra) {
                                        saveData();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Este Código de compra ya esta registrado",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        _textfield.get(8).requestFocus();
                                    }
                                }
                            }
                        }
                        break;
                }
            } catch (HeadlessException e) {
            } catch (SQLException ex) {
                Logger.getLogger(ClienteVM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveData() throws SQLException {
        try {
            String usuario = PrincipalForm.LabelUsuarioPerfil.getText();
            String role = PrincipalForm.LabelRolePerfil.getText();
            int idUsuario = Integer.valueOf(PrincipalForm.LabelIdPerfil.getText());

            QueryRunner qr = new QueryRunner(true);
            getConn().setAutoCommit(false);

            switch (_accion) {
                case "insert":
                    String sqlCompra1 = "INSERT INTO compras(numCompra,fecha,"
                            + "proveedor,importe,iva,totalPagar,usuario,role)"
                            + " VALUES(?,?,?,?,?,?,?,?)";
                    Object[] dataCompra1 = {
                        _textfield.get(8).getText(),
                        new Calendario().getFecha(),
                        _textfield.get(0).getText(),
                        _textfield.get(9).getText(),
                        _textfield.get(10).getText(),
                        _textfield.get(11).getText(),
                        usuario,
                        role
                    };
                    try {
                        qr.insert(getConn(), sqlCompra1, new ColumnListHandler(), dataCompra1);
                    } catch (SQLException ex) {
                        Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Compras> compra = compras1();

                    String sqlCompra = "INSERT INTO detalles_compras(codProducto,producto,cantidad,"
                            + "precio,importe,idProveedor,proveedor,idUsuario,"
                            + "usuario,role,dia,mes,year,fecha,codigoCompra,compra_id)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    numTempoCompras.forEach(item -> {
                        Object[] dataCompra = {
                            item.getCodigo_pro(),
                            item.getProducto(),
                            item.getCantidad(),
                            item.getPrecio(),
                            item.getTotal(),
                            ComprasForm.idProveedor,
                            _textfield.get(0).getText(),
                            idUsuario,
                            usuario,
                            role,
                            new Calendario().getDia(),
                            new Calendario().getMes(),
                            new Calendario().getAnyo(),
                            new Calendario().getFecha(),
                            _textfield.get(8).getText(),
                            compra.get(compra.size() - 1).getIdCompra(),};
                        try {
                            qr.insert(getConn(), sqlCompra, new ColumnListHandler(), dataCompra);
                        } catch (SQLException ex) {
                            Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        String sqlProdu = "UPDATE productos SET stock=? WHERE codigo="
                                + item.getCodigo_pro();
                        double stock1, stock2, stock3;
                        stock1 = formato.reconstruir(item.getStock());
                        stock2 = item.getCantidad();
                        stock3 = stock1 + stock2;
                        Object[] dataProdu = {
                            formato.decimal(stock3)
                        };
                        try {
                            qr.update(getConn(), sqlProdu, dataProdu);
                        } catch (SQLException ex) {
                            Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    JOptionPane.showMessageDialog(null, "El registro se ha insertado correctamente",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    deleteCompras(0);
                    break;

            }
            getConn().commit();
            restablecer();
        } catch (Exception ex) {
            getConn().rollback();
            JOptionPane.showMessageDialog(null, ex);
        }
    }

//    public void searchCompras(JTable table, String campo) {
//        String[] registros = new String[7];
//        String[] titulos = {"ID", "Codigo compra", "Proveedor", "Fecha", "Empleado", "Role", "Total"};
//        modelo2 = new DefaultTableModel(null, titulos);
//        if (campo.isEmpty()) {
//            compras = compras1().stream()
//                    .collect(Collectors.toList());
//        } else {
//            compras = compras1().stream()
//                    .filter(t -> t.getNumCompra().startsWith(campo))
//                    .collect(Collectors.toList());
//        }
//        if (!compras.isEmpty()) {
//            compras.forEach(item -> {
//                registros[0] = String.valueOf(item.getIdCompra());
//                registros[1] = item.getNumCompra();
//                registros[2] = item.getProveedor();
//                registros[3] = item.getFecha();
//                registros[4] = item.getUsuario();
//                registros[5] = item.getRole();
//                registros[6] = item.getTotalPagar();
//                modelo2.addRow(registros);
//            });
//        }
//        table.setModel(modelo2);
//        //table.setDefaultRenderer(Object.class, new RenderCelda(4,0));
//    }
//    public int searchCompras(JTable table, DateChooserCombo dateChooserInicio,
//            DateChooserCombo dateChooserFinal, String campo) {
//        String[] registros = new String[7];
//        String[] titulos = {"ID", "Codigo compra", "Proveedor", "Fecha", "Empleado", "Role", "Total"};
//        modelo3 = new DefaultTableModel(null, titulos);
//        List<Compras> query = new ArrayList<>();
//        int valor = 0;
//        try {
//            String fechaInicio = dateChooserInicio.getSelectedPeriodSet().toString(); // dateChooserInicio.getSelectedPeriodSet().toString();
//            Date fechaDate1 = formateador.parse(fechaInicio);
//            Date fechaDate2 = formateador.parse(dateChooserFinal.getSelectedPeriodSet().toString());
//            if (campo.isEmpty()) {
//                if (fechaDate2.before(fechaDate1)) { // before = menor
//                    JOptionPane.showMessageDialog(null, "La fecha final debe ser"
//                            + " mayor a la fecha de incio", "Error",
//                            JOptionPane.WARNING_MESSAGE);
//                } else {
//                    query = maxCompras(filtrarCompraFechas(fechaInicio));
//                    valor = query.size();
//                }
//            } else {
//                if (fechaDate2.before(fechaDate1)) { // before = menor
//                    JOptionPane.showMessageDialog(null, "La fecha final debe ser"
//                            + " mayor a la fecha de incio", "Error",
//                            JOptionPane.WARNING_MESSAGE);
//                } else {
//                    query = filtrarCompraFechas(fechaInicio).stream()
//                            .filter(p -> p.getNumCompra().startsWith(campo))
//                            .collect(Collectors.toList());
//                    valor = query.size();
//                }
//            }
//
//        } catch (Exception e) {
//        }
//        query.forEach(item -> {
//            registros[0] = String.valueOf(item.getIdCompra());
//            registros[1] = item.getNumCompra();
//            registros[2] = item.getProveedor();
//            registros[3] = item.getFecha();
//            registros[4] = item.getUsuario();
//            registros[5] = item.getRole();
//            registros[6] = item.getTotalPagar();
//            modelo3.addRow(registros);
//        });
//
//        table.setModel(modelo3);
//        table.getColumnModel().getColumn(0).setMaxWidth(0);
//        table.getColumnModel().getColumn(0).setMinWidth(0);
//        table.getColumnModel().getColumn(0).setPreferredWidth(0);
//        //table.setDefaultRenderer(Object.class, new RenderCelda(4,0));
//        return valor;
//    }

    public int searchCompras(JTable table, DateChooserCombo dateChooserInicio,
            DateChooserCombo dateChooserFinal, String campo) {
        String[] registros = new String[7];
        String[] titulos = {"ID", "Codigo compra", "Proveedor", "Fecha", "Empleado", "Role", "Total"};
        modelo3 = new DefaultTableModel(null, titulos);
        List<Compras> query = new ArrayList<>();
        int valor = 0;
        try {
            String fechaInicio = dateChooserInicio.getSelectedPeriodSet().toString(); // dateChooserInicio.getSelectedPeriodSet().toString();
            String fechaFin = dateChooserFinal.getSelectedPeriodSet().toString(); // dateChooserInicio.getSelectedPeriodSet().toString();
            Date fechaDate1 = formateador.parse(fechaInicio);
            Date fechaDate2 = formateador.parse(dateChooserFinal.getSelectedPeriodSet().toString());
            if (campo.isEmpty()) {
                if (fechaDate2.before(fechaDate1)) { // before = menor
                    JOptionPane.showMessageDialog(null, "La fecha final debe ser"
                            + " mayor a la fecha de incio", "Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    query = comprasFechas(fechaInicio, fechaFin);
                    valor = query.size();
                }
            } else {
                if (fechaDate2.before(fechaDate1)) { // before = menor
                    JOptionPane.showMessageDialog(null, "La fecha final debe ser"
                            + " mayor a la fecha de incio", "Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    query = filtrarCompraFechas(fechaInicio).stream()
                            .filter(p -> p.getNumCompra().startsWith(campo))
                            .collect(Collectors.toList());
                    valor = query.size();
                }
            }

        } catch (Exception e) {
        }
        query.forEach(item -> {
            registros[0] = String.valueOf(item.getIdCompra());
            registros[1] = item.getNumCompra();
            registros[2] = item.getProveedor();
            registros[3] = item.getFecha();
            registros[4] = item.getUsuario();
            registros[5] = item.getRole();
            registros[6] = item.getTotalPagar();
            modelo3.addRow(registros);
        });

        table.setModel(modelo3);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        //table.setDefaultRenderer(Object.class, new RenderCelda(4,0));
        return valor;
    }
    
    private List<Compras> maxCompras(List<Compras> query) {
        List<Compras> listProduct = new ArrayList<>();
        for (Compras item : query) {
            listProduct.add(item);
        }
        // ORDENAR DE MENOR A MAYOR
        listProduct.sort((v1, v2) -> Integer.valueOf(v1.getCantidad()).compareTo(v2.getCantidad()));
        // MAYOR A MENOR
        Collections.reverse(listProduct);
        return listProduct;
    }

    private List<Compras> filtrarCompraFechas(String fechaInicio) {
        List<Compras> listFecha = new ArrayList<>();
        try {
            Compras compra = new Compras();
            Date fechaDate1 = formateador.parse(dateChooserInicio.getSelectedPeriodSet().toString());
            Date fechaDate2 = formateador.parse(dateChooserFinal.getSelectedPeriodSet().toString());
            List<Compras> listdb1 = compras1().stream()
                    .filter(b -> b.getFecha().equals(fechaInicio))
                    .collect(Collectors.toList());
            if (0 < listdb1.size()) {
                List<Compras> listdb2 = compras1().stream()
                        .filter(b -> b.getIdCompra() >= listdb1.get(0).getIdCompra())
                        .collect(Collectors.toList());
                for (Compras item : listdb2) {
                    Date fechaDate3 = formateador.parse(item.getFecha());
                    if (fechaDate3.before(fechaDate2)
                            || 0 == fechaDate2.compareTo(fechaDate1)) { // compareTo devuelve 0 se las fechas son iguales
                        compra.setIdCompra(item.getIdCompra());
                        compra.setNumCompra(item.getNumCompra());
                        compra.setFecha(item.getFecha());
                        compra.setProveedor(item.getProveedor());
                        compra.setImporte(item.getImporte());
                        compra.setIva(item.getIva());
                        compra.setTotalPagar(item.getTotalPagar());
                        compra.setUsuario(item.getUsuario());
                        compra.setRole(item.getRole());
                        listFecha.add(compra);
                        compra = new Compras();
                    } else {
                        break;
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(CompraVM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFecha;
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
        searchTempoCompras(_tablaCompra, "");
        numeros();
        //crearTabla();
    }

    private boolean validarDatos() {
        if (_textfield.get(0).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(0).requestFocus();
            return false;
        } else if (_textfield.get(1).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el código del proveedor",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(1).requestFocus();
            return false;
        } else if (_textfield.get(9).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el subtotal",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(9).requestFocus();
            return false;
        } else if (_textfield.get(10).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el IVA",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(10).requestFocus();
            return false;
        } else if (_textfield.get(11).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el total a pagar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(11).requestFocus();
            return false;
        } else if (_textfield.get(8).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nº de compra",
                    "Error", JOptionPane.ERROR_MESSAGE);
            _textfield.get(8).requestFocus();
            return false;
        }
        return true;
    }

    public void numeros() {
        int j;
        String c = null;
        // String SQL="select count(*) from productos";
        //String SQL = "SELECT MAX(codigo_cliente) AS cod_cli FROM cliente";
        //String SQL="SELECT @@identity AS ID";
        List<Compras> compras = compras1();
        if (compras.isEmpty()) {
            _textfield.get(8).setText("COM-00000001");
        } else {
            j = compras.get(compras.size() - 1).getIdCompra();
            c = compras.get(j - 1).getNumCompra();
        }

        if (c == null) {
            _textfield.get(8).setText("COM-00000001");
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
            gen.generarCompra(var);

            _textfield.get(8).setText(gen.serie());

        }
    }

}
