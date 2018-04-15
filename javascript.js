$("#calculate").click(function () {
    GausEliminination.mundhahs.julian.fillEmptyInputsWithZero();
    GausEliminination.mundhahs.julian.calculate();
});
$("#clear_results").click(function () {
    $("#result").empty();
});

$(document).ready(() => GausEliminination.mundhahs.julian.setupListeners());
GausEliminination.mundhahs.julian.setupTable();
