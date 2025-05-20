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
async function generarTablaPeluqueroCliente(idPeluquero) {
    if (!idPeluquero) {
        document.getElementById('contenedor-tabla').innerHTML = "";
        return;
    }

    // Paso 1: obtener citas del backend
    const response = await fetch(`/api/citas/peluquero/${idPeluquero}`);
    const citas = response.ok ? await response.json() : [];

    // Paso 2: crear tabla
    const horas = ["9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30"];
    const dias = ["LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES"];

    let tabla = '<table border="1"><thead><tr><th>HORA</th>';
    dias.forEach(dia => {
        tabla += `<th>${dia}</th>`;
    });
    tabla += '</tr></thead><tbody>';

    horas.forEach(hora => {
        tabla += `<tr><td>${hora}</td>`;
        dias.forEach((dia) => {
            const ocupada = citas.some(cita => cita.dia === dia && cita.hora === hora);
            if (ocupada) {
                tabla += `<td><button class="boton_tabla ocupado" disabled>${hora}</button></td>`;
            } else {
                tabla += `<td><button class="boton_tabla" data-hora="${hora}" data-dia="${dia}">Añadir</button></td>`;
            }
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
  event.preventDefault();

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

async function logout() {
  const res = await fetch('/api/users/me/session', {
    method: 'DELETE',
    credentials: 'include'
  });

  if (res.ok || res.status === 204) {
    window.location.href = '/index.html'; // 👈 redirige a la página de inicio
  } else {
    alert('Error cerrando sesión');
  }
}

async function cargarInfoClienteDesdeSession() {
  try {
    const res = await fetch("/api/users/me", {
      credentials: "include"
    });

    if (!res.ok) {
      console.error("No se pudo obtener el usuario actual");
      return;
    }

    const usuario = await res.json();

    // ✅ Mostrar info directamente desde la sesión
    document.getElementById("info-nombre").textContent = usuario.name || "-";

    document.getElementById("info-email").textContent = usuario.email || "-";

    // 👇 Si quieres todavía cargar sus citas, puedes usar su id
    cargarCitasDeCliente(usuario.id);

  } catch (error) {
    console.error("Error cargando info del usuario:", error);
  }
}

async function cargarPeluqueros() {
  try {
    const response = await fetch("/api/citas/peluqueros", {
      method: "GET",
      headers: { "Content-Type": "application/json" },
      credentials: "include"
    });

    if (!response.ok) {
      console.error("Error al obtener peluqueros");
      return;
    }

    const peluqueros = await response.json();
    const select = document.getElementById("select-peluquero");

    // Limpiar select excepto la primera opción
    select.innerHTML = `<option value="" disabled selected>Elige un peluquer@</option>`;

    peluqueros.forEach(peluquero => {
      const option = document.createElement("option");
      option.value = peluquero.id;
      option.textContent = peluquero.name;
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error cargando peluqueros:", error);
  }
}

async function inicializarPagina() {
  await cargarInfoClienteDesdeSession(); // ✅ rellena nombre/email desde sesión
  await cargarPeluqueros();              // ✅ carga select
}
