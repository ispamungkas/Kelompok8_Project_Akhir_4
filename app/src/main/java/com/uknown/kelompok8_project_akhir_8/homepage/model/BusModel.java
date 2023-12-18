package com.uknown.kelompok8_project_akhir_8.homepage.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BusModel implements Serializable {
    private String namabus;
    private String kodebus;
    private String terminal;
    private String terminaltujuan;
    private int hargaseat;
    private int maxpenumpang;
    private int seatbooked;
    private String jamkeberangkatan;
    private String tanggalkeberangkatan;
    private String gambarbus;
    private ArrayList<String> bookedseat;

    public ArrayList<String> getBookedseat() {
        return bookedseat;
    }

    public void setBookedseat(ArrayList<String> bookedseat) {
        this.bookedseat = bookedseat;
    }
    public String getNamabus() {
        return namabus;
    }

    public String getGambarbus() {
        return gambarbus;
    }

    public void setGambarbus(String gambarbus) {
        this.gambarbus = gambarbus;
    }

    public BusModel(String namabus, String kodebus, String terminal, String terminaltujuan, int hargaseat, int maxpenumpang, int seatbooked, String jamkeberangkatan, String tanggalkeberangkatan, String gambarbus, ArrayList<String> bookedseat) {
        this.namabus = namabus;
        this.kodebus = kodebus;
        this.terminal = terminal;
        this.terminaltujuan = terminaltujuan;
        this.hargaseat = hargaseat;
        this.maxpenumpang = maxpenumpang;
        this.seatbooked = seatbooked;
        this.jamkeberangkatan = jamkeberangkatan;
        this.tanggalkeberangkatan = tanggalkeberangkatan;
        this.gambarbus = gambarbus;
        this.bookedseat = bookedseat;
    }

    public BusModel() {}

    public void setNamabus(String namabus) {
        this.namabus = namabus;
    }

    public String getKodebus() {
        return kodebus;
    }

    public void setKodebus(String kodebus) {
        this.kodebus = kodebus;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminaltujuan() {
        return terminaltujuan;
    }

    public void setTerminaltujuan(String terminaltujuan) {
        this.terminaltujuan = terminaltujuan;
    }

    public int getHargaseat() {
        return hargaseat;
    }

    public void setHargaseat(int hargaseat) {
        this.hargaseat = hargaseat;
    }

    public int getMaxpenumpang() {
        return maxpenumpang;
    }

    public void setMaxpenumpang(int maxpenumpang) {
        this.maxpenumpang = maxpenumpang;
    }

    public int getSeatbooked() {
        return seatbooked;
    }

    public void setSeatbooked(int seatbooked) {
        this.seatbooked = seatbooked;
    }

    public String getJamkeberangkatan() {
        return jamkeberangkatan;
    }

    public void setJamkeberangkatan(String jamkeberangkatan) {
        this.jamkeberangkatan = jamkeberangkatan;
    }

    public String getTanggalkeberangkatan() {
        return tanggalkeberangkatan;
    }

    public void setTanggalkeberangkatan(String tanggalkeberangkatan) {
        this.tanggalkeberangkatan = tanggalkeberangkatan;
    }
}
