package com.example.myhouse.myHouse1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CanvasRectMaquette extends View {
    protected Paint paint;
    protected Canvas canvas;

    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public CanvasRectMaquette(Context context, Paint paint, Canvas canvas, int x1, int y1, int x2, int y2) {
        super(context);
        this.paint = paint;
        this.canvas = canvas;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void onDraw(Canvas canvas){
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAlpha(100);
        canvas.drawRect(x1,y1,x2,y2,paint);
        canvas.drawRect(x1,y1,x2,y2,paint);
        invalidate();
    }

}