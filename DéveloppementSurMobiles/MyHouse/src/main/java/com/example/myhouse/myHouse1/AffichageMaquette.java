package com.example.myhouse.myHouse1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myhouse.R;

public class AffichageMaquette extends AppCompatActivity {
    public BatimentMaquette batimentMaquette;
    final static public String BATIMENT_KEY = "batimentStructure";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        Intent i = getIntent();
        //batiment = (Batiment)i.getSerializableExtra(BATIMENT_KEY);
        batimentMaquette = i.getParcelableExtra(BATIMENT_KEY);

        ViewPager viewPager = findViewById(R.id.viewPager);
        AdapterMaquette adapterMaquette = new AdapterMaquette(this);
        viewPager.setAdapter(adapterMaquette);
        Log.i("Affichage","onCreate Fonction");
    }
}