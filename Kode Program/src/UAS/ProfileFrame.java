package UAS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProfileFrame extends JFrame {
    public ProfileFrame(int userId, String fullName) {
        setTitle("ProfilFrame");
        setSize(1140, 724);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        // Panel kiri (gambar)
        ImageIcon leftImage = new ImageIcon("src/UAS/GambarUAS/1.png");
        JLabel leftLabel = new JLabel(leftImage);
        leftLabel.setBounds(0, 0, 600, 724);
        add(leftLabel);

        // Panel kanan
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(600, 0, 540, 724);
        rightPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Profil Pengguna");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 40, 400, 30);
        rightPanel.add(titleLabel);

        // Label dan Field
        JLabel fullNameLabel = new JLabel("Nama Lengkap");
        fullNameLabel.setBounds(50, 90, 100, 20);
        rightPanel.add(fullNameLabel);

        JTextField fullNameField = new JTextField();
        fullNameField.setBounds(50, 115, 380, 35);
        rightPanel.add(fullNameField);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 165, 100, 20);
        rightPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 190, 380, 35);
        rightPanel.add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 240, 100, 20);
        rightPanel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(50, 265, 380, 35);
        rightPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 315, 100, 20);
        rightPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 340, 380, 35);
        rightPanel.add(passwordField);

        JButton simpanBtn = new JButton("Simpan Perubahan");
        simpanBtn.setBounds(50, 390, 380, 40);
        simpanBtn.setBackground(new Color(0, 153, 76));
        simpanBtn.setForeground(Color.WHITE);
        simpanBtn.setFocusPainted(false);
        rightPanel.add(simpanBtn);

        JButton backButton = new JButton("Kembali ke Dashboard");
        backButton.setBounds(50, 440, 380, 35);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(100, 149, 237));
        backButton.setForeground(Color.WHITE);
        rightPanel.add(backButton);

        add(rightPanel);

        // Load data user dari database
        try (Connection conn = DBConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fullNameField.setText(rs.getString("full_name"));
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat profil: " + e.getMessage());
        }

        // Aksi Simpan Perubahan
        simpanBtn.addActionListener(e -> {
            String fullNameNew = fullNameField.getText().trim();
            String usernameNew = usernameField.getText().trim();
            String emailNew = emailField.getText().trim();
            String passNew = String.valueOf(passwordField.getPassword()).trim();

            if (fullNameNew.isEmpty() || usernameNew.isEmpty() || emailNew.isEmpty() || passNew.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            try (Connection conn = DBConnection.connect()) {
                // Cek apakah email sudah digunakan oleh user lain
                String checkEmailSql = "SELECT user_id FROM users WHERE email = ? AND user_id <> ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql);
                checkStmt.setString(1, emailNew);
                checkStmt.setInt(2, userId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this,
                            "Email sudah digunakan oleh pengguna lain. Gunakan email berbeda.");
                    return;
                }

                // Lanjut update jika email aman
                String updateSql = "UPDATE users SET username=?, email=?, password=?, full_name=? WHERE user_id=?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, usernameNew);
                updateStmt.setString(2, emailNew);
                updateStmt.setString(3, passNew);
                updateStmt.setString(4, fullNameNew);
                updateStmt.setInt(5, userId);

                int updated = updateStmt.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui!");
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui profil.");
                }

            } catch (SQLIntegrityConstraintViolationException dup) {
                JOptionPane.showMessageDialog(this, "Email sudah digunakan. Gunakan email lain.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Kesalahan koneksi:\n" + ex.getMessage());
            }
        });

        // Aksi kembali
        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame(userId, fullName);
        });

        setVisible(true);
    }
}
