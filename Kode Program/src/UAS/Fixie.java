package UAS;

//subclass dari Sepeda
// Kelas fixie mewarisi dari kelas Sepeda
public class Fixie extends Sepeda {
    public Fixie(int id, String nama, double harga, int stok, String pathGambar, String deskripsi) {
        super(id, nama, harga, stok, pathGambar, deskripsi);
    }

    @Override
    // Mengoverride method getKategori dari kelas Sepeda
    // terdapat di produkframe.java
    public String getKategori() {
        return "Sepeda Fixie";
    }
}
