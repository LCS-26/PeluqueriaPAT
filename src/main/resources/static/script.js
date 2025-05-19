var id_peluquero = "1234";
// PELUQUERO – Ver horas a trabajar
function generarTablaPeluqueroPeluquero(idPeluquero) {
    if (!idPeluquero) {
        document.getElementById('contenedor-tabla').innerHTML = "";
        return;
    }

    const horas = ["9.30", "10.30", "11.30", "12.30", "13.30", "14.30", "15.30"];
    const dias = ["LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES"];

    let tabla = '<table border="1"><thead><tr><th>HORA</th>';
    dias.forEach(dia => {
        tabla += `<th>${dia}</th>`;
    });
    tabla += '</tr></thead><tbody>';

    horas.forEach(hora => {
        tabla += `<tr><td>${hora}</td>`;
        dias.forEach((dia) => {
            tabla += `<td><button class="boton_tabla" data-hora="${hora}" data-dia="${dia}">Añadir</button></td>`;
        });
        tabla += '</tr>';
    });

    tabla += '</tbody></table>';
    document.getElementById('contenedor-tabla').innerHTML = tabla;

    //activarSeleccion();
}

// CLIENTE VIP – Ver disponibilidad del peluquero
function generarTablaPeluqueroCliente(idPeluquero) {
    if (!idPeluquero) {
        document.getElementById('contenedor-tabla').innerHTML = "";
        return;
    }

    const horas = ["9.30", "10.30", "11.30", "12.30", "13.30", "14.30", "15.30"];
    const dias = ["LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES"];

    let tabla = '<table border="1"><thead><tr><th>HORA</th>';
    dias.forEach(dia => {
        tabla += `<th>${dia}</th>`;
    });
    tabla += '</tr></thead><tbody>';

    horas.forEach(hora => {
        tabla += `<tr><td>${hora}</td>`;
        dias.forEach((dia) => {
            tabla += `<td><button class="boton_tabla" data-hora="${hora}" data-dia="${dia}">Añadir</button></td>`;
        });
        tabla += '</tr>';
    });

    tabla += '</tbody></table>';
    document.getElementById('contenedor-tabla').innerHTML = tabla;

    activarSeleccion();
}

// CLIENTE NO VIP – Tabla general
function generarTablaTodos() {
    const contenedor = document.getElementById('contenedor-tabla-index');

    const dias = ['LUNES', 'MARTES', 'MIÉRCOLES', 'JUEVES', 'VIERNES'];
    const horas = ['9.30', '10.30', '11.30', '12.30', '13.30', '14.30', '15.30'];

    let tabla = '<table border="1"><thead><tr><th>HORA</th>';
    dias.forEach(dia => {
        tabla += `<th>${dia}</th>`;
    });
    tabla += '</tr></thead><tbody>';

    horas.forEach((hora) => {
        tabla += `<tr><td>${hora}</td>`;
        dias.forEach((dia) => {
            tabla += `<td><button class="boton_tabla" data-hora="${hora}" data-dia="${dia}">Añadir</button></td>`;
        });
        tabla += '</tr>';
    });

    tabla += '</tbody></table>';
    contenedor.innerHTML = tabla;

    activarSeleccion();
}

// Función común para selección exclusiva + control de submit
function activarSeleccion() {
    const botones = document.querySelectorAll('.boton_tabla');
    const botonSubmit = document.querySelector('form button[type="submit"]');
    let seleccionado = null;

    if (botonSubmit) {
        botonSubmit.disabled = true; // Desactiva el submit inicialmente
    }

    botones.forEach(boton => {
        boton.addEventListener('click', e => {
            e.preventDefault();

            // Desmarcar botón anterior
            if (seleccionado) {
                seleccionado.classList.remove('selected');
            }

            // Marcar el nuevo botón
            boton.classList.add('selected');
            seleccionado = boton;

            // Habilitar el submit si existe
            if (botonSubmit) {
                botonSubmit.disabled = false;
            }
        });
    });
}

function editar_informacion_personal(){

}

function editar_cita(){
    
}

async function irAPagina(event, url, rolRequerido) {
  event.preventDefault(); // 👈 evita la navegación automática

  const res = await fetch('/api/users/me', { credentials: 'include' });
  if (!res.ok) {
    window.location.href = '/login.html';
    return;
  }

  const datos = await res.json();
  if (datos.role !== rolRequerido) {
    alert('⛔ No tienes permiso para acceder a esa página.');
    return;
  }

  window.location.href = url;
}