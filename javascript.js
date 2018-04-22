let deferredPrompt;

$("#calculate").click(function () {
    GausEliminination.mundhahs.julian.Werkzeug.fillEmptyInputsWithZero();
    GausEliminination.mundhahs.julian.Werkzeug.calculate();

    if(deferredPrompt !== undefined) {
        // The user has had a positive interaction with our app and Chrome
        // has tried to prompt previously, so let's show the prompt.
        deferredPrompt.prompt();

        // Follow what the user has done with the prompt.
        deferredPrompt.userChoice.then(function(choiceResult) {

            console.log(choiceResult.outcome);

            if(choiceResult.outcome === 'dismissed') {
                console.log('User cancelled home screen install');
            }
            else {
                console.log('User added to home screen');
            }

            // We no longer need the prompt.  Clear it up.
            deferredPrompt = null;
        });
    }
});
$("#clear_results").click(function () {
    $("#result").empty();
});
$("#clear_coefficients").click(function () {
    $("td[id] > input").each(function (index) {
        this.value = 0
    });
});
$("#versionTag").click(function () {
    navigator.serviceWorker.controller.postMessage({command: "clearCache"});
});

$(document).ready(() => GausEliminination.mundhahs.julian.Werkzeug.setupListeners());
GausEliminination.mundhahs.julian.Werkzeug.setupTable();

if ('serviceWorker' in navigator) {
    window.addEventListener('load', function() {
        navigator.serviceWorker.addEventListener("message", function (event) {
            switch (event.data.command) {
                case "getVersion": $("#versionTag").text(event.data.value);
                    break;
            }
        });

        navigator.serviceWorker.register('./serviceWorker.js').then(function(registration) {
            // Registration was successful
            console.log('ServiceWorker registration successful with scope: ', registration.scope);
            navigator.serviceWorker.controller.postMessage({command: "getVersion"});
        }, function(err) {
            // registration failed :(
            alert(`ServiceWorker registration failed: ${err}`);
        });
    });
}

window.addEventListener('beforeinstallprompt', function(e) {
    console.log('beforeinstallprompt Event fired');
    e.preventDefault();

    // Stash the event so it can be triggered later.
    deferredPrompt = e;

    return false;
});
