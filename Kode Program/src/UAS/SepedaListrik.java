package UAS;

//subclass dari Sepeda
// Kelas SepedaListrik mewarisi dari kelas Sepeda
public class SepedaListrik extends Sepeda {
    public SepedaListrik(int id, String nama, double harga, int stok, String pathGambar, String deskripsi) {
        super(id, nama, harga, stok, pathGambar, deskripsi);
    }

    @Override
    public String getKategori() {
        return "Sepeda Listrik";
    }
}
