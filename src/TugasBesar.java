import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Pesawat
 * 
 * Digunakan untuk mepresentasikan sebuah penerbengan pesawat
 */
class Pesawat {
    private String PsKode;
    private String PsMaskapai;
    private String PsTujuan;
    private Date PsKeberangkatan;

    /*
     * Konstruktor kelas pesawat 
     * 
     * @param PsKode kode pernerbangan
     * @param PsMaskapai Maskapai penerbangan
     * @param PsTujuan Tujuan penerbangan
     * @param PsKeberangkatan Tanggal keberangkatan
     * 
     */
    public Pesawat(String PsKode, String PsMaskapai, String PsTujuan, Date PsKeberangkatan) {
        this.PsKode = PsKode;
        this.PsMaskapai = PsMaskapai;
        this.PsTujuan = PsTujuan;
        this.PsKeberangkatan = PsKeberangkatan;
    }

     /**
     * Method untuk mengembalikan nilai dari kode penerbangan
     *
     * @return Kode penerbangan
     */
    public String getPsKode() {
        return PsKode;
    }

      /**
     * Method untuk mengembalikan nilai dari maskapai penerbangan
     *
     * @return Maskapai penerbangan
     */
    public String getPsMaskapai() {
        return PsMaskapai;
    }   

     /**
     * Method untuk mengembalikan nilai dari tujuan penerbangan
     *
     * @return Tujuan penerbangan
     */
    public String getPsTujuan() {
        return PsTujuan;
    }

    
    /**
     * Method untuk mengembalikan nilai dari tanggal keberangkatan
     *
     * @return Tanggal keberangkatan
     */
    public Date getPsKeberangkatan() {
        return PsKeberangkatan;
    }

    
    /**
     * Method untuk merubah nilai dari kode penerbangan
     *
     * @param PsKode Kode penerbangan baru
     */
    public void setPsKode(String PsKode) {
        this.PsKode = PsKode;
    }

    /**
     * Method untuk merubah nilai dari maskapai penerbangan
     *
     * @param PsMaskapai Maskapai penerbangan baru
     */
    public void setPsMaskapai(String PsMaskapai) {
        this.PsMaskapai = PsMaskapai;
    }

    /**
     * Method untuk merubah nilai dari tujuan penerbangan
     *
     * @param PsTujuan Tujuan penerbangan baru
     */
    public void setPsTujuan(String PsTujuan) {
        this.PsTujuan = PsTujuan;
    }

    /**
     * Method untuk merubah nilai dari tanggal keberangkatan
     *
     * @param PsKeberangkatan Tanggal keberangkatan baru
     */
    public void SetPsKeberangkatan(Date PsKeberangkatan) {
        this.PsKeberangkatan = PsKeberangkatan;
    }

    /**
     * Method untuk mengembalikan string representasi dari penerbangan
     *
     * @return String representasi dari penerbangan dalam bentuk
     *         "PsKode-PsMaskapai-PsTujuan-PsKeberangkatan"
     */
    @Override
    public String toString() {
        return PsKode + "-" + PsMaskapai + "-" + PsTujuan + "-" + PsKeberangkatan;
    }

}

/**
 * Database
 * 
 * Digunakan untuk mengatur koneksi dan interaksi dengan database MYSQL
 */
class Database {
    private Connection connection;
    
    /**
     * Konstruktor kelas Database
     *
     * @throws ClassNotFoundException Jika driver MySQL tidak ditemukan
     */
    public Database() throws ClassNotFoundException {
        try {
            // menentukan driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // menentukan URL database
            String url = "jdbc:mysql://localhost/tugasbesar";

            // membuat koneksi ke database
            connection = DriverManager.getConnection(url, "root", "");
            System.out.println("Koneksi kedatabase berhasil");
        } catch (SQLException e) {
            System.out.println("Koneksi gagal" + e.getMessage());
        }
    }

    /**
     * Method untuk menambahkan data pesawat ke database
     *
     * @param P Objek pesawat yang akan ditambahkan ke database
     * @throws SQLException Jika terjadi kesalahan saat menjalankan perintah SQL
     */
    public void tambahPesawat(Pesawat P) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO tb_pesawat (ps_kode , ps_maskapai , ps_tujuan , ps_keberangkatan) VALUES (?, ? ,? ,?)");
        ps.setString(1, P.getPsKode());
        ps.setString(2, P.getPsMaskapai());
        ps.setString(3, P.getPsTujuan());
        ps.setDate(4, P.getPsKeberangkatan());
        ps.execute();
    }

    /**
     * Method untuk menampilkan data pesawat dari database
     *
     * @throws SQLException Jika terjadi kesalahan saat menjalankan perintah SQL
     */
    public void tampilPesawat() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM tb_pesawat");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String PsKode = rs.getString("ps_kode");
            String PsMaskapai = rs.getString("ps_maskapai");
            String PsTujuan = rs.getString("ps_tujuan");
            Date PsKeberangkatan = rs.getDate("ps_keberangkatan");
            Pesawat P = new Pesawat(PsKode, PsMaskapai, PsTujuan, PsKeberangkatan);

            // Display
            System.out.println("===========PROGRAM TAMPIL DATA==========");
            System.out.println("========================================");
            System.out.println("Kode Pesawat     :" + P.getPsKode());
            System.out.println("Nama Maskapai    :" + P.getPsMaskapai());
            System.out.println("Tujuan Pesawat   :" + P.getPsTujuan());
            System.out.println("Jadwal keberangkatan :" + P.getPsKeberangkatan());

        }

    }

    /**
     * Method untuk menghapus data pesawat dari database berdasarkan kode pesawat
     *
     * @param PsKode Kode pesawat yang akan dihapus
     * @throws SQLException Jika terjadi kesalahan saat menjalankan perintah SQL
     */
    public void hapusPesawat(String PsKode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM tb_pesawat WHERE ps_kode = ?");
        ps.setString(1, PsKode);
        ps.executeUpdate();
    }

    /**
     * Method untuk mengupdate data pesawat di database
     *
     * @param PsKode Kode pesawat yang akan diupdate
     * @param PsMaskapai Nama maskapai yang akan diupdate
     * @param PsTujuan Tujuan pesawat yang akan diupdate
     * @param Pskeberangkatan Jadwal keberangkatan yang akan diupdate
     * @throws SQLException Jika terjadi kesalahan saat menjalankan perintah SQL
     */
    public void updatePesawat(String PsKode, String PsMaskapai, String PsTujuan, Date Pskeberangkatan) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
        ("UPDATE tb_pesawat SET ps_maskapai = ?, ps_tujuan = ?, ps_keberangkatan = ? WHERE ps_kode = ?");
        ps.setString(1, PsMaskapai);
        ps.setString(2, PsTujuan);
        ps.setDate(3, Pskeberangkatan);
        ps.setString(4, PsKode);
        ps.executeUpdate();
    }
}

public class TugasBesar {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            Database db = new Database();
            Scanner sc = new Scanner(System.in);
            int pilihan = 1;
            char ulang;
            // Membuat perulangan program dengan menggunakan do-while
            do {
                // Display
                System.out.println("Rafhi Arizkia Nurizal");
                System.out.println("TugasBesar Pemrograman");
                System.out.println("=============SELAMAT DATANG ===========");
                System.out.println("==============DATA PESAWAT=============");
                System.out.println("=======================================");
                System.out.println("1.) Tambah data");
                System.out.println("2.) Lihat data");
                System.out.println("3.) Ubah data");
                System.out.println("4.) Hapus data");
                System.out.println("========================================");
                System.out.println("Masukkan Pilihan Anda");
                pilihan = sc.nextInt();
                // Membuat switch case untuk menjalankan program sesuai dengan pilihan yang dipilih
                switch (pilihan) {
                    case 1:
                        try {
                            // Tambah data
                            System.out.println("========PROGRAM TAMBAH DATA======");
                            System.out.println("=================================");
                            System.out.println("Tambah Kode pesawat");
                            String PsKode = sc.next();
                            System.out.println("Tambah maskapai pesawat");
                            String PsMaskapai = sc.next();
                            System.out.println("Tambah tujuan penerbangan");
                            String PsTujuan = sc.next();
                            System.out.println("Tambah jadwal keberangkatan (yyyy-mm-dd):");
                            String tanggal = sc.next();

                            Date PsKeberangkatan = Date.valueOf(tanggal);
                            Pesawat pesawat = new Pesawat(PsKode, PsMaskapai, PsTujuan, PsKeberangkatan);
                            db.tambahPesawat(pesawat);
                        } catch (IllegalArgumentException e) {
                            // TODO: handle exception
                            System.out.println("Format tanggal tidak valid, harap masukkan tanggal dengan format yyyy-mm-dd");
                        }
                        break;
                    case 2:
                        // Lihat data
                        db.tampilPesawat();
                        break;
                    case 3:
                    try {
                        // Update Data
                        System.out.println("========PROGRAM UPDATE DATA======");
                        System.out.println("=================================");
                        // Meminta input kode pesawat yang akan diupdate
                        System.out.println("Masukkan kode pesawat");
                        String PsKode = sc.next();
                        // meminta input data baru untuk kolom kolom pada tabel
                        System.out.println("Update nama maskapai pesawat");
                        String PsMaskapai = sc.next();
                        System.out.println("Update tujuan penerbangan");
                        String PsTujuan = sc.next();
                        System.out.println("Update jadwal keberangkatan (yyyy-mm-dd):");
                        String tanggal = sc.next();

                        Date PsKeberangkatan = Date.valueOf(tanggal);
                        db.updatePesawat(PsKode, PsMaskapai, PsTujuan, PsKeberangkatan);
                    } catch (IllegalArgumentException e) {
                        // TODO: handle exception
                        System.out.println("Format tanggal tidak valid, harap masukkan tanggal dengan format yyyy-mm-dd");
                    }
                        break;
                    case 4:
                        try {
                            System.out.println("=======PROGRAM HAPUS DATA======");
                            System.out.println("===============================");
                            // Meminta input kode pesawat yang akan dihapus
                            System.out.println("Masukkan kode pesawat");
                            String PsKode = sc.next();
                            
                            db.hapusPesawat(PsKode);
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println("Program gagal dijalankan" + e.getMessage());
                        }   
                        break;
                    default:
                        System.out.println("Pilihan anda tidak valid");
                        break;
                }
                System.out.println("==============================");
                System.out.println("Ingin memilih menu lain? (y/t)");
                ulang = sc.next().charAt(0);
            } while (ulang != 't');
            System.out.println("==============================");
            System.out.println("Terima kasih atas kunjungannya");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
