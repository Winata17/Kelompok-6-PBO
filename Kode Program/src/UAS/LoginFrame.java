package UAS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JButton loginButton, signupButton;

    public LoginFrame() {
        setTitle("LoginFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1140, 724);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        // Panel kiri (gambar)
        ImageIcon leftImage = new ImageIcon("src/UAS/GambarUAS/1.png");
        JLabel leftLabel = new JLabel(leftImage);
        leftLabel.setBounds(0, 0, 600, 724);
        add(leftLabel);

        // Panel kanan (form login)
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(600, 0, 540, 724);
        rightPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Selamat Datang Kembali!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(50, 60, 400, 30);
        rightPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Silakan login ke akun Anda.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setBounds(50, 100, 400, 25);
        rightPanel.add(subtitleLabel);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 150, 100, 20);
        rightPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(50, 175, 380, 35);
        rightPanel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50, 230, 100, 20);
        rightPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 255, 380, 35);
        rightPanel.add(passwordField);

        showPassword = new JCheckBox("Tampilkan Password");
        showPassword.setBounds(50, 295, 200, 20);
        showPassword.setBackground(Color.WHITE);
        showPassword.setFocusPainted(false);
        rightPanel.add(showPassword);

        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•');
        });

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 330, 380, 40);
        loginButton.setBackground(new Color(33, 150, 243)); // warna biru
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        rightPanel.add(loginButton);

        signupButton = new JButton("Belum punya akun? Daftar");
        signupButton.setBounds(50, 385, 380, 30);
        signupButton.setFocusPainted(false);
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setForeground(new Color(0, 120, 200));
        signupButton.setFont(new Font("Arial", Font.PLAIN, 14));
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rightPanel.add(signupButton);

        // Action
        loginButton.addActionListener(e -> loginUser());
        signupButton.addActionListener(e -> {
            dispose(); // tutup login
            new SignUpFrame(); // buka signup
        });

        add(rightPanel);
        setVisible(true);
    }

    private void loginUser() {
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email dan password tidak boleh kosong.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String role = rs.getString("role");

                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + fullName);
                dispose();

                if ("admin".equalsIgnoreCase(role)) {
                    dispose();
                    new AdminDashboardFrame(userId, fullName);
                } else {
                    dispose();
                    new DashboardFrame(userId, fullName);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email atau password salah.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan koneksi: " + ex.getMessage());
        }
    }
}
