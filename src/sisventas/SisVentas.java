/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisventas;

import ViewModels.LoginVM;
import Views.Login;
import Views.PrincipalForm;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.List;
import javax.swing.UIManager;

/**
 *
 * @author avice
 */
public class SisVentas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception ex) {
        }

//        PrincipalForm sistema = new PrincipalForm(null);
//       // Login sistema = new Login();
//        sistema.setExtendedState(MAXIMIZED_BOTH);
//        sistema.setVisible(true);
        new Login().setVisible(true);
    }

}
