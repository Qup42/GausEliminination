$("#calculate").click(function (event) {
    GausEliminination.mundhahs.julian.calculate();
});

$(document).ready(() => GausEliminination.mundhahs.julian.setupListeners());
GausEliminination.mundhahs.julian.setupTable();
