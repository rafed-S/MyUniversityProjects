package com.example.myhouse.myHouse1;

import java.util.ArrayList;

public class MurMaquette {
    public PieceMaquette pieceMaquette;
    public ArrayList<PorteMaquette> porteMaquettes;
    public char type;

    public MurMaquette(PieceMaquette p, char c){
        pieceMaquette = p;
        porteMaquettes = new ArrayList<>(2);
        type = c;
    }

    public int portesSize(){
        return porteMaquettes.size();
    }

    public PorteMaquette getPorte(int i){
        return porteMaquettes.get(i);
    }

}
