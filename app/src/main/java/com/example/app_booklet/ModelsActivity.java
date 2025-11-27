package com.example.app_booklet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView imagenTema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);

        textoContenido = findViewById(R.id.textoContenido);
        imagenTema = findViewById(R.id.imagenTema);
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
                        "Rinoceronte de Java",
                        "Rhinoceros sondaicus",
                        "Bosques tropicales y zonas pantanosas del Parque Nacional Ujung Kulon (Indonesia)",
                        "Aproximadamente 60 a 75 ejemplares",
                        "Solitario, tranquilo y territorial; suele bañarse en el lodo para refrescarse y proteger su piel.",
                        null, null, null, null,
                        R.drawable.rinoceronte
                ),
                new InfoTemas(
                        "animal",
                        "Gorila de montaña",
                        "Gorilla beringei beringei",
                        "Bosques montañosos de Ruanda, Uganda y República Democrática del Congo",
                        "Cerca de 1 000 ejemplares",
                        "Vive en grupos familiares liderados por un macho dominante (‘espalda plateada’); pacífico y social.",
                        null, null, null, null,
                        R.drawable.gorila
                ),
                new InfoTemas(
                        "animal",
                        "Tigre",
                        "Panthera tigris",
                        "Bosques tropicales, manglares, sabanas y selvas del sur y sudeste de Asia",
                        "Aproximadamente 3 900 ejemplares en libertad",
                        "Solitario, cazador nocturno, muy territorial y excelente nadador.",
                        null, null, null, null,
                        R.drawable.tigre
                ),
                new InfoTemas(
                        "animal",
                        "Panda gigante",
                        "Ailuropoda melanoleuca",
                        "Bosques templados de bambú en las montañas del centro de China",
                        "Alrededor de 1 800 ejemplares en libertad",
                        "Solitario, tranquilo, pasa la mayor parte del día comiendo bambú y descansando.",
                        null, null, null, null,
                        R.drawable.panda
                ),
                new InfoTemas(
                        "animal",
                        "Oso polar",
                        "Ursus maritimus",
                        "Regiones árticas cubiertas de hielo y nieve",
                        "Entre 22 000 y 31 000 ejemplares",
                        "Solitario y nómada, excelente nadador, pasa mucho tiempo cazando focas sobre el hielo marino.",
                        null, null, null, null,
                        R.drawable.oso_polar
                ),
                new InfoTemas(
                        "animal",
                        "Morsa del Pacífico",
                        "Odobenus rosmarus divergens",
                        "Costas heladas y plataformas de hielo del mar de Bering y el Ártico",
                        "Entre 200 000 y 250 000 ejemplares",
                        "Social, forma grandes grupos en las costas; usa sus colmillos para desplazarse y defenderse.",
                        null, null, null, null,
                        R.drawable.morsa
                ),
                new InfoTemas(
                        "animal",
                        "Pingüino de Magallanes",
                        "Spheniscus magellanicus",
                        "Costas de Argentina, Chile y las islas Malvinas",
                        "Cerca de 1.3 millones de parejas reproductoras",
                        "Muy sociable, forma grandes colonias, nada y bucea con gran destreza, fiel a su pareja.",
                        null, null, null, null,
                        R.drawable.pinguino
                ),
                new InfoTemas(
                        "animal",
                        "Tortuga laúd",
                        "Dermochelys coriacea",
                        "Océanos tropicales y templados; playas arenosas para anidar",
                        "Unos 25 000 a 30 000 hembras anidantes",
                        "Solitaria, migratoria, puede recorrer miles de kilómetros; anida en el mismo sitio donde nació.",
                        null, null, null, null,
                        R.drawable.tortuga
                ),
                new InfoTemas(
                        "animal",
                        "Mariposa monarca",
                        "Danaus plexippus",
                        "Praderas, bosques templados y zonas rurales de Norteamérica y México",
                        "Millones de ejemplares, aunque en declive",
                        "Migratoria, recorre hasta 4 000 km; forma grandes colonias durante la hibernación en México.",
                        null, null, null, null,
                        R.drawable.mariposa
                ),
                new InfoTemas(
                        "animal",
                        "Abeja",
                        "Apis mellifera",
                        "Praderas, bosques, campos agrícolas y áreas urbanas",
                        "Miles de millones en todo el mundo, aunque en disminución",
                        "Social, vive en colmenas organizadas; esenciales para la polinización y la producción de miel.",
                        null, null, null, null,
                        R.drawable.abeja
                )
        );

        List<InfoTemas> plantas = Arrays.asList(
                new InfoTemas(
                        "planta",
                        "Frailejones (Espeletia spp.)",
                        "Espeletia spp.",
                        "Páramos de los Andes, especialmente en Colombia, Venezuela y Ecuador",
                        "Varias especies; algunas con poblaciones reducidas y en peligro",
                        "Plantas clave en los ecosistemas de páramo; captan agua de la niebla y la liberan al suelo, ayudando a regular el ciclo hídrico.",
                        null, null, null, null,
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "planta",
                        "Tejo europeo",
                        "Taxus baccata",
                        "Bosques templados de Europa, el norte de África y el oeste de Asia",
                        "Poblaciones fragmentadas y en disminución",
                        "Árbol de crecimiento lento y longevidad extrema; produce taxina, una sustancia tóxica. Amenazado por tala y recolección excesiva.",
                        null, null, null, null,
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "planta",
                        "Árbol de agar",
                        "Aquilaria spp.",
                        "Bosques tropicales del sudeste asiático",
                        "Varias especies en peligro crítico debido a la sobreexplotación",
                        "Produce una resina aromática muy valorada (oud); se tala ilegalmente para obtenerla, lo que ha reducido drásticamente sus poblaciones.",
                        null, null, null, null,
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "planta",
                        "Pehuén o pino araucaria",
                        "Araucaria araucana",
                        "Bosques templados de Chile y Argentina",
                        "Poblaciones naturales reducidas",
                        "Árbol sagrado para los pueblos mapuches; produce piñones comestibles. Amenazado por incendios forestales y tala.",
                        null, null, null, null,
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "planta",
                        "Nepenthes attenboroughii",
                        "Nepenthes attenboroughii",
                        "Montañas de Palawan (Filipinas)",
                        "Menos de 1 000 individuos estimados",
                        "Planta carnívora de gran tamaño que atrapa insectos; habita en suelos pobres en nutrientes. Amenazada por coleccionismo y pérdida de hábitat.",
                        null, null, null, null,
                        R.drawable.abeja                ),
                new InfoTemas(
                        "planta",
                        "Caoba",
                        "Swietenia macrophylla",
                        "Bosques tropicales de América Central y del Sur",
                        "En declive por tala indiscriminada",
                        "Árbol de madera fina muy apreciada; cumple funciones ecológicas importantes en los bosques tropicales.",
                        null, null, null, null,
                        R.drawable.abeja                ),
                new InfoTemas(
                        "planta",
                        "Cícadas",
                        "Cycadaceae",
                        "Regiones tropicales y subtropicales de América, África y Asia",
                        "Muchas especies con menos de 1 000 ejemplares",
                        "Plantas antiguas, similares a palmas; de crecimiento lento y muy vulnerables a la extracción ilegal y pérdida de hábitat.",
                        null, null, null, null,
                        R.drawable.abeja                ),
                new InfoTemas(
                        "planta",
                        "Guayacán",
                        "Guaiacum officinale",
                        "Bosques secos tropicales del Caribe y norte de Sudamérica",
                        "Menos de 10 000 individuos maduros",
                        "Árbol de madera extremadamente dura y resistente; sobreexplotado por su valor comercial. También usado en medicina tradicional.",
                        null, null, null, null,
                        R.drawable.abeja                ),
                new InfoTemas(
                        "planta",
                        "Magnolia espinalii",
                        "Magnolia espinalii",
                        "Bosques húmedos del occidente de Colombia",
                        "Población reducida y distribución limitada",
                        "Árbol endémico colombiano con flores blancas grandes y fragantes; amenazado por deforestación y fragmentación del hábitat.",
                        null, null, null, null,
                        R.drawable.abeja                ),
                new InfoTemas(
                        "planta",
                        "Flor de mayo",
                        "Cattleya trianae",
                        "Bosques húmedos de montaña en Colombia",
                        "En peligro debido a la pérdida de hábitat",
                        "Orquídea nacional de Colombia, símbolo de belleza natural; epífita, crece sobre árboles y depende de ambientes muy específicos.",
                        null, null, null, null,
                        R.drawable.abeja                )
        );

        List<InfoTemas> deforestacion = Arrays.asList(
                new InfoTemas(
                        "deforestacion",
                        "Amazonas colombiano",
                        null, null, null, null,
                        "Tala ilegal, ganadería extensiva y minería.",
                        "Pérdida de biodiversidad, alteración del clima y erosión del suelo.",
                        "Fortalecer la reforestación, vigilancia ambiental y educación comunitaria.",
                        "Región Amazónica de Colombia",
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "deforestacion",
                        "Chocó biogeográfico",
                        null, null, null, null,
                        "Expansión agrícola y minería ilegal.",
                        "Desaparición de especies únicas y contaminación de ríos.",
                        "Promover economías sostenibles y programas de conservación y vigilancia ambiental..",
                        "Costa Pacífica colombiana",
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "deforestacion",
                        "Sierra Nevada de Santa Marta",
                        null, null, null, null,
                        "Incendios forestales y expansión urbana.",
                        "Deslizamientos de tierra y pérdida de fuentes hídricas y desequilibrio ecológico..",
                        "Restauración ecológica y control de quemas y sensibilización a comunidades locales..",
                        "Región Caribe de Colombia",
                        R.drawable.abeja
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
                        "Zona Andina y Caribe colombiano",
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "contaminacion",
                        "Río Bogotá",
                        null, null, null, null,
                        "Descarga de aguas residuales y desechos químicos.",
                        "Alta toxicidad del agua, pérdida de especies y malos olores.",
                        "Fortalecer el control de vertimientos y promover el reciclaje de aguas grises.",
                        "Cundinamarca, Colombia",
                        R.drawable.abeja
                ),
                new InfoTemas(
                        "contaminacion",
                        "Contaminación por derrames de petróleo",
                        null, null, null, null,
                        "Fugas en oleoductos y accidentes marítimos.",
                        "Daños graves a ecosistemas acuáticos y aves marinas.",
                        "Mejorar la infraestructura de transporte de crudo y planes de contingencia.",
                        "Zonas petroleras y marítimas de Colombia",
                        R.drawable.abeja
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

            // Mostrar la imagen correspondiente
            imagenTema.setImageResource(item.getImagenResId());
            imagenTema.setAlpha(0f);
            imagenTema.animate().alpha(1f).setDuration(500).start();

            // Mostrar el texto con animación
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