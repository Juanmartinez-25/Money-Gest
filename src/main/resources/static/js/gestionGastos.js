document.addEventListener('DOMContentLoaded', () => {
    cargarSaldoDisponible();
});

function abrirModalGasto(subcategoria) {
    document.getElementById('tituloModalGasto').innerText = "Nuevo Registro de " + subcategoria;
    document.getElementById('subcategoriaGasto').value = subcategoria;
    document.getElementById('modalNuevoGasto').style.display = 'flex';
}

function cerrarModalGasto() {
    document.getElementById('modalNuevoGasto').style.display = 'none';
    document.getElementById('formNuevoGasto').reset();
}

function enviarFormularioGasto() {
    const descripcion = document.getElementById('descGasto').value;
    const monto = document.getElementById('montoGasto').value;
    const subcat = document.getElementById('subcategoriaGasto').value;

    if (!descripcion || !monto) {
        alert("Por favor, llena la descripción y el monto.");
        return;
    }

    const fechaFormateada = new Date().toISOString().slice(0, 19);

    const nuevoGasto = {
        descripcion: descripcion,
        monto: parseFloat(monto),
        unidadNegocio: document.getElementById('unidadGasto').value,
        subcategoria: subcat,
        fecha: fechaFormateada,
        estado: "Aprobado",
        idCategoria: 1,
        idUsuario: 1
    };

    guardarGasto(nuevoGasto);
}

async function guardarGasto(nuevoGasto) {
    try {
        const response = await fetch('/api/gastos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoGasto)
        });

        if(response.ok) {
            alert('¡Gasto registrado con éxito!');
            cerrarModalGasto();
            cargarSaldoDisponible();
        } else {
            alert('Hubo un problema al registrar el gasto en el servidor.');
        }
    } catch (error) {
        console.error("Error al guardar:", error);
        alert('No se pudo conectar con el servidor.');
    }
}

async function cargarSaldoDisponible() {
    try {
        const response = await fetch('/api/gastos/saldo');
        if (response.ok) {
            const data = await response.json();
            const formateador = new Intl.NumberFormat('es-CO', {
                style: 'currency',
                currency: 'COP',
                minimumFractionDigits: 2
            });
            document.getElementById('saldoDisponibleTexto').textContent = formateador.format(data.saldoDisponible);
        } else {
            document.getElementById('saldoDisponibleTexto').textContent = "Error";
        }
    } catch (error) {
        console.error("Error conectando con Spring Boot:", error);
        document.getElementById('saldoDisponibleTexto').textContent = "Sin conexión";
    }
}
