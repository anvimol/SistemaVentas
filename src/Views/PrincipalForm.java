/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Consultas.ComprasRealizadasForm;
import Library.Calendario;
import Models.Usuarios;
import ViewModels.LoginVM;
import ViewModels.UsuariosVM;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author avice
 */
public class PrincipalForm extends javax.swing.JFrame {

    private LoginVM login;
    private UsuariosVM usuario;
    public String nombreEmpleado;

    /**
     * Creates new form PrincipalForm
     */
    public PrincipalForm(Usuarios dataUsuario) {
        initComponents();
        this.getContentPane().setBackground(new Color(101, 142, 173));

        // CODIGO DE USUARIO
        login = new LoginVM();
        Object[] perfil = {
            LabelNombrePerfil,
            LabelUsuarioPerfil,
            LabelRolePerfil,
            LabelIdPerfil
        };
        usuario = new UsuariosVM(dataUsuario, perfil);

        String fecha = new Calendario().getFecha();
        LabelFechaPerfil.setText(fecha);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        escritorio = new javax.swing.JDesktopPane();
        TBPrincipal = new javax.swing.JToolBar();
        mbtnEmpleado = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        mbtnCliente = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        mbtnProducto = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        mbtnCompra = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        mbtnVenta = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        mbtnCaja = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        mbtnEstado = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        LabelUsuarioPerfil = new javax.swing.JLabel();
        LabelNombrePerfil = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        LabelFechaPerfil = new javax.swing.JLabel();
        LabelRolePerfil = new javax.swing.JLabel();
        LabelIdPerfil = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mnuIniciarSesion = new javax.swing.JMenuItem();
        mnuCerrarSesion = new javax.swing.JMenuItem();
        mnuSalir = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        mnuProducto = new javax.swing.JMenuItem();
        mnuCategoria = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        mnuCompra = new javax.swing.JMenuItem();
        mnuProveedor = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mnuVenta = new javax.swing.JMenuItem();
        mnuVerificarProd = new javax.swing.JMenuItem();
        mnuCliente = new javax.swing.JMenuItem();
        mnuConsultas = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mnuCompraRealizada = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        escritorio.setBackground(new java.awt.Color(233, 240, 248));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1044, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        TBPrincipal.setBackground(new java.awt.Color(101, 142, 173));
        TBPrincipal.setRollover(true);

        mbtnEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/empleados_b.png"))); // NOI18N
        mbtnEmpleado.setText("Empleados");
        mbtnEmpleado.setFocusable(false);
        mbtnEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnEmpleado.setPreferredSize(new java.awt.Dimension(55, 65));
        mbtnEmpleado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnEmpleadoActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnEmpleado);

        jLabel1.setText("   ");
        TBPrincipal.add(jLabel1);

        mbtnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/cliente_b.png"))); // NOI18N
        mbtnCliente.setText("Clientes");
        mbtnCliente.setFocusable(false);
        mbtnCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnCliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnClienteActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnCliente);

        jLabel7.setText("   ");
        TBPrincipal.add(jLabel7);

        mbtnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/productos_b.png"))); // NOI18N
        mbtnProducto.setText("Productos");
        mbtnProducto.setFocusable(false);
        mbtnProducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnProducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnProductoActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnProducto);

        jLabel2.setText("   ");
        TBPrincipal.add(jLabel2);

        mbtnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/compra_b.png"))); // NOI18N
        mbtnCompra.setText("Compras");
        mbtnCompra.setFocusable(false);
        mbtnCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnCompraActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnCompra);

        jLabel3.setText("   ");
        TBPrincipal.add(jLabel3);

        mbtnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ventas_b.png"))); // NOI18N
        mbtnVenta.setText("Ventas");
        mbtnVenta.setFocusable(false);
        mbtnVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnVentaActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnVenta);

        jLabel4.setText("   ");
        TBPrincipal.add(jLabel4);

        mbtnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/caja_b.png"))); // NOI18N
        mbtnCaja.setText("Caja");
        mbtnCaja.setFocusable(false);
        mbtnCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnCajaActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnCaja);

        jLabel6.setText("   ");
        TBPrincipal.add(jLabel6);

        mbtnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/estado_b.png"))); // NOI18N
        mbtnEstado.setText("Estado");
        mbtnEstado.setFocusable(false);
        mbtnEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mbtnEstado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mbtnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mbtnEstadoActionPerformed(evt);
            }
        });
        TBPrincipal.add(mbtnEstado);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Usuario:");

        LabelUsuarioPerfil.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        LabelUsuarioPerfil.setForeground(new java.awt.Color(255, 255, 255));
        LabelUsuarioPerfil.setText("usuario");

        LabelNombrePerfil.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        LabelNombrePerfil.setForeground(new java.awt.Color(255, 255, 255));
        LabelNombrePerfil.setText("nombre");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nombre:");

        LabelFechaPerfil.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        LabelFechaPerfil.setForeground(new java.awt.Color(255, 255, 255));
        LabelFechaPerfil.setText("fecha");

        LabelRolePerfil.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        LabelRolePerfil.setForeground(new java.awt.Color(255, 255, 255));
        LabelRolePerfil.setText("Role");

        LabelIdPerfil.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        LabelIdPerfil.setForeground(new java.awt.Color(255, 255, 255));
        LabelIdPerfil.setText("0");

        menuBar.setBackground(new java.awt.Color(255, 255, 255));

        mnuArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/archivo.png"))); // NOI18N
        mnuArchivo.setMnemonic('f');
        mnuArchivo.setText("Archivo");

        mnuIniciarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        mnuIniciarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/connect.png"))); // NOI18N
        mnuIniciarSesion.setMnemonic('o');
        mnuIniciarSesion.setText("Iniciar sesión");
        mnuArchivo.add(mnuIniciarSesion);

        mnuCerrarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, java.awt.event.InputEvent.ALT_MASK));
        mnuCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/disconnect.png"))); // NOI18N
        mnuCerrarSesion.setMnemonic('s');
        mnuCerrarSesion.setText("Cerrar sesión");
        mnuArchivo.add(mnuCerrarSesion);

        mnuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.ALT_MASK));
        mnuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/door_in.png"))); // NOI18N
        mnuSalir.setMnemonic('x');
        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuSalir);

        menuBar.add(mnuArchivo);

        editMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Box.png"))); // NOI18N
        editMenu.setMnemonic('e');
        editMenu.setText("Almacen");

        mnuProducto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnuProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/productos_m.png"))); // NOI18N
        mnuProducto.setMnemonic('t');
        mnuProducto.setText("Producto");
        mnuProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProductoActionPerformed(evt);
            }
        });
        editMenu.add(mnuProducto);

        mnuCategoria.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.ALT_MASK));
        mnuCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/categoria.png"))); // NOI18N
        mnuCategoria.setMnemonic('y');
        mnuCategoria.setText("Categoria");
        mnuCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCategoriaActionPerformed(evt);
            }
        });
        editMenu.add(mnuCategoria);

        menuBar.add(editMenu);

        helpMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/report_add.png"))); // NOI18N
        helpMenu.setMnemonic('h');
        helpMenu.setText("Compras");

        mnuCompra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, java.awt.event.InputEvent.ALT_MASK));
        mnuCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/compra_m.png"))); // NOI18N
        mnuCompra.setMnemonic('c');
        mnuCompra.setText("Compra");
        helpMenu.add(mnuCompra);

        mnuProveedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, java.awt.event.InputEvent.ALT_MASK));
        mnuProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/proveedores_m.png"))); // NOI18N
        mnuProveedor.setMnemonic('a');
        mnuProveedor.setText("Proveedor");
        mnuProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProveedorActionPerformed(evt);
            }
        });
        helpMenu.add(mnuProveedor);

        menuBar.add(helpMenu);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ventas2.png"))); // NOI18N
        jMenu1.setText("Ventas");

        mnuVenta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, java.awt.event.InputEvent.ALT_MASK));
        mnuVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ventas_m.png"))); // NOI18N
        mnuVenta.setText("Venta");
        jMenu1.add(mnuVenta);

        mnuVerificarProd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, java.awt.event.InputEvent.ALT_MASK));
        mnuVerificarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/estado_m.png"))); // NOI18N
        mnuVerificarProd.setText("Verificar producto");
        mnuVerificarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuVerificarProdActionPerformed(evt);
            }
        });
        jMenu1.add(mnuVerificarProd);

        mnuCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.ALT_MASK));
        mnuCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/cliente_m.png"))); // NOI18N
        mnuCliente.setText("Cliente");
        mnuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClienteActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCliente);

        menuBar.add(jMenu1);

        mnuConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/consultas.png"))); // NOI18N
        mnuConsultas.setText("Consultas");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ventasrealizadas.png"))); // NOI18N
        jMenuItem1.setText("Ventas Realizadas");
        mnuConsultas.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ventasdetalladas.png"))); // NOI18N
        jMenuItem2.setText("Ventas Detalladas");
        mnuConsultas.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/estadisticamensual.png"))); // NOI18N
        jMenuItem3.setText("Estadistica Mensual");
        mnuConsultas.add(jMenuItem3);

        mnuCompraRealizada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/comprasrealizadas.png"))); // NOI18N
        mnuCompraRealizada.setText("Compras Realizadas");
        mnuCompraRealizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCompraRealizadaActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuCompraRealizada);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/comprasdetalladas.png"))); // NOI18N
        jMenuItem5.setText("Compras Detalladas");
        mnuConsultas.add(jMenuItem5);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/report.png"))); // NOI18N
        jMenuItem6.setText("Stock Mínimo");
        mnuConsultas.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/coins_add.png"))); // NOI18N
        jMenuItem7.setText("Kardex Valorizado");
        mnuConsultas.add(jMenuItem7);

        menuBar.add(mnuConsultas);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Mantenimiento.png"))); // NOI18N
        jMenu3.setText("Mantenimiento");
        menuBar.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Folder_Remove.png"))); // NOI18N
        jMenu4.setText("Anulaciones");
        menuBar.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Gear.png"))); // NOI18N
        jMenu5.setText("Herramientas");
        menuBar.add(jMenu5);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/ayuda.png"))); // NOI18N
        jMenu6.setText("Ayuda");
        menuBar.add(jMenu6);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
            .addComponent(TBPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelIdPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelNombrePerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelUsuarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelRolePerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelFechaPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(TBPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(escritorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(LabelUsuarioPerfil)
                    .addComponent(LabelFechaPerfil)
                    .addComponent(LabelRolePerfil)
                    .addComponent(jLabel10)
                    .addComponent(LabelNombrePerfil)
                    .addComponent(LabelIdPerfil))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mbtnEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnEmpleadoActionPerformed
        UsuariosForm usuario = new UsuariosForm();
        escritorio.add(usuario);
        usuario.show();
    }//GEN-LAST:event_mbtnEmpleadoActionPerformed

    private void mbtnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnClienteActionPerformed
        ClientesForm cliente = new ClientesForm();
        escritorio.add(cliente);
        cliente.show();
    }//GEN-LAST:event_mbtnClienteActionPerformed

    private void mbtnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnProductoActionPerformed
        ProductosForm producto = new ProductosForm();
        escritorio.add(producto);
        producto.show();
    }//GEN-LAST:event_mbtnProductoActionPerformed

    private void mbtnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnCompraActionPerformed
        ComprasForm compra = new ComprasForm();
        escritorio.add(compra);
        compra.show();
//        compra.IdEmpleado=lblIdEmpleado.getText();
        compra.nombreEmpleado = LabelUsuarioPerfil.getText();
    }//GEN-LAST:event_mbtnCompraActionPerformed

    private void mbtnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnVentaActionPerformed
//        Presentacion.FrmVenta venta=new Presentacion.FrmVenta();
//        Escritorio.add(venta);
//        venta.show();
//        venta.IdEmpleado=lblIdEmpleado.getText();
//        venta.NombreEmpleado=lblNombreEmpleado.getText();
    }//GEN-LAST:event_mbtnVentaActionPerformed

    private void mbtnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnCajaActionPerformed
//        Presentacion.FrmCaja Caja=new Presentacion.FrmCaja();
//        Escritorio.add(Caja);
//        Caja.show();
    }//GEN-LAST:event_mbtnCajaActionPerformed

    private void mbtnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mbtnEstadoActionPerformed
//        Presentacion.FrmProductoEstado ProductoEstado=new Presentacion.FrmProductoEstado();
//        Escritorio.add(ProductoEstado);
//        ProductoEstado.show();
    }//GEN-LAST:event_mbtnEstadoActionPerformed

    private void mnuVerificarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuVerificarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuVerificarProdActionPerformed

    private void mnuCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCategoriaActionPerformed
        CategoriasForm categoria = new CategoriasForm();
        escritorio.add(categoria);
        categoria.show();
    }//GEN-LAST:event_mnuCategoriaActionPerformed

    private void mnuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClienteActionPerformed
        ClientesForm cliente = new ClientesForm();
        escritorio.add(cliente);
        cliente.show();
    }//GEN-LAST:event_mnuClienteActionPerformed

    private void mnuProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProductoActionPerformed
        ProductosForm producto = new ProductosForm();
        escritorio.add(producto);
        producto.show();
    }//GEN-LAST:event_mnuProductoActionPerformed

    private void mnuProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProveedorActionPerformed
        ProveedoresForm prov = new ProveedoresForm();
        escritorio.add(prov);
        prov.show();
    }//GEN-LAST:event_mnuProveedorActionPerformed

    private void mnuCompraRealizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCompraRealizadaActionPerformed
        ComprasRealizadasForm compra = new ComprasRealizadasForm();
        escritorio.add(compra);
        compra.show();
    }//GEN-LAST:event_mnuCompraRealizadaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelFechaPerfil;
    public static javax.swing.JLabel LabelIdPerfil;
    private javax.swing.JLabel LabelNombrePerfil;
    public static javax.swing.JLabel LabelRolePerfil;
    public static javax.swing.JLabel LabelUsuarioPerfil;
    private javax.swing.JToolBar TBPrincipal;
    private javax.swing.JMenu editMenu;
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JButton mbtnCaja;
    private javax.swing.JButton mbtnCliente;
    private javax.swing.JButton mbtnCompra;
    private javax.swing.JButton mbtnEmpleado;
    private javax.swing.JButton mbtnEstado;
    private javax.swing.JButton mbtnProducto;
    private javax.swing.JButton mbtnVenta;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenuItem mnuCategoria;
    private javax.swing.JMenuItem mnuCerrarSesion;
    private javax.swing.JMenuItem mnuCliente;
    private javax.swing.JMenuItem mnuCompra;
    private javax.swing.JMenuItem mnuCompraRealizada;
    private javax.swing.JMenu mnuConsultas;
    private javax.swing.JMenuItem mnuIniciarSesion;
    private javax.swing.JMenuItem mnuProducto;
    private javax.swing.JMenuItem mnuProveedor;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JMenuItem mnuVenta;
    private javax.swing.JMenuItem mnuVerificarProd;
    // End of variables declaration//GEN-END:variables

}
