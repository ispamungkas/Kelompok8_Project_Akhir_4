package com.uknown.kelompok8_project_akhir_4.homepage.model;

import java.io.Serializable;

public class NearbyLocationModel implements Serializable {
    private String daerah;
    private String terminal;
    private int km;

    public NearbyLocationModel(String daerah, String terminal, int km) {
        this.daerah = daerah;
        this.terminal = terminal;
        this.km = km;
    }

    public NearbyLocationModel(){
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }
}
