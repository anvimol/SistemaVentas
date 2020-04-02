/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModels;

import Conexion.Consult;
import Library.Fechas;
import Models.Compras;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author avice
 */
public class ComprasRealizadasVM extends Consult {
    
    private ArrayList<JLabel> _label;
    private ArrayList<JTextField> _textfield;
    private JDateChooser _dateChooserFechaIni, _dateChooserFechaFin;
    private JTable _tablaCompra;
    private DefaultTableModel modelo1, modelo2;
    private Fechas fecha = new Fechas();
    private int totalCompras = 0;
    
    public ComprasRealizadasVM(Object[] objects, ArrayList<JLabel> label,
            ArrayList<JTextField> textfield) {
        _label = label;
        _textfield = textfield;
        _dateChooserFechaIni = (JDateChooser) objects[0];
        _dateChooserFechaFin = (JDateChooser) objects[1];
        _tablaCompra = (JTable) objects[2];
        restablecer();
    }
    
    public void searchCompras(String campo) {
        List<Compras> comprasFilter;
        totalCompras = 0;
        _label.get(0).setText(String.valueOf(totalCompras));
        String[] titulos = {"ID", "Codigo compra", "Proveedor", "Fecha", 
            "Empleado", "Role", "Importe", "IVA", "Total"};
        modelo1 = new DefaultTableModel(null, titulos);
        if (campo.isEmpty()) {
            comprasFilter = compras1().stream()
                    .collect(Collectors.toList());
        } else {
            comprasFilter = compras1().stream()
                    .filter(u -> u.getProveedor().startsWith(campo) || 
                            u.getNumCompra().startsWith(campo))
                    .collect(Collectors.toList());
        }

        if (!comprasFilter.isEmpty()) {
            comprasFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdCompra(),
                    item.getNumCompra(),
                    item.getProveedor(),
                    item.getFecha(),
                    item.getUsuario(),
                    item.getRole(),
                    item.getImporte(),
                    item.getIva(),
                    item.getTotalPagar(),
                };
                modelo1.addRow(registros);
                totalCompras = totalCompras + 1;
            });
            _label.get(0).setText(String.valueOf(totalCompras));
        }
        _tablaCompra.setModel(modelo1);
        crearTabla(_tablaCompra);
    }
    
    public void searchComprasFecha(String campo, Date fecha1, Date fecha2) {
        String fechaIni = fecha.getFechaMySQL(_dateChooserFechaIni);
        String fechaFin = fecha.getFechaMySQL(_dateChooserFechaFin);
        List<Compras> comprasFilter;
        totalCompras = 0;
        _label.get(0).setText(String.valueOf(totalCompras));
        String[] titulos = {"ID", "Codigo compra", "Proveedor", "Fecha", 
            "Empleado", "Role", "Importe", "IVA", "Total"};
        modelo2 = new DefaultTableModel(null, titulos);
        if (campo.isEmpty()) {
            comprasFilter = comprasFechas(fechaIni, fechaFin).stream()
                    .collect(Collectors.toList());
        } else {
            comprasFilter = comprasFechas(fechaIni, fechaFin).stream()
                    .filter(u -> u.getProveedor().startsWith(campo) || 
                            u.getNumCompra().startsWith(campo))
                    .collect(Collectors.toList());
        }

        if (!comprasFilter.isEmpty()) {
            comprasFilter.forEach(item -> {
                Object[] registros = {
                    item.getIdCompra(),
                    item.getNumCompra(),
                    item.getProveedor(),
                    item.getFecha(),
                    item.getUsuario(),
                    item.getRole(),
                    item.getImporte(),
                    item.getIva(),
                    item.getTotalPagar(),
                };
                modelo2.addRow(registros);
                totalCompras = totalCompras + 1;
            });
            _label.get(0).setText(String.valueOf(totalCompras));
        }
        _tablaCompra.setModel(modelo2);
        crearTabla(_tablaCompra);
    }

    private void restablecer() {
        
    }
    
    public void crearTabla(JTable tabla) {
        //--------------------PRESENTACION DE JTABLE----------------------

        TableCellRenderer render = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //Determinar Alineaciones   
                if (column == 1) {
                    l.setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    l.setHorizontalAlignment(SwingConstants.LEFT);
                    l.setForeground(Color.BLACK);
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
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        //Activar ScrollBar
        tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);

        // Encabezados
        tabla.getTableHeader().setFont(new Font("Cooper Black", 1, 14));
// cambia el fondo del encabezado de la _tablaUsuarios
        tabla.getTableHeader().setBackground(new Color(0, 51, 102));
// cambia el color de la letra del encabezado de la _tablaUsuarios
        tabla.getTableHeader().setForeground(Color.white);

        //Anchos de cada columna
        int[] anchos = {10, 150, 236, 100, 150, 100, 100, 100, 150};
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Altos de cada fila
        tabla.setRowHeight(30);

        //ocultarColumnas();
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
