package UAS;

// subclass dari metode pembayaran
// Kelas TransferBank mewarisi dari interface MetodePembayaran
public class TransferBank implements MetodePembayaran {
    public double hitungTotal(double totalAwal) {
        return totalAwal * 0.90; // Diskon 10%
    }
}
