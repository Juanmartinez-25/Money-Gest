document.addEventListener("DOMContentLoaded", function() {
    const contenedores = document.querySelectorAll('.input-contenedor');

    contenedores.forEach(div => {
        div.addEventListener('click', function() {
            // Al hacer clic en la caja, activamos el input de fecha
            const input = this.querySelector('input');
            if (input) {
                input.showPicker(); // Abre el calendario nativo
            }
        });
    });

    // Validación básica antes de enviar
    const form = document.querySelector('.form-busqueda');
    form.addEventListener('submit', function(e) {
        const desde = document.getElementsByName('fechaDesde')[0].value;
        const hasta = document.getElementsByName('fechaHasta')[0].value;

        if (new Date(desde) > new Date(hasta)) {
            e.preventDefault();
            alert("La fecha 'Desde' no puede ser mayor a la fecha 'Hasta'");
        }
    });
});