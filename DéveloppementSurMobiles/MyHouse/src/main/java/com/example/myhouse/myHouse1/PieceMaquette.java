package com.example.myhouse.myHouse1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class PieceMaquette extends View{
    public Rect rect;
    public Paint paint;
    public int left;
    public int top;
    public int right;
    public int bottom;
    private boolean actionDown = false;
    public int intNumPiece;
    public MurMaquette murMaquetteO;
    public MurMaquette murMaquetteN;
    public MurMaquette murMaquetteE;
    public MurMaquette murMaquetteS;
    public String type;


    public PieceMaquette(Context context, int num){
        super(context);
        intNumPiece = num;
        left = 100;
        top = 100;
        right = 300;
        bottom = 300;
        murMaquetteO = new MurMaquette(this,'O');
        murMaquetteN = new MurMaquette(this,'N');
        murMaquetteE = new MurMaquette(this,'E');
        murMaquetteS = new MurMaquette(this,'S');
        this.type = "piece";
    }

    public PieceMaquette(Context context, int num, int l, int t, int r, int b){
        super(context);
        intNumPiece = num;
        left = l;
        top = t;
        right = r;
        bottom = b;
        murMaquetteO = new MurMaquette(this,'O');
        murMaquetteN = new MurMaquette(this,'N');
        murMaquetteE = new MurMaquette(this,'E');
        murMaquetteS = new MurMaquette(this,'S');
        this.type = "couloir";
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        rect = new Rect((int)left,(int)top,(int)left+(int)right,(int)top+(int)bottom);
        paint = new Paint();
        paint.setColor(Color.rgb(0,128,128));
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,paint);
    }

    public void setActionDown(boolean action) {
        actionDown = action;
    }

    public boolean setActionDown() {
        return actionDown;
    }

    public void setPosition(float x, float y) {
        this.left = (int)x-100;
        this.top = (int)y-100;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getL() {
        return this.left;
    }

    public int getT() {
        return top;
    }

    public int getR() {
        return right;
    }

    public int getB() {
        return bottom;
    }

    public Rect getRect() {
        return rect;
    }

    public String getType() {
        return type;
    }

    public MurMaquette getMurO() {
        return murMaquetteO;
    }

    public MurMaquette getMurN() {
        return murMaquetteN;
    }

    public MurMaquette getMurE() {
        return murMaquetteE;
    }

    public MurMaquette getMurS() {
        return murMaquetteS;
    }

    public int getNbPortes(){
        int o = murMaquetteO.portesSize();
        int n = murMaquetteN.portesSize();
        int e = murMaquetteE.portesSize();
        int s = murMaquetteS.portesSize();
        return o+n+e+s;
    }
}
