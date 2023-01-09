package com.example.myhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PortesActivity extends AppCompatActivity {
    public RelativeLayout relativeLayout;
    public Piece piece;
    public ImageView imageViewMur;
    public int position;
    public PorteRect porteRect;
    public Porte porte;
    final static public String KEY_ENVOYER_PORTES = "envoyer_portes";
    final static public String KEY_RECEVOIR_PORTES = "reception_portes";
    float x,y,dx,dy;
    public TextView pieceEtMur;


    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portes);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        imageViewMur = (ImageView) findViewById(R.id.imageViewMur);
        this.pieceEtMur = findViewById(R.id.pieceEtMur);
        //batiment = (Batiment) getIntent().getSerializableExtra(KEY_ENVOYER_PORTES);
        piece = (Piece) getIntent().getSerializableExtra(KEY_ENVOYER_PORTES);
        position = 0;
        Log.i("PortesActivity","onCreate Fonction");
        porteRect = null;
        MursImages();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int[] dim2 = new int[2];
        this.porteRect.getLocationOnScreen(dim2);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }
        boolean b2 = false;
        if ((event.getRawX() > dim2[0]) && (event.getRawX() < dim2[0] + this.porteRect.getWidth()) && (event.getRawY() > dim2[1]) && (event.getRawY() < dim2[1] + this.porteRect.getHeight())) {
            b2 = true;
        }
        if ((event.getAction() == MotionEvent.ACTION_MOVE) && b2) {
            dx = event.getX() - x;
            dy = event.getY() - y;
            this.porteRect.setX(this.porteRect.getX() + dx);
            this.porteRect.setY(this.porteRect.getY() + dy);
            x = event.getX();
            y = event.getY();
        }
        return super.onTouchEvent(event);
    }


    public Bitmap stringToImage(String s){
        byte[] imageBytes = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    @SuppressLint("SetTextI18n")
    public void MursImages(){
        imageViewMur.setImageBitmap(stringToImage(piece.murs.get(position).mur));
        this.pieceEtMur.setText("piece : "+piece.PieceName+"| Mur : "+piece.murs.get(position).murName);
        if(!piece.murs.get(position).disponible){
            //relativeLayout.addView(piece.murs.get(position).porteRect);
            this.porteRect.setX(piece.murs.get(position).porte.x);
            this.porteRect.setY(piece.murs.get(position).porte.y);
            relativeLayout.addView(this.porteRect);
        }
    }

    public void setOnClickAjouterPorteActivity(View view) {
        if(piece.murs.get(position).disponible) {
            PorteRect p = new PorteRect(this);
            this.porteRect = p;
            relativeLayout.addView(this.porteRect);
            piece.murs.get(position).disponible=false;
            ajouterPorte();
        }
    }


    public void setOnClickSortirActivity(View view) {
        Toast.makeText(PortesActivity.this, "MyHouse", Toast.LENGTH_SHORT).show();
        if (piece==null){
            Log.i("PortesActivity","error Rec piece dans PortesActivity");
        }
        relativeLayout.removeView(this.porteRect);
        Intent intent = new Intent(this, PortesActivity.class);
        intent.putExtra(KEY_RECEVOIR_PORTES,piece);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void ajouterPorte(){
        Porte p = new Porte(porteRect.getX(),this.porteRect.getY(),Integer.parseInt( piece.PieceNumber));
        this.porte =p;
        piece.murs.get(position).setPorte(this.porte);
    }

    public void setOnClickApres(View view) {
        if(this.position<=2) {
            if(!piece.murs.get(position).disponible){
                ajouterPorte();
            }
            relativeLayout.removeView(this.porteRect);
            this.position += 1;
            MursImages();
        }else{
            if(!piece.murs.get(position).disponible){
                ajouterPorte();
            }
            relativeLayout.removeView(this.porteRect);
            this.position = 0;
            MursImages();
        }
    }

    public void setOnClickAvant(View view) {
        if(this.position>=1) {
            if(!piece.murs.get(position).disponible){
                ajouterPorte();
            }
            relativeLayout.removeView(this.porteRect);
            this.position -= 1;
            MursImages();
        }else{
            if(!piece.murs.get(position).disponible){
                ajouterPorte();
            }
            relativeLayout.removeView(this.porteRect);
            this.position = 3;
            MursImages();
        }
    }
}