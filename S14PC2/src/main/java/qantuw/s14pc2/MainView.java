/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package qantuw.s14pc2;

import qantuw.s14pc2.com_2312044.Controller.Controller;
import qantuw.s14pc2.com_2312044.Repository.GastoRepository;
import qantuw.s14pc2.com_2312044.Service.GastoService;
import javax.swing.*;
import qantuw.s14pc2.com_2312044.View.GastoView;
/**
 *
 * @author ASUS
 */
public class MainView {
    public static void main(String[] args) {
       
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el estilo del sistema: " + e.getMessage());
        }


        SwingUtilities.invokeLater(() -> {
            try {
              
                GastoRepository gastoRepository = new GastoRepository();
                GastoService gastoService = new GastoService(gastoRepository);
                Controller Controller = new Controller(gastoService);

                
                GastoView mainView = new GastoView(Controller);
                mainView.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
