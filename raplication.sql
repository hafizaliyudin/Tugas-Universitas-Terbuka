-- =====================================================================

-- User replika dipakai slave untuk membaca binary log master.
-- Host '%' karena koneksi dari VM diteruskan lewat Windows.
CREATE USER IF NOT EXISTS 'replika'@'%' IDENTIFIED BY 'Replika#2026';
GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'replika'@'%';
FLUSH PRIVILEGES;

-- Posisi binary log master saat ini (File dan Position).
-- MySQL 8.4 ke atas: SHOW BINARY LOG STATUS;
SHOW MASTER STATUS;

-- Export database beserta posisi binary log, lalu kirim ke slave
-- (dijalankan di terminal WSL, bukan di MySQL):
--
--   sudo mysqldump --routines --triggers --source-data=2 db_rumahsakit > ~/db_rumahsakit.sql
--   grep -m1 "CHANGE" ~/db_rumahsakit.sql
--   scp ~/db_rumahsakit.sql administrator@192.168.56.20:~


-- ---------------------------------------------------------------------
-- PENGUJIAN (jalankan setelah slave aktif)
-- ---------------------------------------------------------------------

-- Data baru ini harus otomatis muncul di tabel obat pada slave.
USE db_rumahsakit;
INSERT INTO obat (kode_obat, nama_obat, harga, stok) VALUES ('A005', 'Demam', 150, 100);
SELECT * FROM obat;