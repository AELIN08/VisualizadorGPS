package com.aerodesign.gps_visualizer.service;

import com.aerodesign.gps_visualizer.model.GpsData;
import com.fazecast.jSerialComm.SerialPort;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;


/**
 * Clase encargada de iniciar la lectura de datos GPS desde un puerto serial específico.
 * Utiliza jSerialComm para la comunicación serial y Jackson para deserializar los datos JSON
 * recibidos en objetos de tipo {@link GpsData}.
 */

public class GpsReader {

    // Objeto que almacena los datos GPS más recientes
    private static GpsData latestData = new GpsData();
    // Puerto serial donde está conectado el módulo GPS (ajustar según el sistema operativo)
    private static final String PUERTO = "COM6";
    // Velocidad de transmisión serial (baudios)
    private static final int BAUDIOS = 9600;

    /**
     * Inicia la lectura de datos del puerto serial en un hilo separado.
     * Los datos recibidos se procesan como cadenas JSON y se almacenan en latestData.
     */

    public static void start() {
        try {
            SerialPort port = SerialPort.getCommPort(PUERTO);
            port.setBaudRate(BAUDIOS);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 0);

            if (!port.openPort()) {
                System.err.println("❌ No se pudo abrir el puerto serial " + PUERTO);
                return;
            }

            System.out.println("✅ Puerto " + PUERTO + " abierto. Esperando datos...");

            ObjectMapper mapper = new ObjectMapper();

            // Hilo para leer continuamente los datos del puerto
            new Thread(() -> {

                //Menejo de posibles errores
                try (InputStream in = port.getInputStream()) {
                    StringBuilder buffer = new StringBuilder();
                    int c;

                    while ((c = in.read()) != -1) {
                        buffer.append((char) c);

                        if (c == '\n') {
                            String json = buffer.toString().trim();
                            buffer.setLength(0); // Limpiar el buffer

                            // Ignorar líneas vacías
                            if (json.isEmpty()) {
                                continue;
                            }

                            // Validar formato básico JSON
                            if (!json.startsWith("{") || !json.endsWith("}")) {
                                System.out.println("⚠️ Línea ignorada (no es JSON completo): " + json);
                                continue;
                            }

                            System.out.println("✨ Línea recibida: " + json);

                            try {
                                GpsData data = mapper.readValue(json, GpsData.class);
                                latestData = data;
                                System.out.println("⭐ Datos actualizados: " + latestData);
                            } catch (Exception e) {
                                System.err.println("❌ Error al parsear JSON: " + e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❗ Error leyendo del puerto serial: " + e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            System.err.println("❗ Error iniciando el lector GPS: " + e.getMessage());
        }
    }

    //Devuelve los datos GPS más recientes obtenidos desde el puerto serial.
    public static GpsData getLatestData() {
        return latestData;
    }
}
