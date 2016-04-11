$(document).ready(function () {
    // Ovako zakachish evente i ne treba ti onaj loop
    // Eventi su registrovani za sve elemente sa tom klasom
    $(document).on('click', '.dugme_tabela', function (event) {

        pregledKorisnika(event.target);
    });

   
    $(document).on('click' ,'.dodavanjeMestoId' , function (event) {

        cuvanjeIdMesta(event.target);
    });


    $(document).on('click', '.dugme_tabela_proizvod', function (event) {

        pregledIEditProizvoda(event.target);
    });

    $(document).on('click', '.dugme_tabela_proizvod_brisi', function (event) {

        obrisiProizvod(event.target);
    });

    $(document).on('click', '.dugme_tabela_ponuda', function (event) {

        pregledPonude(event.target);
    });

    $(document).on('click', '.dugme_tabela_proizvod_ponuda', function (event) {

        addStavkaPonude(event.target);
    });



     $(document).on('click', '.dugme_tabela_ponuda_brisi', function (event) {

        obrisiPonudu(event.target);
     });
     
     
 
  
     
  
    $('#tabela_stavki_ponude').on('focus',".datepicker_recurring_start", function(){
    $(this).datepicker();
});


  
});





function osveziTabelu(brojStrane, vrednost, ime, prezime, email) {

    var tabela = 'tabela_korisnika';
    var tr;
    var table = document.querySelector('#tabela_admin').innerHTML = '';


    $.post('noviservlet', {status: tabela, brStrane: brojStrane, vrednost: vrednost, ime: ime, prezime: prezime, email: email}, function (data) {



        for (var i = 0; i < data.length; i++) {
            tr = $('<tr>');

            tr.append('<td class="dugme_tabela">' + data[i].sifra_radnika + '</td>');

            tr.append('<td>' + data[i].ime + '</td>');
            tr.append('<td>' + data[i].prezime + '</td>');
            tr.append('<td>' + data[i].jmbg + '</td>');

            $('#tabela_admin').append(tr);
        }

        var pip = $('#tabela_admin tr').length;

        if (pip == 0) {


            var poruka = document.getElementById('poruka_za_tabelu');
            //tabelaAdmin.style.display = "none";
            poruka.style.display = "block"
            setTimeout(function () {
                poruka.style.display = "none";
            }, 2000);
            setTimeout(function () {
                prikaziTabeluKorisnika();
            }, 2000);
        }
    });

}

function pregledKorisnika(elKorisnik) {

    var id_korisnika = elKorisnik.innerHTML;
    var status = "pogled_na_korisnika";

    $.post('noviservlet', {
        status: status,
        id: id_korisnika
    },
    function (data) {

  
        localStorage.setItem("zaposleni_uKodu", data.sifra_radnika);

        var email = document.getElementById("email_labela");
        var ime = document.getElementById("ime_labela");
        var prezime = document.getElementById("prezime_labela");
        var naziv_funkcije = document.getElementById("funkcija_labela");
        var status = document.getElementById('status_labela');
        var dugmence = document.getElementById('dugmenceAktivacija');
        var statusOk = document.getElementById('statusok');
        var statusNotOK = document.getElementById('statusnotok');

        email.value = data.email;
        ime.value = data.ime;
        prezime.value = data.prezime;
        naziv_funkcije.value = data.nazivFunkcije;
        status.value = data.status;
        email.readOnly = true;
        ime.readOnly = true;
        prezime.readOnly = true;
        naziv_funkcije.readOnly = true;
        status.readOnly = true;

//        if (status.value == 'NEAKTIVAN') {
//            statusNotOK.style.display = "block";
//            dugmence.value = 'Aktiviraj';
//        }
//        else {
//            statusOk.style.display = "block";
//            dugmence.value = 'Deaktiviraj';
//        }

        var elem = document.getElementById('tabela_i_sve_tako');
        elem.style.transition = 'opacity 0.3s linear 0s';
        elem.style.opacity = 0;
        setTimeout(function () {
            elem.style.display = "none";
        }, 1000);

        var elem3 = document.getElementById('korisnici_glob');
        elem3.style.transition = 'opacity 0.3s linear 0s';
        elem3.style.opacity = 0;
        setTimeout(function () {
            elem3.style.display = "none";
        }, 1000);

          var elem2 = document.getElementById('korisnik_zasebno');
        elem2.style.transition = 'opacity 0.3s linear 0s';
        elem2.style.opacity = 1;
        setTimeout(function () {
        elem2.style.display = "block";
        }, 500);
        

    });
}


function promeniStatusKorisnika() {

    var dugmence = document.getElementById('dugmenceAktivacija');
    var status_labela = document.getElementById('status_labela');
    var statusOk = document.getElementById('statusok');
    var statusNotOK = document.getElementById('statusnotok');





    if (status_labela.value == 'NEAKTIVAN' && dugmence.value == 'Aktiviraj') {
        dugmence.value = 'Deaktiviraj';
        status_labela.value = 'AKTIVAN';
        statusOk.style.display = "block";
        statusNotOK.style.display = "none";
        $('#status_labela').toggleClass(".locikonica .unlocikonica");


    } else {
        dugmence.value = 'Aktiviraj';
        status_labela.value = 'NEAKTIVAN';
        statusOk.style.display = "none";
        statusNotOK.style.display = "block";
        $('#status_labela').toggleClass(".locikonica .unlocikonica");

    }


}

function sacuvajKorisnika() {

var zaposleniId = parseInt(localStorage.getItem("zaposleni_uKodu"));
    
    var brojStrane = parseInt(localStorage.getItem("brojstrane"));
    var poruka = document.getElementById('poruka_korisniku').value;
    var status_labela = document.getElementById('status_labela').value;
    var status = 'poruka_status';

    $.post('noviservlet', {zaposleniId: zaposleniId ,poruka: poruka, status: status, statuslabela: status_labela}, function (data) {

        osveziTabelu(brojStrane);
    
        povratakNaListuKorisnika();
    });
}

function povratakNaListuKorisnika() {
    
    var elem = document.getElementById('korisnik_zasebno');
        elem.style.transition = 'opacity 0.3s linear 0s';
        elem.style.opacity = 0;
        setTimeout(function () {
            elem.style.display = "none";
        }, 200);

        var elem3 = document.getElementById('korisnici_glob');
        elem3.style.transition = 'opacity 0.3s linear 0s';
        elem3.style.opacity = 1;
        setTimeout(function () {
            elem3.style.display = "block";
        }, 500);

        var elem2 = document.getElementById('tabela_i_sve_tako');
        elem2.style.transition = 'opacity 0.3s linear 0s';
        elem2.style.opacity = 1;
        setTimeout(function () {
        elem2.style.display = "block";
        }, 500);

}



function prikaziTabeluKorisnika() {

    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaKupci = document.getElementById('tabela_kupci');
    var korisniciLog = document.getElementById('podaci_o_logovanom');
    var proizvodiSve = document.getElementById('svi_proizvodi');


    var AdminTabela = document.getElementById('tabela_ikontrole');



    if (proizvodiSve.style.display != "none") {
        sakrijProizvodKomplet();
    }
    if (korisniciLog.style.top == "0px") {
        slideOutTOP('podaci_o_logovanom');
        setTimeout(function () {
            korisniciLog.style.display = "none";
        }, 500);
    }
    if (adminProsPretraga.style.right == "-430px") {
        slideOutTabela('prosiri_pretragu_div');
        setTimeout(function () {
            adminProsPretraga.style.display = "none";
        }, 500);
    }
    if (tabelaKupci.style.bottom == "0px") {
        skloniformuZaUnosKupca('tabela_kupci');
    }


    var vrednost = document.getElementById('demoime').value;

    var broj_strane = 0;
    localStorage.setItem("brojstrane", broj_strane);
    osveziTabelu(broj_strane, vrednost);

    if (AdminTabela.style.display == 'none') {


        setTimeout(function () {
            AdminTabela.style.display = "block";
        }, 300);
    }
    setTimeout(function () {
        slideInDiv1('tabela_ikontrole');
    }, 400);


//	$('#korisnici_glob').show();
}

function prikaziSledecu(ulaziParametar) {


    ugasiPoruku();


    var brojStrane = localStorage.getItem("brojstrane");
    var brojStrane2 = parseInt(brojStrane);
 

    if (ulaziParametar == 'sledeca') {
        brojStrane2 = brojStrane2 + 1;

    } else {
        brojStrane2 = brojStrane2 - 1;
        if (brojStrane2 < 0) {
            brojStrane2 = 0;
        }
    }


    localStorage.setItem("brojstrane", brojStrane2);

    osveziTabelu(brojStrane2);

}

function autoComplete() {

    var broj_strane = 0;
    localStorage.setItem("brojstrane", broj_strane);
    var nekiParametar = document.getElementById('demoime').value;
    osveziTabelu(broj_strane, nekiParametar);

}

function dodatnaPretraga() {

    var broj_strane = 0;
    var nekiParametar = null;
    var paramIme = document.getElementById('ime_pretraga').value;
    console.log(paramIme);
    var paramPrezime = document.getElementById('prezime_pretraga').value;
    console.log(paramPrezime);
    var paramEmail = document.getElementById('email_pretraga').value;
    console.log(paramEmail);

    osveziTabelu(broj_strane, nekiParametar, paramIme, paramPrezime, paramEmail);
    document.getElementById('ime_pretraga').value = "";
    document.getElementById('prezime_pretraga').value = "";
    document.getElementById('email_pretraga').value = "";

    var podLog = document.getElementById('podaci_o_logovanom');
    podLog.style.display = "none";
    slideOutTabela('prosiri_pretragu_div');
    slideInDiv1('tabela_ikontrole');
}

function ugasiPoruku() {

    var poruka = document.getElementById('poruka_za_tabelu_zaposleni');

    if (poruka.style.display == "block") {

        poruka.style.display = "none";
    }

}


function prikaziPodatkeOSebi() {


    var podLog = document.getElementById('podaci_o_logovanom');
    podLog.style.display = "block";
    var adminLista = document.getElementById('tabela_ikontrole');
    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaKupci = document.getElementById('tabela_kupci');
    var tabelaProizvoda = document.getElementById('svi_proizvodi');


    if (adminLista.style.left == "0px") {
        slideOutDiv2('tabela_ikontrole');
    }
    if (adminProsPretraga.style.right == "0px") {
        slideOutTabela('prosiri_pretragu_div');
    }
    if (tabelaKupci.style.bottom == "0px") {
        slideOutBottom('tabela_kupci');
        setTimeout(function () {
            tabelaKupci.style.display = "none";
        }, 500);
    }

    if (tabelaProizvoda.style.display != "none") {
        $('#svi_proizvodi').toggle('clip');
        setTimeout(function () {
            tabelaProizvoda.style.display = "none";
        }, 300);
    }

    setTimeout(function () {
        slideInTOP('podaci_o_logovanom');
    }, 200);

    var ime = document.getElementById("ime_korisnik");
    var prezime = document.getElementById("prezime_korisnik");
    var jmbg = document.getElementById("jmbg_korisnik");
    var email = document.getElementById("email_korisnik");
    var telefon = document.getElementById('telefon_korisnik');
    var sifra = document.getElementById('sifra_korisnik');
    var status = 'podaci_kor';

    $.post('zaposleni', {status: status}, function (data) {

        ime.value = data.ime;
        prezime.value = data.prezime;
        jmbg.value = data.jmbg;
        email.value = data.email;
        telefon.value = data.telefon;
        sifra.value = data.sifra;

    });

    ime.readOnly = true;
    prezime.readOnly = true;
    jmbg.readOnly = true;
    email.readOnly = true;
    telefon.readOnly = true;
    sifra.readOnly = true;

}

function menjanjePoljaZaEdit(idTextPolje) {

    var polje = document.getElementById(idTextPolje);
    polje.readOnly = false;
}

function blokiranjePoljaZaEdit(idTextPolje) {

    var polje = document.getElementById(idTextPolje);
    polje.readOnly = true;
}
function ugasiPodatkeOKorinsiku() {

    var podaci = document.getElementById('podaci_o_logovanom');

    if (podaci.style.display == "block") {

        podaci.style.display = "none";
    }

}


function sacuvajUlogovanogKorisnika_p() {


    var ime = $('#ime_korisnik').val();
    var prezime = $('#prezime_korisnik').val();
    var jmbg = $('#jmbg_korisnik').val();
    var email = $('#email_korisnik').val();
    var telefon = $('#telefon_korisnik').val();
    var sifra = $('#sifra_korisnik').val();
    var status = 'podaci_kor';



    $.post('zaposleni', {
        status: status,
        ime: ime,
        prezime: prezime,
        jmbg: jmbg,
        email: email,
        telefon: telefon,
        sifra: sifra



    }, function (podatak) {


        slideOutTOP('podaci_o_logovanom');

    });
}



function formaZaUnosKupca() {

    var kupac = document.getElementById('tabela_kupci');
    var podLog = document.getElementById('podaci_o_logovanom');
    var adminLista = document.getElementById('tabela_ikontrole');
    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaProizvoda = document.getElementById('svi_proizvodi');


    if (tabelaProizvoda.style.display != "none") {
        sakrijProizvodKomplet();
    }
    if (podLog.style.top == "0px") {
        slideOutTOP('podaci_o_logovanom');
        setTimeout(function () {
            podLog.style.display = "none";
        }, 500);
    }
    if (adminProsPretraga.style.right == "-430px") {
        slideOutTabela('prosiri_pretragu_div');
        setTimeout(function () {
            adminProsPretraga.style.display = "none";
        }, 500);
    }

    if (adminLista.style.left == "0px") {
        slideOutDiv2('tabela_ikontrole');
        setTimeout(function () {
            adminLista.style.display = "none";
        }, 400);
    }

    if (kupac.style.display == "none") {

        setTimeout(function () {
            kupac.style.display = "block";
        }, 100);
    }

    setTimeout(function () {
        slideInBottom('tabela_kupci');
    }, 300);
    osveziTabeluKupaca();
    if ($("#kupac_glob").css('display') == 'none') {

        setTimeout(function () {
            $("#kupac_glob").toggle("clip");
        }, 550);
    }



}

function sacuvajKupca() {

    var status = "sacuvaj_kupca";
    var imeKupca = document.getElementById('unosi_ime_kupca');
    var pibKupca = document.getElementById('unosi_pib_kupca');
    var adresaKupca = document.getElementById('unosi_adresu_kupca');
    var gradKupca = document.getElementById('unosi_grad_kupca');


    $.post('noviservlet', {
        status: status,
        ime: imeKupca.value,
        pib: pibKupca.value,
        adresa: adresaKupca.value,
        grad: gradKupca.value

    }, function (podatak) {

        $("#unos_kupca_uk").toggle("clip");
        setTimeout(function () {
            $('#unos_kupca_uk').style("display", "none");
        }, 200);

        imeKupca.value = "";
        pibKupca.value = "";
        adresaKupca.value = "";
        gradKupca.value = "";


    });

}

function osveziTabeluKupaca(nazivKupca) {

    var status = 'tabela_kupaca';
    var tr;
    var table = document.querySelector('#tabela_kupac').innerHTML = '';
    var brisi = "obrisi";

    $.post('noviservlet', {status: status, naziv: nazivKupca}, function (data) {

        for (var i = 0; i < data.length; i++) {

            console.log(data[i]);


            tr = $('<tr>');

            tr.append('<td class="dugme_tabela_kupac">' + data[i].kupacId + '</td>');
            tr.append('<td>' + data[i].imeKupca + '</td>');
            tr.append('<td>' + data[i].pib + '</td>');
            tr.append('<td>' + data[i].adresa + '</td>');
            tr.append('<td>' + data[i].grad + '</td>');
            tr.append('<td class="dugme_tabela_proizvod_brisi" id="' + data[i].sifraProizvoda + brisi + '">' + "OBRISI" + '</td>');

            $('#tabela_kupac').append(tr);

        }


    });

}


function skloniformuZaUnosKupca() {

    var kupac = document.getElementById('tabela_kupci');


    $("#unos_kupca_uk").toggle("clip");
    setTimeout(function () {
        slideOutBottom('tabela_kupci');
    }, 100);
    setTimeout(function () {
        kupac.style.display = "none";
    }, 300);

}





function prikaziFormeZaProizvode(idDolazeceForme, idDrugDiv) {


    var korisniciLog = document.getElementById('podaci_o_logovanom');
    var adminLista = document.getElementById('tabela_ikontrole');
    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaKupci = document.getElementById('tabela_kupci');

    if (korisniciLog.style.top == "0px") {
        slideOutTOP('podaci_o_logovanom');
        setTimeout(function () {
            korisniciLog.style.display = "none";
        }, 500);
    }
    if (adminLista.style.left == "0px") {
        slideOutDiv2('tabela_ikontrole');
    }
    setTimeout(function () {
        adminLista.style.display = "none";
    }, 200);
    if (adminProsPretraga.style.right == "-430px") {
        slideOutTabela('prosiri_pretragu_div');
        setTimeout(function () {
            adminProsPretraga.style.display = "none";
        }, 500);
    }
    if (tabelaKupci.style.bottom == "0px") {
        slideOutBottom('tabela_kupci');
        setTimeout(function () {
            tabelaKupci.style.display = "none";
        }, 500);
    }

    setTimeout(function () {
        $(idDolazeceForme).toggle("clip");
    }, 150);

    if ($(idDrugDiv).css('display') == 'none') {

        setTimeout(function () {
            $(idDrugDiv).toggle("clip");
        }, 150);

    }

    osveziTabeluProizvoda();

    var dugme = document.getElementById("proizvod_dugmici");
    dugme.disabled = true;
    dugme.style.opacity = 0.5;






}

function sakrijFormeZaProizvode(idDolazeceForme, idDrugDiv) {


    $(idDrugDiv).toggle("clip");

    setTimeout(function () {
        $(idDolazeceForme).toggle("clip");
    }, 300);
    setTimeout(function () {
        $(idDolazeceForme).css("display", "none");
    }, 400);

    var dugme = document.getElementById("proizvod_dugmici");
    dugme.disabled = false;
    dugme.style.opacity = 1;

}

function sakrijProizvodKomplet() {

    $('#svi_proizvodi').toggle("clip");

    if (document.getElementById('unos_proizvoda_uk').style.display != "none") {
        setTimeout(function () {
            $('#unos_proizvoda_uk').style("display", "none");
        }, 150);
    }
    if (document.getElementById('proizvod_zasebno').style.display != "none") {
        setTimeout(function () {
            $('#proizvod_zasebno').style("display", "none");
        }, 150);
    }
    if (document.getElementById('proizvodi_glob').style.display != "none") {
        setTimeout(function () {
            $('#proizvodi_glob').style("display", "none");
        }, 150);
    }
    setTimeout(function () {
        $('#svi_proizvodi').style("display", "none");
    }, 300);



    var dugme = document.getElementById("proizvod_dugmici");
    dugme.disabled = false;
    dugme.style.opacity = 1;


}



function osveziTabeluProizvoda(nazivProizvoda) {

    var status = 'tabela_proizvoda';
    var tr;
    var table = document.querySelector('#tabela_proizvodi').innerHTML = '';
    var brisi = "obrisi";
    var sifraProizvoda = "editujSifraProizvoda";
    
   
    $.post('noviservlet', {status: status, naziv: nazivProizvoda}, function (data) {

        for (var i = 0; i < data.length; i++) {

           
            tr = $('<tr>');

            tr.append('<td class="dugme_tabela_proizvod" id= "idProizvoda" >' + data[i].sifraProizvoda + '</td>');
            tr.append('<td>' + data[i].nazivProizvoda + '</td>');
            tr.append('<td class="tdfixing dugme_tabela_proizvod_akt_cena"><input type="text"  onchange="promenaAktuelneCene(this);" class = "form-control zaSiveDugmice" id="' + data[i].sifraProizvoda + sifraProizvoda + '"  value ="' + data[i].aktuelnaCena + '"></td>');
            tr.append('<td>' + data[i].nazivJM + '</td>');
            tr.append('<td class="dugme_tabela_proizvod_brisi" id="' + data[i].sifraProizvoda + brisi + '">' + "OBRISI" + '</td>');

            $('#tabela_proizvodi').append(tr);
            
         

        }


    });

}

function pregledIEditProizvoda(elKorisnik) {

    $('#proizvodi_glob').toggle("clip");
    setTimeout(function () {
        document.getElementById('proizvodi_glob').style.display = "none";
    }, 100);

    var id_proizvoda = elKorisnik.innerHTML;
    var status = "pregled_i_edit_proizvoda";

    $.post('noviservlet', {
        status: status,
        id: id_proizvoda
    },
    function (data) {

        //  localStorage.setItem("zaposleni_uKodu", data);
        var data1 = data[0];
        var data2 = data[1];


        var nazivProizvoda = document.getElementById("proizvod_naziv");
        var iznos = document.getElementById("proizvod_cena");
        var nazivProizvodjaca = document.getElementById("proizvodjac_naziv");
        var ulica = document.getElementById("proizvodjac_adresa");
        var grad = document.getElementById('proizvodjac_grad');
        var broj = document.getElementById('proizvodjac_broj');
        var drzava = document.getElementById('proizvodjac_drzava');
        var jedinicaMere = document.getElementById('proizvod_jedinica_mere');
        var tezina = document.getElementById('proizvod_tezina');

        nazivProizvoda.value = data2.nazivProizvoda;
        iznos.value = data2.aktuelnaCena;
        nazivProizvodjaca.value = data2.nazivProizvodjaca;
        ulica.value = data2.adresaProizvodjaca;
        grad.value = data2.gradProizvodjaca;
        broj.value = data2.brojProizvodjaca;
        drzava.value = data2.drzavaProizvodjaca;

        var option = '';

        var comboBox = document.querySelector('#proizvod_jedinica_mere').innerHTML = '';
        for (var i = 0; i < data1.length; i++) {
            option += '<option value="' + data1[i].nazivJM + '">' + data1[i].nazivJM + '</option>';
        }
        $('#proizvod_jedinica_mere').append(option);


        tezina.value = data2.vrednost;

        nazivProizvoda.readOnly = true;
        iznos.readOnly = true;
        nazivProizvodjaca.readOnly = true;
        ulica.readOnly = true;
        grad.readOnly = true;
        broj.readOnly = true;
        drzava.readOnly = true;
        jedinicaMere.readOnly = true;
        tezina.readOnly = true;


        $('#proizvod_zasebno').toggle("clip");

    });
}

function updateProizvoda() {


//        alert('radi dugme');
    var status = "update_proizvod";

    var nazivProizvoda = document.getElementById("proizvod_naziv");
    var iznos = document.getElementById("proizvod_cena");
    var nazivProizvodjaca = document.getElementById("proizvodjac_naziv");
    var ulica = document.getElementById("proizvodjac_adresa");
    var grad = document.getElementById('proizvodjac_grad');
    var broj = document.getElementById('proizvodjac_broj');
    var drzava = document.getElementById('proizvodjac_drzava');
    var jedinicaMere = document.getElementById('proizvod_jedinica_mere');
    var tezina = document.getElementById('proizvod_tezina');


    $.post('noviservlet', {
        status: status,
        nazivProizvoda: nazivProizvoda.value,
        aktuelnaCena: iznos.value,
        nazivProizvodjaca: nazivProizvodjaca.value,
        adresaProizvodjaca: ulica.value,
        gradProizvodjaca: grad.value,
        brojProizvodjaca: broj.value,
        drzavaProizvodjaca: drzava.value,
        nazivJM: jedinicaMere.value,
        tezina: tezina.value

    },
    function (data) {

//         alert(data);

        osveziTabeluProizvoda();

        $('#proizvod_zasebno').toggle("clip");
        $('#proizvodi_glob').toggle("clip");

    });
}

function backToPreviousPAge(prethodnaStrana, sledecaStrana) {

    $(prethodnaStrana).toggle("clip");
    setTimeout(function () {
        $(prethodnaStrana).style("display", "none");
    }, 200);
    setTimeout(function () {
        $(sledecaStrana).style("display", "none");
    }, 300);

    $(sledecaStrana).toggle("clip");

}



function obrisiProizvod(elementName) {

    var idElementa = elementName.id;
    var status = "obrisi_proizvod";
    $.post('noviservlet', {status: status, id: idElementa}, function (data) {
        osveziTabeluProizvoda();
    });
}



function UnosProizvoda() {

    $('#proizvodi_glob').toggle("clip");

    setTimeout(function () {
        document.getElementById('proizvodi_glob').style.display = "none";
    }, 100);

    var status = "unos_Proizvoda";

    $.post('noviservlet', {
        status: status,
    },
            function (data) {

                var option = '';

                var comboBox = document.querySelector('#proizvod_jedinica_mere').innerHTML = '';

                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].nazivJM + '">' + data[i].nazivJM + '</option>';
                }
                $('#jedinica_mere_unos').append(option);

                $('#unos_proizvoda_uk').toggle("clip");

            });
}

function unosProizvodaProcedura() {


//        alert('radi dugme');
    var status = "unos_proizvoda_procedura";

    var nazivProizvoda = document.getElementById("naziv_proizvoda_unos");
    var iznos = document.getElementById("cena_proizvoda_unos");
    var nazivProizvodjaca = document.getElementById("naziv_proizvodjaca_unos");
    var ulica = document.getElementById("adresa_proizvodjaca_unos");
    var grad = document.getElementById('grad_proizvodjaca_unos');
    var broj = document.getElementById('broj_proizvodjaca_unos');
    var drzava = document.getElementById('drzava_proizvodjaca_unos');
    var jedinicaMere = document.getElementById('jedinica_mere_unos');
    var tezina = document.getElementById('tezina_unos');


    $.post('noviservlet', {
        status: status,
        nazivProizvoda: nazivProizvoda.value,
        aktuelnaCena: iznos.value,
        nazivProizvodjaca: nazivProizvodjaca.value,
        adresaProizvodjaca: ulica.value,
        gradProizvodjaca: grad.value,
        brojProizvodjaca: broj.value,
        drzavaProizvodjaca: drzava.value,
        nazivJM: jedinicaMere.value,
        tezina: tezina.value

    },
    function (data) {


        nazivProizvoda.value = "";
        iznos.value = "";
        nazivProizvodjaca.value = "";
        ulica.value = "";
        grad.value = "";
        broj.value = "";
        drzava.value = "";
        tezina.value = "";
    });
}

function vratiSeNaListuProizvoda() {

    $('#unos_proizvoda_uk').toggle("clip");
    setTimeout(function () {
        $('#unos_proizvoda_uk').style("display", "none")
    }, 200);
    osveziTabeluProizvoda();

    setTimeout(function () {
        $('#proizvodi_glob').toggle("clip")
    }, 400);

}

function autoCompleteProizvod() {

    var nekiParametar = document.getElementById('demoproizvod').value;
    osveziTabeluProizvoda(nekiParametar);

}

function UnosKupca() {

    $('#kupac_glob').toggle("clip");

    setTimeout(function () {
        $('#kupac_glob').style("display", "none");
    }, 150);

    setTimeout(function () {
        $('#unos_kupca_uk').toggle("clip");
    }, 400);
    document.getElementById('panoid').innerHTML = "UNOS KUPCA";

}

function vratiSeNaListuKupaca() {

    $('#unos_kupca_uk').toggle("clip");

    setTimeout(function () {
        $('#unos_kupca_uk').style("display", "none");
    }, 150);

    setTimeout(function () {
        $('#kupac_glob').toggle("clip");
    }, 400);
    document.getElementById('panoid').innerHTML = "KUPCI";

}


function prikaziFormePonude() {

    var korisniciLog = document.getElementById('podaci_o_logovanom');
    var adminLista = document.getElementById('tabela_ikontrole');
    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaKupci = document.getElementById('tabela_kupci');


    if ($('#svi_proizvodi').css('display') != 'none') {
        sakrijProizvodKomplet();
    }
    if (korisniciLog.style.top == "0px") {
        slideOutTOP('podaci_o_logovanom');
        setTimeout(function () {
            korisniciLog.style.display = "none";
        }, 500);
    }
    if (adminLista.style.left == "0px") {
        slideOutDiv2('tabela_ikontrole');
    }
    setTimeout(function () {
        adminLista.style.display = "none";
    }, 200);
    if (adminProsPretraga.style.right == "-430px") {
        slideOutTabela('prosiri_pretragu_div');
        setTimeout(function () {
            adminProsPretraga.style.display = "none";
        }, 500);
    }
    if (tabelaKupci.style.bottom == "0px") {
        slideOutBottom('tabela_kupci');
        setTimeout(function () {
            tabelaKupci.style.display = "none";
        }, 500);
    }

    if ($('#ponuda_glob').css('display') == 'none') {
        $('#ponuda_glob').css('display', 'block');
    }

    osveziTabeluPonuda();

    setTimeout(function () {
        $('#sva_ponuda').toggle("clip");
    }, 200);

    var dugme = document.getElementById("ponuda_dugmici");
    dugme.disabled = true;
    dugme.style.opacity = 0.5


}

function sakrijPonuduKomplet() {

    $('#sva_ponuda').toggle("clip");

    if (document.getElementById('ponuda_deo1').style.display != "none") {
        setTimeout(function () {
            $('#ponuda_deo1').css("display", "none");
        }, 150);
    }
    if (document.getElementById('ponuda_glob').style.display != "none") {
        setTimeout(function () {
            $('#ponuda_glob').css("display", "none");
        }, 150);
    }
    // if(document.getElementById('').style.display != "none") {setTimeout(function (){$('#proizvodi_glob').style("display","none");},150);}
    setTimeout(function () {
        $('#sva_ponuda').css("display", "none");
    }, 300);

    var dugme = document.getElementById("ponuda_dugmici");
    dugme.disabled = false;
    dugme.style.opacity = 1;
}


function popuniComboBoxKupcima() {

    var status = "kupci_comboBox";

    $.post('noviservlet', {
        status: status,
    },
            function (data) {

                var option = '';
                var comboBox = document.querySelector('#kupici_za_CB').innerHTML = '';

                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].imeKupca + '">' + data[i].imeKupca + '</option>';
                }
                $('#kupici_za_CB').append(option);
            });

}


function osveziTabeluPonuda() {

    var tabela = 'tabela_svih_ponuda';
    var tr;
    var table = document.querySelector('#tabela_svih_ponuda').innerHTML = '';
    var brisi = "obrisi";
    var editUkupnaSuma = "edituj";

    $.post('noviservlet', {status: tabela}, function (data) {





//        console.log(data);
        for (var i = 0; i < data.length; i++) {

            tr = $('<tr>');

            tr.append('<td class="dugme_tabela_ponuda">' + data[i].sifraPonude + '</td>');
            tr.append('<td>' + data[i].imeKupca + '</td>');
            tr.append('<td>' + data[i].datum + '</td>');
            if(data[i].ukupnaSuma == null){data[i].ukupnaSuma = 0;}
            tr.append('<td>' + data[i].ukupnaSuma + '</td>');
            tr.append('<td class="dugme_tabela_ponuda_brisi" id="' + data[i].sifraPonude + brisi + '">' + "OBRISI" + '</td>');


            $('#tabela_svih_ponuda').append(tr);
        }

        var pip = $('#tabela_svih_ponuda tr').length;

        if (pip == 0) {

            var poruka = document.getElementById('poruka_za_ponude');
            //tabelaAdmin.style.display = "none";
            poruka.style.display = "block"
            setTimeout(function () {
                poruka.style.display = "none";
            }, 2000);

        }
    });

}

function osveziStavkePonude(idPonude) {

    var status = "osvezi_tabelu_stavki_ponude";
    var table = document.querySelector('#tabela_stavki_ponude').innerHTML = '';
    var brisi = "stavkaponude";

    $.post('noviservlet', {status: status, idPonude: idPonude}, function (data) {



        for (var i = 0; i < data.length; i++) {

            tr = $('<tr>');

            tr.append('<td>' + data[i].sifraPonude + '</td>');
            tr.append('<td>' + data[i].imeKupca + '</td>');
            tr.append('<td>' + data[i].datum + '</td>');
            tr.append('<td>' + data[i].ukupnaSuma + '</td>');
            tr.append('<td class="dugme_stavka_ponuda_brisi" id="' + data[i].sifraPonude + brisi + '">' + "OBRISI" + '</td>');


            $('#tabela_stavki_ponude').append(tr);
        }

    });
}


function pregledPonude(idElement) {

    var id = idElement.innerHTML;
  //  var status = "pregledPonude";

    var poljeKupca = document.getElementById('kupac_za_ponudux');
    var poljeDatum = document.getElementById('datum_ponudex');
    var poljeUkupnaSuma = document.getElementById('ukupna_suma_ponude');

   $('#ponuda_glob').toggle("clip");

        setTimeout(function () {
            $('#ponuda_glob').style("display", "none")
        }, 100);
        setTimeout(function () {
            $('#ponuda_zasebno').toggle("clip");
        }, 200);



        poljeKupca.value = "";
        poljeDatum.value = "";
        poljeUkupnaSuma.value = "";

        osveziTabeluStavkiPonuda(id);

   

}


function UnosPonude() {

    $('#ponuda_glob').toggle("clip");
    setTimeout(function () {
        $('#ponuda_deo1').toggle("clip");
    }, 80);

    popuniComboBoxKupcima();
    osveziTabeluProizvodauPonudi();
    setTimeout(function () {
        $('#ponuda_glob').style("display", "none")
    }, 1000);

}

function osveziTabeluProizvodauPonudi(nekoIme) {

    var status = 'tabela_proizvoda';
    var tr;
    var table = document.querySelector('#tabela_proizvodiuponudi').innerHTML = '';

    $.post('noviservlet', {status: status, naziv: nekoIme}, function (data) {

        for (var i = 0; i < data.length; i++) {

            //console.log(data[i].sifraProizvoda);


            tr = $('<tr>');

            tr.append('<td class="dugme_tabela_proizvod_ponuda" id="' + data[i].sifraProizvoda + "sifra_ponude" + '">' + data[i].nazivProizvoda + '</td>');


            $('#tabela_proizvodiuponudi').append(tr);

        }


    });

}

function autoCompleteProizvodPonuda() {

    var nekiParametar = document.getElementById('proizvod_na_ponudi').value;
    osveziTabeluProizvodauPonudi(nekiParametar);

}


function addStavkaPonude(element) {

    var ElementNaziv = element.innerHTML;
    var ElementId = element.id;

    var tr;

    var brojRedova = $('#tabela_dodatih_proizvoda tr').length;


    tr = $('<tr>');

    tr.append('<td class="novaDugmeKlasa">' + ElementNaziv + '</td>');
    tr.append('<td><input class ="form-control zaSiveDugmice" type="number"></input></td>')
    tr.append('<td style="display: none;"><input type="hidden" value="' + ElementId + '"></input></td>')


    $('#tabela_dodatih_proizvoda').append(tr);
}



function uhvatiSveVrednostiTrIzTabele() {

    var TableData = new Array();

    $('#tabela_dodatih_proizvoda tr').each(function (row, tr) {

        TableData[row] = {
            "NazivProizvoda": $(tr).find('td:eq(0)').text()
            , "Kolicina": $(tr).find('td:eq(1) input').val()
            , "Id": $(tr).find('td:eq(2) input').val()

        }
    });
    return TableData;

}


function pogodiServletSaJsonObjektima() {


    var status = "popuni_stavke_ponude";
    var datum = document.getElementById('datepicker');
    var nazivKupca = document.getElementById('kupici_za_CB');
    var poruka = document.getElementById('poruka_za_unos_ponude');

    var mestoId = localStorage.getItem("kupacId");
    var dodatnaPretraga = document.getElementById('dodatniParametriPretrageKupca');

//alert(mestoId);
    
    var Table;

    Table = uhvatiSveVrednostiTrIzTabele();

    var jsonText = JSON.stringify(Table);


    // console.log(jsonText);
    $.post('ponuda', {status: status, tabela: jsonText, datum: datum.value, nazivKupca: nazivKupca.value, mestoId : mestoId}, function (data) {

    //alert(data);
        datum.value = "";
        //nazivKupca.value = "";
        var table = document.querySelector('#tabela_dodatih_proizvoda').innerHTML = '';
        poruka.innerHTML = data;
        poruka.style.display = "block";
        
        dodatnaPretraga.style.display = "none";
        
        
        setTimeout(function(){poruka.style.display = "none";},2000);
        
    });

}



function obrisiPonudu(elementName) {

    var idElementa = elementName.id;
    var status = "obrisi_ponudu";
    $.post('noviservlet', {status: status, id: idElementa}, function (data) {
        osveziTabeluPonuda();
    });
}

function osveziTabeluStavkiPonuda(ulaz){
    
    
    console.log(ulaz);
    
    localStorage.setItem("idPonude", ulaz);
    
    var status = "stavke_ponude_tabela";
    var brisi = "obrisistavku";
    var editujDatum = "izmenaDatuma";
    var editujKolicinu = "izmenaKolicine";
    var editujNazivProizvoda = "izmenaNazivaProizvoda";
    var editujAktuelnuCenu = "izmenaCeneProizvoda";
    
    var proizvodID = "sifraProizvoda";
    
    
    var datumPOlje = document.getElementById("datum_ponudex");
    var kupacPOlje = document.getElementById("kupac_za_ponudux");
    var sumaPOlje = document.getElementById("ukupna_suma_ponude");
    var datumNaStavci = document.getElementById("novi_datum_ponude");
    var cenaProizvodaNaStavci = document.getElementById("cena_proizvoda_tabelaSponude");


   var tabela = document.querySelector('#tabela_stavki_ponude').innerHTML = '';
     $.post('ponuda', {status: status, id:ulaz}, function (data) {

      var data1 = data[0];
      var data2 = data[1];
      var data3 = data[2];
      var data4 = data[3];
      
    
        
    for (var i = 0; i < data4.length; i++) {
        
        console.log(data4[i]);

            var tr = $('<tr>');
            tr.append('<td class="dodajStavkuPonude edituj tdfixing"> + stavka </td>');   
            tr.append('<td class="tdfixing"><input type="text" class = "form-control zaSiveDugmice" readonly="true" id="' + data4[i].rbsPonude + editujNazivProizvoda + '" value ="' + data4[i].nazivProizvoda + '"></td>');
            tr.append('<td class="tdfixing"><input type="text" class = "form-control zaSiveDugmice" readonly="true" id="' + data4[i].rbsPonude + editujAktuelnuCenu + '" value ="' + data4[i].aktuelnaCenaProizvoda + '"></td>');
            tr.append('<td class="tdfixing"><input type="number" class = "form-control zaSiveDugmice" onchange="updateKolicinaNaStavci(this);"  id="' + data4[i].rbsPonude + editujKolicinu + '" value ="' + data4[i].kolicina + '"></td>');

            tr.append('<td onclick="obrisiStavkuPonude(this);" class="dugme_ponuda_stavka_brisi edituj tdfixing" id="' + data4[i].rbsPonude + brisi + data4[i].sifraPonude + '">' + "OBRISI" + '</td>');


            $('#tabela_stavki_ponude').append(tr);
        
         }
         
         var brojRedova = $('#tabela_stavki_ponude tr').length; 
         var porukaZaTabelu =  document.getElementById('poruka_za_listu_stavki_ponude'); 
         if(brojRedova  == 0){
             
             porukaZaTabelu.style.display = "block";
         }
         
         datumPOlje.value = data1.datum;
         kupacPOlje.value = data1.imeKupca;
         sumaPOlje.value = data1.ukupnaSuma;
      
         
         

         var option = '';
         var comboBox = document.querySelector('#kupci_za_CBsaPOnude').innerHTML = '';
         for (var i = 0; i < data2.length; i++) {
                    option += '<option id="" value="' + data2[i].imeKupca + '">' + data2[i].imeKupca + '</option>';
                   
                }
                $('#kupci_za_CBsaPOnude').append(option);
          
         var option2 = '';
         var comboBox2 = document.querySelector('#izbor_proizvoda_na_st_ponude').innerHTML = '';
        
         for (var j = 0; j < data3.length; j++) {
          
                    option2 += '<option id="' + data3[j].sifraProizvoda + proizvodID + '" value="' + data3[j].nazivProizvoda + '">' + data3[j].nazivProizvoda + '</option>';
                    cenaProizvodaNaStavci.value = data3[j].aktuelnaCena; 
                }
                $('#izbor_proizvoda_na_st_ponude').append(option2);
                
                     
         datumNaStavci.value = data1.datum;
         cenaProizvodaNaStavci.value = ""; 
         
    
                
     });
}


function vratiSeNaListuPonuda(){

var elementPonudaUnos = document.getElementById('ponuda_deo1');
var elementPOnudaUpdate = document.getElementById('ponuda_zasebno');
var elementPonudaTabela = document.getElementById('ponuda_glob');
if(elementPonudaUnos.style.display != "none"){
     $('#ponuda_deo1').toggle("clip");
     setTimeout(function(){elementPonudaUnos.style.display = "none";},300);}
if(elementPOnudaUpdate.style.display != "none"){
     $('#ponuda_zasebno').toggle("clip");
     setTimeout(function(){elementPOnudaUpdate.style.display = "none";},300);}
        
   if(elementPonudaTabela.style.display == "none"){setTimeout(function(){ $('#ponuda_glob').toggle("clip");},500);}

    osveziTabeluPonuda();
    }

function popuniPodatkeZaKupca(){
    

    var selektovaniElement = document.getElementById('kupici_za_CB');
    var elementAdresa = document.getElementById('adresaKupcaSaPonude');
    var elementGrad = document.getElementById('GradKupcaSaPonude');
    var divSaParam = document.getElementById('dodatniParametriPretrageKupca');
    var status = "dodatniPodaciOKupcu";
    var idkupac = "sifraadresa";
    var idkupac2 = "sifragrad";
    $.post('noviservlet', {status: status, vrednost : selektovaniElement.value }, function (data) {
                
          var option = '';
          var option2 = '';
        
        
        var comboBox = document.querySelector('#adresaKupcaSaPonude').innerHTML = '';
        var comboBox2 = document.querySelector('#GradKupcaSaPonude').innerHTML = '';
         for (var i = 0; i < data.length; i++) {
                    option += '<option id="'+ data[i].kupacId + idkupac + '" value = "' + data[i].adresa + '">' + data[i].adresa + '</option>';
                    option2 += '<option id="'+ data[i].kupacId + idkupac2 + '" value = "' + data[i].grad + '">' + data[i].grad + '</option>';
                }
                $('#adresaKupcaSaPonude').append(option);
                $('#GradKupcaSaPonude').append(option2);
                
         divSaParam.style.display = "block";
     
        
    });
   
}


function obrisiStavkuPonude(element){

    var idStavke = element.id;
    var status  = "obrisiStavkuPOnudeSaPonude";
    var ukupnaSumaPonude = document.getElementById("ukupna_suma_ponude"); 
    var porukaZaTabelu =  document.getElementById('poruka_za_listu_stavki_ponude'); 
    
  
    
    $.post('ponuda', {status: status, id:idStavke}, function (data) {
    
             ukupnaSumaPonude.value = data;
    
     osveziTabeluSaNovimStavkamaPonude();
     
    
    

        // porukaZaTabelu.innerHTML = data;
         


//         if(porukaZaTabelu.style.display == "none"){porukaZaTabelu.style.display = "block";}
//         setTimeout(function(){porukaZaTabelu.style.display == "none";},2000);
     
    });
    
    
    
}

function dodajStavkuNaPonudu(){
  
    
    var status = "dodajStavkuPonudeSaForme";
    
    var proizvodId = localStorage.getItem("idProizvoda");
  
    var ponudaId =  parseInt(localStorage.getItem("idPonude"));
   
    var cenaProizvoda = document.getElementById('cena_proizvoda_tabelaSponude');
    var kolicinaEl =  document.getElementById('kolicina_prizvoda_tabelaSponude').value;
    var datumEl = document.getElementById('novi_datum_ponude');
    var porukaZaTabelu =  document.getElementById('poruka_za_listu_stavki_ponude'); 
    var ukupnaSumaPonude = document.getElementById('ukupna_suma_ponude');
    var nazivProizvoda = document.getElementById('izbor_proizvoda_na_st_ponude');
    
    var p = nazivProizvoda[nazivProizvoda.selectedIndex].value;
    
alert(p);


$.post('ponuda', {status: status, proizvodId : proizvodId, kolicina:kolicinaEl, ponudaId: ponudaId, cenaProizvoda: cenaProizvoda.value, nazivProizvoda : p}, function (data) {


    ukupnaSumaPonude.value = data;

     
      
//      
//      
//     
//             porukaZaTabelu.innerHTML = data;
//         
//         if(porukaZaTabelu.style.display == "none"){porukaZaTabelu.style.display = "block";}
//         setTimeout(function(){porukaZaTabelu.style.display == "none";},2000);
    
    });
    
     osveziTabeluSaNovimStavkamaPonude();
    
}


function showOptions(s){
    
      var p = s[s.selectedIndex].id;
      var imeKupca = s[s.selectedIndex].value;
      localStorage.setItem("kupacId", p);
      var status = "updateKupcaNaPonudi";
      var kupacNaPonudi = document.getElementById("kupac_za_ponudux");
      kupacNaPonudi.value =  imeKupca;
      
      
      
   
}

function showOptions2(s){
    
    //localStorage.removeItem("idProizvoda");
      var p = s[s.selectedIndex].id;
      localStorage.setItem("idProizvoda", p);
      var status = "menjanjeCeneProizvodaNaSP";
      var cenaProizvodaNaStavci = document.getElementById("cena_proizvoda_tabelaSponude"); 
  
    
    $.post('noviservlet', {status: status, id : p}, function (data) {
       
      
        cenaProizvodaNaStavci.value = data.aktuelnaCena; 
    
    
    });
}

    
function osveziTabeluSaNovimStavkamaPonude(){
    
    
    
   // alert('uslo');
    var id = parseInt(localStorage.getItem("idPonude"));
    var status = "osvezavanjeTabeleStavkePonude";
    
    var brisi = "obrisistavku";
    var editujDatum = "izmenaDatuma";
    var editujKolicinu = "izmenaKolicineStavke";
    var editujNazivProizvoda = "izmenaNazivaProizvoda";
    var editujAktuelnuCenu = "izmenaCeneProizvoda";
    
    var proizvodID = "sifraProizvoda";
    
    
   var tabela = document.querySelector('#tabela_stavki_ponude').innerHTML = '';
      $.post('ponuda', {status: status, id:id}, function (data) {
    
    var tr;
    for (var i = 0; i < data.length; i++) {
         if(data[i].vidljivo == "true"){
            tr = $('<tr>');
            tr.append('<td class="dodajStavkuPonude edituj tdfixing"> + stavka </td>');   
            tr.append('<td class="tdfixing"><input type="text" readonly="true" class = "form-control zaSiveDugmice " id="' + data[i].rbsPonude + editujNazivProizvoda + '" value ="' + data[i].nazivProizvoda + '"></td>');
            tr.append('<td class="tdfixing"><input type="text" readonly="true" class = "form-control zaSiveDugmice " id="' + data[i].rbsPonude + editujAktuelnuCenu + '" value ="' + data[i].aktuelnaCenaProizvoda + '"></td>');
            tr.append('<td class="tdfixing"><input type="number"  class = "form-control zaSiveDugmice " onchange="updateKolicinaNaStavci(this);" id="'+ data[i].rbsPonude + editujKolicinu + '" value ="' + data[i].kolicina + '"></td>');

            tr.append('<td onclick="obrisiStavkuPonude(this);" class="dugme_ponuda_stavka_brisi edituj tdfixing" id="' + data[i].rbsPonude + brisi + data[i].sifraPonude + '">' + "OBRISI" + '</td>');


            $('#tabela_stavki_ponude').append(tr);
         }
        
       }
     //    alert('uslo');
         
         var brojRedova = $('#tabela_stavki_ponude tr').length; 
         var porukaZaTabelu =  document.getElementById('poruka_za_listu_stavki_ponude'); 
         if(brojRedova  == 0){
             
             porukaZaTabelu.style.display = "block";
         }else{
             
             porukaZaTabelu.style.display = "none";  
             
         }
      });
}

    function naPromenuUTabeliSPonude(id){
    
    var status = "menjanjeSadrzinePolja";
    var element = id.id;
    var nazivPro = id.value;

    var idPonude = localStorage.getItem("idPonude");
    var tabelaStavki = document.getElementById('tabela_stavki_ponude');
        
    var porukaZaTabelu =  document.getElementById('poruka_za_listu_stavki_ponude'); 
   
       $.post('noviservlet', {status: status, id:element, idPonude : idPonude, nazivParametra:nazivPro }, function (data) {
            
         porukaZaTabelu.innerHTML = data;
         porukaZaTabelu.style.display = "block";
         setTimeout(function (){osveziTabeluSaNovimStavkamaPonude(); porukaZaTabelu.style.display = "none";},2500);
           
       });

}

function promenaDatumaNaTabeli(){
    var datum = document.getElementById('datum_ponudex');
    var status = 'datumNaTabeliPonudePosebno';
    var ponudaId = localStorage.getItem('idPonude');
    var DatumNaPrvojLiniji = document.getElementById('novi_datum_ponude');
     
    alert(datum);
    $.post('noviservlet', {status: status, datum:datum.value, ponudaId: ponudaId}, function (data){
         
         
         DatumNaPrvojLiniji.value = datum.value;
         osveziTabeluSaNovimStavkamaPonude();
         
     });
    
}

function promenaAktuelneCene (element) {
    
    var status = 'editAktCenaNaProizvodu';
  // var aktCena = document.getElementById('aktCenaEdit');
    var proizvodId = element.id;
    var tabelaProizvoda = document.getElementById('tabela_proizvodi');

    var porukaZaTabeluProizvoda =  document.getElementById('poruka_za_proizvod_tabelu'); 

    $.post('noviservlet', {status: status, aktCenaEdit:element.value, idProizvoda: proizvodId}, function (data){
         
       
         
          porukaZaTabeluProizvoda.innerHTML = data;
    
          porukaZaTabeluProizvoda.style.display = "block";
          
          osveziTabeluProizvoda();
       
   
          setTimeout(function(){porukaZaTabeluProizvoda.style.display = "none";},1500);
         
     });
    
    }
    
   function updatePonude(){
        
   var ponudaId = localStorage.getItem('idPonude');
   
   var datum  =  document.getElementById('datum_ponudex');
   var kupac = document.getElementById('kupac_za_ponudux');
   var poruka = document.getElementById('poruka_za_listu_stavki_ponude');
   
   var status = 'updatePonude';
   
    $.post('ponuda', {status: status, idPonude:ponudaId, datum:datum, kupac:kupac}, function (data){
   
    poruka.innerHTML = data;
    
          poruka.style.display = "block";
          
          osveziTabeluProizvoda();
   
        setTimeout(function(){poruka.style.display = "none";},1500);
        
    });
    
    
    }
    
    function prikaziTabeluProizvodaProizvodjaca(){
        
    
    var korisniciLog = document.getElementById('podaci_o_logovanom');
    var adminLista = document.getElementById('tabela_ikontrole');
    var adminProsPretraga = document.getElementById('prosiri_pretragu_div');
    var tabelaKupci = document.getElementById('tabela_kupci');
    var tabelaPonuda = document.getElementById('sva_ponuda');
    var tabelaProizvoda = document.getElementById('svi_proizvodi');
    


    if (korisniciLog.style.top == "0px") {
        slideOutTOP('podaci_o_logovanom');
        setTimeout(function () {
            korisniciLog.style.display = "none";
        }, 500);
    }
    if (adminLista.style.left == "0px") {
        slideOutDiv2('tabela_ikontrole');
    }
    setTimeout(function () {
        adminLista.style.display = "none";
    }, 200);
    if (adminProsPretraga.style.right == "-430px") {
        slideOutTabela('prosiri_pretragu_div');
        setTimeout(function () {
            adminProsPretraga.style.display = "none";
        }, 500);
    }
    if (tabelaKupci.style.bottom == "0px") {
        slideOutBottom('tabela_kupci');
        setTimeout(function () {
            tabelaKupci.style.display = "none";
        }, 500);
    }
    
    if(tabelaPonuda.style.display == "block"){tabelaPonuda.style.display = "none";}
    if(tabelaProizvoda.style.display == "block"){tabelaProizvoda.style.display = "none";}
      
    
      
        var status = "ortopanService";
        
        $.post('noviservlet', {status: status}, function (data){
        
        
         var tr;
    
            for (var i = 0; i < data.length; i++) {

            tr = $('<tr>');

            tr.append('<td class="tdfixing">' + data[i].nazivArtikla +  '</td>');
            tr.append('<td class="tdfixing">' + data[i].cena +  '</td>');
 
      
            $('#tabela_artikala_proizvodjaca123').append(tr);
        
         }
        });
          
        $('#svi_artikli_proizvodjaca').toggle('clip');
        
    }
     
        
        function UgasiTabeluProizvodaProizvodjaca(){
         
             $('#svi_artikli_proizvodjaca').toggle('clip');
           setTimeout(function(){$('#svi_artikli_proizvodjaca').css("display","none");}, 500)}
   
   
  function zabraniIzmenuSumeIzBaze(element){
      
     var idPOnude = parseInt(localStorage.getItem("idPonude"));
     var status = "ZabraniIzmenuUkupneSumePOnude";
     var poruka = document.getElementById('poruka_za_listu_stavki_ponude');
      var suma = element.value;
      
      $.post('noviservlet', {status: status, idPonude:idPOnude, suma: suma}, function (data){
      
      
      var data1 = data[0];
      var data2 = data[1];
           
          poruka.innerHTML = data1;
            poruka.style.display = "block";
            setTimeout(function(){poruka.style.display = "none";},2000)
      
          element.value = data2;
            
      
      
      });
      
  }
  
  function sacuvajPonuduUBAzu(){
      
      
 
      var status = "update_ponude";
      var IdPonude = parseInt(localStorage.getItem("idPonude"));
      var datum  =  document.getElementById('datum_ponudex');
      var kupac = document.getElementById('kupac_za_ponudux');
       var poruka = document.getElementById('poruka_za_listu_stavki_ponude');
    
      $.post('ponuda', {status: status, idPonude:IdPonude, datum:datum.value, kupac:kupac.value}, function (data){
      
          osveziTabeluSaNovimStavkamaPonude();  
          
          
          poruka.innerHTML = data;
            poruka.style.display = "block";
            setTimeout(function(){poruka.style.display = "none";},2000)
      
       
      
      
      
  });
  
  
  }
  
  function updateKolicinaNaStavci(element){
      
      
      var status = "updateKolicineNaStavciPonude";
      var rbsStavke = element.id;
      var ukupnaSumaPonude = document.getElementById("ukupna_suma_ponude"); 
       

      $.post('ponuda', {status: status, idrbs:rbsStavke, kolicina: element.value}, function (data){
      

      ukupnaSumaPonude.value = data;

      });
  
  }