package com.example.myhouse;

import java.io.Serializable;

public class Porte  implements Serializable {
    public float x;
    public float y;
    public int intNumPiece1;
    public int intNumPiece2;

    public Porte(float xx,float yy,int nump){
        this.x = xx;
        this.y=yy;
        this.intNumPiece1=nump;
    }

    public void setIntNumPiece1(int intNumPiece1) {
        this.intNumPiece1 = intNumPiece1;
    }
    public void setIntNumPiece2(int intNumPiece2) {
        this.intNumPiece2 = intNumPiece2;
    }
    public void setX(int n) {
        this.x = n;
    }
    public void setY(int n) {
        this.y = n;
    }
}
