$("#calculate").click(function () {
    GausEliminination.mundhahs.julian.fillEmptyInputsWithZero();
    GausEliminination.mundhahs.julian.calculate();
});
$("#clear_results").click(function () {
    $("#result").empty();
});

$(document).ready(() => GausEliminination.mundhahs.julian.setupListeners());
GausEliminination.mundhahs.julian.setupTable();

if ('serviceWorker' in navigator) {
    window.addEventListener('load', function() {
        navigator.serviceWorker.register('/serviceWorker.js').then(function(registration) {
            // Registration was successful
            console.log('ServiceWorker registration successful with scope: ', registration.scope);
        }, function(err) {
            // registration failed :(
            alert('ServiceWorker registration failed: ', err);
        });
    });
}
