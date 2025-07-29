package com.aerodesign.gps_visualizer.controller;

import com.aerodesign.gps_visualizer.model.GpsData;
import com.aerodesign.gps_visualizer.service.GpsReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer los datos GPS más recientes a través de la API.
 * Proporciona dos endpoints: uno más formal (/api/gps) y otro más directo (/gps).
 */

@RestController
public class GpsController {

     /**
     * Endpoint principal para obtener los datos GPS más recientes.
     * Ruta: /api/gps
     *
     * @return {@link ResponseEntity} con el objeto {@link GpsData} si está disponible,
     *         o un código 204 No Content si no hay datos válidos.
     */

    @GetMapping("/api/gps")
    public ResponseEntity<GpsData> getGpsDataApi() {
        return getGpsResponse();
    }

    /**
     * Endpoint alternativo simplificado para obtener datos GPS.
     * Ruta: /gps
     *
             * @return {@link ResponseEntity} con el objeto {@link GpsData} si está disponible,
            *         o un código 204 No Content si no hay datos válidos.
     */

    @GetMapping("/gps")
    public ResponseEntity<GpsData> getGpsDataShort() {
        return getGpsResponse();
    }

    /**
     * Método privado que centraliza la lógica de respuesta para ambos endpoints.
     * Verifica si los datos GPS más recientes son válidos antes de responder.
     **/
    private ResponseEntity<GpsData> getGpsResponse() {
        GpsData data = GpsReader.getLatestData();
        if (data == null || (data.getLatitud() == 0.0 && data.getLongitud() == 0.0)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }
}
