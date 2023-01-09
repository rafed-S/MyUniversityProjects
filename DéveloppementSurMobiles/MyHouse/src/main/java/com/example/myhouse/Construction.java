package com.example.myhouse;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Construction extends AppCompatActivity {
    final static public String KEY_ENVOYER = "envoyer";
    final static public String KEY_RECEVOIR = "reception";
    //public Batiment batiment;
    public Piece piece;
    protected ActivityResultLauncher<Intent> launcherN;
    protected ActivityResultLauncher<Intent> launcherE;
    protected ActivityResultLauncher<Intent> launcherW;
    protected ActivityResultLauncher<Intent> launcherS;
    public ImageView imageViewN;
    public ImageView imageViewE;
    public ImageView imageViewW;
    public ImageView imageViewS;
    public String mN;
    public String mE;
    public String mW;
    public String mS;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;
    private ImageView imageView;
    private SensorManager mSensorManager2;
    private Sensor sensorMagneticField;
    private Sensor mAccelerometer2;
    private float[] floatGravity = new float[3];
    private float[] floatMagnetic = new float[3];
    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];
    private int pointsPlotted=5;
    public double rotation;
    boolean n,s,w,e;
    //Meteo
    private Runnable runnable;
    private double longitude, latitude;
    private FusedLocationProviderClient FusedLocationProviderClient;
    TextView textView2;
    TextView textView3;
    public String meteoV;
    public String meteoT;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = Math.sqrt(x*x + y*y + z*z);
            double changeInAccelleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;
            ///magnetometre
            floatGravity = sensorEvent.values;
            mSensorManager2.getRotationMatrix(floatRotationMatrix,null,floatGravity,floatMagnetic);
            mSensorManager2.getOrientation(floatRotationMatrix,floatOrientation);
            imageView.setRotation((float) (-floatOrientation[0]*180/3.14159));
            //update graph
            pointsPlotted++;
            if(pointsPlotted > 1000){
                pointsPlotted=1;
            }

            double rotation = -floatOrientation[0]*180/3.14159;
            if(rotation<40 && rotation>-40){
                n=true;
            }else{
                n=false;
            }
            if((rotation <= 180 && rotation >= 150) ||(rotation >= -180 && rotation <= -150)){
                s=true;
            }else{
                s=false;
            }
            if(rotation >= 60 && rotation <= 130){
                w=true;
            }else{
                w=false;
            }
            if(rotation <= -60 && rotation >= -130){
                e=true;
            }else{
                e=false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            floatMagnetic = sensorEvent.values;
            mSensorManager2.getRotationMatrix(floatRotationMatrix,null,floatGravity,floatMagnetic);
            mSensorManager2.getOrientation(floatRotationMatrix,floatOrientation);
            imageView.setRotation((float) (-floatOrientation[0]*180/3.14159));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction);
        this.textView2 = findViewById(R.id.textView2);
        this.textView3 = findViewById(R.id.textView3);
        //batiment = (Batiment) getIntent().getSerializableExtra(KEY_ENVOYER);
        piece = (Piece) getIntent().getSerializableExtra(KEY_ENVOYER);
        this.imageViewN = (ImageView)findViewById(R.id.imageViewN);
        imageViewN.setClickable(true);
        this.imageViewE = (ImageView)findViewById(R.id.imageViewE);
        imageViewE.setClickable(true);
        this.imageViewW = (ImageView)findViewById(R.id.imageViewW);
        imageViewW.setClickable(true);
        this.imageViewS = (ImageView)findViewById(R.id.imageViewS);
        imageViewS.setClickable(true);
        //Accéléromètre
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //magnétomètre
        imageView = findViewById(R.id.imageView);
        mSensorManager2 =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer2 = mSensorManager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = mSensorManager2.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager2.registerListener(sensorEventListener,mAccelerometer2,mSensorManager2.SENSOR_DELAY_NORMAL);
        mSensorManager2.registerListener(sensorEventListenerMagneticField,sensorMagneticField,mSensorManager2.SENSOR_DELAY_NORMAL);

        OnClickListener();



        launcherN = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result) {
                Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                //mN =BitMapToString(imageBitmap);
                Toast.makeText(Construction.this, "Save Photo", Toast.LENGTH_SHORT).show() ;
                Log.i("Construction","onActivityResult Fonction");
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("imageN.data", MODE_PRIVATE);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recupererImagesN();
            }});

        launcherE = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                        mE =BitMapToString(imageBitmap);
                        Toast.makeText(Construction.this, "Save Photo", Toast.LENGTH_SHORT).show() ;
                        Log.i("Construction","onActivityResult Fonction");

                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("imageE.data", MODE_PRIVATE);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        recupererImagesE();
                    }});

        launcherW = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                        mW =BitMapToString(imageBitmap);
                        Toast.makeText(Construction.this, "Save Photo", Toast.LENGTH_SHORT).show() ;
                        Log.i("Construction","onActivityResult Fonction");

                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("imageW.data", MODE_PRIVATE);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        recupererImagesW();
                    }});

        launcherS = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                        mS =BitMapToString(imageBitmap);
                        Toast.makeText(Construction.this, "Save Photo", Toast.LENGTH_SHORT).show() ;
                        Log.i("Construction","onActivityResult Fonction");

                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("imageS.data", MODE_PRIVATE);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        recupererImagesS();
                    }});
            //Meteo();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 48);
        }
        FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        runnable = ()->{
            //FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                runOnUiThread(()->{
                    Toast.makeText(this,"Issue without permission",Toast.LENGTH_SHORT).show();
                });
                return;
            }
            CancellationToken cancellationToken = new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    Log.i("MeteoActivity", "dans le cancellationtoken");
                    return null;
                }
            };

            FusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationToken).addOnSuccessListener(this,location -> {

                //FusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,location -> {
                if(location != null){
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Log.i("longitude :",String.valueOf(longitude));
                    Log.i("latitude :",String.valueOf(latitude));
                    runOnUiThread(()->{
                        Toast.makeText(this,"latitude: "+latitude+"longitude: "+longitude,Toast.LENGTH_SHORT).show();
                        Log.i("location","latitude: "+latitude+" || longitude: "+longitude);
                    });
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(()-> {
                        try {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "location ", Toast.LENGTH_SHORT).show();
                            });
                            String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=metric&appid=0626187f7963623f3b8ad314d62c65d3";
                            //String url = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=0626187f7963623f3b8ad314d62c65d3";
                            InputStream in = new URL(url).openStream();
                            //in.close();
                            JSONObject res = readStream(in) ;
                            //this.textView2.setText(""+res.get("name"));
                            JSONObjectReadStream(res);
                            /*
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        };
        //this.runnable = runnableF();
        setOnClickMeteo();
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public void OnClickListener(){
            imageViewN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent class will help to go to next activity using
                    // it's object named intent.
                    // SecondActivty is the name of new created EmptyActivity.
                    if(n==true) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        launcherN.launch(intent);
                    }
                }
            });
            imageViewE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent class will help to go to next activity using
                    // it's object named intent.
                    // SecondActivty is the name of new created EmptyActivity.
                    if(e==true) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        launcherE.launch(intent);
                    }
                }
            });
            imageViewW.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent class will help to go to next activity using
                    // it's object named intent.
                    // SecondActivty is the name of new created EmptyActivity.
                    if(w==true) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        launcherW.launch(intent);
                    }
                }
            });
            imageViewS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent class will help to go to next activity using
                    // it's object named intent.
                    // SecondActivty is the name of new created EmptyActivity.
                    if(s==true) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        launcherS.launch(intent);
                    }
                }
            });
    }


    private void recupererImagesN() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("imageN.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);

            mN =BitMapToString(bm);

            this.imageViewN = (ImageView)findViewById(R.id.imageViewN);
            this.imageViewN.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void recupererImagesE() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("imageE.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            this.imageViewE = (ImageView)findViewById(R.id.imageViewE);
            this.imageViewE.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void recupererImagesW() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("imageW.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            this.imageViewW = (ImageView)findViewById(R.id.imageViewW);
            this.imageViewW.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void recupererImagesS() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("imageS.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            this.imageViewS = (ImageView)findViewById(R.id.imageViewS);
            this.imageViewS.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void recuperer() {
        Mur mur1 = new Mur(this.mN,"N");
        //this.batiment.getPiece(batiment.pieceSelectedNum).murs.add(mur1);
        piece.murs.add(mur1);
        Mur mur2 = new Mur(this.mE,"E");
        //this.batiment.getPiece(batiment.pieceSelectedNum).murs.add(mur2);
        piece.murs.add(mur2);
        Mur mur3 = new Mur(this.mW,"W");
        //this.batiment.getPiece(batiment.pieceSelectedNum).murs.add(mur3);
        piece.murs.add(mur3);
        Mur mur4 = new Mur(this.mS,"S");
        //this.batiment.getPiece(batiment.pieceSelectedNum).murs.add(mur4);
        piece.murs.add(mur4);

        piece.setMeteoVille(this.meteoV);
        piece.setMeteoTemperature(this.meteoT);
    }

    public void setOnClickMyHouseActivity(View view) {
        Toast.makeText(Construction.this, "MyHouse", Toast.LENGTH_SHORT).show();
        recuperer();
        if (piece==null){
            Log.i("Construction","error Rec batiment dans Construction");
        }
        Intent intent = new Intent(this, Construction.class);
        intent.putExtra(KEY_RECEVOIR,piece);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager2.registerListener(sensorEventListenerMagneticField, mAccelerometer2, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
        //mSensorManager2.unregisterListener(sensorEventListenerMagneticField);
    }

    public void Meteo(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 48);
        }
        FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        runnable = ()->{
            //FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                runOnUiThread(()->{
                    Toast.makeText(this,"Issue without permission",Toast.LENGTH_SHORT).show();
                });
                return;
            }
            CancellationToken cancellationToken = new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    Log.i("MeteoActivity", "dans le cancellationtoken");
                    return null;
                }
            };

            FusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationToken).addOnSuccessListener(this,location -> {
                //FusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,location -> {
                if(location != null){
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Log.i("longitude :",String.valueOf(longitude));
                    Log.i("latitude :",String.valueOf(latitude));
                    runOnUiThread(()->{
                        Toast.makeText(this,"latitude: "+latitude+"longitude: "+longitude,Toast.LENGTH_SHORT).show();
                        Log.i("location","latitude: "+latitude+" || longitude: "+longitude);
                    });

                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(()-> {
                        try {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "location ", Toast.LENGTH_SHORT).show();
                            });
                            String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=metric&appid=0626187f7963623f3b8ad314d62c65d3";
                            //String url = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=0626187f7963623f3b8ad314d62c65d3";
                            InputStream in = new URL(url).openStream();
                            //in.close();
                            JSONObject res = readStream(in) ;
                            //this.textView2.setText(""+res.get("name"));
                            JSONObjectReadStream(res);
                            /*
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        };
        //this.runnable = runnableF();
    }

    public JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for(String line = r.readLine(); line !=null; line = r.readLine()){
            sb.append(line);
        }
        is.close();
        return new JSONObject(sb.toString());
    }

    public void JSONObjectReadStream(JSONObject jsonObject)throws JSONException{

        String s1 = "Ville : "+jsonObject.get("name");
        String s2 = "Temperature : "+jsonObject.getJSONObject("main").get("temp")+" °";
        System.out.println("0000000000000000 Test Meteo 00000000000000000000");
        //System.out.println(this.meteo);

        this.meteoV= s1;
        this.meteoT=s2;
        this.textView2.setText("Ville : "+jsonObject.get("name"));
        this.textView2.getTypeface().getStyle();
        this.textView2.setGravity(Gravity.CENTER);

        this.textView3.setText("Temperature : "+jsonObject.getJSONObject("main").get("temp")+" °");
        this.textView3.getTypeface().getStyle();
        this.textView3.setGravity(Gravity.CENTER);

    }

    public void setOnClickMeteo(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(this.runnable);
    }




}