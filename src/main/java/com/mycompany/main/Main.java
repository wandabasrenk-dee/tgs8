/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.main;

/**
 *
 * @author phoca
 */
import com.mycompany.view.TampilanKasir;
import com.mycompany.controller.KasirController;

public class Main {

    public static void main(String[] args) {
        // Jalankan GUI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TampilanKasir view = new TampilanKasir();
                new KasirController(view);
                view.setVisible(true);
            }
        });
    }
}
