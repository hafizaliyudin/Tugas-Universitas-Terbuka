import java.util.*;

/*
 * Tugas Praktik 3 - Struktur Data
 * Pencarian angka n pada graf menggunakan DFS dan BFS.
 *
 * Graf (tidak berarah), 8 node a1..a8. Angka di dalam kurung = nilai node.
 * Angka di garis = bobot/nilai hubungan antar node.
 * XX = 2 digit terakhir NIM.
 *
 *                 a1(12)
 *               /        \
 *           XX /          \ 15
 *             /            \
 *         a2(7)            a3(25)
 *         /   \            /    \
 *     10 /     \ 20    XX /      \ 30
 *       /       \        /        \
 *   a4(9)     a5(33)  a6(XX)     a7(41)
 *               \      /
 *             25 \    / 40
 *                 \  /
 *                a8(16)
 */
public class Tugas3 {

    // GANTI dengan NIM kamu. Program memakai 2 digit terakhirnya.
    static final String NIM = "058005434";

    // ---------- STRUKTUR GRAF ----------
    static class Edge {
        String tujuan;
        int bobot;

        Edge(String tujuan, int bobot) {
            this.tujuan = tujuan;
            this.bobot = bobot;
        }
    }

    // nilai tiap node, misal a1 -> 12
    static Map<String, Integer> nilaiNode = new LinkedHashMap<>();
    // adjacency list: node -> daftar tetangga
    static Map<String, List<Edge>> adj = new LinkedHashMap<>();

    static void tambahNode(String nama, int nilai) {
        nilaiNode.put(nama, nilai);
        adj.put(nama, new ArrayList<>());
    }

    // graf tidak berarah -> sisi ditambahkan ke dua arah
    static void tambahSisi(String a, String b, int bobot) {
        adj.get(a).add(new Edge(b, bobot));
        adj.get(b).add(new Edge(a, bobot));
    }

    static String node(String nama) {
        return nama + "(" + nilaiNode.get(nama) + ")";
    }

    public static void main(String[] args) {
        int duaDigitNim = Integer.parseInt(NIM.substring(NIM.length() - 2));

        // 8 node, a6 berisi 2 digit terakhir NIM
        tambahNode("a1", 12);
        tambahNode("a2", 7);
        tambahNode("a3", 25);
        tambahNode("a4", 9);
        tambahNode("a5", 33);
        tambahNode("a6", duaDigitNim);
        tambahNode("a7", 41);
        tambahNode("a8", 16);

        // hubungan antar node + bobotnya (dua sisi memakai 2 digit NIM)
        tambahSisi("a1", "a2", duaDigitNim);
        tambahSisi("a1", "a3", 15);
        tambahSisi("a2", "a4", 10);
        tambahSisi("a2", "a5", 20);
        tambahSisi("a3", "a6", duaDigitNim);
        tambahSisi("a3", "a7", 30);
        tambahSisi("a5", "a8", 25);
        tambahSisi("a6", "a8", 40);

        int n = duaDigitNim; // angka yang dicari

        System.out.println("NIM                 : " + NIM);
        System.out.println("2 digit akhir NIM   : " + duaDigitNim);
        System.out.println("Angka yang dicari n : " + n);
        tampilkanGraf();

        dfs("a1", n);
        bfs("a1", n);
    }

    static void tampilkanGraf() {
        System.out.println("\n=== GRAF (Adjacency List) ===");
        for (String v : adj.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (Edge e : adj.get(v)) {
                sb.append(node(e.tujuan)).append("[bobot ").append(e.bobot).append("]  ");
            }
            System.out.printf("%-7s -> %s%n", node(v), sb.toString().trim());
        }
    }

    // ---------- DEPTH-FIRST SEARCH (pakai STACK) ----------
    static void dfs(String awal, int n) {
        System.out.println("\n==============================================");
        System.out.println(" DEPTH-FIRST SEARCH (DFS) - mencari angka " + n);
        System.out.println("==============================================");

        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();
        Map<String, String> parent = new HashMap<>();

        stack.push(awal);
        System.out.println("Mulai: push " + awal + " ke stack. Stack = " + stack);

        int langkah = 1;
        while (!stack.isEmpty()) {
            String sekarang = stack.pop();
            if (visited.contains(sekarang)) {
                System.out.println("\n(" + sekarang + " sudah dikunjungi, dilewati)");
                continue;
            }
            visited.add(sekarang);

            System.out.println("\nLangkah " + langkah++ + ":");
            System.out.println("  Pop " + node(sekarang) + " dari stack -> kunjungi");
            System.out.println("  Cek: " + nilaiNode.get(sekarang) + " == " + n + " ? "
                    + (nilaiNode.get(sekarang) == n ? "YA" : "tidak"));

            if (nilaiNode.get(sekarang) == n) {
                selesai("DFS", sekarang, new ArrayList<>(visited), parent);
                return;
            }

            // tetangga dimasukkan terbalik supaya tetangga pertama diproses lebih dulu
            List<Edge> tetangga = adj.get(sekarang);
            List<String> dipush = new ArrayList<>();
            for (int i = tetangga.size() - 1; i >= 0; i--) {
                String t = tetangga.get(i).tujuan;
                if (!visited.contains(t)) {
                    stack.push(t);
                    // node yang terakhir di-push akan di-pop duluan, jadi induknya ditimpa
                    parent.put(t, sekarang);
                    dipush.add(t);
                }
            }
            System.out.println("  Push tetangga yang belum dikunjungi: "
                    + (dipush.isEmpty() ? "(tidak ada)" : dipush));
            System.out.println("  Stack (atas -> bawah) = " + stack);
            System.out.println("  Sudah dikunjungi      = " + visited);
        }
        tidakDitemukan("DFS", n, new ArrayList<>(visited));
    }

    // ---------- BREADTH-FIRST SEARCH (pakai QUEUE) ----------
    static void bfs(String awal, int n) {
        System.out.println("\n==============================================");
        System.out.println(" BREADTH-FIRST SEARCH (BFS) - mencari angka " + n);
        System.out.println("==============================================");

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new LinkedHashSet<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> level = new HashMap<>();
        List<String> urutan = new ArrayList<>(); // urutan node yang benar-benar dicek

        queue.add(awal);
        visited.add(awal); // BFS: tandai saat masuk antrian
        level.put(awal, 0);
        System.out.println("Mulai: enqueue " + awal + " ke queue. Queue = " + queue);

        int langkah = 1;
        while (!queue.isEmpty()) {
            String sekarang = queue.poll();
            urutan.add(sekarang);

            System.out.println("\nLangkah " + langkah++ + " (level " + level.get(sekarang) + "):");
            System.out.println("  Dequeue " + node(sekarang) + " dari depan queue -> kunjungi");
            System.out.println("  Cek: " + nilaiNode.get(sekarang) + " == " + n + " ? "
                    + (nilaiNode.get(sekarang) == n ? "YA" : "tidak"));

            if (nilaiNode.get(sekarang) == n) {
                selesai("BFS", sekarang, urutan, parent);
                return;
            }

            List<String> dimasukkan = new ArrayList<>();
            for (Edge e : adj.get(sekarang)) {
                if (!visited.contains(e.tujuan)) {
                    visited.add(e.tujuan);
                    parent.put(e.tujuan, sekarang);
                    level.put(e.tujuan, level.get(sekarang) + 1);
                    queue.add(e.tujuan);
                    dimasukkan.add(e.tujuan);
                }
            }
            System.out.println("  Enqueue tetangga yang belum dikunjungi: "
                    + (dimasukkan.isEmpty() ? "(tidak ada)" : dimasukkan));
            System.out.println("  Queue (depan -> belakang) = " + queue);
        }
        tidakDitemukan("BFS", n, urutan);
    }

    // ---------- HASIL ----------
    static void selesai(String metode, String ketemu, List<String> urutan, Map<String, String> parent) {
        LinkedList<String> jalur = new LinkedList<>();
        for (String v = ketemu; v != null; v = parent.get(v)) {
            jalur.addFirst(v);
        }
        System.out.println("\n>>> HASIL " + metode + ": angka " + nilaiNode.get(ketemu)
                + " DITEMUKAN di node " + ketemu);
        System.out.println(">>> Jalur dari a1      : " + String.join(" -> ", jalur));
        System.out.println(">>> Urutan kunjungan   : " + String.join(" -> ", urutan));
        System.out.println(">>> Jumlah node dicek  : " + urutan.size());
    }

    static void tidakDitemukan(String metode, int n, List<String> urutan) {
        System.out.println("\n>>> HASIL " + metode + ": angka " + n + " TIDAK ditemukan.");
        System.out.println(">>> Semua node sudah dikunjungi: " + urutan);
    }
}
