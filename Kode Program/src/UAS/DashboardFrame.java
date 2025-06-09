package UAS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {
    private int userId;
    private String fullName;

    public DashboardFrame(int userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;

        setTitle("DashboardFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1140, 724);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set content pane dengan background gradient
        setContentPane(new GradientPanel());
        setLayout(null);

        // Header dari Template
        JPanel headerPanel = Template.buatHeader(this, userId, fullName);
        headerPanel.setBounds(0, 0, 1140, 70);
        add(headerPanel);

        // Footer dari Template
        JPanel footerPanel = Template.buatFooter();
        footerPanel.setBounds(0, 600, 1140, 90);
        add(footerPanel);

        // Ambil kategori dari database
        ArrayList<Kategori> kategoriList = getKategoriListFromDB();

        int x = 50;
        for (Kategori kategori : kategoriList) {
            String namaKategori = kategori.nama;
            String imgPath = kategori.imagePath;


        // Panel gambar
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(x, 100, 300, 400);
        imageLabel.setLayout(null); // wajib supaya kita bisa add tombol di posisi bebas

        try {
            ImageIcon icon = new ImageIcon(imgPath);
            Image img = icon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            imageLabel.setText("Gambar tidak ditemukan");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Tombol kategori, ditaruh di dalam imageLabel
        JButton btnKategori = new JButton(namaKategori);
        btnKategori.setBounds(0, 360, 300, 40); // posisinya di bagian bawah gambar
        btnKategori.setBackground(new Color(255, 153, 51));
        btnKategori.setForeground(Color.BLACK);
        btnKategori.setFont(new Font("Arial", Font.BOLD, 14));
        btnKategori.setFocusPainted(false);
        btnKategori.setOpaque(true);
        btnKategori.setBorderPainted(false);

        int kategoriId = kategori.id;
        btnKategori.addActionListener(e -> {
            dispose();
            new ProdukFrame(kategoriId, namaKategori, userId, fullName);
        });

        // Tambahkan tombol ke dalam gambar
        imageLabel.add(btnKategori);

        // Tambahkan gambar ke frame
        add(imageLabel);

        x += 370;
    }
        setVisible(true);
    }

    private ArrayList<Kategori> getKategoriListFromDB() {
        ArrayList<Kategori> list = new ArrayList<>();
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT kategori_id, nama_kategori, image_path FROM kategori")) {

            while (rs.next()) {
                int id = rs.getInt("kategori_id");
                String nama = rs.getString("nama_kategori");
                String path = rs.getString("image_path");
                list.add(new Kategori(id, nama, path));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil kategori: " + e.getMessage());
        }
        return list;
    }

    class Kategori {
        int id;
        String nama;
        String imagePath;

        public Kategori(int id, String nama, String imagePath) {
            this.id = id;
            this.nama = nama;
            this.imagePath = imagePath;
        }
    }

    // Panel dengan background gradient
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

    // public static void main(String[] args) {
    //     // Contoh pemanggilan, sesuaikan dengan user yang login
    //     new DashboardFrame(1, "Nama User");
    // }
}
