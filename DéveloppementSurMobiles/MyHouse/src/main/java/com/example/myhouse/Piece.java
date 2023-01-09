package com.example.myhouse;
import java.io.Serializable;
import java.util.ArrayList;

public class Piece implements Serializable {
    public String PieceName;
    public String PieceNumber;
    public ArrayList<Mur> murs;
    public String meteoVille;
    public String meteoTemperature;


    public Piece(String n , String np){
        this.PieceName = n;
        this.PieceNumber=np;
        murs = new ArrayList<Mur>();
    }

    public void setMeteoVille(String nm) {
        this.meteoVille = nm;
    }

    public void setMeteoTemperature(String nm) {
        this.meteoTemperature = nm;
    }

    public String getPieceName() {
        return PieceName;
    }

    public String getPieceNumber() {
        return PieceNumber;
    }


    public void setName(String name) {
        this.PieceName = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PieceNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contacte{" +
                "name='" + PieceName + '\'' +
                ", phoneNumber='" + PieceNumber + '\'' +
                '}';
    }
}
