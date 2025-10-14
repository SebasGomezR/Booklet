package com.example.app_booklet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    //En esta Clase el usuario tiene la opción de seleccionar el tema sobre el cual desea observar y aprender

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.btnAnimales).setOnClickListener(v -> abrirTema("animales"));
        findViewById(R.id.btnPlantas).setOnClickListener(v -> abrirTema("plantas"));
        findViewById(R.id.btnDeforestacion).setOnClickListener(v -> abrirTema("deforestacion"));
        findViewById(R.id.btnContaminacion).setOnClickListener(v -> abrirTema("contaminacion"));
    }

    private void abrirTema(String tema) {
        Intent intent = new Intent(this, ModelsActivity.class);
        intent.putExtra("tema", tema);
        startActivity(intent);
    }
}