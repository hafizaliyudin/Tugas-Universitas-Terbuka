import java.util.Scanner;

public class Operator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Array Gaji Pokok
        int[] gaji = {5000000, 6500000, 9500000};

        // Array Persentase Lembur
        double[] lembur = {0.30, 0.32, 0.34, 0.36, 0.38};

        String golongan;
        int jamLembur;
        int gajiPokok = 0;
        double persenLembur = 0;
        double gajiLembur;
        double totalGaji;

        System.out.println("================================================");
        System.out.println("         PROGRAM PERHITUNGAN GAJI KARYAWAN      ");
        System.out.println("================================================");

        // Input Golongan
        System.out.print("Masukkan Golongan Karyawan [A/B/C] : ");
        golongan = input.nextLine();

        // Menentukan Gaji Pokok
        if (golongan.equalsIgnoreCase("A")) {
            gajiPokok = gaji[0];
        } else if (golongan.equalsIgnoreCase("B")) {
            gajiPokok = gaji[1];
        } else if (golongan.equalsIgnoreCase("C")) {
            gajiPokok = gaji[2];
        } else {
            System.out.println("\nGolongan tidak valid!");
            return;
        }

        // Input Jam Lembur
        System.out.print("Masukkan Jumlah Jam Lembur        : ");
        jamLembur = input.nextInt();

        // Menentukan Persentase Lembur
        if (jamLembur == 1) {
            persenLembur = lembur[0];
        } else if (jamLembur == 2) {
            persenLembur = lembur[1];
        } else if (jamLembur == 3) {
            persenLembur = lembur[2];
        } else if (jamLembur == 4) {
            persenLembur = lembur[3];
        } else if (jamLembur >= 5) {
            persenLembur = lembur[4];
        } else {
            persenLembur = 0;
        }

        // Perhitungan
        gajiLembur = gajiPokok * persenLembur;
        totalGaji = gajiPokok + gajiLembur;

        // Output
        System.out.println("\n================================================");
        System.out.println("              HASIL PERHITUNGAN                 ");
        System.out.println("================================================");
        System.out.println("Golongan Karyawan  : " + golongan.toUpperCase());
        System.out.println("Gaji Pokok         : Rp " + gajiPokok);
        System.out.println("Jam Lembur         : " + jamLembur + " Jam");
        System.out.println("Persentase Lembur  : " + (persenLembur * 100) + "%");
        System.out.println("Gaji Lembur        : Rp " + gajiLembur);
        System.out.println("------------------------------------------------");
        System.out.println("Total Gaji         : Rp " + totalGaji);
        System.out.println("================================================");
        System.out.println("        Terima Kasih Telah Menggunakan Program  ");
        System.out.println("================================================");

    }
}