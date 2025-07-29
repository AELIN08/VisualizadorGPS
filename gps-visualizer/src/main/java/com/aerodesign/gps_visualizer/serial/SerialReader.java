package com.aerodesign.gps_visualizer.serial;

import com.aerodesign.gps_visualizer.model.GpsData;
import com.fazecast.jSerialComm.SerialPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Clase encargada de leer datos desde un puerto serial, validar si la información recibida
 * es un JSON válido y procesar los datos GPS deserializados.
 *
 * Utiliza la librería jSerialComm para la comunicación serial y Jackson para el manejo de JSON.
 */

public class SerialReader {
    private static final int TIMEOUT = 1000; // Timeout de 1 segundo
    private static final int BAUD_RATE = 9600; // Velocidad de baudios

    private SerialPort serialPort;

    public SerialReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void startReading() {
        try {
            // Establecer el puerto serial
            serialPort.openPort();  // Asegúrate de abrir el puerto

            // Crear el reader para leer las líneas del puerto serial
            InputStream inputStream = serialPort.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Línea recibida: " + line); // Para depuración

                // Validar si la línea es un JSON completo
                if (isValidJson(line)) {
                    processGpsData(line);
                } else {
                    // Si no es un JSON válido, ignorar
                    System.out.println("Línea ignorada (no es JSON completo): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo del puerto serial: " + e.getMessage());
        } finally {
            // Cierra el puerto cuando termine la lectura
            if (serialPort.isOpen()) {
                serialPort.closePort();
            }
        }
    }

    // Verifica si la línea es un JSON válido
    private boolean isValidJson(String line) {
        try {
            new ObjectMapper().readTree(line); // Usamos Jackson para validar el JSON
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Procesa los datos GPS
    private void processGpsData(String json) {
        try {
            // Deserializa el JSON a un objeto de la clase de datos GPS
            GpsData gpsData = new ObjectMapper().readValue(json, GpsData.class);
            System.out.println("Datos GPS recibidos: " + gpsData);

            // Envia datos al frontend
        } catch (IOException e) {
            System.err.println("Error procesando datos GPS: " + e.getMessage());
        }
    }
}
