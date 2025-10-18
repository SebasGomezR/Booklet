package com.example.app_booklet;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity{

    private TextView[] mensajes;
    private Button btnIniciar;
    private ImageView biopochito1, biopochito2;

    private int indice = 0; // mensaje actual


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, InicioActivity.class);
            startActivity(intent);
        });

        biopochito1 = findViewById(R.id.biopochitoImage);
        biopochito2 = findViewById(R.id.biopochitoImage2);

        // Se oculta la segunda imagen al inicio
        biopochito2.setVisibility(View.GONE);
        biopochito2.setAlpha(0f);

        mensajes = new TextView[]{
                findViewById(R.id.msg1),
                findViewById(R.id.msg2),
                findViewById(R.id.msg3),
                findViewById(R.id.msg4)
        };

        // Después de inicializar el array mensajes:
        for (int i = 0; i < mensajes.length; i++) {
            if (i == 0) {
                mensajes[i].setVisibility(View.VISIBLE);
                mensajes[i].setAlpha(1f);
            } else {
                mensajes[i].setVisibility(View.GONE);
                mensajes[i].setAlpha(0f);
            }
        }

        //Se oculta el botón iniciar
        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setVisibility(View.GONE);

        indice = 0;

        btnIniciar = findViewById(R.id.btnIniciar);

        // Evento al tocar la pantalla
        View fondo = findViewById(android.R.id.content); // toda la pantalla
        fondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSiguiente();
            }
        });
    }

    private void mostrarSiguiente() {
        if (indice < mensajes.length - 1) {
            // Animación: ocultar el mensaje actual
            mensajes[indice].animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction(() -> {
                        mensajes[indice].setVisibility(View.GONE);

                        // Mostrar el siguiente mensaje
                        indice++;
                        mensajes[indice].setAlpha(0f);
                        mensajes[indice].setVisibility(View.VISIBLE);

                        // 🔹 Cambiar imagen de Biopochito
                        if(indice == mensajes.length-1){
                            biopochito1.animate()
                                    .alpha(0f)
                                    .setDuration(600)
                                    .withEndAction(() -> {
                                        biopochito1.setVisibility(View.GONE);
                                        biopochito2.setVisibility(View.VISIBLE);
                                        biopochito2.animate().alpha(1f).setDuration(600).start();
                                    }).start();
                        }

                        // Animación de aparición
                        mensajes[indice].animate()
                                .alpha(1f)
                                .setDuration(700)
                                .withEndAction(() -> {
                                    // Efecto de flotación continua
                                    ObjectAnimator floatAnim = ObjectAnimator.ofFloat(
                                            mensajes[indice],
                                            "translationY",
                                            -10f, 10f
                                    );
                                    floatAnim.setDuration(2000);
                                    floatAnim.setRepeatCount(ValueAnimator.INFINITE);
                                    floatAnim.setRepeatMode(ValueAnimator.REVERSE);
                                    floatAnim.start();
                                })
                                .start();
                    }).start();
        } else {
            // Ya no hay más mensajes → mostrar botón iniciar
            mensajes[indice].animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction(() -> {
                        mensajes[indice].setVisibility(View.GONE);

                        // 🔹 Mostrar el botón Iniciar
                        btnIniciar.setVisibility(View.VISIBLE);
                        btnIniciar.setAlpha(0f);
                        btnIniciar.animate()
                                .alpha(1f)
                                .setDuration(700)
                                .start();
                    }).start();
        }
    }

}