package UAS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpFrame extends JFrame {
    private JTextField usernameField, emailField, fullNameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public SignUpFrame() {
        setTitle("SignUpFrame");
        setSize(1140, 724);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JLabel titleLabel = new JLabel("Buat Akun Baru");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 40, 400, 30);
        rightPanel.add(titleLabel);

        JLabel fullNameLabel = new JLabel("Nama Lengkap");
        fullNameLabel.setBounds(50, 90, 100, 20);
        rightPanel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(50, 115, 380, 35);
        rightPanel.add(fullNameField);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 165, 100, 20);
        rightPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 190, 380, 35);
        rightPanel.add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 240, 100, 20);
        rightPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(50, 265, 380, 35);
        rightPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 315, 100, 20);
        rightPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 340, 380, 35);
        rightPanel.add(passwordField);

        registerButton = new JButton("Daftar");
        registerButton.setBounds(50, 390, 380, 40);
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        rightPanel.add(registerButton);

        backButton = new JButton("Sudah punya akun? Login");
        backButton.setBounds(50, 440, 380, 30);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(new Color(0, 120, 200));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rightPanel.add(backButton);

        add(rightPanel);
        setVisible(true);

        // Event handler
        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        String fullName = fullNameField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            // Cek apakah email sudah digunakan
            String cekEmail = "SELECT * FROM users WHERE email = ?";
            PreparedStatement psCek = conn.prepareStatement(cekEmail);
            psCek.setString(1, email);
            ResultSet rs = psCek.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Email sudah digunakan. Gunakan email lain.");
                return;
            }

            // Cek apakah username sudah digunakan
            String cekUsername = "SELECT * FROM users WHERE username = ?";
            PreparedStatement psCekUser = conn.prepareStatement(cekUsername);
            psCekUser.setString(1, username);
            ResultSet rsUser = psCekUser.executeQuery();

            if (rsUser.next()) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan. Gunakan yang lain.");
                return;
            }

            // Insert user baru
            String sql = "INSERT INTO users (username, email, password, full_name, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, fullName);
            stmt.setString(5, "user");
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pendaftaran berhasil! Silakan login.");
            dispose();
            new LoginFrame();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan koneksi: " + ex.getMessage());
        }
    }
}