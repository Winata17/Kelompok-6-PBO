package UAS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class ProdukFrame extends JFrame {
    private int id;
    private int kategoriId;
    private String namaKategori;
    private int userId;          // tambah userId
    private String fullName;     // tambah fullName

    public ProdukFrame(int kategoriId, String namaKategori, int userId, String fullName) {
        this.kategoriId = kategoriId;
        this.namaKategori = namaKategori;
        this.userId = userId;
        this.fullName = fullName;

        setTitle("ProdukFrame");
        setSize(1140, 724);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new GradientPanel());
        setLayout(null);

        // Pass userId dan fullName ke header
        JPanel headerPanel = Template.buatHeader(this, userId, fullName);
        add(headerPanel);

        JLabel header = new JLabel(""+ namaKategori.toUpperCase(), JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setBounds(0, 100, 1140, 40);
        add(header);

        JPanel container = new JPanel(null);
        container.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 140, 1080, 480);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        ArrayList<Sepeda> daftarSepeda = ambilDataProdukDariDatabase();

        int x = 20; // posisi awal
        for (Sepeda sepeda : daftarSepeda) {
            JPanel card = buatCardSepeda(sepeda);
            card.setBounds(x, 50, 220, 350);
            container.add(card);
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            x += 240;
        }
        container.setPreferredSize(new Dimension(x, 420));

        JButton backBtn = new JButton("Kembali");
        backBtn.setBounds(20, 640, 100, 30);
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> {
            dispose();
            new DashboardFrame(userId, fullName);  // harus passing userId & fullName juga
        });
        add(backBtn);
        setVisible(true);
    }

    private JPanel buatCardSepeda(Sepeda sepeda) {
        JPanel card = new JPanel(null);
        card.setSize(220, 350);
        card.setBackground(Color.WHITE);

        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(sepeda.getPathGambar());
            Image scaled = icon.getImage().getScaledInstance(218, 218, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel = new JLabel("Gambar tidak ditemukan", JLabel.CENTER);
        }
        imageLabel.setBounds(1, 1, 218, 218);
        imageLabel.setOpaque(false);
        card.add(imageLabel);

        JLabel namaLabel = new JLabel(sepeda.getNama(), JLabel.CENTER);
        namaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        namaLabel.setBounds(10, 240, 200, 20);
        card.add(namaLabel);

        JLabel kategoriLabel = new JLabel(sepeda.getKategori(), JLabel.CENTER);
        kategoriLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        kategoriLabel.setBounds(10, 275, 200, 20);
        card.add(kategoriLabel);

        JLabel hargaLabel = new JLabel("Rp " + String.format("%,.0f", sepeda.getHarga()), JLabel.CENTER);
        hargaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hargaLabel.setBounds(10, 295, 200, 20);
        card.add(hargaLabel);

        JButton checkout = new JButton("Checkout");
        checkout.setBounds(0, 320, 220, 30);
        checkout.setBackground(new Color(255, 153, 51));
        checkout.setForeground(Color.WHITE);
        checkout.setFont(new Font("Arial", Font.BOLD, 13));
        checkout.setFocusPainted(false);
        checkout.addActionListener(e -> {
        new CheckoutFrame(sepeda, userId, fullName);
        dispose();
        });
        card.add(checkout);

        return card;
        }

        private ArrayList<Sepeda> ambilDataProdukDariDatabase() {
            ArrayList<Sepeda> list = new ArrayList<>();
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_sepeda", "root", "");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM produk WHERE kategori_id = " + kategoriId);

                while (rs.next()) {
        int id = rs.getInt("produk_id"); // <== INI PENTING!
        String nama = rs.getString("name");
        double harga = rs.getDouble("harga");
        int stok = rs.getInt("stok");
        String deskripsi = rs.getString("deskripsi");
        String pathGambar = rs.getString("image_path");

        if (pathGambar == null || pathGambar.trim().isEmpty()) {
            pathGambar = "src/UAS/GambarUAS/default.jpg";
        }

        Sepeda sepeda;
        if (kategoriId == 1) {
            sepeda = new Roadbike(id, nama, harga, stok, pathGambar, deskripsi);
        } else if (kategoriId == 2) {
            sepeda = new Fixie(id, nama, harga, stok, pathGambar, deskripsi);
        } else if (kategoriId == 3) {
            sepeda = new SepedaListrik(id, nama, harga, stok, pathGambar, deskripsi);
        } else {
            sepeda = new Sepeda(id, nama, harga, stok, pathGambar, deskripsi);
        }

        list.add(sepeda);
        }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil data:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
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
