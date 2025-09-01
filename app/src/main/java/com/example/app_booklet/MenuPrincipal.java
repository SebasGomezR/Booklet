package com.example.app_booklet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {

    //En esta Clase el usuario tiene la opción de seleccionar el tema sobre el cual desea observar y aprender

    Button btn_animal_extincion, btn_planta_extincion, btn_tala_arboles, btn_contaminacion_agua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btn_animal_extincion = findViewById(R.id.animal_extincion);
        btn_planta_extincion = findViewById(R.id.planta_extincion);
        btn_tala_arboles = findViewById(R.id.tala_arboles);
        btn_contaminacion_agua = findViewById(R.id.contaminacion_agua);

        btn_animal_extincion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, InfoAnimalesExtincion.class);
                startActivity(intent);
                finish();
            }
        });

    }
}