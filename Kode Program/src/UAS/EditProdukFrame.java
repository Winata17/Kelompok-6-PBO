package UAS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EditProdukFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public EditProdukFrame(int userId, String fullName) {
        setTitle("EditProdukFrame");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("EDIT PRODUK", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(0, 20, 1000, 30);
        add(title);

        String[] kolom = {"ID", "Nama Produk", "Kategori ID", "Harga", "Stok", "Deskripsi", "Image Path"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 900, 350);
        add(scrollPane);

        // Load data
        loadProduk();

        // Tombol
        JButton simpanBtn = new JButton("Simpan Perubahan");
        JButton tambahBtn = new JButton("Tambah Produk");
        JButton hapusBtn = new JButton("Hapus Produk");
        JButton kembaliBtn = new JButton("Kembali");

        simpanBtn.setBounds(50, 440, 180, 40);
        tambahBtn.setBounds(250, 440, 180, 40);
        hapusBtn.setBounds(450, 440, 180, 40);
        kembaliBtn.setBounds(700, 440, 180, 40);

        simpanBtn.setBackground(new Color(46, 204, 113));
        tambahBtn.setBackground(new Color(52, 152, 219));
        hapusBtn.setBackground(new Color(231, 76, 60));
        kembaliBtn.setBackground(new Color(149, 165, 166));

        Color textColor = Color.WHITE;
        simpanBtn.setForeground(textColor);
        tambahBtn.setForeground(textColor);
        hapusBtn.setForeground(textColor);
        kembaliBtn.setForeground(textColor);

        add(simpanBtn);
        add(tambahBtn);
        add(hapusBtn);
        add(kembaliBtn);

        // Event Simpan
        simpanBtn.addActionListener(e -> simpanPerubahan());

        // Event Tambah
        tambahBtn.addActionListener(e -> model.addRow(new Object[]{"", "", "", "", "", "", ""}));

        // Event Hapus
        hapusBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String idStr = String.valueOf(model.getValueAt(row, 0)).trim();
                if (!idStr.isEmpty()) {
                    try (Connection conn = DBConnection.connect();
                         PreparedStatement ps = conn.prepareStatement("DELETE FROM produk WHERE produk_id = ?")) {
                        ps.setInt(1, Integer.parseInt(idStr));
                        ps.executeUpdate();
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Produk berhasil dihapus.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Gagal menghapus produk: " + ex.getMessage());
                    }
                } else {
                    model.removeRow(row); // baris baru belum tersimpan, cukup hapus dari tabel
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris produk yang ingin dihapus.");
            }
        });

        // Event Kembali
        kembaliBtn.addActionListener(e -> {
            dispose();
            new AdminDashboardFrame(userId, fullName);
        });

        setVisible(true);
    }

    private void loadProduk() {
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produk")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("produk_id"),
                        rs.getString("name"),
                        rs.getInt("kategori_id"),
                        String.format("%.0f", rs.getDouble("harga")),
                        rs.getInt("stok"),
                        rs.getString("deskripsi"),
                        rs.getString("image_path")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat produk: " + e.getMessage());
        }
    }

    private void simpanPerubahan() {
    try (Connection conn = DBConnection.connect()) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String idStr = String.valueOf(model.getValueAt(i, 0)).trim();
            String name = String.valueOf(model.getValueAt(i, 1)).trim();
            String kategoriStr = String.valueOf(model.getValueAt(i, 2)).trim();
            String hargaStr = String.valueOf(model.getValueAt(i, 3)).trim();
            String stokStr = String.valueOf(model.getValueAt(i, 4)).trim();
            String deskripsi = String.valueOf(model.getValueAt(i, 5)).trim();
            String imagePath = String.valueOf(model.getValueAt(i, 6)).trim();

            // Validasi
            if (name.isEmpty() || kategoriStr.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lengkapi semua data (kecuali ID) di baris ke-" + (i + 1));
                return;
            }

            int kategoriId = Integer.parseInt(kategoriStr);
            double harga = Double.parseDouble(hargaStr);
            int stok = Integer.parseInt(stokStr);

            boolean isBaru = false;
            int id = -1;

            try {
                id = Integer.parseInt(idStr); // akan gagal kalau kosong
            } catch (NumberFormatException e) {
                isBaru = true;
            }

            if (isBaru) {
                // INSERT PRODUK BARU
                String sql = "INSERT INTO produk (name, kategori_id, harga, stok, deskripsi, image_path) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, kategoriId);
                ps.setDouble(3, harga);
                ps.setInt(4, stok);
                ps.setString(5, deskripsi);
                ps.setString(6, imagePath);
                ps.executeUpdate();
            } else {
                // UPDATE PRODUK LAMA
                String sql = "UPDATE produk SET name=?, kategori_id=?, harga=?, stok=?, deskripsi=?, image_path=? WHERE produk_id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, kategoriId);
                ps.setDouble(3, harga);
                ps.setInt(4, stok);
                ps.setString(5, deskripsi);
                ps.setString(6, imagePath);
                ps.setInt(7, id);
                ps.executeUpdate();
            }
        }

        JOptionPane.showMessageDialog(this, "Perubahan berhasil disimpan!");
        model.setRowCount(0); // reset tabel
        loadProduk();         // reload dari database
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        e.printStackTrace();
    }
}


}
