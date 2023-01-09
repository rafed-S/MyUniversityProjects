package com.example.myhouse.myHouse1;

import android.content.Context;

public class CouloirMaquette extends PieceMaquette {
    public int left = 100;
    public int top = 100;
    public int right = 150;
    public int bottom = 400;

    public CouloirMaquette(Context context, int num, int l, int t, int r, int b) {
        super(context, num,l,t,r,b);
    }
}
