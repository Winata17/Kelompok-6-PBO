package UAS;
//subclass dari Sepeda
// Kelas Roadbike mewarisi dari kelas Sepeda
public class Roadbike extends Sepeda {
    public Roadbike(int id, String nama, double harga, int stok, String pathGambar, String deskripsi) {
        super(id, nama, harga, stok, pathGambar, deskripsi);
    }

    @Override
    public String getKategori() {
        return "Roadbike";
    }
}
