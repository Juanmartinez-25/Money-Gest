document.addEventListener('DOMContentLoaded', function() {
    // Buscamos el ícono del ojo y el campo de contraseña del login
    const togglePassword = document.querySelector('.togglePassword');
    const passwordInput = document.getElementById('password');

    // Si ambos existen en la pantalla, les agregamos la función del clic
    if (togglePassword && passwordInput) {
        togglePassword.addEventListener('click', function () {

            // 1. Cambiamos el input entre 'password' (oculto) y 'text' (visible)
            const tipo = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', tipo);

            // 2. Cambiamos el ícono de FontAwesome (ponemos y quitamos la rayita)
            this.classList.toggle('fa-eye');
            this.classList.toggle('fa-eye-slash');
        });
    }
});