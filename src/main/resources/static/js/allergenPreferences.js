document.addEventListener("DOMContentLoaded", function () {
    const successMessage = document.querySelector(".alert-success");
    const errorMessage = document.querySelector(".alert-error");

    if (successMessage) {
        setTimeout(() => successMessage.style.display = "none", 5000);
    }

    if (errorMessage) {
        setTimeout(() => errorMessage.style.display = "none", 5000);
    }
});
