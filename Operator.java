import java.util.Scanner;

public class Operator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ================= ARRAY =================
        String[] daftarGolongan = {"A", "B", "C"};
        double[] arrayGaji = {5000000, 6500000, 9500000};
        double[] arrayLembur = {30, 32, 34, 36, 38};

        System.out.println("====================================================");
        System.out.println("            PROGRAM PENGGAJIAN KARYAWAN             ");
        System.out.println("====================================================");

        // ================= INPUT =================
        System.out.print("Masukkan Golongan Karyawan (A / B / C) : ");
        String golongan = sc.nextLine().trim().toUpperCase();

        System.out.print("Masukkan Jam Lembur (0 jika tidak)     : ");
        int jamLembur = sc.nextInt();

        // ================= PROSES GAJI =================
        double gajiPokok = 0;

        // Mengambil gaji dari array berdasarkan golongan
        if (golongan.equals(daftarGolongan[0])) {
            gajiPokok = arrayGaji[0];

        } else if (golongan.equals(daftarGolongan[1])) {
            gajiPokok = arrayGaji[1];

        } else if (golongan.equals(daftarGolongan[2])) {
            gajiPokok = arrayGaji[2];

        } else {
            System.out.println("\n[!] Golongan tidak valid!");
            sc.close();
            return;
        }

        // ================= PROSES LEMBUR =================
        double persenLembur = 0;

        if (jamLembur == 1) {
            persenLembur = arrayLembur[0];

        } else if (jamLembur == 2) {
            persenLembur = arrayLembur[1];

        } else if (jamLembur == 3) {
            persenLembur = arrayLembur[2];

        } else if (jamLembur == 4) {
            persenLembur = arrayLembur[3];

        } else if (jamLembur >= 5) {
            persenLembur = arrayLembur[4];

        } else {
            persenLembur = 0;
        }

        // ================= PERHITUNGAN =================
        double gajiLembur = gajiPokok * (persenLembur / 100);
        double totalPenghasilan = gajiPokok + gajiLembur;

        // ================= OUTPUT =================
        System.out.println("\n====================================================");
        System.out.println("                 RINCIAN PENGHASILAN                ");
        System.out.println("====================================================");

        System.out.printf("  Golongan Karyawan   : %s%n", golongan);
        System.out.printf("  Gaji Pokok          : Rp %,.0f%n", gajiPokok);

        if (jamLembur > 0) {

            System.out.printf("  Jam Lembur          : %d Jam%n", jamLembur);
            System.out.printf("  Persentase Lembur   : %.0f%%%n", persenLembur);
            System.out.printf("  Gaji Lembur         : Rp %,.0f%n", gajiLembur);

        } else {

            System.out.println("  Jam Lembur          : Tidak Lembur");
            System.out.println("  Persentase Lembur   : 0%");
            System.out.println("  Gaji Lembur         : Rp 0");

        }

        System.out.println("----------------------------------------------------");
        System.out.printf("  TOTAL PENGHASILAN  : Rp %,.0f%n", totalPenghasilan);
        System.out.println("====================================================");
        System.out.println("          Terima Kasih Telah Menggunakan Program    ");
        System.out.println("====================================================");

        sc.close();
    }
}