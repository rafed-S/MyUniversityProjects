package com.example.myhouse.myHouse1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PorteMaquette extends View {
    public PieceMaquette p1;
    public PieceMaquette p2;
    public MurMaquette m1;
    public MurMaquette m2;
    public Paint paint;
    public float leftLine1;
    public float topLine1;
    public float rightLine1;
    public float bottomLine1;
    public int leftLine2;
    public int topLine2;
    public int rightLine2;
    public int bottomLine2;
    public String type;
    public char orientation;

    //public Porte(Context context,Piece pn1,Mur mn1, int x1l1, int y1l1, int x2l1, int y2l1, int x1l2, int y1l2, int x2l2, int y2l2){
    //public Porte(Context context,Piece pn1,Mur mn1, int x1l1, int y1l1, int x2l1, int y2l1){
    public PorteMaquette(Context context, PieceMaquette pn1, MurMaquette mn1, char c, float x, float y){
        super(context);
        p1=pn1;
        m1=mn1;
        this.type = "porte";
        orientation = c;
        if(orientation=='H'){
            leftLine1 = x;
            topLine1 = y;
            rightLine1 = leftLine1+100;
            bottomLine1 = topLine1;
        }else if(orientation=='V'){
            leftLine1 = x;
            topLine1 = y;
            rightLine1 = leftLine1;
            bottomLine1 = topLine1+100;
        }
    }

    public PorteMaquette(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(Color. rgb(255,140,0));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        if(orientation =='H') {
            canvas.drawLine(leftLine1, topLine1, rightLine1, bottomLine1, paint);
            canvas.drawLine(leftLine1, topLine1 + 25, rightLine1, bottomLine1 + 25, paint);
        }else if(orientation =='V'){
            canvas.drawLine(leftLine1, topLine1, rightLine1, bottomLine1, paint);
            canvas.drawLine(leftLine1+ 25, topLine1, rightLine1+ 25, bottomLine1, paint);
        }
    }

    public void setP1(PieceMaquette n1) {
        this.p1 = n1;
    }

    public void setP2(PieceMaquette n2) {
        this.p2 = n2;
    }

    public PieceMaquette getP1() {
        return p1;
    }

    public PieceMaquette getP2() {
        return p2;
    }

    public void setM1(MurMaquette n1) {
        this.m1 = n1;
    }

    public void setM2(MurMaquette n2) {
        this.m2 = n2;
    }

    public MurMaquette getM1() {
        return m1;
    }

    public MurMaquette getM2() {
        return m2;
    }
}
