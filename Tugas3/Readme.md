# TUGAS PRAKTIK 3 – STRUKTUR DATA

Nama  : Muhamad Hafizh Aliyuddin
NIM   : 058005434
Prodi : Sistem Informasi

Link video: ........................

---

## Cara menjalankan

1. Buka `Tugas3.java`, `NIM` sudah diisi 058005434.
2. Jalankan:

```bash
javac Tugas3.java
java Tugas3
```

> Semua contoh di bawah memakai 2 digit akhir NIM 058005434, yaitu **34**.

---

## Graf yang digunakan

Graf tidak berarah dengan 8 node (a1 – a8). Angka dalam kurung = nilai node,
angka di garis = nilai hubungan (bobot) antar node.
2 digit akhir NIM dipakai sebagai nilai node **a6** dan bobot sisi **a1–a2** serta **a3–a6**.

```
                 a1(12)
               /        \
           34 /          \ 15
             /            \
         a2(7)            a3(25)
         /   \            /    \
     10 /     \ 20    34 /      \ 30
       /       \        /        \
   a4(9)     a5(33)  a6(34)     a7(41)
               \      /
             25 \    / 40
                 \  /
                a8(16)
```

Adjacency list:

| Node   | Tetangga (bobot)                 |
|--------|----------------------------------|
| a1(12) | a2 (34), a3 (15)                 |
| a2(7)  | a1 (34), a4 (10), a5 (20)        |
| a3(25) | a1 (15), a6 (34), a7 (30)        |
| a4(9)  | a2 (10)                          |
| a5(33) | a2 (20), a8 (25)                 |
| a6(34) | a3 (34), a8 (40)                 |
| a7(41) | a3 (30)                          |
| a8(16) | a5 (25), a6 (40)                 |

Angka yang dicari: **n = 34**. Pencarian dimulai dari node **a1**.

---

## Soal 1 – Depth-First Search (DFS)

**Prinsip:** DFS menelusuri satu cabang sedalam mungkin dulu, baru mundur (backtrack)
ke cabang lain. DFS memakai **Stack** (LIFO – yang terakhir masuk, pertama keluar).

**Algoritma:**
1. Push node awal (a1) ke stack.
2. Selama stack tidak kosong:
   - Pop node teratas. Jika sudah pernah dikunjungi, lewati.
   - Tandai sebagai dikunjungi, lalu cek apakah nilainya sama dengan n.
   - Jika sama → **ditemukan**, berhenti.
   - Jika tidak → push semua tetangga yang belum dikunjungi (urutan terbalik supaya
     tetangga pertama diproses lebih dulu).
3. Jika stack kosong dan belum ketemu → angka tidak ada di graf.

**Proses pencarian n = 34:**

| Langkah | Pop (dicek) | Nilai = 34? | Push tetangga | Isi stack (atas → bawah) | Sudah dikunjungi |
|---|---|---|---|---|---|
| 0 | – | – | a1 | [a1] | – |
| 1 | a1(12) | tidak | a3, a2 | [a2, a3] | a1 |
| 2 | a2(7) | tidak | a5, a4 | [a4, a5, a3] | a1, a2 |
| 3 | a4(9) | tidak | – (buntu, backtrack) | [a5, a3] | a1, a2, a4 |
| 4 | a5(33) | tidak | a8 | [a8, a3] | a1, a2, a4, a5 |
| 5 | a8(16) | tidak | a6 | [a6, a3] | a1, a2, a4, a5, a8 |
| 6 | a6(34) | **YA** | – | – | a1, a2, a4, a5, a8, a6 |

**Hasil DFS:**
- Angka 34 ditemukan di node **a6** pada langkah ke-6.
- Urutan kunjungan: a1 → a2 → a4 → a5 → a8 → a6
- Jalur yang ditempuh: a1 → a2 → a5 → a8 → a6

Penjelasan: DFS langsung masuk ke cabang kiri (a2) sampai ke ujung (a4). Karena a4
buntu, DFS mundur ke a5, lanjut turun ke a8, lalu ke a6 dan menemukan angka 34.
Cabang a3 tidak sempat diperiksa karena angka sudah ketemu lebih dulu.

---

## Soal 2 – Breadth-First Search (BFS)

**Prinsip:** BFS menelusuri graf **per level** – semua tetangga terdekat diperiksa
dulu sebelum turun ke level berikutnya. BFS memakai **Queue** (FIFO – yang pertama
masuk, pertama keluar).

**Algoritma:**
1. Masukkan node awal (a1) ke queue dan tandai sudah dikunjungi.
2. Selama queue tidak kosong:
   - Ambil (dequeue) node paling depan, cek apakah nilainya sama dengan n.
   - Jika sama → **ditemukan**, berhenti.
   - Jika tidak → masukkan (enqueue) semua tetangga yang belum dikunjungi ke
     belakang queue dan tandai sudah dikunjungi.
3. Jika queue kosong dan belum ketemu → angka tidak ada di graf.

**Proses pencarian n = 34:**

| Langkah | Level | Dequeue (dicek) | Nilai = 34? | Enqueue tetangga | Isi queue (depan → belakang) |
|---|---|---|---|---|---|
| 0 | – | – | – | a1 | [a1] |
| 1 | 0 | a1(12) | tidak | a2, a3 | [a2, a3] |
| 2 | 1 | a2(7) | tidak | a4, a5 | [a3, a4, a5] |
| 3 | 1 | a3(25) | tidak | a6, a7 | [a4, a5, a6, a7] |
| 4 | 2 | a4(9) | tidak | – | [a5, a6, a7] |
| 5 | 2 | a5(33) | tidak | a8 | [a6, a7, a8] |
| 6 | 2 | a6(34) | **YA** | – | – |

**Hasil BFS:**
- Angka 34 ditemukan di node **a6** pada langkah ke-6.
- Urutan kunjungan: a1 → a2 → a3 → a4 → a5 → a6
- Jalur yang ditempuh: a1 → a3 → a6 (jalur terpendek, hanya 2 sisi)

Penjelasan: BFS memeriksa a1 (level 0), lalu semua tetangganya a2 dan a3 (level 1),
lalu node level 2 dari kiri ke kanan: a4, a5, kemudian a6 yang berisi angka 34.

---

## Perbandingan DFS dan BFS

| Aspek | DFS | BFS |
|---|---|---|
| Struktur data | Stack (LIFO) | Queue (FIFO) |
| Cara menelusuri | Masuk sedalam mungkin, lalu backtrack | Melebar per level |
| Urutan kunjungan | a1, a2, a4, a5, a8, a6 | a1, a2, a3, a4, a5, a6 |
| Jalur ke a6 | a1 → a2 → a5 → a8 → a6 (4 sisi) | a1 → a3 → a6 (2 sisi) |
| Kelebihan | Hemat memori, cocok untuk graf yang dalam | Selalu menemukan jalur terpendek (jumlah sisi) |

Kompleksitas waktu keduanya O(V + E), dengan V = jumlah node dan E = jumlah sisi.
