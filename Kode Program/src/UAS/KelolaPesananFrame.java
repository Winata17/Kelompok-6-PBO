package UAS;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class KelolaPesananFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public KelolaPesananFrame(int userId, String fullName) {
        setTitle("KelolaPesananFrame");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new GradientPanel());
        setLayout(null);

        JLabel header = new JLabel("KELOLA PESANAN", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBounds(0, 20, 1000, 30);
        add(header);

        String[] kolom = {"Order ID", "Nama Pembeli", "Tanggal", "Total", "Status"};
        model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // hanya kolom "Status" yang bisa diedit
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 900, 350);
        add(scrollPane);

        // Editor combo box untuk kolom Status
        String[] statusOptions = {"pending", "diproses", "dikirim", "selesai"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(statusCombo));

        JButton simpanBtn = new JButton("Simpan Status");
        simpanBtn.setBounds(50, 440, 200, 40);
        simpanBtn.setBackground(new Color(241, 196, 15));
        simpanBtn.setForeground(Color.BLACK);
        add(simpanBtn);

        JButton kembaliBtn = new JButton("Kembali");
        kembaliBtn.setBounds(270, 440, 150, 40);
        kembaliBtn.setBackground(new Color(100, 149, 237));
        kembaliBtn.setForeground(Color.WHITE);
        add(kembaliBtn);

        simpanBtn.addActionListener(e -> {
            simpanStatus();
            reloadData(); // refresh isi tabel
        });

        kembaliBtn.addActionListener(e -> {
            dispose();
            new AdminDashboardFrame(userId, fullName);
        });

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0); // clear table

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT o.order_id, u.full_name, o.order_date, o.total_price, o.status " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.user_id " +
                     "WHERE EXISTS (SELECT 1 FROM order_items oi WHERE oi.order_id = o.order_id)"
             )) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("full_name"),
                        rs.getString("order_date"),
                        "Rp " + String.format("%,.0f", rs.getDouble("total_price")),
                        rs.getString("status")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data pesanan: " + ex.getMessage());
        }
    }

    private void reloadData() {
        SwingUtilities.invokeLater(this::loadData);
    }

    private void simpanStatus() {
        try (Connection conn = DBConnection.connect()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int orderId = Integer.parseInt(model.getValueAt(i, 0).toString());
                String status = model.getValueAt(i, 4).toString();

                String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, status);
                    ps.setInt(2, orderId);
                    ps.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "Status pesanan berhasil diperbarui!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan status: " + ex.getMessage());
        }
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(224, 255, 248);
            Color color2 = new Color(255, 240, 220);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
