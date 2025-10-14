package com.example.app_booklet;

public class InfoTemas {

    private String tipo;
    private String nombre;
    private String nombreCientifico;
    private String habitat;
    private String cantidadEjemplares;
    private String comportamiento;
    private String causas;
    private String consecuencias;
    private String posiblesSoluciones;
    private String ubicacion;


    public InfoTemas(String tipo, String nombre, String nombreCientifico, String habitat,
                         String cantidadEjemplares, String comportamiento,
                         String causas, String consecuencias, String posiblesSoluciones,
                         String ubicacion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.habitat = habitat;
        this.cantidadEjemplares = cantidadEjemplares;
        this.comportamiento = comportamiento;
        this.causas = causas;
        this.consecuencias = consecuencias;
        this.posiblesSoluciones = posiblesSoluciones;
        this.ubicacion = ubicacion;
    }

    public String getTipo() { return tipo; }

    public String getNombre() { return nombre; }

    public String getNombreCientifico() { return nombreCientifico; }

    public String getHabitat() { return habitat; }

    public String getCantidadEjemplares() { return cantidadEjemplares; }

    public String getComportamiento() { return comportamiento; }

    public String getCausas() { return causas; }

    public String getConsecuencias() { return consecuencias; }

    public String getPosiblesSoluciones() { return posiblesSoluciones; }

    public String getUbicacion() { return ubicacion; }
}
