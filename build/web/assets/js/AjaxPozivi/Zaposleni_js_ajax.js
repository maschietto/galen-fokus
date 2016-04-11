
$(document).ready(function () {




});

$(document).ready(function () {
    $('#regsub').click(function () {

        var ime = $('#imezaposlenog').val();
        var prezime = $('#prezimezaposlenog').val();
        var email = $('#emailzaposlenog').val();
        var jmbg = $('#jmbgzaposlenog').val();
        var telefon = $('#telzaposlenog').val();
        var sifra = $('#pwdzaposlenog').val();
        var status = 'registracija';
        $.post('zaposleni', {
            ime: ime,
            prezime: prezime,
            email: email,
            jmbg: jmbg,
            telefon: telefon,
            sifra: sifra,
            status: status
        }, function (responseText) {
            var k = responseText;
            slideup('toggle');
            fadeIn('logovanje');
            document.getElementById("imeZaposlenogLabel").innerHTML = k;

            setTimeout(function () {
                fadeIn('imeZaposlenogLabel');
            }, 2000);
            setTimeout(function () {
                fadeOut('imeZaposlenogLabel');
            }, 10000);
        });
    });
});



function ZaposleniProvera(id, markok, marknot) {


    var podatak;
    var element = document.getElementById(id);


    if (id == "imezaposlenog" || id == "prezimezaposlenog" || id == "pwdzaposlenog") {
        if (element.value != "") {
            element.style.backgroundColor = "#ffff99";
        } else {
            element.style.backgroundColor = "white";
        }
    } else {


        podatak = element.value;
        $.get('noviservlet', {
            userName: podatak,
            status: id
        }, function (responseText) {

            if (responseText == '') {

                $(markok).css("display", "block");
                $(marknot).css("display", "none");
                element.style.backgroundColor = "#ffff99";
            }
            else {
                $(marknot).css("display", "block");
                $(markok).css("display", "none");
                element.value = responseText;
                element.style.backgroundColor = "red";
                setTimeout(function () {
                    element.style.backgroundColor = "white";
                    element.value = "";
                    $(marknot).css("display", "none");
                }, 3000);
            }
        });
    }
}


function SubmitujKorisnika(ime, password) {

    var ime1 = document.getElementById(ime).value;
    var password1 = document.getElementById(password).value;
    var status = "login";

    $.post('noviservlet', {
        email: ime1,
        password: password1,
        status: status
    }, function (responseText) {



        if (responseText != "admin_true" && responseText != "user_true") {
            var el = document.getElementById("imeZaposlenogLabel");
            el.innerHTML = responseText;
            el.style.opacity = 1;

        }

        if (responseText == 'admin_true') {

             fadeOutLogin('login_korisnika');
            

            aktiviranjeDeaktiviranjeDiv("login_korisnika", "deaktiviraj");
          
            var eko = document.getElementById('administracija_korisnika');
            eko.style.display = "block";
            setTimeout(function () {
                fadeInAdmin('administratorski_deop');
            }, 500);

        } else {

        }

    });


}









