#include <TinyGPS++.h>
#include <SoftwareSerial.h>

SoftwareSerial gpsSerial(2, 3); // RX, TX
TinyGPSPlus gps;

void setup() {
  Serial.begin(9600);       // Comunicación con la PC
  gpsSerial.begin(9600);    // Comunicación con el módulo GPS
}

void loop() {
  while (gpsSerial.available() > 0) {
    gps.encode(gpsSerial.read());

    // Si hay una nueva ubicación válida
    if (gps.location.isUpdated()) {
      float latitud = gps.location.lat();
      float longitud = gps.location.lng();
      float altitud = gps.altitude.meters();        // Altitud en metros
      float velocidad = gps.speed.kmph();           // Velocidad en km/h

      int dia = gps.date.day();
      int mes = gps.date.month();
      int anio = gps.date.year();

      // Imprimir en formato JSON
      Serial.print("{");
      Serial.print("\"latitud\":"); Serial.print(latitud, 6); Serial.print(", ");
      Serial.print("\"longitud\":"); Serial.print(longitud, 6); Serial.print(", ");
      Serial.print("\"altitud\":"); Serial.print(altitud, 2); Serial.print(", ");
      Serial.print("\"velocidad\":"); Serial.print(velocidad, 2); Serial.print(", ");
      
      // Fecha con formato YYYY-MM-DD
      Serial.print("\"fecha\":\"");
      Serial.print(anio); Serial.print("-");
      if (mes < 10) Serial.print("0");
      Serial.print(mes); Serial.print("-");
      if (dia < 10) Serial.print("0");
      Serial.print(dia);
      Serial.println("\"}");

      // Asegurarse de enviar el salto de línea
      Serial.println();  // Esto asegura que cada mensaje termine con un salto de línea
    }
  }
}
