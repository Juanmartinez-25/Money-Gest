document.addEventListener("DOMContentLoaded", () => {

    // 1. Atrapar los elementos del diseño (Mockup)
    const btnAgregar = document.querySelector(".btn-add");
    const inputCodigo = document.querySelector(".add-product input");
    const listaProductos = document.querySelector(".list-container");

    // Atrapar las casillas de los totales en la parte inferior derecha
    const inputsTotales = document.querySelectorAll(".totals-area input");
    const inputSubTotal = inputsTotales[0];
    const inputIva = inputsTotales[1];
    const inputTotal = inputsTotales[2]; // Esta es la casilla name="total" que viaja a Java

    // Variables para llevar la cuenta del dinero
    let subtotalAcumulado = 0;
    const porcentajeIva = 0.19; // IVA del 19% (Ejemplo Colombia)

    // 2. ¿Qué pasa cuando hacemos clic en "Agregar"?
    if(btnAgregar) {
        btnAgregar.addEventListener("click", () => {
            const codigo = inputCodigo.value;

            if (codigo.trim() === "") {
                alert("Por favor, escribe un código de producto válido.");
                return;
            }

            // SIMULACIÓN: Como aún no hemos conectado la tabla de productos de la base de datos,
            // simularemos que cada producto cuesta $50.000.
            const precioSimulado = 50000;
            const cantidad = 1; // Por ahora suma de a 1

            // A) Dibujar el producto en la caja blanca grande
            const nuevoItem = document.createElement("div");
            nuevoItem.classList.add("list-item");
            nuevoItem.innerHTML = `<span>Código: ${codigo}</span> <span>Cant: ${cantidad}</span> <span>$${precioSimulado}</span>`;

            // Lo pegamos en la lista visual
            listaProductos.appendChild(nuevoItem);

            // B) Cálculos matemáticos
            subtotalAcumulado += (precioSimulado * cantidad);
            const valorIva = subtotalAcumulado * porcentajeIva;
            const totalFinal = subtotalAcumulado + valorIva;

            // C) Escribir los resultados en las casillas azules
            // NOTA: Se mandan sin puntos para que Java y MySQL (Error 400 y 1364) no se molesten
            inputSubTotal.value = subtotalAcumulado;
            inputIva.value = valorIva;
            inputTotal.value = totalFinal;

            // Limpiar la casilla del código para el siguiente
            inputCodigo.value = "";
        });
    }

    // 3. Botón Limpiar (Reset)
    const btnLimpiar = document.querySelector("button[type='reset']");
    if(btnLimpiar) {
        btnLimpiar.addEventListener("click", () => {
            // Vaciar la caja blanca y reiniciar contadores
            listaProductos.innerHTML = "";
            subtotalAcumulado = 0;
            // Los inputs se vacían solos por ser type="reset"
        });
    }
});