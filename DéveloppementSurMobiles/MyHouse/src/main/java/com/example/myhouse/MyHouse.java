package com.example.myhouse;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class MyHouse extends AppCompatActivity {
    final static public String KEY_ENVOYER = "envoyer";
    final static public String KEY_RECEVOIR = "reception";
    protected ActivityResultLauncher<Intent> launcher;
    final static public String KEY_ENVOYER_PORTES = "envoyer_portes";
    final static public String KEY_RECEVOIR_PORTES = "reception_portes";
    protected ActivityResultLauncher<Intent> launcherPortes;
    public Batiment batiment;
    public ArrayList<Piece> pieces;
    public RecyclerView recycler_view;
    public PieceAdapter adapter;
    //ajouter porte
    public EditText editText;
    public EditText piece1;
    public EditText piece2;
    int piece1Porte;
    int piece2Porte;
    ArrayList<Integer> laCarte = new ArrayList<Integer>();
    //affichage
    public RelativeLayout relativeLayout;
    public ImageView imageView;
    public int positionMur=0;
    public int positionPiece=0;
    public PorteRect porteRect;
    public TextView pieceEtMur;
    public int switchMode ;


    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchMode=0;

        setContentView(R.layout.activity_my_house);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        pieces = new ArrayList<Piece>();
        batiment = new Batiment(pieces);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        editText = (EditText)findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        piece1 = (EditText)findViewById(R.id.piece1);
        piece1.setInputType(InputType.TYPE_CLASS_TEXT);
        piece2 = (EditText)findViewById(R.id.piece2);
        piece2.setInputType(InputType.TYPE_CLASS_TEXT);
        this.pieceEtMur = findViewById(R.id.pieceEtMur);
        //batiment.setPieceAdapter(adapter);

        ////////////////////////Gson
        SharedPreferences sharedPreference = getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreference.getString("studata", null);
        Type type = new TypeToken<ArrayList<Piece>>(){}.getType();
        pieces=gson.fromJson(json,type);
        if(pieces==null){
            pieces=new ArrayList<>();
        }
        ////////////////////////

        buildRecycler();
        this.porteRect=null;

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            assert result.getData() != null;
            this.pieces.set(batiment.pieceSelectedNum,(Piece) result.getData().getSerializableExtra(KEY_RECEVOIR));
            buildRecycler();
            batiment.pieceSelectedNum=pieces.size()+1;
            ver();
        });

        launcherPortes = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            assert result.getData() != null;
            this.pieces.set(piece1Porte,(Piece) result.getData().getSerializableExtra(KEY_RECEVOIR_PORTES));
            for(int i=0;i<4;i++) {
                if(!this.pieces.get(piece1Porte).murs.get(i).disponible){
                    this.pieces.get(piece1Porte).murs.get(i).porte.setIntNumPiece2(piece2Porte);
                    Porte p = new Porte(this.pieces.get(piece1Porte).murs.get(i).porte.x,this.pieces.get(piece1Porte).murs.get(i).porte.y,this.pieces.get(piece1Porte).murs.get(i).porte.intNumPiece2);
                    //p.setIntNumPiece2(ajouterPorteAuto(i,this.pieces.get(piece1Porte)));
                    p.setIntNumPiece2(this.pieces.get(piece1Porte).murs.get(i).porte.intNumPiece1);
                    //this.pieces.get(piece2Porte).murs.get(i).setPorte(this.pieces.get(piece1Porte).murs.get(ajouterPorteAuto(i,this.pieces.get(piece1Porte))).porte);
                    this.pieces.get(piece2Porte).murs.get(ajouterPorteAuto(i,this.pieces.get(piece1Porte))).setPorte(p);
                }
            }
            buildRecycler();
            batiment.pieceSelectedNum=pieces.size()+1;
            ver();
        });
        ver();

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Choose which motion action has been performed
                float X = 0;
                float Y = 0;
                float piecesX1=0;
                float piecesY1=0;
                float piecesX2=0;
                float piecesY2=0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Get X, Y coordinates from the ImageView
                        X = (float) event.getX();
                        Y = (float) event.getY();
                }
                if(porteRect!=null) {
                    piecesX1 = porteRect.getX()+ porteRect.left;
                    piecesY1 = porteRect.getY()+ porteRect.left;
                    piecesX2 = porteRect.getX()+ porteRect.right+porteRect.left;
                    piecesY2 = porteRect.getY()+ porteRect.bottom+porteRect.left;
                }
                System.out.println("-=-=-=-=-=-=-=-=- Test onTouchEvent -=-=-=-=-=-=-=-=-");
                boolean b2 = false;
                System.out.println("b2 :"+b2);
                if ((X > piecesX1) && (X < piecesX2) && ( Y> piecesY1) && (Y < piecesY2)) {
                    b2 = true;
                    int p=positionPiece;
                    int p2=pieces.get(positionPiece).murs.get(positionMur).porte.intNumPiece2;
                    positionPiece=p2;
                    int p3 =ajouterPorteAuto(positionMur,pieces.get(p));
                    positionMur= p3;
                    AfficherImages();
                }
                System.out.println("piecesX1 :"+piecesX1);
                System.out.println("piecesX2 :"+piecesX2);
                System.out.println("piecesY1 :"+piecesY1);
                System.out.println("piecesY2 :"+piecesY2);
                System.out.println("X :"+X);
                System.out.println("Y :"+Y);
                System.out.println("b2 :"+b2);
                System.out.println("-=-=-=-=-=-=-=-=- Fin Test onTouchEvent -=-=-=-=-=-=-=-=-");
                return false;
            }
        });

    }

    public void ver(){
        System.out.println(" ########################  test dans MyHous ########################  ");
        System.out.println("nombre des piece = "+ batiment.pieces.size());
        for (int i=0;i<batiment.pieces.size();i++){
            System.out.println("batiment pieces "+i +" size murs :"+batiment.pieces.get(i).murs.size());
            for (int l=0;l<batiment.pieces.get(i).murs.size();l++){
                //System.out.println("murs :"+l+" image= "+batiment.pieces.get(i).murs.get(l).mur);
                boolean b = false;
                if(batiment.pieces.get(i).murs.get(l).mur != null){
                    b=true;
                }
                System.out.println("murs :"+l+" image= "+b);
            }
        }
        System.out.println(" ######################## Fin test dans MyHous ########################  ");

    }

    public void setOnClickSupprimer() {
        Log.i("MyHouse","setOnClickSupprimer Fonction");
        if(pieces.size()>0){
            for(int i=0;i<pieces.size();i++){
                if(adapter.getPieceSelected() == pieces.get(i)){
                    if(pieces.get(i).murs.size()>3) {
                        for (int l = 0; l < 4; l++) {
                            if (!pieces.get(i).murs.get(l).disponible) {
                                int n = pieces.get(i).murs.get(l).porte.intNumPiece2;
                                int m = ajouterPorteAuto(l, pieces.get(i));
                                pieces.get(n).murs.get(m).porte = null;
                                pieces.get(n).murs.get(m).setDisponible(true);
                                positionPiece=0;
                                positionMur=0;
                            }
                        }
                    }
                    pieces.remove(i);
                    buildRecycler();
                }
            }
        }
    }


    public void buildRecycler(){
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PieceAdapter(this,pieces);
        recycler_view.setAdapter(adapter);
    }

    public void setOnClickAjouter(View view) {
        String textValue = editText.getText().toString();
        if(editText.getText().length()>0){
            Log.i("MyHouse","setOnClickAjouter Fonction");
            String s = Integer.toString(pieces.size());
            Piece p = new Piece(textValue,s);
            pieces.add(p);
            buildRecycler();
        }else{
            runOnUiThread(() -> Toast.makeText(this,"Entrez le nom",Toast.LENGTH_SHORT).show());
        }
    }

    public void setOnClickConstructionActivity(View view) {
        if(this.porteRect!=null){
            relativeLayout.removeView(this.porteRect);
        }
        int n=pieces.size()+1;
        for(int i=0;i<pieces.size();i++){
            if(adapter.pieceSelected == pieces.get(i)){
                n=i;
                batiment.setPieceSelectedNum(n);
            }
        }
        batiment.setPieces(pieces);
        Intent intent = new Intent(this, Construction.class);
        Log.i("MyHouse","envoyer batiment dans MyHouse");
        if(n!=pieces.size()+1){
            //intent.putExtra(KEY_ENVOYER,batiment);
            intent.putExtra(KEY_ENVOYER,batiment.pieces.get(batiment.pieceSelectedNum));
            launcher.launch(intent);
        }else{
            Toast.makeText(MyHouse.this, "select piece", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnClickAjouterPortesActivity(View view) {
        if(this.porteRect!=null){
            relativeLayout.removeView(this.porteRect);
        }
        String piece1 = this.piece1.getText().toString();
        String piece2 = this.piece2.getText().toString();
        boolean piece1Bool= false;
        boolean piece2Bool= false;
        piece1Porte = 0;
        System.out.println("************** test AjouterPortes********************");
        System.out.println("** piece1"+":"+piece1);
        System.out.println("** piece2"+":"+piece2);
        for(int i=0;i<pieces.size();i++){
            System.out.println("-> pieces"+i+":"+pieces.get(i).PieceName);
            if(pieces.get(i).PieceName.equals(piece1)){
                piece1Bool =true;
                piece1Porte=i;
                System.out.println("||| piece1Bool: "+piece1Bool+"  i="+piece1Porte);
            }
            if(pieces.get(i).PieceName.equals(piece2)){
                piece2Bool =true;
                piece2Porte=i;
                System.out.println("||| piece1Bool: "+piece2Bool+"  i="+piece2Porte);
            }
        }
        if(piece1Bool && piece2Bool){
            Intent intent = new Intent(this, PortesActivity.class);
            Log.i("MyHouse","envoyer piece dans Porte");
            intent.putExtra(KEY_ENVOYER_PORTES,pieces.get(piece1Porte));
            launcherPortes.launch(intent);
        }else{
            Toast.makeText(MyHouse.this, "une piece n'existe pas ", Toast.LENGTH_SHORT).show();
        }
        System.out.println("************** Fin test AjouterPortes********************");
    }

    public void setOnClickApres(View view) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+positionMur);
        /*
        if(this.positionMur<=2) {
            relativeLayout.removeView(this.porteRect);
            this.positionMur += 1;
            AfficherImages();
        }else{
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 0;
            AfficherImages();
        }
         */
        if(this.positionMur==0){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 1;
            AfficherImages();
        }else if(this.positionMur==1){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 3;
            AfficherImages();
        }else if(this.positionMur==3){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 2;
            AfficherImages();
        }else if(this.positionMur==2){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 0;
            AfficherImages();
        }else {
            Toast.makeText(MyHouse.this, "ERROR Apres", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnClickAvant(View view) {
        /*
        if(this.positionMur>=1) {
            relativeLayout.removeView(this.porteRect);
            this.positionMur -= 1;
            AfficherImages();
        }else{
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 3;
            AfficherImages();
        }
         */
        if(this.positionMur==0){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 2;
            AfficherImages();
        }else if(this.positionMur==2){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 3;
            AfficherImages();
        }else if(this.positionMur==3){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 1;
            AfficherImages();
        }else if(this.positionMur==1){
            relativeLayout.removeView(this.porteRect);
            this.positionMur = 0;
            AfficherImages();
        }else {
            Toast.makeText(MyHouse.this, "ERROR Avant", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap stringToImage(String s){
        byte[] imageBytes = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    @SuppressLint("SetTextI18n")
    public void AfficherImages() {
        if(porteRect!=null){
            relativeLayout.removeView(this.porteRect);
        }
        if(pieces.get(positionPiece).murs.size()>3) {
            if(!pieces.get(positionPiece).murs.get(positionMur).disponible){
                affichagePorte();
            }
            imageView.setImageBitmap(stringToImage(pieces.get(positionPiece).murs.get(positionMur).mur));
            this.pieceEtMur.setText("piece : "+pieces.get(positionPiece).getPieceName()+"| Mur : "+pieces.get(positionPiece).murs.get(positionMur).murName);
        }else{
            Toast.makeText(MyHouse.this, " une piece n'existe pas", Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnClickAffichage(View view) {
        //laCarte.add(0);
        switchMode=1;

        positionPiece=0;
        positionMur=0;
        AfficherImages();
        /*
        if(switchMode==0) {
            switchMode=1;
            editText.setVisibility(View.INVISIBLE);
            recycler_view.setVisibility(View.INVISIBLE);
            button0.setVisibility(View.INVISIBLE);
            piece1.setVisibility(View.INVISIBLE);
            piece2.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);


            imageView.setVisibility(View.VISIBLE);
            buttonR.setVisibility(View.VISIBLE);
            buttonL.setVisibility(View.VISIBLE);
            pieceEtMur.setVisibility(View.VISIBLE);

            positionPiece=0;
            positionMur=0;
            AfficherImages();
        }else{
            editText.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.VISIBLE);
            button0.setVisibility(View.VISIBLE);
            piece1.setVisibility(View.VISIBLE);
            piece2.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);

            imageView.setVisibility(View.INVISIBLE);
            buttonR.setVisibility(View.INVISIBLE);
            buttonL.setVisibility(View.INVISIBLE);
            pieceEtMur.setVisibility(View.INVISIBLE);
            switchMode=0;
        }
       */
    }

    public void affichagePorte(){
        this.porteRect=null;
        PorteRect pr = new PorteRect(this);
        this.porteRect = pr;
        if(!pieces.get(positionPiece).murs.get(positionMur).disponible) {
            this.porteRect.setX(pieces.get(positionPiece).murs.get(positionMur).porte.x);
            this.porteRect.setY(pieces.get(positionPiece).murs.get(positionMur).porte.y);
            relativeLayout.addView(this.porteRect);
        }
    }

    public int ajouterPorteAuto(int i,Piece p){
        int n=0;
        String s=p.murs.get(i).murName;
        if(s.equals("N")){
            n=3;
        }else if(s.equals("E")){
            n=2;
        }else if(s.equals("W")){
            n=1;
        }else{
            n=0;
        }
        return n;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Toast.makeText(this,"Supprimer",Toast.LENGTH_SHORT).show();
                setOnClickSupprimer();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Recommencer",Toast.LENGTH_SHORT).show();
                reload();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Suspendre",Toast.LENGTH_SHORT).show();
                this.finish();
                return true;
            case R.id.item4:
                Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show();
                this.Save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Save() {
        //pieces.setPieces(pieces);
        //finish();*/
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gsonn = new Gson();
        // pieces.add(new Piece());
        String jsonn= gsonn.toJson(pieces);
        editor.putString("studata",jsonn);
        editor.apply();
        Toast.makeText(this, "Pieces enregistrées", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void reload() {
        /*
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
         */
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gsonn = new Gson();
        pieces=new ArrayList<>();
        String jsonn= gsonn.toJson(pieces);
        editor.putString("studata",jsonn);
        editor.apply();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_myhous,menu);
        return true;
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int[] dim2 = new int[2];
        dim2[0]= 0;
        dim2[0]= 0;
        if(this.porteRect!=null) {
            this.porteRect.getLocationOnScreen(dim2);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }
        boolean b2 = false;
        if ((event.getRawX() > dim2[0]) && (event.getRawX() < dim2[0] + this.porteRect.getWidth()) && (event.getRawY() > dim2[1]) && (event.getRawY() < dim2[1] + this.porteRect.getHeight())) {
            b2 = true;
            System.out.println("============= Test onTouchEvent =============");
            System.out.println("event.getRawX() :"+event.getRawX());
            System.out.println("event.getRawY() :"+event.getRawY());
            System.out.println("dim2[0] :"+dim2[0]);
            System.out.println("dim2[1] :"+dim2[1]);
            System.out.println("this.porteRect.getX() :"+this.porteRect.getX());
            System.out.println("this.porteRect.getX() :"+this.porteRect.getY());
            System.out.println("event.getX() :"+event.getX());
            System.out.println("event.getY() :"+event.getY());
            System.out.println("event.getY() :"+event.getY());
            System.out.println("============= Fin Test onTouchEvent =============");
        }
        return super.onTouchEvent(event);
    }

     */

}