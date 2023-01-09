package com.example.myhouse;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MeteoActivity extends AppCompatActivity {
    private Runnable runnable;
    private double longitude, latitude;
    private com.google.android.gms.location.FusedLocationProviderClient FusedLocationProviderClient;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);
        this.textView2 = findViewById(R.id.textView2);
        this.textView3 = findViewById(R.id.textView3);
        this.textView4 = findViewById(R.id.textView4);
        this.textView5 = findViewById(R.id.textView5);
        this.textView6 = findViewById(R.id.textView6);
        this.textView7 = findViewById(R.id.textView7);

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
        this.textView2.setText("Ville : "+jsonObject.get("name"));
        this.textView2.getTypeface().getStyle();
        this.textView2.setGravity(Gravity.CENTER);

        this.textView3.setText("Temperature : "+jsonObject.getJSONObject("main").get("temp")+" °");
        this.textView3.getTypeface().getStyle();
        this.textView3.setGravity(Gravity.CENTER);

        this.textView4.setText("Vent : "+jsonObject.getJSONObject("wind").get("speed"));
        this.textView4.getTypeface().getStyle();
        this.textView4.setGravity(Gravity.CENTER);

        this.textView5.setText("Humidité : "+jsonObject.getJSONObject("main").get("humidity"));
        this.textView5.getTypeface().getStyle();
        this.textView5.setGravity(Gravity.CENTER);

        this.textView6.setText("Pression : "+jsonObject.getJSONObject("main").get("pressure"));
        this.textView6.getTypeface().getStyle();
        this.textView6.setGravity(Gravity.CENTER);

        this.textView7.setText("Description : "+jsonObject.getJSONArray("weather").getJSONObject(0).get("description"));
        this.textView7.getTypeface().getStyle();
        this.textView7.setGravity(Gravity.CENTER);

        //image.setContentView(R.layout._1d);

    }

    public void setOnClicklocation(View view){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(this.runnable);
    }


    public Runnable runnableF()
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(MeteoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MeteoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    runOnUiThread(()->{
                        Toast.makeText(MeteoActivity.this,"Issue without permission",Toast.LENGTH_SHORT).show();
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

                //fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationToken).addOnSuccessListener(this,location -> {
                FusedLocationProviderClient.getLastLocation().addOnSuccessListener(MeteoActivity.this,location -> {
                    if(location != null){
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Log.i("longitude :",String.valueOf(longitude));
                        Log.i("latitude :",String.valueOf(latitude));
                        runOnUiThread(()->{
                            Toast.makeText(MeteoActivity.this,"latitude: "+latitude+"longitude: "+longitude,Toast.LENGTH_SHORT).show();
                        });

                        //String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=metric&appid=0626187f7963623f3b8ad314d62c65d3";
                        //String url = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=0626187f7963623f3b8ad314d62c65d3";
                        String urlTest = "https://www.google.com";
                        InputStream in = null;
                        URL url = null;

                        try {
                            runOnUiThread(()->{
                                Toast.makeText(MeteoActivity.this,"openStream: ",Toast.LENGTH_SHORT).show();
                            });

                            //in = new URL(urlTest).openStream();
                            ///
                            url = new URL(urlTest);
                            BufferedInputStream bis = new BufferedInputStream(url.openStream());
                            byte[] buffer = new byte[1024];
                            StringBuilder sb = new StringBuilder();
                            int bytesRead = 0;
                            while((bytesRead = bis.read(buffer)) > 0) {
                                String text = new String(buffer, 0, bytesRead);
                                sb.append(text);
                            }

                            ///
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });

            }
        };
        return runnable;
    }
}