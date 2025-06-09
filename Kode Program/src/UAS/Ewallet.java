package UAS;

//subclass dari metode pembayaran
// Kelas Ewallet mewarisi dari interface MetodePembayaran
public class Ewallet implements MetodePembayaran {
    public double hitungTotal(double totalAwal) {
        return totalAwal * 0.95; // Diskon 5%
    }
}
