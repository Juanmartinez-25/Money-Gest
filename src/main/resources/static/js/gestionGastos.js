document.addEventListener('DOMContentLoaded', () => {
    // Apenas cargue la página, traemos el saldo desde la base de datos
    cargarSaldoDisponible();
});

// GET: Consultar el saldo disponible
async function cargarSaldoDisponible() {
    try {
        const response = await fetch('http://localhost:8080/api/gastos/saldo');

        if (response.ok) {
            const data = await response.json();

            // Formatear el número como moneda (ej: $ 60.000.000,00)
            const formateador = new Intl.NumberFormat('es-CO', {
                style: 'currency',
                currency: 'COP',
                minimumFractionDigits: 2
            });

            document.getElementById('saldoDisponibleTexto').textContent = formateador.format(data.saldoDisponible);
        } else {
            document.getElementById('saldoDisponibleTexto').textContent = "Error al cargar";
        }
    } catch (error) {
        console.error("Error conectando con Spring Boot:", error);
        document.getElementById('saldoDisponibleTexto').textContent = "Sin conexión";
    }
}

// Lógica para cuando hagas clic en el botón "[Registro de Gastos]"
function abrirModalGasto() {
    console.log("Enviando un gasto de prueba a la base de datos...");

    // Obtenemos la fecha actual y la formateamos para que Spring Boot (LocalDateTime) la entienda
    const fechaFormateada = new Date().toISOString().slice(0, 19);

    // Objeto JSON que hace "match" exacto con tu Gasto.java
    const nuevoGasto = {
        descripcion: "Compra de resina para producción",
        monto: 250000.00,
        fecha: fechaFormateada,
        estado: "Aprobado",
        subcategoria: "Insumos Operativos",
        unidadNegocio: "Principal",
        idCategoria: 1,
        idUsuario: 1
    };

    guardarGasto(nuevoGasto);
}

// POST: Enviar el gasto a la base de datos
async function guardarGasto(nuevoGasto) {
    try {
        const response = await fetch('http://localhost:8080/api/gastos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoGasto)
        });

        if(response.ok) {
            alert('¡Gasto registrado con éxito en la base de datos! 🚀');
            // Como ya gastamos dinero, el saldo debe bajar. Lo recargamos automáticamente:
            cargarSaldoDisponible();
        } else {
            alert('Ups... Hubo un problema al registrar el gasto. Revisa la consola de Java.');
        }
    } catch (error) {
        console.error("Error al guardar:", error);
        alert('No se pudo conectar con el servidor para guardar el gasto.');
    }
}