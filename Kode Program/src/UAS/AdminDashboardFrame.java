package UAS;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {
    public AdminDashboardFrame(int userId, String fullName) {
        setTitle("AdminDashboardFrame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientPanel());
        setLayout(null);
        setResizable(false);

        // Background panel (opsional)
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(200, 100, 400, 350); // Konten di tengah frame
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(contentPanel);

        JLabel welcomeLabel = new JLabel("Selamat Datang, Admin " + fullName, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(30, 30, 340, 30);
        contentPanel.add(welcomeLabel);

        JButton editProdukBtn = new JButton("Edit Produk");
        editProdukBtn.setBounds(50, 90, 300, 40);
        editProdukBtn.setBackground(new Color(72, 201, 176));
        editProdukBtn.setForeground(Color.WHITE);
        editProdukBtn.setFocusPainted(false);
        contentPanel.add(editProdukBtn);

        JButton kelolaPesananBtn = new JButton("Kelola Status Pesanan");
        kelolaPesananBtn.setBounds(50, 150, 300, 40);
        kelolaPesananBtn.setBackground(new Color(241, 196, 15));
        kelolaPesananBtn.setForeground(Color.BLACK);
        kelolaPesananBtn.setFocusPainted(false);
        contentPanel.add(kelolaPesananBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(50, 210, 300, 40);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        contentPanel.add(logoutBtn);

        // Aksi tombol
        editProdukBtn.addActionListener(e -> {
            dispose();
            new EditProdukFrame(userId, fullName);
        });

        kelolaPesananBtn.addActionListener(e -> {
            dispose();
            new KelolaPesananFrame(userId, fullName);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
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
