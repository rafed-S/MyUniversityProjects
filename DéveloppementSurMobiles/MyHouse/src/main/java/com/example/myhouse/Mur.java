package com.example.myhouse;

import java.io.Serializable;

public class Mur implements Serializable {
    public String mur;
    public Porte porte;
    public String murName= "test";
    public boolean disponible;

    public Mur(String m,String name){
        this.mur = m;
        this.murName = name;
        disponible=true;
    }

    public void setmurName(String nm) {
        this.murName = nm;
    }

    public String getmurName() {
        return murName;
    }

    public void setPorte(Porte p) {
        this.porte = p;
        disponible=false;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setDisponible(boolean b) {
        this.disponible = b;
    }

    public boolean getDisponible() {
        return disponible;
    }
}
