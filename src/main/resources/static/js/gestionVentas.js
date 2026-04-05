let carrito = [];


const productosPrecios = {
    '8085215': 150000,
    '808315': 200000,
    '808416': 180000
};

function agregarAlCarrito() {
    const codigo = document.getElementById('codigoInput').value.trim();
    const cantidad = parseInt(document.getElementById('cantidadInput').value);

    if (!codigo || isNaN(cantidad) || cantidad < 1) return;

    const precio = productosPrecios[codigo] || 100000; // Precio por defecto si no existe

    const itemExistente = carrito.find(p => p.codigo === codigo);
    if (itemExistente) {
        itemExistente.cantidad += cantidad;
    } else {
        carrito.push({ codigo, cantidad, precio });
    }

    document.getElementById('codigoInput').value = "";
    document.getElementById('cantidadInput').value = "1";
    renderizar();
}

function actualizarCantidad(index, nuevaCant) {
    const cant = parseInt(nuevaCant);
    if (cant > 0) {
        carrito[index].cantidad = cant;
        renderizar();
    }
}

function renderizar() {
    const tabla = document.getElementById('tablaCarrito');
    tabla.innerHTML = "";
    let subtotal = 0;

    carrito.forEach((item, index) => {
        subtotal += (item.precio * item.cantidad);
        tabla.innerHTML += `
            <tr>
                <td style="width: 40%">${item.codigo}</td>
                <td style="width: 60%; text-align: right;">
                    cant <input type="number" value="${item.cantidad}"
                         class="qty-edit" onchange="actualizarCantidad(${index}, this.value)">
                </td>
            </tr>
        `;
    });

    const iva = subtotal * 0.19;
    const total = subtotal + iva;

    // Actualizar inputs del formulario que van a Java
    document.getElementById('inputSubtotal').value = subtotal;
    document.getElementById('inputIva').value = iva;
    document.getElementById('inputTotal').value = total;
}

function limpiarPantalla() {
    carrito = [];
    renderizar();
    document.getElementById('fechaInput').value = new Date().toISOString().slice(0, 10);
}

// Inicializar fecha al cargar
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('fechaInput').value = new Date().toISOString().slice(0, 10);
});