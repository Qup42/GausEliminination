$("#calculate").click(function () {
    GausEliminination.mundhahs.julian.Werkzeug.fillEmptyInputsWithZero();
    GausEliminination.mundhahs.julian.Werkzeug.calculate();
});
$("#clear_results").click(function () {
    $("#result").empty();
});

$(document).ready(() => GausEliminination.mundhahs.julian.Werkzeug.setupListeners());
GausEliminination.mundhahs.julian.Werkzeug.setupTable();

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

var deferredPrompt;

window.addEventListener('beforeinstallprompt', function(e) {
    console.log('beforeinstallprompt Event fired');
    e.preventDefault();

    // Stash the event so it can be triggered later.
    deferredPrompt = e;

    return false;
});

$("#calculate").click(function() {
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
