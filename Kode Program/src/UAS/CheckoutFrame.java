// File: CheckoutFrame.java
package UAS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckoutFrame extends JFrame {
    public CheckoutFrame(Sepeda sepeda, int userId, String fullName) {
        setTitle("Checkout Produk");
        setSize(1140, 724);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientPanel());
        setLayout(null);

        JLabel headerLabel = new JLabel("CHECKOUT PRODUK", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBounds(0, 30, 1140, 40);
        add(headerLabel);

        // Panel kiri
        JPanel panelProduk = new JPanel(null);
        panelProduk.setBackground(Color.WHITE);
        panelProduk.setBounds(60, 100, 450, 500);
        panelProduk.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(panelProduk);

        JLabel gambarLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(sepeda.getPathGambar());
            Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            gambarLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            gambarLabel.setText("Gambar tidak ditemukan");
        }
        gambarLabel.setBounds(100, 30, 250, 250);
        panelProduk.add(gambarLabel);

        JLabel namaProduk = new JLabel(sepeda.getNama());
        namaProduk.setFont(new Font("Arial", Font.BOLD, 20));
        namaProduk.setBounds(30, 300, 390, 30);
        panelProduk.add(namaProduk);

        JLabel deskripsi = new JLabel("<html>" + sepeda.getDeskripsi() + "</html>");
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 14));
        deskripsi.setBounds(30, 330, 390, 60);
        panelProduk.add(deskripsi);

        JLabel stok = new JLabel("Stok: " + sepeda.getStok());
        stok.setBounds(30, 390, 390, 20);
        panelProduk.add(stok);

        JLabel harga = new JLabel("Harga: Rp " + String.format("%,.0f", sepeda.getHarga()));
        harga.setFont(new Font("Arial", Font.BOLD, 16));
        harga.setBounds(30, 420, 390, 20);
        panelProduk.add(harga);

        // Panel kanan
        JPanel panelForm = new JPanel(null);
        panelForm.setBackground(Color.WHITE);
        panelForm.setBounds(530, 100, 550, 500);
        panelForm.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(panelForm);

        JTextField namaField = new JTextField(fullName);
        JTextField emailField = new JTextField();
        JTextField alamatField = new JTextField();
        JComboBox<String> metodeCombo = new JComboBox<>(new String[] { "COD", "Transfer Bank", "E-Wallet" });
        JTextField catatanField = new JTextField();
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, sepeda.getStok(), 1);
        JSpinner qtySpinner = new JSpinner(model);
        JLabel totalLbl = new JLabel();

        // Label
        String[] labelNames = { "Nama:", "Email:", "Alamat:", "Metode Bayar:", "Catatan (opsional):", "Jumlah:" };
        int y = 30;
        for (String name : labelNames) {
            JLabel lbl = new JLabel(name);
            lbl.setBounds(30, y, 120, 30);
            panelForm.add(lbl);
            y += 40;
        }

        namaField.setBounds(150, 30, 350, 30);
        emailField.setBounds(150, 70, 350, 30);
        alamatField.setBounds(150, 110, 350, 30);
        metodeCombo.setBounds(150, 150, 350, 30);
        catatanField.setBounds(150, 190, 350, 30);
        qtySpinner.setBounds(150, 230, 80, 30);
        totalLbl.setBounds(250, 230, 300, 30);
        totalLbl.setFont(new Font("Arial", Font.BOLD, 14));

        panelForm.add(namaField);
        panelForm.add(emailField);
        panelForm.add(alamatField);
        panelForm.add(metodeCombo);
        panelForm.add(catatanField);
        panelForm.add(qtySpinner);
        panelForm.add(totalLbl);

        // Update total dengan diskon
        ActionListener updateTotal = e -> {
            int qty = (int) qtySpinner.getValue();
            double totalAwal = qty * sepeda.getHarga();
            MetodePembayaran metodeBayar;
            String metode = metodeCombo.getSelectedItem().toString();

            if (metode.equals("Transfer Bank")) {
                metodeBayar = new TransferBank();
            } else if (metode.equals("E-Wallet")) {
                metodeBayar = new Ewallet();
            } else {
                metodeBayar = new COD();
            }

            double totalDiskon = metodeBayar.hitungTotal(totalAwal);
            totalLbl.setText("Total: Rp " + String.format("%,.0f", totalDiskon));
        };

        metodeCombo.addActionListener(updateTotal);
        qtySpinner.addChangeListener(e -> updateTotal.actionPerformed(null));
        updateTotal.actionPerformed(null);

        // Tombol beli
        JButton buyBtn = new JButton("Beli Sekarang");
        buyBtn.setBounds(180, 290, 200, 40);
        buyBtn.setBackground(new Color(0, 153, 76));
        buyBtn.setForeground(Color.WHITE);
        panelForm.add(buyBtn);

        buyBtn.addActionListener(e -> {
            String nama = namaField.getText().trim();
            String email = emailField.getText().trim();
            String alamat = alamatField.getText().trim();
            String metode = metodeCombo.getSelectedItem().toString();
            String catatan = catatanField.getText().trim();
            int qty = (int) qtySpinner.getValue();
            double totalAwal = qty * sepeda.getHarga();

            MetodePembayaran metodeBayar;
            if (metode.equalsIgnoreCase("Transfer Bank")) {
                metodeBayar = new TransferBank();
            } else if (metode.equalsIgnoreCase("E-Wallet")) {
                metodeBayar = new Ewallet();
            } else {
                metodeBayar = new COD();
            }

            double total = metodeBayar.hitungTotal(totalAwal);

            if (nama.isEmpty() || email.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data wajib!");
                return;
            }

            try (Connection conn = DBConnection.connect()) {
                conn.setAutoCommit(false);

                try (PreparedStatement psNama = conn
                        .prepareStatement("SELECT full_name FROM users WHERE user_id = ?")) {
                    psNama.setInt(1, userId);
                    ResultSet rs = psNama.executeQuery();
                    if (!rs.next() || !nama.equalsIgnoreCase(rs.getString("full_name"))) {
                        JOptionPane.showMessageDialog(this, "Nama tidak sesuai akun!");
                        return;
                    }
                }

                String insertOrder = "INSERT INTO orders (user_id, order_date, total_price, status, total_amount, alamat_pengiriman, metode_pembayaran, pesan_opsional) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?)";
                int orderId;
                try (PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                    psOrder.setInt(1, userId);
                    psOrder.setDouble(2, total);
                    psOrder.setString(3, "proses");
                    psOrder.setInt(4, qty);
                    psOrder.setString(5, alamat);
                    psOrder.setString(6, metode);
                    psOrder.setString(7, catatan);
                    psOrder.executeUpdate();
                    ResultSet rs = psOrder.getGeneratedKeys();
                    if (!rs.next()) {
                        conn.rollback();
                        JOptionPane.showMessageDialog(this, "Gagal menyimpan pesanan.");
                        return;
                    }
                    orderId = rs.getInt(1);
                }

                try (PreparedStatement psItem = conn.prepareStatement(
                        "INSERT INTO order_items (order_id, produk_id, quantity, subtotal) VALUES (?, ?, ?, ?)")) {
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, sepeda.getId());
                    psItem.setInt(3, qty);
                    psItem.setDouble(4, total);
                    psItem.executeUpdate();
                }

                try (PreparedStatement psStok = conn
                        .prepareStatement("UPDATE produk SET stok = stok - ? WHERE produk_id = ?")) {
                    psStok.setInt(1, qty);
                    psStok.setInt(2, sepeda.getId());
                    psStok.executeUpdate();
                }

                try (PreparedStatement psRiwayat = conn.prepareStatement(
                        "INSERT INTO riwayat_transaksi (user_id, produk_id, jumlah, total_harga) VALUES (?, ?, ?, ?)")) {
                    psRiwayat.setInt(1, userId);
                    psRiwayat.setInt(2, sepeda.getId());
                    psRiwayat.setInt(3, qty);
                    psRiwayat.setDouble(4, total);
                    psRiwayat.executeUpdate();
                }

                conn.commit();

                // Panggil class NotaTransaksi
                NotaFrame notaFrame = new NotaFrame(nama, sepeda.getNama(), qty, sepeda.getHarga(), total, metode,
                        catatan);
                notaFrame.setAlwaysOnTop(true); // tampil di depan
                notaFrame.setVisible(true);
                dispose(); // tutup checkout
                new DashboardFrame(userId, fullName);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage());
            }
        });

        JButton backBtn = new JButton("Kembali");
        backBtn.setBounds(20, 640, 100, 30);
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> {
            dispose();
            new DashboardFrame(userId, fullName);
        });
        add(backBtn);

        setVisible(true);
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(224, 255, 248), 0, getHeight(),
                    new Color(255, 240, 220));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
