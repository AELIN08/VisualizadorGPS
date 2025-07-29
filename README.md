# 🌍 Visualizador GPS en Tiempo Real

Este proyecto es una aplicación web que permite visualizar coordenadas gps obtenida en tiempo real desde un módulo GPS **NEO-6M** conectado a un **Arduino UNO**. Los datos se procesan a través de un backend en **Java con Spring Boot**, que se comunica con el Arduino por medio del puerto serial.

---

## 🚀 Tecnologías utilizadas

### 🔧 Backend
- Java 17+
- Spring Boot
- jSerialComm (para comunicación serial)
- REST API

### 💻 Frontend
- HTML5
- CSS
- JavaScript
- Bootstrap 5 (opcional)

### ⚙️ Hardware
- Arduino UNO
- Módulo GPS NEO-6M
- Cable USB

---

## 📁 Estructura del proyecto
gps-visualizer/
│
├── src/
│ └── main/
│ ├── java/ # Código fuente Java (controladores, servicios)
│ │ └── com.aerodesign.gps_visualizer/
│ ├── resources/
│ │ └── application.properties # Configuración de Spring Boot
│ │ └── static/ # Archivos estáticos
  │ │ ├── css/
  │ │ ├── js/
  │ │ └── html
  │
│
├── arduino/
│ └── gps_reader.ino # Código Arduino para lectura del GPS
│
└── README.md # Este archivo

---

## ⚡ Funcionamiento

1. El Arduino UNO lee las coordenadas GPS (latitud y longitud) del módulo NEO-6M.
2. Los datos se envían vía serial al backend (Java).
3. El backend expone una API REST (`/api/gps`) que entrega las coordenadas más recientes.
4. El frontend (HTML + JS) consulta la API periódicamente y actualiza un mapa Leaflet con la nueva posición.

---

## 📌 Cómo ejecutar

### 1. Subir código a Arduino
Sube el archivo `gps_reader.ino` al Arduino UNO desde el IDE de Arduino.

### 2. Ejecutar el backend
Asegúrate de tener Java 17+ y ejecuta el proyecto Spring Boot. 
Verifica que se detecte correctamente el puerto serial donde está conectado el Arduino.

### 3. Abrir la interfaz
Accede en tu navegador a http://localhost:8080/ y podrás ver el mapa actualizado en tiempo real.

## 🔌 Requisitos
- Java 17+
- Maven o Gradle
- Arduino IDE
- Navegador web moderno (Chrome, Firefox, etc.)
- Arduino UNO + NEO-6M + cable USB

## 🧠 Créditos
Proyecto desarrollado como parte del un reto técnico de AeroDesign 2025
Desarrollado por: Evelin Mondragon

