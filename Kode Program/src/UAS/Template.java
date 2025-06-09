package UAS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Template {

    // Method buatHeader dengan parameter yang benar
    public static JPanel buatHeader(JFrame parentFrame, int userId, String fullName) {
        JPanel headerPanel = new JPanel(null);
        headerPanel.setOpaque(false);

        headerPanel.setBounds(0, 0, 1140, 70);

        JLabel logoLabel = new JLabel("GOWEZ");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(Color.BLACK);
        logoLabel.setBounds(30, 20, 200, 30);
        headerPanel.add(logoLabel);

        JTextField searchBar = new JTextField();
        searchBar.setBounds(250, 20, 500, 30);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(searchBar);

        searchBar.addActionListener(e -> {
            String input = searchBar.getText().toLowerCase().trim();
            switch (input) {
                case "roadbike":
                case "road bike":
                    new ProdukFrame(1, "Roadbike", userId, fullName);
                    break;
                case "fixie":
                    new ProdukFrame(2, "Fixie", userId, fullName);
                    break;
                case "sepeda listrik":
                case "listrik":
                    new ProdukFrame(3, "Sepeda Listrik", userId, fullName);
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "Kategori tidak ditemukan. Coba ketik: roadbike, fixie, atau sepeda listrik.");
                    return;
            }
            parentFrame.dispose();
        });

        // Gunakan fullName jika ada, kalau tidak "User"
        String usernameToShow = (fullName != null && !fullName.isEmpty()) ? fullName : "User";

        // Label username
        JLabel usernameLabel = new JLabel(usernameToShow);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(860, 25, 200, 20);
        headerPanel.add(usernameLabel);

        // Tombol profil (ganti path ikon sesuai)
        JButton profileButton = new JButton(new ImageIcon("src/UAS/GambarUas/profile.png"));
        profileButton.setBounds(1020, 20, 40, 30);
        profileButton.setFocusPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setBorderPainted(false);
        headerPanel.add(profileButton);

        // Tombol menu (ikon hamburger)
        ImageIcon menuIcon = new ImageIcon("src/UAS/GambarUas/menu.png");
        JButton menuButton = new JButton(menuIcon);
        menuButton.setBounds(1070, 20, 40, 30);
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        headerPanel.add(menuButton);

        // Popup menu
        JPopupMenu menuPopup = new JPopupMenu();
        menuPopup.setBackground(Color.WHITE);
        menuPopup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        Font menuFont = new Font("Segoe UI", Font.PLAIN, 14);

        JMenuItem transaksiItem = new JMenuItem("Riwayat Transaksi  >");
        transaksiItem.setFont(menuFont);
        transaksiItem.setBackground(new Color(245, 245, 245));
        transaksiItem.setForeground(Color.DARK_GRAY);
        transaksiItem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        transaksiItem.setFocusPainted(false);

        JMenuItem akunItem = new JMenuItem("Akun Saya  >");
        akunItem.setFont(menuFont);
        akunItem.setBackground(new Color(245, 245, 245));
        akunItem.setForeground(Color.DARK_GRAY);
        akunItem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        akunItem.setFocusPainted(false);

        JMenuItem logoutItem = new JMenuItem("Logout  >");
        logoutItem.setFont(menuFont);
        logoutItem.setBackground(new Color(245, 245, 245));
        logoutItem.setForeground(Color.DARK_GRAY);
        logoutItem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutItem.setFocusPainted(false);

        menuPopup.add(transaksiItem);
        menuPopup.add(akunItem);
        menuPopup.add(logoutItem);

        menuButton.addActionListener(e -> {
            menuPopup.show(menuButton, -150, menuButton.getHeight());
        });

        transaksiItem.addActionListener(e -> {
            // Buka frame riwayat transaksi
            parentFrame.dispose();
            new RiwayatTransaksiFrame(userId, fullName);
        });

        akunItem.addActionListener(e -> {
            parentFrame.dispose();
            new ProfileFrame(userId, fullName);
        });

        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin logout?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                parentFrame.dispose();
                new LoginFrame();
            }
        });

        return headerPanel;
    }

    public static JPanel buatFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new GridLayout(1, 3));
        footerPanel.setBackground(Color.BLACK);

        JLabel kelompokLabel = new JLabel("Kelompok 6", SwingConstants.CENTER);
        kelompokLabel.setForeground(Color.WHITE);
        kelompokLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        footerPanel.add(kelompokLabel);

        JLabel kontakLabel = new JLabel("Kontak: kelompok6@gmail.com", SwingConstants.CENTER);
        kontakLabel.setForeground(Color.WHITE);
        kontakLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(kontakLabel);

        JLabel sistemLabel = new JLabel("Sistem Penjualan Sepeda | UAS OOP Java", SwingConstants.CENTER);
        sistemLabel.setForeground(Color.WHITE);
        sistemLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerPanel.add(sistemLabel);

        return footerPanel;
    }
}
