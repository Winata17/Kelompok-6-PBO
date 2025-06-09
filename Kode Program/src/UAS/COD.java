package UAS;

// superclass dari metode pembayaran
// Kelas COD mewarisi dari interface MetodePembayaran
public class COD implements MetodePembayaran {
    public double hitungTotal(double totalAwal) {
        return totalAwal; // Tanpa diskon
    }
}
