package org.d3if3127.sertifikasiaplikasikpu.model;

public class Data {
    private String id, nik, nama,no_hp, jenis_kelamin,tanggal,alamat;
    private byte[] gambar;


    public Data() {
    }
    public Data(String id, String nik, String nama,String no_hp, String jenis_kelamin, String tanggal, String alamat, byte[] gambar) {
        this.id = id;
        this.nik = nik;
        this.nama = nama;
        this.no_hp = no_hp;
        this.jenis_kelamin = jenis_kelamin;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.gambar = gambar;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id= id;
    }
    public String getNik() {
        return nik;
    }
    public void setNik(String nik) {
        this.nik= nik;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama= nama;
    }
    public String getNo_hp() {
        return no_hp;
    }
    public void setNo_hp(String no_hp) {
        this.no_hp= no_hp;
    }
    public String getJenis_kelamin() {
        return jenis_kelamin;
    }
    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin= jenis_kelamin;
    }
    public String getTanggal() {
        return tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal= tanggal;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat= alamat;
    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }
}
