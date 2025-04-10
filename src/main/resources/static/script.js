//funciones de actualizar tablas
//cliente no VIP

//cliente VIP --> quiero ver la disponibilidad del peluquero
const verTablaUno = (tel_peluquero) => {
    console.log(tel_peluquero);
}

const verTablaTodos = () => {
    console.log("enseñando tabla de todos")
}
document.getElementById("cita").addEventListener("submit", function(event){
    event.preventDefault(); // Evitar el envío del formulario

    const clienteID = document.getElementById("clienteID").value;
    const peluqueroID = document.getElementById("peluqueroID").value;
    const fecha = document.getElementById("fecha").value;
    const hora = document.getElementById("hora").value;

    registrarCita(clienteID, peluqueroID, fecha, hora);
})

function registrarCita(clienteID, peluqueroID, fecha, hora) {
    const data = {
        clienteID: clienteID,
        peluqueroID: peluqueroID,
        fecha: fecha,
        hora: hora
    };

    fetch('/registrarCita', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Cita registrada:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}