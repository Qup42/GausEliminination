$("#calculate").click(function () {
    GausEliminination.mundhahs.julian.fillEmptyInputsWithZero();
    GausEliminination.mundhahs.julian.calculate();
});

$(document).ready(() => GausEliminination.mundhahs.julian.setupListeners());
GausEliminination.mundhahs.julian.setupTable();
