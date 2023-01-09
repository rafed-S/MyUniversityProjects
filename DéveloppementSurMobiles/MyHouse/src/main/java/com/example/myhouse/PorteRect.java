package com.example.myhouse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.io.Serializable;

public class PorteRect extends View implements Serializable {
        public Rect rect;
        public Paint paint;
        public int left;
        public int top;
        public int right;
        public int bottom;
        public int intNumPiece1;
        public int intNumPiece2;

        public PorteRect(Context context){
            super(context);
            //intNumPiece = num;
            left = 100;
            top = 100;
            right = 150;
            bottom = 350;
        }

        public PorteRect(Context context, int num, int l, int t, int r, int b){
            super(context);
            intNumPiece1 = num;
            left = l;
            top = t;
            right = r;
            bottom = b;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            rect = new Rect((int)left,(int)top,(int)left+(int)right,(int)top+(int)bottom);
            paint = new Paint();
            paint.setColor(Color.rgb(205,205,205));
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect,paint);
        }

        public void setPosition(float x, float y) {
            this.left = (int)x-100;
            this.top = (int)y-100;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

    public void setIntNumPiece2(int intNumPiece2) {
        this.intNumPiece2 = intNumPiece2;
    }

    }
