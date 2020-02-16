/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Models.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author antonio
 */
public class Consult extends Conexion {

    private QueryRunner QR = new QueryRunner();

    public List<Clientes> clientes() {
        String where = "";
        List<Clientes> reportes = new ArrayList();
        String condicion1 = " personas.id = clientes.persona_id ";
        String campos = " personas.id, personas.nombre, personas.apellidos,"
                + "personas.razonSocial, personas.direccion, personas.dni,"
                + "personas.cif, personas.telefono, personas.email, personas.imagen,"
                + "clientes.idCliente, clientes.codCliente, clientes.estado, clientes.persona_id";
        try {
            reportes = (List<Clientes>) QR.query(getConn(),
                    "SELECT" + campos + " FROM personas INNER JOIN clientes ON"
                    + condicion1 + where, new BeanListHandler(Clientes.class));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return reportes;
    }
    
    public List<Persona> personas() {
        List<Persona> persona = new ArrayList();
        try {
            persona = (List<Persona>) QR.query(getConn(), "SELECT * FROM personas",
                    new BeanListHandler(Persona.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return persona;
    }
    
    public List<Proveedores> proveedores() {
        String where = "";
        List<Proveedores> reportes = new ArrayList();
        String condicion1 = " personas.id = proveedores.persona_id ";
        String campos = " personas.id, personas.nombre, personas.apellidos,"
                + "personas.razonSocial, personas.direccion, personas.dni,"
                + "personas.cif, personas.telefono, personas.email, personas.imagen,"
                + "proveedores.idProveedor, proveedores.codProveedor, "
                + "proveedores.estado, proveedores.persona_id";
        try {
            reportes = (List<Proveedores>) QR.query(getConn(),
                    "SELECT" + campos + " FROM personas INNER JOIN proveedores ON"
                    + condicion1 + where, new BeanListHandler(Proveedores.class));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return reportes;
    }
//
//    public List<TReportes_clientes> reportesClientes(int idCliente) {
//        String where = "WHERE tclientes.id =" + idCliente;
//        List<TReportes_clientes> reportes = new ArrayList();
//        String condicion1 = " tclientes.id = treportes_clientes.idCliente ";
//        String condicion2 = " tclientes.id = treportes_intereses_clientes.idCliente ";
//        String campos = " tclientes.id, tclientes.dni, tclientes.nombre, tclientes.apellidos,"
//                + "treportes_clientes.idReporte, treportes_clientes.deudaActual,"
//                + "treportes_clientes.fechaDeuda, treportes_clientes.ultimoPago,"
//                + "treportes_clientes.fechaPago, treportes_clientes.ticket,"
//                + "treportes_clientes.deuda, treportes_clientes.mensual, treportes_clientes.cambio,"
//                + "treportes_clientes.fechaLimite,treportes_intereses_clientes.idReportInteres,"
//                + "treportes_intereses_clientes.intereses,treportes_intereses_clientes.pago,"
//                + "treportes_intereses_clientes.cambio,treportes_intereses_clientes.cuotas,"
//                + "treportes_intereses_clientes.interesFecha,treportes_intereses_clientes.ticketIntereses";
//        try {
//            reportes = (List<TReportes_clientes>) QR.query(getConn(),
//                    "SELECT" + campos + " FROM tclientes INNER JOIN treportes_clientes ON"
//                    + condicion1 + "INNER JOIN treportes_intereses_clientes ON" 
//                    + condicion2 + where, new BeanListHandler(TReportes_clientes.class));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e);
//        }
//        return reportes;
//    }
//
    public List<Departamentos> departamentos() {
            List<Departamentos> departamentos = new ArrayList<>();
        try {
            departamentos = (List<Departamentos>) QR.query(getConn(), "SELECT * FROM departamentos",
                    new BeanListHandler(Departamentos.class));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return departamentos;
    }

    public List<Categorias> categorias() {
        List<Categorias> categoria = new ArrayList();
        try {
            categoria = (List<Categorias>) QR.query(getConn(), "SELECT * FROM categorias",
                    new BeanListHandler(Categorias.class));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return categoria;
    }

    public List<Productos> productos() {
        List<Productos> producto = new ArrayList();
        try {
            producto = (List<Productos>) QR.query(getConn(), "SELECT * FROM productos",
                    new BeanListHandler(Productos.class));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return producto;
    }
    
    public List<Usuarios> usuarios() {
        List<Usuarios> usuarios = new ArrayList();
        try {
            usuarios = (List<Usuarios>) QR.query(getConn(), "SELECT * FROM usuarios",
                    new BeanListHandler(Usuarios.class));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return usuarios;
    }
    
    public List<Roles> roles() {
        List<Roles> rol = new ArrayList();
        try {
            rol = (List<Roles>) QR.query(getConn(), "SELECT * FROM roles",
                    new BeanListHandler(Roles.class));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return rol;
    }
//    
//    public List<TIntereses_clientes> interesesClientes() {
//        List<TIntereses_clientes> intereses = new ArrayList();
//        try {
//            intereses = (List<TIntereses_clientes>) QR.query(getConn(), "SELECT * FROM tintereses_clientes",
//                    new BeanListHandler(TIntereses_clientes.class));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e);
//        }
//        return intereses;
//    }
//    
//    public List<TReportes_clientes> reporteCliente() {
//        List<TReportes_clientes> reporte = new ArrayList();
//        try {
//            reporte = (List<TReportes_clientes>) QR.query(getConn(), "SELECT * FROM treportes_clientes",
//                    new BeanListHandler(TReportes_clientes.class));
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e);
//        }
//        return reporte;
//    }
//    
//    public List<TReportes_clientes> reportes_Clientes() {
//        String where = "";
//        List<TReportes_clientes> reportes = new ArrayList();
//        String condicion1 = " tclientes.id = treportes_clientes.idCliente ";
//        String campos = " tclientes.id, tclientes.dni, tclientes.nombre, tclientes.apellidos,"
//                + "tclientes.telefono,tclientes.email,tclientes.direccion,"
//                + "treportes_clientes.idReporte, treportes_clientes.deudaActual,"
//                + "treportes_clientes.fechaDeuda, treportes_clientes.ultimoPago,"
//                + "treportes_clientes.fechaPago, treportes_clientes.ticket,"
//                + "treportes_clientes.deuda, treportes_clientes.mensual, treportes_clientes.cambio,"
//                + "treportes_clientes.fechaLimite";
//        try {
//            reportes = (List<TReportes_clientes>) QR.query(getConn(),
//                    "SELECT" + campos + " FROM tclientes INNER JOIN treportes_clientes ON"
//                    + condicion1 + where, new BeanListHandler(TReportes_clientes.class));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e);
//        }
//        return reportes;
//    }
}
