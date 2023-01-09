package com.example.myhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myhouse.myHouse1.AffichageMaquette;
import com.example.myhouse.myHouse1.BatimentMaquette;
import com.example.myhouse.myHouse1.CanvasRectMaquette;
import com.example.myhouse.myHouse1.CouloirMaquette;
import com.example.myhouse.myHouse1.PieceMaquette;
import com.example.myhouse.myHouse1.PorteMaquette;
import java.util.ArrayList;

public class MyHouseMaquette extends AppCompatActivity {
    public Switch switchB;
    public BatimentMaquette batimentMaquette;
    public ArrayList<PieceMaquette> pieceMaquettes;
    public ArrayList<PorteMaquette> porteMaquettes;
    public RelativeLayout relativeLayout;
    final static public String BATIMENT_KEY = "batimentStructure";
    private SurfaceView surfaceView;
    private CanvasRectMaquette canvasRectMaquette;
    float pointerX1;
    float pointerY1;
    float pointerX2;
    float pointerY2;
    private TextView textView;
    private int select;
    float x,y,dx,dy;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_house_maquette);
        switchB = findViewById(R.id.switch1);
        pieceMaquettes = new ArrayList<PieceMaquette>();
        porteMaquettes = new ArrayList<PorteMaquette>();
        batimentMaquette = new BatimentMaquette(pieceMaquettes);
        surfaceView = findViewById(R.id.surfaceView);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceView.setZOrderOnTop(true);
        this.textView = findViewById(R.id.textView);

        /*
        if(!switchB.isChecked()){
            relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    relativeLayout.removeView(canvasRect);
                    Toast.makeText(getApplicationContext(), "I was touched", Toast.LENGTH_SHORT).show();
                    //relativeLayout.removeView(canvasRect);

                    if (event.getPointerCount() == 2) {
                        if (event.getActionIndex() == MotionEvent.ACTION_DOWN) {
                            pointerX1 = event.getX(0);
                            pointerY1 = event.getY(0);
                        }
                        pointerX2 = event.getX(1);
                        pointerY2 = event.getY(1);
                    }
                    CreerRectangle();

                    if (pieces.size() > 0) {
                        Selection(pointerX1, pointerY1, pointerX2, pointerY2);
                    }
                    return true;
                }
            });
        }
         */
    }


    public void Selection(float x1,float y1,float x2,float y2){
        float piecesX1,piecesY1,piecesX2,piecesY2;
        for(int i = 0; i< pieceMaquettes.size(); i++){
            piecesX1 = pieceMaquettes.get(i).getX()+ pieceMaquettes.get(i).getRect().left;
            piecesY1 = pieceMaquettes.get(i).getY()+ pieceMaquettes.get(i).getRect().left;
            piecesX2 = pieceMaquettes.get(i).getX()+ pieceMaquettes.get(i).getRect().right;
            piecesY2 = pieceMaquettes.get(i).getY()+ pieceMaquettes.get(i).getRect().bottom;
            if ((x1 < piecesX1) && (x2 > piecesX2) && (y1 < piecesY1) && (y2 > piecesY2)) {
                select = i;
                /*
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(20);
                paint.setStyle(Paint.Style.STROKE);
                pieces.get(select).setPaint(paint);
                */
            }
        }
        this.textView.setText("P "+select);
    }

    public void CreerRectangle(){
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        canvasRectMaquette = new CanvasRectMaquette(MyHouseMaquette.this,paint,canvas,(int)pointerX2,(int)pointerY2,(int)pointerX1,(int)pointerY1);
        relativeLayout.addView(canvasRectMaquette);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(switchB.isChecked()) {
            for (int i = 0; i < pieceMaquettes.size(); i++) {
                int[] dim2 = new int[2];
                pieceMaquettes.get(i).getLocationOnScreen(dim2);

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = event.getX();
                    y = event.getY();
                }
                boolean b2 = false;
                if ((event.getRawX() > dim2[0]) && (event.getRawX() < dim2[0] + pieceMaquettes.get(i).getWidth()) && (event.getRawY() > dim2[1]) && (event.getRawY() < dim2[1] + pieceMaquettes.get(i).getHeight())) {
                    b2 = true;
                }
                if ((event.getAction() == MotionEvent.ACTION_MOVE) && b2) {
                    dx = event.getX() - x;
                    dy = event.getY() - y;
                    pieceMaquettes.get(i).setX(pieceMaquettes.get(i).getX() + dx);
                    pieceMaquettes.get(i).setY(pieceMaquettes.get(i).getY() + dy);
                    x = event.getX();
                    y = event.getY();
                }
            }
        }else{
            relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    relativeLayout.removeView(canvasRectMaquette);
                    Toast.makeText(getApplicationContext(), "I was touched", Toast.LENGTH_SHORT).show();
                    //relativeLayout.removeView(canvasRect);

                    if (event.getPointerCount() == 2) {
                        if (event.getActionIndex() == MotionEvent.ACTION_DOWN) {
                            pointerX1 = event.getX(0);
                            pointerY1 = event.getY(0);
                        }
                        pointerX2 = event.getX(1);
                        pointerY2 = event.getY(1);
                    }
                    CreerRectangle();

                    if (pieceMaquettes.size() > 0) {
                        Selection(pointerX1, pointerY1, pointerX2, pointerY2);
                    }
                    return true;
                }
            });
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maquette_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Toast.makeText(this,"Ajouter ",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Supprimer",Toast.LENGTH_SHORT).show();
                setOnClickSupprimer();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Suspendre",Toast.LENGTH_SHORT).show();
                this.finish();
                return true;
            case R.id.subitem1:
                setOnClickAjouterPieces();
                Toast.makeText(this,"Ajouter une Piece",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                setOnClickAjouterCouloir();
                Toast.makeText(this,"Ajouter un Couloir",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem3:
                //setOnClickAjouterPorte();
                Toast.makeText(this,"Ajouter une Porte",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.OuestTop:
                //setOnClickAjouterPorte();
                Toast.makeText(this,"Mur Ouest Top",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.OuestBottom:
                //setOnClickAjouterPorte();
                Toast.makeText(this,"Mur Ouest Bottom",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.EstTop:
                //setOnClickAjouterPorte();
                Toast.makeText(this,"Mur Est Top",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.EstBottom:
                //setOnClickAjouterPorte();
                Toast.makeText(this,"Mur Est Bottom",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.NordLeft:
                setOnClickAjouterPorteHorizontal('N','L');
                Toast.makeText(this,"Mur Nord Left",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.NordRight:
                setOnClickAjouterPorteHorizontal('N','R');
                Toast.makeText(this,"Mur Nord Right",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.SudLeft:
                setOnClickAjouterPorteHorizontal('S','L');
                Toast.makeText(this,"Mur Sud Left",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.SudRight:
                setOnClickAjouterPorteHorizontal('S','R');
                Toast.makeText(this,"Mur Sud Right",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setOnClickAjouterPieces() {
        PieceMaquette p = new PieceMaquette(this, pieceMaquettes.size());
        //System.out.println("p num: "+p.intNumPiece);
        pieceMaquettes.add(p);
        relativeLayout.addView(p);
    }

    public void setOnClickAjouterCouloir() {
        CouloirMaquette c = new CouloirMaquette(this, pieceMaquettes.size(),100,100,150,400);
        //System.out.println("p num: "+p.intNumPiece);
        pieceMaquettes.add(c);
        relativeLayout.addView(c);
    }

    public void setOnClickAjouterPorte(char c, PieceMaquette p, int x, int y) {
        if(pieceMaquettes.size()>0) {
            int x1 = pieceMaquettes.get(select).getR() - pieceMaquettes.get(select).getL() + (1 / 4) * pieceMaquettes.get(select).getR();
            int y1 = pieceMaquettes.get(select).getB() - pieceMaquettes.get(select).getT();
            //Porte p = new Porte(this,pieces.get(select),pieces.get(select).murE,x1,y1,x1+(1/2)*pieces.get(select).getR(),y1);
            ///////Porte po = new Porte(this, pieces.get(select), pieces.get(select).murE,'H');
            //System.out.println("p num: "+p.intNumPiece);
            ///////portes.add(po);
            ///////relativeLayout.addView(po);
        }
    }

    public void setOnClickAjouterPorteHorizontal(char c,char side) {
        if(pieceMaquettes.size()>0) {
            if(c=='N'){
                if(side=='L') {
                    float x1 = pieceMaquettes.get(select).getX()+ pieceMaquettes.get(select).getRect().left + pieceMaquettes.get(select).getRect().right * 1/5;
                    float y1 = pieceMaquettes.get(select).getY()+ pieceMaquettes.get(select).getRect().top * 1/2;
                    PorteMaquette po = new PorteMaquette(this, pieceMaquettes.get(select), pieceMaquettes.get(select).murMaquetteN, 'V',x1,y1);
                    porteMaquettes.add(po);
                    relativeLayout.addView(po);
                }else if(side=='R'){
                    float x1 = pieceMaquettes.get(select).getX()+ pieceMaquettes.get(select).getRect().left + pieceMaquettes.get(select).getRect().right * 1/2;
                    float y1 = pieceMaquettes.get(select).getY()+ pieceMaquettes.get(select).getRect().top * 1/2;
                    PorteMaquette po = new PorteMaquette(this, pieceMaquettes.get(select), pieceMaquettes.get(select).murMaquetteN, 'V',x1,y1);
                    porteMaquettes.add(po);
                    relativeLayout.addView(po);
                }
            }else if(c=='S'){
                if(side=='L') {
                    float x1 = pieceMaquettes.get(select).getX()+ pieceMaquettes.get(select).getRect().left + pieceMaquettes.get(select).getRect().right * 1/5;
                    float y1 = pieceMaquettes.get(select).getY()+ pieceMaquettes.get(select).getRect().bottom ;
                    PorteMaquette po = new PorteMaquette(this, pieceMaquettes.get(select), pieceMaquettes.get(select).murMaquetteS,'V',x1,y1);
                    porteMaquettes.add(po);
                    relativeLayout.addView(po);
                }else if(side=='R'){
                    float x1 = pieceMaquettes.get(select).getX()+ pieceMaquettes.get(select).getRect().left + pieceMaquettes.get(select).getRect().right * 1/2;
                    float y1 = pieceMaquettes.get(select).getY()+ pieceMaquettes.get(select).getRect().bottom;
                    PorteMaquette po = new PorteMaquette(this, pieceMaquettes.get(select), pieceMaquettes.get(select).murMaquetteS,'V',x1,y1);
                    porteMaquettes.add(po);
                    relativeLayout.addView(po);
                }
            }
        }
    }

    public void setOnClickSupprimer() {
        if(pieceMaquettes.size()>0) {
            relativeLayout.removeView(pieceMaquettes.get(select));
            pieceMaquettes.remove(pieceMaquettes.get(select));
            select = 0;
        }
    }

    public void setOnClickAffichageActivity(View view) {
        Toast.makeText(MyHouseMaquette.this, "Affichage", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AffichageMaquette.class);
        intent.putExtra(BATIMENT_KEY, batimentMaquette);
        startActivity(intent);
    }

    public void setOnClickLeft(View view) {
        relativeLayout.removeView(canvasRectMaquette);
        if(pieceMaquettes.size()>0) {
            pieceMaquettes.get(select).setX((int) pieceMaquettes.get(select).getX() - 25);
        }
    }

    public void setOnClickTop(View view) {
        relativeLayout.removeView(canvasRectMaquette);
        if(pieceMaquettes.size()>0) {
            pieceMaquettes.get(select).setY((int) pieceMaquettes.get(select).getY() - 25);
        }
    }
    public void setOnClickRight(View view) {
        relativeLayout.removeView(canvasRectMaquette);
        if(pieceMaquettes.size()>0) {
            pieceMaquettes.get(select).setX((int) pieceMaquettes.get(select).getX() + 25);
        }
    }
    public void setOnClickBottom(View view) {
        relativeLayout.removeView(canvasRectMaquette);
        if(pieceMaquettes.size()>0) {
            pieceMaquettes.get(select).setY((int) pieceMaquettes.get(select).getY() + 25);
        }
    }
}