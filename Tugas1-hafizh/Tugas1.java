import java.util.LinkedList;

public class Tugas1 {

    public static void main(String[] args) {

        // 1️ Deklarasi variabel integer
        // akan menampilkan output dari struktur baris
        int StrukturBaris = 10;
        System.out.println("1. Nilai variabel StrukturBaris: " + StrukturBaris);

        // 2️ Deklarasi variabel String
        // akan menampilkan output dari tipe data string "KataBaru"
        String KataBaru = "Muhamad Hafizh Aliyuddin";
        System.out.println("2. Isi variabel KataBaru: " + KataBaru);

        // 3️ Deklarasi array 1 dimensi
        // akan menampilkan output dari array 1 dimensi dari 4 angka
        int[] empatAngka = { 7, 10, 20, 23 };
        System.out.print("3. Isi array empatAngka: ");
        for (int i = 0; i < empatAngka.length; i++) {
            System.out.print(empatAngka[i] + " ");
        }
        System.out.println();

        // 4️ Deklarasi array 2 dimensi
        String[][] Angka = {
                { "1", "3", "5" },
                { "14", "19", "20" },
                { "22", "27", "29" }
        };

        System.out.println("4. Isi array dua dimensi Angka:");
        for (int i = 0; i < Angka.length; i++) {
            for (int j = 0; j < Angka[i].length; j++) {
                System.out.print(Angka[i][j] + " ");
            }
            System.out.println();
        }

        // 5️ Deklarasi Linked List
        LinkedList<Integer> listAngka = new LinkedList<>();
        listAngka.add(5);
        listAngka.add(19);
        listAngka.add(44);
        listAngka.add(60);
        listAngka.add(35);

        System.out.println("5. Isi Linked List listAngka: " + listAngka);
    }
}
