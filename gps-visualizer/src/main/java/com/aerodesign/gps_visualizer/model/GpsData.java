package com.aerodesign.gps_visualizer.model;

/**
 * Modelo de datos que representa la información obtenida de un módulo GPS.
 * Incluye latitud, longitud, altitud, velocidad y la fecha del dato recibido.
 *
 * Esta clase es utilizada para la deserialización de datos JSON, así como para
 * transferir la información entre el backend y el frontend.
 */

public class GpsData {
    private double latitud;
    private double longitud;
    private double altitud;
    private double velocidad;
    private String fecha;

    //Constructor vacío necesario para frameworks como Jackson (deserialización JSON).
    public GpsData() {
    }

    //Constructor completo para inicializar todos los campos del objeto.
    public GpsData(double latitud, double longitud, double altitud, double velocidad, String fecha) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
        this.velocidad = velocidad;
        this.fecha = fecha;
    }

    // Getters y setters
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public double getAltitud() { return altitud; }
    public void setAltitud(double altitud) { this.altitud = altitud; }

    public double getVelocidad() { return velocidad; }
    public void setVelocidad(double velocidad) { this.velocidad = velocidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Retorna una representación legible del objeto GPSData, útil para depuración.
    @Override
    public String toString() {
        return String.format("Lat: %.6f, Lng: %.6f, Alt: %.2f, Vel: %.2f, Fecha: %s",
                latitud, longitud, altitud, velocidad, fecha);
    }
}
