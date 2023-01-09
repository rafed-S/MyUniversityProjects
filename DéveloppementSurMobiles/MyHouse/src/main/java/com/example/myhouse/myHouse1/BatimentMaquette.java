package com.example.myhouse.myHouse1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//public class Batiment implements Serializable {
public class BatimentMaquette implements Parcelable {
    public ArrayList<PieceMaquette> pieceMaquettes;


    public BatimentMaquette(ArrayList<PieceMaquette> arrayList){
        pieceMaquettes = arrayList;
    }


    protected BatimentMaquette(Parcel in) {
    }

    public static final Creator<BatimentMaquette> CREATOR = new Creator<BatimentMaquette>() {
        @Override
        public BatimentMaquette createFromParcel(Parcel in) {
            return new BatimentMaquette(in);
        }

        @Override
        public BatimentMaquette[] newArray(int size) {
            return new BatimentMaquette[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
