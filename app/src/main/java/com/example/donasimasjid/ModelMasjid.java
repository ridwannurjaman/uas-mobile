package com.example.donasimasjid;

public class ModelMasjid {
    private String nama;
    private String alamat;
    private String nohp;
    private String donasi;
    private String latt,longg;
    private String id;
    private int totalDonasi;

    public ModelMasjid(String nama, String alamat, String nohp, String donasi, String latt, String longg, String id, int totalDonasi) {
        this.alamat = alamat;
        this.nohp = nohp;
        this.donasi = donasi;
        this.latt = latt;
        this.longg = longg;
        this.nama = nama;
        this.id = id;
        this.totalDonasi = totalDonasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getDonasi() {
        return donasi;
    }

    public void setDonasi(String donasi) {
        this.donasi = donasi;
    }

    public String getLatt() {
        return latt;
    }

    public void setLatt(String latt) {
        this.latt = latt;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalDonasi() {
        return totalDonasi;
    }

    public void setTotalDonasi(int totalDonasi) {
        this.totalDonasi = totalDonasi;
    }
}
