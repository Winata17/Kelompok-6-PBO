package UAS;

import javax.swing.*;
import java.awt.*;

public class NotaFrame extends JFrame {

    public NotaFrame(String nama, String produk, int jumlah, double harga, double total, String metode, String catatan) {
        setTitle("Nota Pembelian");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextPane notaArea = new JTextPane();
        notaArea.setContentType("text/html");
        notaArea.setEditable(false);

        String htmlNota = "<html><body style='font-family:sans-serif; padding:20px;'>"
                + "<h2 style='text-align:center;'>NOTA PEMBELIAN</h2><hr>"
                + "<p><b>Nama Pembeli:</b> " + nama + "<br>"
                + "<b>Produk:</b> " + produk + "<br>"
                + "<b>Jumlah:</b> " + jumlah + "<br>"
                + "<b>Harga Satuan:</b> Rp " + String.format("%,.0f", harga) + "<br>"
                + "<b>Total Bayar:</b> <span style='color:green;font-weight:bold;'>Rp " + String.format("%,.0f", total) + "</span><br>"
                + "<b>Metode Pembayaran:</b> " + metode + "<br>"
                + (catatan != null && !catatan.isEmpty() ? "<b>Catatan:</b> " + catatan + "<br>" : "")
                + "</p>";

        if (metode.equalsIgnoreCase("Transfer Bank") || metode.equalsIgnoreCase("E-Wallet")) {
            htmlNota += "<hr><p><b>Silakan transfer ke:</b><br>"
                    + "Bank: <b>Bank Mandiri</b><br>"
                    + "No Rekening: <b>123456789</b><br>"
                    + "A/N: <b>Kelompok 6</b></p>";
        }

        htmlNota += "<hr><p style='text-align:center;'>Terima kasih sudah belanja 🙏<br>Semoga harimu menyenangkan!</p>"
                + "</body></html>";

        notaArea.setText(htmlNota);

        JScrollPane scroll = new JScrollPane(notaArea);
        add(scroll);

        setVisible(true);
    }
}
