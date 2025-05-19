function inicializar() {
  if (location.search === '?registrado') {
    mostrarAviso('✓ ¡Registrado! Prueba a entrar', 'success');
  }
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

const login = async (event) => {
  event.preventDefault();
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

const res = await fetch("/api/users/me/session", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ email, password }),
  credentials: "include"
});

  if (res.status === 201 or res.status === 302) {
    const perfil = await fetch("/api/users/me", {
    credentials: "include"
    });
    const datos = await perfil.json();
    switch (datos.role) {
        case "CLIENTE":
            window.location.href = "/cliente.html";
            break;
        case "ENCARGADO":
            window.location.href = "/encargado.html";
            break;
        case "PELUQUERO":
            window.location.href = "/peluquero.html";
        break;
    }
  } else {
    alert("Login incorrecto");
  }
};