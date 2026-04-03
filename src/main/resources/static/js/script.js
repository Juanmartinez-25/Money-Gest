/**
 * Script completo para MoneyGest
 * Maneja la visibilidad de la contraseña y permite el envío del formulario.
 */
document.addEventListener("DOMContentLoaded", () => {
    // Selección de elementos
    const passwordInput = document.getElementById("password");
    const togglePassword = document.querySelector(".togglePassword");
    const loginForm = document.querySelector("form");

    // 1. LÓGICA DEL OJO (MOSTRAR/OCULTAR CONTRASEÑA)
    if (togglePassword && passwordInput) {
        togglePassword.addEventListener("click", function () {
            // Verificamos si actualmente es tipo password
            const isPassword = passwordInput.getAttribute("type") === "password";

            // Cambiamos el tipo de input
            passwordInput.setAttribute("type", isPassword ? "text" : "password");

            // Intercambiamos las clases del icono de Font Awesome
            if (isPassword) {
                this.classList.remove("fa-eye-slash");
                this.classList.add("fa-eye");
            } else {
                this.classList.remove("fa-eye");
                this.classList.add("fa-eye-slash");
            }
        });
    }

    // 2. LÓGICA DEL BOTÓN SIGUIENTE
    // No usamos e.preventDefault() para que Spring Security
    // pueda recibir los datos y redirigir al /menuPrincipal
    if (loginForm) {
        console.log("Formulario de MoneyGest detectado y listo para enviar.");
    }
});