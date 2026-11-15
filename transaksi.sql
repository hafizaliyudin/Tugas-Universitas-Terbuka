-- =====================================================================
-- 01_transaksi.sql
-- Tugas 3 MSIM4206 Basis Data - Praktikum Transaksi
-- Database : db_rumahsakit (MySQL 8.0, server MASTER)
-- Cara pakai: mysql -u root -p < 01_transaksi.sql
-- Di DBeaver: blok setiap CREATE PROCEDURE / CREATE TRIGGER sampai END,
-- lalu jalankan dengan Ctrl+Enter (baris DELIMITER tidak perlu ikut).
-- =====================================================================

USE db_rumahsakit;

-- Cek data awal dan storage engine.
-- Transaksi hanya didukung oleh tabel dengan engine InnoDB.
SELECT * FROM pasien;
SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'db_rumahsakit';


-- ---------------------------------------------------------------------
-- 1. COMMIT
-- ---------------------------------------------------------------------

DELIMITER $$

-- transaksi1: menambah pasien Tito TANPA COMMIT.
-- Data hanya terlihat di sesi ini dan belum tersimpan permanen.
CREATE PROCEDURE transaksi1()
BEGIN
  START TRANSACTION;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (6, 'Tito', 'Jl.Pakis Haji no 23', 'L');
  SELECT * FROM pasien;
END$$

-- transaksi2: perintah yang sama ditambah COMMIT.
-- COMMIT menyimpan perubahan secara permanen ke tabel.
CREATE PROCEDURE transaksi2()
BEGIN
  START TRANSACTION;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (6, 'Tito', 'Jl.Pakis Haji no 23', 'L');
  SELECT * FROM pasien;
  COMMIT;
END$$

DELIMITER ;

-- Tito tampil, tetapi belum di-commit (cek dari sesi lain: tidak ada).
CALL transaksi1();

-- Membatalkan transaksi1 yang masih terbuka sebelum menjalankan transaksi2.
ROLLBACK;

-- Tito tersimpan permanen.
CALL transaksi2();


-- ---------------------------------------------------------------------
-- 2. ROLLBACK
-- ---------------------------------------------------------------------

DELIMITER $$

-- transaksi3: UPDATE dibatalkan oleh ROLLBACK,
-- sehingga nama pasien id 3 tetap Rizky Pratama.
CREATE PROCEDURE transaksi3()
BEGIN
  START TRANSACTION;
  UPDATE pasien SET nama_pasien = 'Danar' WHERE id_pasien = 3;
  ROLLBACK;
  COMMIT;
  SELECT * FROM pasien;
END$$

-- transaksi4: INSERT Hanif dibatalkan oleh ROLLBACK,
-- sedangkan UPDATE setelahnya tetap dijalankan dan di-commit.
CREATE PROCEDURE transaksi4()
BEGIN
  START TRANSACTION;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (7, 'Hanif', 'Jl.Babakan loa no 22', 'L');
  ROLLBACK;
  UPDATE pasien SET nama_pasien = 'Septin' WHERE id_pasien = 3;
  COMMIT;
  SELECT * FROM pasien;
END$$

DELIMITER ;

CALL transaksi3();
CALL transaksi4();


-- ---------------------------------------------------------------------
-- 3. CHECKPOINT (SAVEPOINT)
-- ---------------------------------------------------------------------

DELIMITER $$

-- transaksi5: ROLLBACK TO SAVEPOINT hanya membatalkan perintah setelah point1.
-- Hasil: Hanif (7) dan Lutfhi (9) tersimpan, Tina (8) dibatalkan.
CREATE PROCEDURE transaksi5()
BEGIN
  START TRANSACTION;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (7, 'Hanif', 'Jl.Babakan loa no 22', 'L');
  SAVEPOINT point1;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (8, 'Tina', 'Jl.Sangkuriang no 45', 'P');
  ROLLBACK TO SAVEPOINT point1;
  INSERT INTO pasien (id_pasien, nama_pasien, alamat_pasien, jenis_kelamin)
  VALUES (9, 'Lutfhi', 'Jl.Dipenogoro 24', 'L');
  COMMIT;
  SELECT * FROM pasien;
END$$

DELIMITER ;

CALL transaksi5();


-- ---------------------------------------------------------------------
-- 4. TABEL OBAT DAN TRIGGER
-- ---------------------------------------------------------------------

-- Tabel obat menyimpan data dan stok obat.
CREATE TABLE obat (
  id_obat   INT AUTO_INCREMENT PRIMARY KEY,
  kode_obat VARCHAR(5),
  nama_obat VARCHAR(30),
  harga     INT,
  stok      INT
);

-- Tabel transaksi_obat berelasi ke pasien dan obat melalui foreign key.
CREATE TABLE transaksi_obat (
  id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
  id_pasien    INT,
  id_obat      INT,
  jumlah       INT,
  total_harga  INT,
  FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien),
  FOREIGN KEY (id_obat)   REFERENCES obat(id_obat)
);

INSERT INTO obat (kode_obat, nama_obat, harga, stok) VALUES
('A001', 'Amikacin',  20000, 10),
('A002', 'Amoxillin', 15000, 20),
('A003', 'Betadine',   7000,  5);

DELIMITER $$

-- tg_jual: berjalan otomatis setelah data masuk ke transaksi_obat,
-- lalu mengurangi stok obat sesuai jumlah yang dibeli.
CREATE TRIGGER tg_jual AFTER INSERT ON transaksi_obat
FOR EACH ROW
BEGIN
  UPDATE obat SET stok = stok - NEW.jumlah WHERE id_obat = NEW.id_obat;
END$$

DELIMITER ;

-- Uji trigger: stok Amoxillin 20, pasien id 1 membeli 5, stok menjadi 15.
SELECT * FROM obat;
INSERT INTO transaksi_obat (id_transaksi, id_pasien, id_obat, jumlah, total_harga)
VALUES (NULL, 1, 2, 5, 0);
SELECT * FROM obat;