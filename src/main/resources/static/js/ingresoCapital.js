document.addEventListener('DOMContentLoaded', () => {
    cargarHistorial();
    cargarTotal();

    // Poner la fecha de hoy por defecto
    document.getElementById('fechaIngreso').valueAsDate = new Date();
});

// Lógica para habilitar/deshabilitar Motivo Anulación
function verificarAnulacion() {
    const estado = document.getElementById('estadoIngreso').value;
    const selectMotivo = document.getElementById('motivoAnulacion');
    if (estado === 'Anulado') {
        selectMotivo.disabled = false;
        selectMotivo.required = true;
    } else {
        selectMotivo.disabled = true;
        selectMotivo.value = "";
        selectMotivo.required = false;
    }
}

function activarModoAnulacion() {
    document.getElementById('estadoIngreso').value = 'Anulado';
    verificarAnulacion();
    alert("Modo anulación activado. Seleccione el motivo.");
}

function limpiarFormulario() {
    document.getElementById('formIngreso').reset();
    document.getElementById('fechaIngreso').valueAsDate = new Date();
    verificarAnulacion(); // Vuelve a bloquear el motivo
}

// Enviar datos al Backend
async function registrarIngreso() {
    const monto = document.getElementById('valorIngreso').value;
    const fecha = document.getElementById('fechaIngreso').value;

    if (!monto || !fecha) {
        alert("El valor y la fecha son obligatorios.");
        return;
    }

    // Capturamos el socio correctamente incluso si está bloqueado
    const ocultoSocio = document.getElementById('socioOculto');
    const selectSocio = document.getElementById('socioIngreso');
    const idSocioReal = ocultoSocio ? ocultoSocio.value : selectSocio.value;

    const nuevoIngreso = {
        idSocio: parseInt(idSocioReal),
        monto: parseFloat(monto),
        metodo: document.getElementById('metodoIngreso').value,
        estado: document.getElementById('estadoIngreso').value,
        fecha: fecha + "T12:00:00", // Agregamos hora para cumplir con LocalDateTime
        idUsuario: 1 // Quemado por ahora
    };

    try {
        const response = await fetch('/api/ingresos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoIngreso)
        });

        if (response.ok) {
            alert('Capital registrado correctamente');
            limpiarFormulario();
            cargarHistorial();
            cargarTotal();
        } else {
            alert('Error al guardar en la base de datos.');
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Cargar la tabla
async function cargarHistorial() {
    try {
        const response = await fetch('/api/ingresos');
        const ingresos = await response.json();

        const tbody = document.getElementById('cuerpoTablaHistorico');
        tbody.innerHTML = '';

        ingresos.forEach(ing => {
            // Formatear fecha
            const fechaCorta = new Date(ing.fecha).toLocaleDateString('es-CO');
            // Formatear dinero
            const montoFormateado = new Intl.NumberFormat('es-CO', {style: 'currency', currency: 'COP'}).format(ing.monto);

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${fechaCorta}</td>
                <td>Socio ${ing.idSocio}</td>
                <td>${montoFormateado}</td>
                <td>${ing.metodo}</td>
                <td>${ing.estado}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error("Error cargando historial:", error);
    }
}

// Cargar el Total
async function cargarTotal() {
    try {
        const response = await fetch('/api/ingresos/total');
        const data = await response.json();

        const montoFormateado = new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0
        }).format(data.total);

        document.getElementById('textoTotalActual').textContent = montoFormateado;
    } catch (error) {
        console.error("Error cargando total:", error);
    }
}

// ==========================================
// LÓGICA DEL MODAL DE BODEGA / INVENTARIO
// ==========================================

function abrirModalInventario() {
    const modal = document.getElementById('modalInventario');
    if (modal) {
        modal.style.display = 'flex';
    }
}

function cerrarModalInventario() {
    const modal = document.getElementById('modalInventario');
    if (modal) {
        modal.style.display = 'none';
        document.getElementById('formInventario').reset();
    }
}

async function guardarInventario() {
    const p = {
        codigo: document.getElementById('invCodigo').value,
        nombre: document.getElementById('invNombre').value,
        precioCompra: parseFloat(document.getElementById('invCompra').value) || 0,
        precioVenta: parseFloat(document.getElementById('invVenta').value) || 0,
        stock: parseInt(document.getElementById('invStock').value),
        categoria: "General"
    };

    if (!p.codigo || !p.nombre || isNaN(p.stock)) {
        alert("Por favor llena los campos de código, nombre y stock.");
        return;
    }

    try {
        const response = await fetch('/api/productos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(p)
        });

        if (response.ok) {
            alert(`¡Se agregaron ${p.stock} unidades de '${p.nombre}' a la bodega!`);
            cerrarModalInventario();
        } else {
            alert("Error al conectar con el servidor.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Hubo un fallo al registrar la mercancía.");
    }
}