package UAS;

// superclass Sepeda
public class Sepeda {
    protected int id;
    protected String nama;
    protected double harga;
    protected int stok;
    protected String pathGambar;
    protected String deskripsi;

    public Sepeda(int id, String nama, double harga, int stok, String pathGambar, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.pathGambar = pathGambar;
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public String getPathGambar() {
        return pathGambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKategori() {
        return "Umum";
    }
}