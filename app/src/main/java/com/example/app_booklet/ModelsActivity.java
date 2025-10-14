package com.example.app_booklet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelsActivity extends AppCompatActivity {

    private TextView textoContenido;
    private Button btnSiguiente;
    private int indice = 0;
    private List<InfoTemas> listaActual;
    private String temaSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        textoContenido = findViewById(R.id.textoContenido);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        temaSeleccionado = getIntent().getStringExtra("tema");
        listaActual = cargarInformacion(temaSeleccionado);

        mostrarInformacionActual();

        btnSiguiente.setOnClickListener(v -> mostrarSiguiente());
    }

    private List<InfoTemas> cargarInformacion(String tema) {
        Map<String, List<InfoTemas>> infoPorTema = new HashMap<>();

        // ********    INFORMACIÓN DE LOS 4 TEMAS PRINCIPALES     *********  //
        List<InfoTemas> animales = Arrays.asList(
                new InfoTemas(
                        "animal",
                        "Jaguar",
                        "Panthera onca",
                        "Selvas tropicales de América del Sur y Central",
                        "Aproximadamente 15 000 ejemplares",
                        "Cazador solitario y excelente nadador. Su supervivencia se ve afectada por la pérdida de hábitat y la caza ilegal.",
                        null, null, null, null
                ),
                new InfoTemas(
                        "animal",
                        "Oso de anteojos",
                        "Tremarctos ornatus",
                        "Bosques nublados y zonas montañosas de los Andes",
                        "Entre 10 000 y 20 000 ejemplares",
                        "Es el único oso de Sudamérica. Juega un papel clave en la dispersión de semillas. Amenazado por la deforestación.",
                        null, null, null, null
                ),
                new InfoTemas(
                        "animal",
                        "Delfín rosado del Amazonas",
                        "Inia geoffrensis",
                        "Ríos y afluentes del Amazonas y Orinoco",
                        "Menos de 50 000 ejemplares",
                        "Inteligente y sociable. Afectado por la contaminación y las redes de pesca.",
                        null, null, null, null
                )
        );

        List<InfoTemas> plantas = Arrays.asList(
                new InfoTemas(
                        "planta",
                        "Guayacán",
                        "Guaiacum officinale",
                        "Bosques secos tropicales del Caribe y norte de Sudamérica",
                        "En peligro",
                        "Su madera es muy dura y valiosa. Es importante para la polinización de abejas y la recuperación de suelos.",
                        null, null, null, null
                ),
                new InfoTemas(
                        "planta",
                        "Magnolia espinalii",
                        "Magnolia espinalii",
                        "Bosques húmedos del noroccidente de Colombia",
                        "En peligro crítico",
                        "Contribuye al equilibrio de los ecosistemas de montaña. Amenazada por la deforestación y expansión agrícola.",
                        null, null, null, null
                ),
                new InfoTemas(
                        "planta",
                        "Flor de mayo (Cattleya trianae)",
                        "Cattleya trianae",
                        "Selvas húmedas andinas de Colombia",
                        "Vulnerable",
                        "Es la flor nacional de Colombia. Su belleza la hace objeto de extracción ilegal.",
                        null, null, null, null
                )
        );

        List<InfoTemas> deforestacion = Arrays.asList(
                new InfoTemas(
                        "deforestacion",
                        "Amazonas colombiano",
                        null, null, null, null,
                        "Tala ilegal, ganadería extensiva y minería.",
                        "Pérdida de biodiversidad, alteración del clima y erosión del suelo.",
                        "Fortalecer la reforestación, vigilancia ambiental y educación comunitaria.",
                        "Región Amazónica de Colombia"
                ),
                new InfoTemas(
                        "deforestacion",
                        "Chocó biogeográfico",
                        null, null, null, null,
                        "Expansión agrícola y minería ilegal.",
                        "Desaparición de especies únicas y contaminación de ríos.",
                        "Promover economías sostenibles y programas de conservación y vigilancia ambiental..",
                        "Costa Pacífica colombiana"
                ),
                new InfoTemas(
                        "deforestacion",
                        "Sierra Nevada de Santa Marta",
                        null, null, null, null,
                        "Incendios forestales y expansión urbana.",
                        "Deslizamientos de tierra y pérdida de fuentes hídricas y desequilibrio ecológico..",
                        "Restauración ecológica y control de quemas y sensibilización a comunidades locales..",
                        "Región Caribe de Colombia"
                )
        );

        List<InfoTemas> contaminacion = Arrays.asList(
                new InfoTemas(
                        "contaminacion",
                        "Río Magdalena",
                        null, null, null, null,
                        "Vertimiento de residuos industriales y domésticos.",
                        "Afecta la fauna acuática y el suministro de agua potable.",
                        "Implementar plantas de tratamiento y campañas de educación ambiental.",
                        "Zona Andina y Caribe colombiano"
                ),
                new InfoTemas(
                        "contaminacion",
                        "Río Bogotá",
                        null, null, null, null,
                        "Descarga de aguas residuales y desechos químicos.",
                        "Alta toxicidad del agua, pérdida de especies y malos olores.",
                        "Fortalecer el control de vertimientos y promover el reciclaje de aguas grises.",
                        "Cundinamarca, Colombia"
                ),
                new InfoTemas(
                        "contaminacion",
                        "Contaminación por derrames de petróleo",
                        null, null, null, null,
                        "Fugas en oleoductos y accidentes marítimos.",
                        "Daños graves a ecosistemas acuáticos y aves marinas.",
                        "Mejorar la infraestructura de transporte de crudo y planes de contingencia.",
                        "Zonas petroleras y marítimas de Colombia"
                )
        );

        infoPorTema.put("animales", animales);
        infoPorTema.put("plantas", plantas);
        infoPorTema.put("deforestacion", deforestacion);
        infoPorTema.put("contaminacion", contaminacion);

        return infoPorTema.get(tema);
    }

    private void mostrarInformacionActual() {
        if (listaActual != null && indice < listaActual.size()) {
            InfoTemas item = listaActual.get(indice);
            String texto = "";

            switch (item.getTipo()) {
                case "animal":
                    texto = "🐾 " + item.getNombre() + "\n\n" +
                            "Nombre científico: " + item.getNombreCientifico() + "\n\n" +
                            "Hábitat: " + item.getHabitat() + "\n\n" +
                            "Cantidad estimada: " + item.getCantidadEjemplares() + "\n\n" +
                            "Comportamiento: " + item.getComportamiento();
                    break;

                case "planta":
                    texto = "🌿 " + item.getNombre() + "\n\n" +
                            "Nombre científico: " + item.getNombreCientifico() + "\n\n" +
                            "Hábitat: " + item.getHabitat() + "\n\n" +
                            "Estado de conservación: " + item.getCantidadEjemplares() + "\n\n" +
                            "Importancia ecológica: " + item.getComportamiento();
                    break;

                case "deforestacion":
                case "contaminacion":
                    texto = "🌎 " + item.getNombre() + "\n\n" +
                            "Causas: " + item.getCausas() + "\n\n" +
                            "Consecuencias: " + item.getConsecuencias() + "\n\n" +
                            "Posibles soluciones: " + item.getPosiblesSoluciones();
                    break;
            }

            // Animación de aparición
            textoContenido.setAlpha(0f);
            textoContenido.setText(texto);
            textoContenido.animate().alpha(1f).setDuration(500).start();
        } else {
            volverAlMenu();
        }
    }

    private void mostrarSiguiente() {
        if (indice < listaActual.size() - 1) {
            textoContenido.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction(() -> {
                        indice++;
                        mostrarInformacionActual();
                    }).start();
        } else {
            volverAlMenu();
        }
    }

    private void volverAlMenu() {
        textoContenido.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            textoContenido.setText("🌱 Has completado este tema.\n\nPresiona para volver al menú principal.");
            btnSiguiente.setText("Volver al menú");
            textoContenido.animate().alpha(1f).setDuration(400).start();
            btnSiguiente.setOnClickListener(v -> finish());
        }).start();
    }
}