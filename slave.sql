-- 1. Tambahkan konfigurasi di /etc/mysql/mysql.conf.d/mysqld.cnf
sudo systemctl restart mysql
--
server-id       = 2
replicate-do-db = db_rumahsakit
--
-- 2. Import database hasil export dari master:
--
sudo mysql -e "CREATE DATABASE db_rumahsakit;"
sudo mysql db_rumahsakit < ~/db_rumahsakit.sql
--
-- 3. Ganti SOURCE_LOG_FILE dan SOURCE_LOG_POS di bawah dengan hasil
grep -m1 "CHANGE" ~/db_rumahsakit.sql
-- =====================================================================

-- Menghentikan replikasi lama (jika ada).
STOP REPLICA;

-- Menghubungkan slave ke master lewat jembatan Windows 192.168.56.1.
-- GET_SOURCE_PUBLIC_KEY dibutuhkan untuk autentikasi caching_sha2_password.
CHANGE REPLICATION SOURCE TO
  SOURCE_HOST = '192.168.56.1',
  SOURCE_PORT = 3306,
  SOURCE_USER = 'replika',
  SOURCE_PASSWORD = 'Replika#2026',
  SOURCE_LOG_FILE = 'mysql-bin.000001',
  SOURCE_LOG_POS = 19980,
  GET_SOURCE_PUBLIC_KEY = 1;

-- Menjalankan replikasi.
START REPLICA;

-- Replica_IO_Running dan Replica_SQL_Running harus bernilai Yes.
SHOW REPLICA STATUS\G

-- PENGUJIAN: obat Demam yang ditambahkan di master harus muncul di sini.
SELECT * FROM db_rumahsakit.obat;