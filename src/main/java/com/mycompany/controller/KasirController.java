/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author phoca
 */
import com.mycompany.model.*;
import com.mycompany.view.TampilanKasir;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class KasirController {

    private TampilanKasir view;
    private Database db;

    public KasirController(TampilanKasir view) {
        this.view = view;
        this.db = new Database();

        // Memasang Listener ke tombol bayar dari View
        this.view.gettombolBayar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesPembayaran();
            }
        });
    }

    private void prosesPembayaran() {
        try {
            // 1. Ambil data dari GUI View
            String inputHarga = view.gettotalHarga().getText();
            String metode = (String) view.getmetodePembayaran().getSelectedItem();

            if (inputHarga.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Masukkan total harga terlebih dahulu!");
                return;
            }

            double totalHarga = Double.parseDouble(inputHarga);
            Payment payment;

            // 2. Terapkan prinsip Polymorphism / LSP (SOLID)
            if (metode.equalsIgnoreCase("Cash") || metode.equalsIgnoreCase("Tunai")) {
                payment = new CashPayment();
            } else {
                payment = new QrisPayment();
            }

            // Eksekusi pembayaran
            payment.pay(totalHarga);

            // 3. Simpan transaksi ke database MySQL (JDBC)
            db.connect();
            String sql = "INSERT INTO transaksi (total_harga, metode_pembayaran) VALUES ("
                    + totalHarga + ", '" + metode + "')";
            db.query(sql);
            db.disconnect();

            // 4. Beri feedback ke user dan bersihkan input
            JOptionPane.showMessageDialog(view, "Pembayaran Berhasil via " + metode + "!");
            view.gettotalHarga().setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Input harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Gagal memproses transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
