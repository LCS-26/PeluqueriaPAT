async function inicializarVistaPeluquero() {
  try {
    const res = await fetch("/api/users/me", {
      credentials: "include"
    });

    if (!res.ok) {
      console.error("Error al obtener peluquero actual");
      return;
    }

    const usuario = await res.json();

    // Solo si es peluquero
    if (usuario.role === "PELUQUERO") {
      generarTablaPeluqueroPeluquero(usuario.id);
    } else {
      console.warn("Este usuario no es peluquero");
    }
  } catch (error) {
    console.error("Error inicializando vista del peluquero:", error);
  }
}

// PELUQUERO – Ver horas a trabajar
async function generarTablaPeluqueroPeluquero(idPeluquero) {
  if (!idPeluquero) {
    document.getElementById('contenedor-tabla').innerHTML = "";
    return;
  }

  // Obtenemos las citas del peluquero
  let citas = [];
  try {
    const response = await fetch(`/api/citas/peluquero/${idPeluquero}`, {
      credentials: "include"
    });

    if (response.ok) {
      citas = await response.json();
    } else {
      console.warn("No se pudieron obtener las citas del peluquero");
    }
  } catch (error) {
    console.error("Error al cargar citas del peluquero:", error);
  }

  // Definimos las horas y los días (sin tildes para evitar conflictos)
  const horas = ["9:30", "10:30", "11:30", "12:30", "13:30", "14:30", "15:30"];
  const dias = ["LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES"];

  // Creamos la tabla
  let tabla = '<table border="1"><thead><tr><th>HORA</th>';
  dias.forEach(dia => {
    tabla += `<th>${dia}</th>`;
  });
  tabla += '</tr></thead><tbody>';

  horas.forEach(hora => {
    tabla += `<tr><td>${hora}</td>`;
    dias.forEach(dia => {
      const ocupada = citas.some(cita =>
        cita.dia === dia && cita.hora === hora
      );

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
    const dias = ["LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES"];

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

    const dias = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES'];
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

function editar_informacion_personal() {
  // TODO Mostrar formulario con los datos actuales
  // Enviar PUT a `/api/users/me`
}

async function editar_cita(idCita, nuevaHora, nuevoDia) {
  try {
    const res = await fetch(`/api/citas/${idCita}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify({ hora: nuevaHora, dia: nuevoDia })
    });

    if (res.ok) {
      alert("Cita actualizada");
    } else {
      alert("Error al actualizar cita");
    }
  } catch (error) {
    console.error("Error actualizando la cita:", error);
  }
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

async function reservarCita(event) {
  event.preventDefault();

  const botonSeleccionado = document.querySelector(".boton_tabla.selected");
  const selectPeluquero = document.getElementById("select-peluquero");

  if (!botonSeleccionado || !selectPeluquero.value) {
    alert("Selecciona un peluquero y una hora");
    return;
  }

  const dia = botonSeleccionado.dataset.dia;
  const hora = botonSeleccionado.dataset.hora;
  const peluqueroId = selectPeluquero.value;

  // Obtener el cliente desde la sesión
  const perfilRes = await fetch("/api/users/me", { credentials: "include" });
  if (!perfilRes.ok) {
    alert("No se ha podido obtener la sesión del cliente");
    return;
  }

  const cliente = await perfilRes.json();

  const cita = {
    dia: dia,
    hora: hora,
    peluqueroId: parseInt(peluqueroId),
    clienteId: cliente.id
  };

  try {
    const response = await fetch("/api/citas/me", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(cita)
    });

    if (response.ok) {
      alert("✅ Cita reservada con éxito");
      generarTablaPeluqueroCliente(peluqueroId); // refresca la tabla
    } else {
      alert("❌ Error al reservar la cita");
    }
  } catch (error) {
    console.error("Error enviando la cita:", error);
    alert("❌ Error técnico al reservar la cita");
  }
}


async function cargarCitasDeCliente(idCliente) {
  try {
    const res = await fetch(`/api/citas/cliente/${idCliente}`, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
      credentials: "include"
    });

    if (!res.ok) {
      console.warn("No se pudieron cargar las citas del cliente");
      return;
    }

    const citas = await res.json();
    const lista = document.getElementById("info-citas");
    lista.innerHTML = ""; // Limpia antes

    citas.forEach(cita => {
      const li = document.createElement("li");
      li.textContent = `${cita.dia}: ${cita.hora}`;
      lista.appendChild(li);
    });
  } catch (error) {
    console.error("Error cargando citas del cliente:", error);
  }

}

async function actualizar_cita(){
    try{

    } catch (error){
        console.log("Error actualizando la cita", error);
    }

}

async function actualizar_informacion_personal(){
    await cargarPeluqueros();
    await cargarClientes();
    try{


    } catch (error){
        console.error("Error actualizando informacion", error);
    }
}

