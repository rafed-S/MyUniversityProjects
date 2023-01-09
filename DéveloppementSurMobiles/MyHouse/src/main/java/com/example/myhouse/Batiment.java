package com.example.myhouse;
import java.io.Serializable;
import java.util.ArrayList;

public class Batiment implements Serializable {

    public ArrayList<Piece> pieces;
    public int pieceSelectedNum;

    public Batiment(){
    }

    public int getPieceSelectedNum() {
        return pieceSelectedNum;
    }

    public void setPieceSelectedNum(int pieceSelectedNum) {
        this.pieceSelectedNum = pieceSelectedNum;
    }

    public Piece getPieceSelected() {
        return pieces.get(pieceSelectedNum);
    }

    public Piece getPiece(int n) {
        return pieces.get(n);
    }

    public Batiment(ArrayList<Piece> arrayList){
        pieces = arrayList;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

}
