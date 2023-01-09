package com.example.myhouse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setOnClickMyHouseActivity(View view) {
        Toast.makeText(MainActivity.this, "MyHouse", Toast.LENGTH_SHORT).show() ;
        Intent intent = new Intent(this, MyHouse.class);
        startActivity(intent);
        Log.i("MainActivity","setOnClickMyHouseActivity Fonction");
    }

    public void setOnClickMyHouseMaquetteActivity(View view) {
        Toast.makeText(MainActivity.this, "MyHouse", Toast.LENGTH_SHORT).show() ;
        Intent intent = new Intent(this, MyHouseMaquette.class);
        startActivity(intent);
        Log.i("MainActivity","setOnClickMyHouseMaquetteActivity Fonction");
    }

    public void setOnClickMeteoActivity(View view) {
        Toast.makeText(MainActivity.this, "Meteo", Toast.LENGTH_SHORT).show() ;
        Intent intent = new Intent(this,MeteoActivity.class);
        startActivity(intent);
        Log.i("MainActivity","setOnClickMeteoActivity (MeteoActivity) Fonction");
    }

}