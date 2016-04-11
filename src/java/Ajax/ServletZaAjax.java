/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import ViewModels.KupacDetalji;
import ViewModels.PonudaDetalji;
import ViewModels.ProizvodDetalji;
import ViewModels.ZaposleniDetalji;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Poruka;
import model.Zaposleni;
import util.Validate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import klijentServisi.ArtikliWebService;
import klijentServisi.ArtikliWebService_Service;
import model.CenaProizvoda;
import model.JedinicaMere;
import model.Kupac;
import model.Mesto;
import model.Ponuda;
import model.Proizvod;
import model.StavkaPonude;

import model.TabelaArtikli;
import org.jboss.weld.jsf.JsfApiAbstraction;
import util.DB_broker;
import util.Wrapper;

/**
 *
 * @author studio69
 */
public class ServletZaAjax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

        String status = request.getParameter("status");
        String textUserName = request.getParameter("userName");

        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?

        Zaposleni m;
        String ver = "";
        String zaPretragu = "";
        String poruka = "";

        if (status.equals("emailzaposlenog")) {
            zaPretragu = "ZD.EMAIL";
            poruka = Validate.prekontrolisiEmail(textUserName);
            if (!poruka.equals("")) {

                response.getWriter().write(poruka);
            }
        }
        if (status.equals("jmbgzaposlenog")) {
            zaPretragu = "ZD.JMBG";
            poruka = Validate.kontrolisiJmbg(textUserName);
            if (!poruka.equals("")) {

                response.getWriter().write(poruka);
            }
        }
        if (status.equals("telzaposlenog")) {
            zaPretragu = "ZD.TELEFON";
            poruka = Validate.kontrolisiTelefon(textUserName);
            if (!poruka.equals("")) {
                response.getWriter().write(poruka);
            }
        }

        if (poruka.equals("")) {
            try {
                m = Zaposleni.findUnique(zaPretragu, textUserName);
                if (m != null) {
                    ver = "Korisnik sa tim podatcima Postoji u bazi!";
                    response.getWriter().write(ver);
                } else {
                    ver = "";
                    response.getWriter().write(ver);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

        String status = request.getParameter("status");

        
        
        if(status.equals("poruka_status")){
        
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            String stat =  request.getParameter("statuslabela");
            String zaposleniId = request.getParameter("zaposleniId");
            
            try {
            
                Zaposleni zap = Zaposleni.findByPrimaryKey(Integer.valueOf(zaposleniId));
                
                zap.setStatus(stat);
                
                zap.update();
            
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
        
            response.getWriter().write("stagod");
            
        }

        if (status.equals("login")) {

            try {
                Integer brojac;
                brojac = (Integer) request.getSession().getAttribute("brojac");

                String poruka = "";
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                email = email.toLowerCase();

                ZaposleniDetalji z;

                z = ZaposleniDetalji.findUnique("ZD.EMAIL", email);

                if (z != null) {

                    if (z.getIsadmin().equals("TRUE")) {
                        poruka = "admin_true";

                    } else {

                        poruka = "user_true";
                    }
                    if (brojac == null) {

                        brojac = z.getBrojac();
                    }
                    request.getSession().setAttribute("brojac", brojac);

                } else {
                    response.getWriter().write("Niste se registrovali! Morate se Registrovati da bi pristupili Sistemu Firme Galen - Fokus!");

                }

                if (z != null && !z.getSifra().equals(password) && brojac < 3) {

                    poruka = "Nije Dobar Password, pokusajte Ponovo!";
                    brojac++;
                    request.getSession().setAttribute("brojac", brojac);
                    if (brojac < 3) {
                        response.getWriter().write(poruka);
                    }
                }

                if (z != null && z.getSifra().equals(password) && brojac < 3) {

                    response.getWriter().write(poruka);
                    request.getSession().setAttribute("korisnik", z);
                    brojac = 0;

                }

                if (z != null && !z.getSifra().equals(password) && brojac == 3) {

                    poruka = "Blokirao Vas Je Sistem. Morate sacekati Administratora sistema!";

                    Poruka p = new Poruka();
                    p.setBrojac(brojac);
                    p.setPorukaId(z.getPorukaId());
                    p.setSifraRadnika(z.getSifra_radnika());
                    p.setVreme(Validate.dajVrijemeSada());
                    p.setNazivAkcije("LOGIN");
                    p.setPoruka(poruka);
                    p.update();

                    Zaposleni zap = Zaposleni.findByPrimaryKey(z.getSifra_radnika());
                    zap.setStatus("FALSE");
                    zap.update();

                    request.getSession().setAttribute("brojac", brojac);

                    response.getWriter().write(poruka);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();

            }
        }

        if (status.equals("tabela_korisnika")) {

            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String vrednstTextBoxa = request.getParameter("vrednost");
                String brojStrane = request.getParameter("brStrane");
                //ZA PROSIRENU PRETRAGU PARAMETRI
                String vrednstIme = request.getParameter("ime");
                String vrednostPrezime = request.getParameter("prezime");
                String vrednstEmail = request.getParameter("email");

                if (Validate.exists(vrednstIme)) {
                    vrednstIme = Validate.preurediStringZaIme(vrednstIme);
                }
                if (Validate.exists(vrednostPrezime)) {
                    vrednostPrezime = Validate.preurediStringZaIme(vrednostPrezime);
                }
                if (Validate.exists(vrednstEmail)) {
                    vrednstEmail = vrednstEmail.toLowerCase();
                }

                Integer brojStrane2 = Integer.valueOf(brojStrane);
                //if(brojStrane == null){brojStrane = "0";}
                Integer pocetak = Integer.valueOf(brojStrane);
                Integer inkrement;
                Gson gson = new Gson();
                List<ZaposleniDetalji> listaZaSlanje;
                if (pocetak == null || pocetak == 0) {
                    pocetak = 1;
                    inkrement = pocetak + 4;
                } else {
                    pocetak = pocetak * 5 + 1;
                    inkrement = pocetak + 4;
                }
                if (!Validate.exists(vrednstTextBoxa) && !Validate.exists(vrednstIme) && !Validate.exists(vrednostPrezime) && !Validate.exists(vrednstEmail)) {

                    listaZaSlanje = ZaposleniDetalji.findByParameter(null, null, pocetak, inkrement);

                } else {

                    if (Validate.exists(vrednstTextBoxa)) {
                        vrednstTextBoxa = Validate.preurediStringZaIme(vrednstTextBoxa);
                        listaZaSlanje = ZaposleniDetalji.findByAutoComplete("Z.IME", vrednstTextBoxa, null, null, null);
                    } else {
                        if (vrednstIme.equals("")) {
                            vrednstIme = null;
                        }
                        if (vrednostPrezime.equals("")) {
                            vrednostPrezime = null;
                        }
                        if (vrednstEmail.equals("")) {
                            vrednstEmail = null;
                        }
                        listaZaSlanje = ZaposleniDetalji.findByAutoComplete(null, null, vrednstIme, vrednostPrezime, vrednstEmail);
                    }

                }

                response.getWriter().write(gson.toJson(listaZaSlanje));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("pogled_na_korisnika")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String idKorisnika = request.getParameter("id");

            Integer id = Integer.valueOf(idKorisnika);

            ZaposleniDetalji zap;
            try {
                zap = ZaposleniDetalji.findByPrimaryKey(id);
                response.getWriter().write(new Gson().toJson(zap));

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        

        if (status.equals("tabela_proizvoda")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String nazivProizvoda = request.getParameter("naziv");

            List<ProizvodDetalji> listaSvihProizvoda;

            try {
                if (!Validate.exists(nazivProizvoda)) {
                    listaSvihProizvoda = ProizvodDetalji.findAll();
                    response.getWriter().write(new Gson().toJson(listaSvihProizvoda));
                } else {

                    nazivProizvoda = nazivProizvoda.toLowerCase();

                    listaSvihProizvoda = ProizvodDetalji.findByAutoComplete("P.NAZIV_PROIZVODA", nazivProizvoda);

                    response.getWriter().write(new Gson().toJson(listaSvihProizvoda));

                }

            } catch (SQLException ex) {

                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("pregled_i_edit_proizvoda")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String id = request.getParameter("id");
            Integer redniBroj = Integer.valueOf(id);
            ProizvodDetalji proz;
            List<JedinicaMere> jm;

            try {

                jm = JedinicaMere.findByParameter();

                proz = ProizvodDetalji.findByPrimaryKey(redniBroj);
                request.getSession().setAttribute("proizvod_id", redniBroj);

                String json1 = new Gson().toJson(jm);
                String json2 = new Gson().toJson(proz);

                String bothJson = "[" + json1 + "," + json2 + "]";

                response.getWriter().write(bothJson);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("obrisi_proizvod")) {

            try {
                String id = request.getParameter("id");
                id = id.replaceAll("\\D+", "");
                Integer sifra = Integer.valueOf(id);

                CenaProizvoda cp;
                Proizvod p = new Proizvod();
                p.setSifraProizvoda(sifra);
                p.delete();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("update_proizvod")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ProizvodDetalji pd;
            Integer id = (Integer) request.getSession().getAttribute("proizvod_id");

            Proizvod p;
            CenaProizvoda cp;
            JedinicaMere jm;

            String nazivProizvoda = request.getParameter("nazivProizvoda");
            String aktuelnaCena = request.getParameter("aktuelnaCena");
            String nazivJM = request.getParameter("nazivJM");
            String tezina = request.getParameter("tezina");

            String ImeProizvodjaca = request.getParameter("nazivProizvodjaca");
            String adresa = request.getParameter("adresaProizvodjaca");
            String grad = request.getParameter("gradProizvodjaca");
            String brojUlice = request.getParameter("brojProizvodjaca");
            String drzava = request.getParameter("drzavaProizvodjaca");

            try {
                pd = ProizvodDetalji.findByPrimaryKey(id);

                pd.setNazivProizvoda(nazivProizvoda);
                pd.setAktuelnaCena(Integer.valueOf(aktuelnaCena));
                pd.setNazivProizvodjaca(ImeProizvodjaca);
                pd.setAdresaProizvodjaca(adresa);
                pd.setGradProizvodjaca(ImeProizvodjaca);
                pd.setBrojProizvodjaca(Integer.valueOf(brojUlice));
                pd.setDrzavaProizvodjaca(drzava);
                pd.setNazivJM(nazivJM);

                jm = JedinicaMere.findByUsername(nazivJM);

                if (jm == null) {

                    JedinicaMere jedMEr = new JedinicaMere();
                    jedMEr.setSifraJM(pd.getSifraJM());
                    jedMEr.setNazivJM(nazivJM);
                    jedMEr.setVrednost(Double.valueOf(tezina));
                    jedMEr.saveJedinicaMere();

                } else {

                    jm.setVrednost(Double.valueOf(tezina));
                    jm.update();

                }

                p = new Proizvod();

                p.setSifraProizvoda(pd.getSifraProizvoda());
                p.setNazivProizvoda(pd.getNazivProizvoda());
                p.setSifraJM(jm.getSifraJM());
                p.setNazivProizvodjaca(pd.getNazivProizvodjaca());
                p.setAdresa(pd.getAdresaProizvodjaca());
                p.setGrad(pd.getGradProizvodjaca());
                p.setBroj(pd.getBrojProizvodjaca());
                p.setDrzava(pd.getDrzavaProizvodjaca());

                CenaProizvoda poslednjaCena = CenaProizvoda.izvadiPoslednjiDatum(pd.getSifraProizvoda());

                cp = new CenaProizvoda();

                String datum = Validate.dajVrijemeSada();
                datum = datum.substring(0, datum.indexOf(" "));

                String datum2 = poslednjaCena.getDatum();

                SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");

                //parse the string into Date object
                Date date;
                try {
                    date = sdfSource.parse(datum2);

                    SimpleDateFormat sdfDestination = new SimpleDateFormat("dd.MM.yyyy");

                    datum2 = sdfDestination.format(date);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                if (datum2.equals(datum)) {

                    poslednjaCena.setIznos(pd.getAktuelnaCena());
                    poslednjaCena.setDatum(datum);
                    poslednjaCena.update();
                    CenaProizvoda.azuriranjeAktuelneCene(pd.getSifraProizvoda());

                } else {
                    cp.setDatum(datum);
                    cp.setIznos(pd.getAktuelnaCena());
                    cp.setSifra_proizvoda(pd.getSifraProizvoda());
                    cp.saveProizvod();
                    CenaProizvoda.azuriranjeAktuelneCene(pd.getSifraProizvoda());

                }

                p.update();

                response.getWriter().write(new Gson().toJson("Uspesno Poslata Poruka!"));

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("unos_Proizvoda")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {

                List<JedinicaMere> jm;
                jm = JedinicaMere.findByParameter();

                response.getWriter().write(new Gson().toJson(jm));

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (status.equals("unos_proizvoda_procedura")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String nazivProizvoda = request.getParameter("nazivProizvoda");
            String aktuelnaCena = request.getParameter("aktuelnaCena");
            String nazivJM = request.getParameter("nazivJM");
            String tezina = request.getParameter("tezina");

            String ImeProizvodjaca = request.getParameter("nazivProizvodjaca");
            String adresa = request.getParameter("adresaProizvodjaca");
            String grad = request.getParameter("gradProizvodjaca");
            String brojUlice = request.getParameter("brojProizvodjaca");
            String drzava = request.getParameter("drzavaProizvodjaca");

            Proizvod p;
            CenaProizvoda cp;
            JedinicaMere jm;

            try {

                jm = JedinicaMere.findPoslednjuJM();

                if (jm == null) {
                    jm.setSifraJM(1);
                } else {
                    jm.setSifraJM(jm.getSifraJM() + 1);
                }

                jm.setNazivJM(nazivJM);
                jm.setVrednost(Double.valueOf(tezina));

                jm.saveJedinicaMere();

                p = Proizvod.nadjiPoslednjiProizvod();

                if (p == null) {
                    p.setSifraProizvoda(0);
                } else {
                    p.setSifraProizvoda(p.getSifraProizvoda() + 1);
                }
                p.setNazivProizvoda(nazivProizvoda);
                p.setSifraJM(jm.getSifraJM());
                p.setNazivProizvodjaca(nazivProizvoda);
                p.setAdresa(adresa);
                p.setGrad(grad);
                p.setBroj(Integer.valueOf(brojUlice));
                p.setDrzava(drzava);

                p.saveProizvod();

                CenaProizvoda poslednjaCena = CenaProizvoda.izvadiPoslednjiDatum(p.getSifraProizvoda());

                String datum = Validate.dajVrijemeSada();
                datum = datum.substring(0, datum.indexOf(" "));

                if (poslednjaCena != null) {

                    cp = new CenaProizvoda();

                    String datum2 = poslednjaCena.getDatum();

                    SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");

                    //parse the string into Date object
                    Date date;
                    try {
                        date = sdfSource.parse(datum2);

                        SimpleDateFormat sdfDestination = new SimpleDateFormat("dd.MM.yyyy");

                        datum2 = sdfDestination.format(date);

                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    if (datum2.equals(datum)) {

                        poslednjaCena.setIznos(Integer.valueOf(aktuelnaCena));
                        poslednjaCena.setDatum(datum);
                        poslednjaCena.update();
                        CenaProizvoda.azuriranjeAktuelneCene(p.getSifraProizvoda());

                    } else {

                        cp.setDatum(datum);
                        cp.setIznos(Integer.valueOf(aktuelnaCena));
                        cp.setSifra_proizvoda(p.getSifraProizvoda());
                        cp.saveProizvod();

                        CenaProizvoda.azuriranjeAktuelneCene(p.getSifraProizvoda());
                    }

                } else {

                    poslednjaCena = new CenaProizvoda();
                    poslednjaCena.setDatum(datum);
                    poslednjaCena.setIznos(Integer.valueOf(aktuelnaCena));
                    poslednjaCena.setSifra_proizvoda(p.getSifraProizvoda());
                    poslednjaCena.saveProizvod();

                    CenaProizvoda.azuriranjeAktuelneCene(p.getSifraProizvoda());

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            response.getWriter().write(new Gson().toJson("Uspesno Unesen Proizvod!"));
        }

        if (status.equals("sacuvaj_kupca")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String nazivKupca = request.getParameter("ime");
            String pibKupca = request.getParameter("pib");
            String mestoKupca = request.getParameter("adresa");
            String gradKupca = request.getParameter("grad");

            Mesto m;
            Kupac k;

            try {

                m = Mesto.findPoslednjeMestoUBazi();

                if (m == null) {
                    m.setMestoId(1);
                } else {
                    m.setMestoId(m.getMestoId() + 1);
                }
                m.setAdresa(mestoKupca);
                m.setGrad(gradKupca);

                k = new Kupac();

                k.setImeKupca(nazivKupca);
                k.setPib(pibKupca);
                k.setMestoId(m.getMestoId());

                m.saveMesto();
                k.saveKupac();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            response.getWriter().write("Unesen kUpac!");

        }

        if (status.equals("tabela_kupaca")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String nazivKupac = request.getParameter("naziv");

            List<KupacDetalji> listaSvihKupaca;

            try {
                if (!Validate.exists(nazivKupac)) {
                    listaSvihKupaca = KupacDetalji.findAll();
                    response.getWriter().write(new Gson().toJson(listaSvihKupaca));
                } else {

                    nazivKupac = nazivKupac.toLowerCase();

                    listaSvihKupaca = KupacDetalji.findByAutoComplete("P.NAZIV_PROIZVODA", nazivKupac);

                    response.getWriter().write(new Gson().toJson(listaSvihKupaca));

                }

            } catch (SQLException ex) {

                ex.printStackTrace();

            }
        }

        if (status.equals("kupci_comboBox")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            List<Kupac> k;

            try {

                k = Kupac.findByParameter();
                response.getWriter().write(new Gson().toJson(k));

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        
        if(status.equals("dodatniPodaciOKupcu")){
          
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            String ime = request.getParameter("vrednost");
            
            try {
                    
                List<KupacDetalji> k = KupacDetalji.findByParameter("K.IME_KUPCA",ime);
                response.getWriter().write(new Gson().toJson(k));
            
           } catch (SQLException ex) {
               ex.printStackTrace();
            }
        
        }

        if (status.equals("tabela_svih_ponuda")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            List<Ponuda> p = new ArrayList<Ponuda>();
            p = DB_broker.findByParameter(null, null,p);
            for (Ponuda pon : p) {
                
                pon.setDatum(Validate.konvertujDatum(pon.getDatum()));

            }
            response.getWriter().write(new Gson().toJson(p));

        }



        if (status.equals("osvezi_tabelu_stavki_ponude")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String idPonude = request.getParameter("idPonude");

            try {

                PonudaDetalji p = PonudaDetalji.findByPrimaryKey(Integer.valueOf(idPonude));

                response.getWriter().write(new Gson().toJson(p));

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

//        if (status.equals("popuni_stavke_ponude")) {
//
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            String nesto = request.getParameter("tabela");
//            String datum = request.getParameter("datum");
//            String kupac = request.getParameter("nazivKupca");
//            String mestoId = request.getParameter("mestoId");
//
//            
//           
//            ZaposleniDetalji logovani = (ZaposleniDetalji) request.getSession().getAttribute("korisnik");
//
//            Wrapper[] niz = new Gson().fromJson(nesto, Wrapper[].class);
//
//            Ponuda p = null;
//            StavkaPonude sp = null;
//            List<StavkaPonude> listaStavki = new ArrayList<StavkaPonude>();
//
//            try {
//                p = DB_broker.findPoslednjuPonuduUBazi(p);
//                if (p == null) {
//                    p = new Ponuda();
//                    p.setSifraPonude(1);
//                } else {
//                    p.setSifraPonude(p.getSifraPonude() + 1);
//                }
//                p.setDatum(datum);
//                p.setSifraRadnika(logovani.getSifra_radnika());
//                p.setKupacId(Validate.vratiBroj(mestoId));
//                p.setUkupnaSuma(0);
//
//                for (int i = 0; i < niz.length; i++) {
//
//                    
//                    String proizvod = niz[i].getId();
//                    proizvod = proizvod.replaceAll("\\D+", "");
//                    Integer sifra = Integer.valueOf(proizvod);
//                    sp = new StavkaPonude();
//                    sp.setSifraProizvoda(sifra);
//                    sp.setKolicina(Integer.valueOf(niz[i].getKolicina()));
//                    sp.setSifraPonude(p.getSifraPonude());
//                    sp.setRbsPonude(i);
//                    listaStavki.add(sp);
//
//                }
//                
//                DB_broker.savePonuda(p);
//                for (StavkaPonude stavkaPonude : listaStavki) {
//                    stavkaPonude.saveStavkuPonude();
//                }
//                DB_broker.commitTransaction();
//               
//            } catch (SQLException ex) {
//                DB_broker.rollbackTransaction();
//                ex.printStackTrace();
//            }
//            response.getWriter().write(new Gson().toJson("Ponuda je sacuvana!"));
//        }
//        
        
        
        
        //NEKI METODI ZA PONUDU
//          if (status.equals("obrisi_ponudu")) {
//
//            try {
//                
//                String id = request.getParameter("id");
//                id = id.replaceAll("\\D+", "");
//                Integer sifra = Integer.valueOf(id);
//                
//                
//                List<StavkaPonude> sp = StavkaPonude.findByParameter("SIFRA_PONUDE",sifra);
//                for(StavkaPonude stavka : sp){
//                stavka.delete();
//                }
//                Ponuda p = new Ponuda();
//                p.setSifraPonude(sifra);
//                DB_broker.deletePonuda(p);
//                DB_broker.commitTransaction();
//                
//                
//            } catch (SQLException ex) {
//               ex.printStackTrace();
//               DB_broker.rollbackTransaction();
//            }
//
//        }
          
          
          if(status.equals("menjanjeCeneProizvodaNaSP")){
          
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            String id =  request.getParameter("id");
            Integer sifra = Validate.vratiBroj(id);
            try {
                
                Proizvod proizvod = Proizvod.findByPrimaryKey(sifra);
                response.getWriter().write(new Gson().toJson(proizvod));
           
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
          }
          
          
         
        
         
         
          
//          if(status.equals("menjanjeSadrzinePolja")){
//          
//            
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//             
//            String[] nizAkcija = {"izmenaDatuma" , "izmenaNazivaProizvoda"};
//            
//            String idPonuda = request.getParameter("idPonude");
//            String idStavkePonude = request.getParameter("id");
//            String nazivParametra = request.getParameter("nazivParametra");
//          
//      
//            //String datum = request.getParameter("nazivParametra");
//            //String datum = nazivParametra;
//            String ostatak = "";
//              StavkaPonude sp =  new StavkaPonude();
//            String poruka = "akonistadrugoUpisOvo";
//            
//            
//            for(int i = 0;i<idStavkePonude.length(); i++){
//            
//               if(Character.isLetter(idStavkePonude.charAt(i))){
//                ostatak += idStavkePonude.charAt(i);
//                }
//           }
//            
//                sp.setSifraPonude(Integer.valueOf(idPonuda));
//                sp.setRbsPonude(Validate.vratiBroj(idStavkePonude));
//                sp.setKolicina(0);
//              //  sp.setDatum(Validate.konvertujDatum(nazivParametra));
//                //sp.setNazivProizvoda(nazivParametra);
//            
//                if(ostatak.equals(nizAkcija[0])){
//                    
//                    sp.setDatum(nazivParametra);
//                    poruka = sp.updateZaGreskeDatum("datum");
//                
//                }
//                
//                if(ostatak.equals(nizAkcija[1])){
//                  
//                    sp.setNazivProizvoda(nazivParametra);
//                    poruka = sp.updateZaGreskeDatum("nazivProizvoda");
//                
//                }
//                
//                response.getWriter().write(new Gson().toJson(poruka));
//            
//          }
//          
          
          
          if(status.equals("datumNaTabeliPonudePosebno")){
          
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
          
           String datum = request.getParameter("datum");
           String ponudaId = request.getParameter("ponudaId");
           
           List<Ponuda> listaPonuda;
           
            try {
                listaPonuda = new ArrayList<>();
                Ponuda p = DB_broker.findByPrimaryKey(Integer.valueOf(ponudaId), listaPonuda);
                p.setDatum(datum);
                
                DB_broker.updatePonuda(p,DB_broker.getConnection());
                DB_broker.commitTransaction();
                 
            } catch (SQLException ex) {
                ex.printStackTrace();
                DB_broker.rollbackTransaction();
            }
           
           
            response.getWriter().write(new Gson().toJson(""));
          }
          
          
          
          
          
          if(status.equals("editAktCenaNaProizvodu")){
           
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           
           String aktCena = request.getParameter("aktCenaEdit");
           String proizvodId = request.getParameter("idProizvoda");
           String poruka = "";
          
            Integer sifraProizvoda = Validate.vratiBroj(proizvodId);
            try {
                Proizvod pz = Proizvod.findByPrimaryKey(sifraProizvoda);
                pz.setAktuelnaCena(Integer.valueOf(aktCena));
                
                poruka = pz.updateAktuelnaCena();
                
               DB_broker.commitTransaction();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                DB_broker.rollbackTransaction();
            }
          response.getWriter().write(new Gson().toJson(poruka));
          
          
          
          }
         
//          if(status.equals("obrisiStavkuPOnudeSaPonude")){
//             
//              response.setContentType("application/json");
//              response.setCharacterEncoding("UTF-8");
//              
//            String idStavke = request.getParameter("id");
//            
//        String idStavkePOnude = "" ;
//        String idPOnude = "" ;
//       
//       String unutrasnjistring = idStavke.replaceAll("\\d","");
//       
//       idStavkePOnude = idStavke.substring(0, idStavke.indexOf(unutrasnjistring));
//       idPOnude = idStavke.substring(idStavkePOnude.length() + unutrasnjistring.length(), idStavke.length());
//       
//       
//       Integer rbsStavke = Integer.valueOf(idStavkePOnude);
//       Integer IdPonudeInt = Integer.valueOf(idPOnude);
//       
//          StavkaPonude sp = new StavkaPonude();
//          sp.setRbsPonude(rbsStavke);
//          sp.setSifraPonude(IdPonudeInt);
//      
//      
//          Ponuda p =  null;
//        List<Ponuda> listaPOnuda;
//      
//            try { 
//       
//                sp.delete();
//                listaPOnuda =  new ArrayList<Ponuda>();
//                
//                p = DB_broker.findByPrimaryKey(IdPonudeInt, listaPOnuda);
//                
//                DB_broker.commitTransaction();
//                
//            } catch (SQLException ex) {
//               ex.printStackTrace();
//               DB_broker.rollbackTransaction();
//            }
//            String poruka = "uspesno ste obrisali stavku!";
//                 
//            String json1 = new Gson().toJson(poruka);
//            String json2 = new Gson().toJson(p);
//               
//                String bothJson = "[" + json1 + "," + json2 + "]";
//            
//             response.getWriter().write(bothJson);
//          }
          
        
         
          
          if(status.equals("ortopanService")){
          
             response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
              
             ArtikliWebService_Service artikli = new ArtikliWebService_Service();
             ArtikliWebService arser = artikli.getArtikliWebServicePort();
               
             response.getWriter().write(new Gson().toJson(arser.vratiOrtopanArtikle()));
              
          }

          
//          if(status.equals("ZabraniIzmenuUkupneSumePOnude")){
//          
//              response.setContentType("application/json");
//              response.setCharacterEncoding("UTF-8");
//          
//              String suma = request.getParameter("suma");
//              String idPonude =  request.getParameter("idPonude");
//              String poruka = "";
//              
//              Ponuda b =  null;
//               Ponuda p = new Ponuda();
//               poruka = p.updateZaGresku(Integer.valueOf(suma), Integer.valueOf(idPonude));
//            try {
//                b =  Ponuda.findByPrimaryKey(Integer.valueOf(idPonude));
//            } catch (SQLException ex) {
//                
//                ex.printStackTrace();
//            }
//             
//               
//             String json1 = new Gson().toJson(poruka);
//             String json2 = new Gson().toJson(b.getUkupnaSuma());
//               
//                String bothJson = "[" + json1 + "," + json2 + "]";
//            
//               response.getWriter().write(bothJson);
//               
//               
//          }
         
  }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
