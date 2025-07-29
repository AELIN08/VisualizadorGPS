// Objeto para rastrear el estado de visibilidad de los distintos elementos de información
let estado = {
  altitud: false,   // Estado de visibilidad de la altitud
  velocidad: false, // Estado de visibilidad de la velocidad
  mapa: false       // Estado de visibilidad del mapa
};

/**
 * Muestra u oculta la información de altitud o velocidad según el tipo recibido.
 * @param {string} tipo - Puede ser 'altitud' o 'velocidad'.
 */
function toggleInfo(tipo) {
  if (tipo === 'altitud') {
    document.getElementById('altitudLi').classList.toggle('d-none');
    estado.altitud = !estado.altitud; // Actualiza el estado
  } else if (tipo === 'velocidad') {
    document.getElementById('velocidadLi').classList.toggle('d-none');
    estado.velocidad = !estado.velocidad; // Actualiza el estado
  }
}

/**
 * Alterna entre mostrar u ocultar el contenedor del mapa,
 * y ajusta la disposición del contenido principal en función de la visibilidad del mapa.
 */
function toggleMapa() {
  estado.mapa = !estado.mapa; // Cambia el estado del mapa

  const mapaBox = document.getElementById('mapaBox');     // Contenedor del mapa
  const infoBox = document.getElementById('infoBox');     // Contenedor de información
  const contenido = document.getElementById('contenido'); // Contenedor general

  // Mostrar u ocultar el mapa según el estado
  mapaBox.classList.toggle('d-none', !estado.mapa);

  if (estado.mapa) {
    // Muestra el mapa en diseño horizontal en pantallas grandes
    contenido.classList.remove('flex-column');
    contenido.classList.add('flex-lg-row');
    infoBox.classList.remove('centrada'); // Elimina centrado en el modo con mapa
  } else {
    // Reestablece diseño vertical cuando el mapa está oculto
    contenido.classList.remove('flex-lg-row');
    contenido.classList.add('flex-column');
    infoBox.classList.add('centrada'); // Centra el contenedor de información
  }
}

/**
 * Obtiene los datos GPS desde la API local y actualiza el DOM con la información.
 * Se ejecuta periódicamente para mantener los datos actualizados en tiempo real.
 */
async function actualizarDatos() {
  try {
    const res = await fetch('/gps'); // Solicitud a la API local
    if (!res.ok) {
      console.log('No se pudo obtener datos del GPS');
      return;
    }

    const data = await res.json(); // Conversión a objeto JSON
    const lat = parseFloat(data.latitud);
    const lon = parseFloat(data.longitud);

    // Actualiza el contenido del DOM con los datos recibidos o muestra '...' si no hay datos válidos
    document.getElementById('latitud').textContent = isNaN(lat) ? '...' : lat.toFixed(6);
    document.getElementById('longitud').textContent = isNaN(lon) ? '...' : lon.toFixed(6);
    document.getElementById('altitud').textContent = data.altitud || '...';
    document.getElementById('velocidad').textContent = data.velocidad || '...';
    document.getElementById('fecha').textContent = `Fecha: ${data.fecha || '...'}`;

  } catch (error) {
    // Muestra el error en consola en caso de fallo
    console.error('❌ Error al obtener datos:', error);
  }
}

// Ejecuta la función de actualización cada 2 segundos para mantener los datos en tiempo real
setInterval(actualizarDatos, 2000);

// Llamada inicial para mostrar datos inmediatamente al cargar
actualizarDatos();
