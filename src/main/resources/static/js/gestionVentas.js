// Modal NUEVA VENTA
const modalNueva = document.getElementById('modalNuevaVenta');
const btnNueva = document.getElementById('btnAbrirNueva');
const cerrarNueva = document.getElementById('cerrarNueva');

btnNueva.onclick = () => {
    document.getElementById('formNuevaVenta').reset();
    document.getElementById('fechaNueva').value = new Date().toISOString().slice(0,10);
    modalNueva.style.display = 'block';
};

cerrarNueva.onclick = () => modalNueva.style.display = 'none';

window.onclick = (event) => {
    if (event.target === modalNueva) modalNueva.style.display = 'none';
};
