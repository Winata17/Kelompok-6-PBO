package UAS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RiwayatTransaksiFrame extends JFrame {
    public RiwayatTransaksiFrame(int userId, String fullName) {
        setTitle("RiwayatTransaksiFrame");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new GradientPanel());
        setLayout(null);

        // Header
        JLabel header = new JLabel("RIWAYAT TRANSAKSI", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setBounds(0, 10, 800, 40);
        add(header);

        // Kolom tabel
        String[] kolom = {"No.", "Produk", "Jumlah", "Total Harga", "Tanggal", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 700, 340);
        add(scrollPane);

        // Ambil data dari database
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT r.id, p.name, r.jumlah, r.total_harga, r.tanggal, o.status " +
                     "FROM riwayat_transaksi r " +
                     "JOIN produk p ON r.produk_id = p.produk_id " +
                     "JOIN orders o ON o.user_id = r.user_id AND o.order_date = r.tanggal " +
                     "WHERE r.user_id = ?"
             )) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            int no = 1;
            while (rs.next()) {
                Object[] row = {
                        no++,
                        rs.getString("name"),
                        rs.getInt("jumlah"),
                        "Rp " + String.format("%,.0f", rs.getDouble("total_harga")),
                        rs.getString("tanggal"),
                        rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil riwayat:\n" + e.getMessage());
        }

        // Tombol Kembali
        JButton backBtn = new JButton("Kembali");
        backBtn.setBounds(30, 415, 100, 30);
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> {
            dispose();
            dispose();
            new DashboardFrame(userId, fullName);
        });
        add(backBtn);

        setVisible(true);
    }

    // Background gradasi
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(224, 255, 248), 0, getHeight(), new Color(255, 240, 220));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
