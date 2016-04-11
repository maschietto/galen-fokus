/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewModels.PonudaDetalji;
import ViewModels.ProizvodDetalji;
import ViewModels.ZaposleniDetalji;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Kupac;
import model.Ponuda;
import model.Proizvod;
import model.StavkaPonude;
import util.DB_broker;
import util.Validate;
import util.Wrapper;

/**
 *
 * @author Aleksa Jonic
 */
public class PonudaController extends HttpServlet {

    List<StavkaPonude> aktuelneStavkePonude = new ArrayList<StavkaPonude>();
    
 
    public Ponuda dodajPonudu(String datum, String naizvKupca, String mestoId, Ponuda p, ZaposleniDetalji zd) {

        if (p == null) {
            p = new Ponuda();
            p.setSifraPonude(1);
        } else {
            p.setSifraPonude(p.getSifraPonude() + 1);
        }
        p.setDatum(datum);
        p.setSifraRadnika(zd.getSifra_radnika());
        p.setKupacId(Validate.vratiBroj(mestoId));
        p.setUkupnaSuma(0);
        return p;

    };
    
    
    
    public void snimiPonuduUBazu(Ponuda pon) {

        try {
            Connection con =  DB_broker.getConnection();
            DB_broker.savePonuda(pon, con);
            DB_broker.commitTransaction();
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            DB_broker.rollbackTransaction();
        }
   

    };

    
    
    public void izmeniPonudu(String datum, String kupac, Ponuda pon) {

        pon.setDatum(datum);
        pon.setImeKupca(kupac);
        
        try {
        
            Connection con = DB_broker.getConnection();
            DB_broker.updatePonuda(pon,con);
            DB_broker.commitTransaction();
        } catch (SQLException ex) {
            ex.printStackTrace();
            DB_broker.rollbackTransaction();
        }
        

    };
       
    
 
     
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
     
    }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

        String status = request.getParameter("status");



       //U OVOM BLOKU KODA SE POZIVA SNIMANJE PONUDE:
        
        if (status.equals("popuni_stavke_ponude")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String nesto = request.getParameter("tabela");
            String datum = request.getParameter("datum");
            String kupac = request.getParameter("nazivKupca");
            String mestoId = request.getParameter("mestoId");

            ZaposleniDetalji logovani = (ZaposleniDetalji) request.getSession().getAttribute("korisnik");

            Wrapper[] niz = new Gson().fromJson(nesto, Wrapper[].class);

            Ponuda p = null;
            StavkaPonude sp = null;
            List<StavkaPonude> listaStavki = new ArrayList<StavkaPonude>();

         
                p = DB_broker.findPoslednjuPonuduUBazi(p);
                p = dodajPonudu(datum, kupac, mestoId, p, logovani);

                for (int i = 0; i < niz.length; i++) {

                    String proizvod = niz[i].getId();
                    proizvod = proizvod.replaceAll("\\D+", "");
                    Integer sifra = Integer.valueOf(proizvod);
                    sp = new StavkaPonude();
                    sp.setSifraProizvoda(sifra);
                    sp.setKolicina(Integer.valueOf(niz[i].getKolicina()));
                    sp.setSifraPonude(p.getSifraPonude());
                    sp.setRbsPonude(i);
                    sp.setStatusStavke("insert");
                    listaStavki.add(sp);

                }
                
                p.setListaStavki(listaStavki);

                snimiPonuduUBazu(p);
            
                response.getWriter().write(new Gson().toJson("Ponuda je sacuvana!"));
        }
        
                
        //U OVOM BLOKU KODA SE POZIVA IZMENA PONUDE:
        
            if (status.equals("update_ponude")) {
                    
                    
            String datum = request.getParameter("datum");
            String kupac = request.getParameter("kupac");
            String ponudaId =  request.getParameter("idPonude");

            
            List<Ponuda> listaPonuda = new ArrayList<Ponuda>();
            Ponuda p = null;
            try{
                
                
           //PREKO KONSTRUKTORA PRONALAZIM PONUDU U BAZI:    
         
           p = new Ponuda(DB_broker.findByPrimaryKey(Integer.valueOf(ponudaId), listaPonuda), DB_broker.findByParameterSP("SIFRA_PONUDE", Integer.valueOf(ponudaId)));
           
            }catch(SQLException ex){
            ex.printStackTrace();
            }
            
            p.setListaStavki(aktuelneStavkePonude);
            
            
            //UPDATEUJEM PONUDU:
            
            izmeniPonudu(datum, kupac, p);
         
            response.getWriter().write(new Gson().toJson("Uspesno ste izmenili ponudu"));
        
            
            }
            
             
            if(status.equals("stavke_ponude_tabela")){
          
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
          
            String idPonude = request.getParameter("id");
            
            try {
                List<Ponuda> listaPOnuda =  new ArrayList<Ponuda>();
                
                Ponuda p =  new Ponuda(DB_broker.findByPrimaryKey(Integer.valueOf(idPonude), listaPOnuda), DB_broker.findByParameterSP("SIFRA_PONUDE",Integer.valueOf(idPonude)));
                
//                List<PonudaDetalji> pd = PonudaDetalji.findByParameter("p.sifra_ponude", Integer.valueOf(idPonude));
//                
                List<Kupac> kupci = Kupac.findByParameter();
               
                List<Proizvod> proizvodi = new ArrayList<Proizvod>();
                
                proizvodi = DB_broker.findAllProizvode(null, null,proizvodi);
                
                
                
                for(StavkaPonude stavka : p.getListaStavki()){
                
                    stavka.setDatum(Validate.konvertujDatum(stavka.getDatum()));
                   
                    for(Proizvod proz : proizvodi){
                    
                        if(stavka.getSifraProizvoda().equals(proz.getSifraProizvoda())){
                        stavka.setAktuelnaCenaProizvoda(proz.getAktuelnaCena());
                        }
                    }
                    
                }
               
                aktuelneStavkePonude = p.getListaStavki();
                Integer suma = 0;
               
                for(StavkaPonude sp : aktuelneStavkePonude){
                sp.setStatusStavke("");
                sp.setVidljivo("true");
                suma += sp.getKolicina() * sp.getAktuelnaCenaProizvoda();
                   
               }
                
                p.setUkupnaSuma(suma);
                p.setDatum(Validate.konvertujDatum(p.getDatum()));
          
                String json1 = new Gson().toJson(p);
                String json2 = new Gson().toJson(kupci);
              
                String json3 = new Gson().toJson(proizvodi);
                String json4 = new Gson().toJson(aktuelneStavkePonude);
                String bothJson = "[" + json1 + "," + json2 + "," + json3 + "," + json4 + "]";
                
                response.getWriter().write(bothJson);
            
            
            } catch (SQLException ex) {
               ex.printStackTrace();
            } 
          }
            
            
          if(status.equals("osvezavanjeTabeleStavkePonude")){
          
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
          
            response.getWriter().write(new Gson().toJson(aktuelneStavkePonude));
                
          }
          
           if(status.equals("dodajStavkuPonudeSaForme")){
          
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
          
          
            String ponudaId = request.getParameter("ponudaId");
            String proizvodId = request.getParameter("proizvodId");
            String kolicina = request.getParameter("kolicina");
            String cenaProizvoda =  request.getParameter("cenaProizvoda");
            String nazivProizvoda = request.getParameter("nazivProizvoda");
         
            Integer sifraProizvoda = Validate.vratiBroj(proizvodId);
            
       
            StavkaPonude novaStavka =  new StavkaPonude();
            novaStavka.setSifraPonude(Integer.valueOf(ponudaId));
            novaStavka.setSifraProizvoda(Integer.valueOf(sifraProizvoda));
            novaStavka.setKolicina(Integer.valueOf(kolicina));
            novaStavka.setAktuelnaCenaProizvoda(Integer.valueOf(cenaProizvoda));
            novaStavka.setNazivProizvoda(nazivProizvoda);
            novaStavka.setStatusStavke("insert");
            novaStavka.setVidljivo("true");
            
            Integer broj = 0;
            
            for(StavkaPonude sp : aktuelneStavkePonude){
            if(sp.getRbsPonude() > broj){
            broj = sp.getRbsPonude();
            }
           }
              novaStavka.setRbsPonude( broj + 1);
            
            aktuelneStavkePonude.add(novaStavka);
            
     
          Integer ukupnaSuma = 0;
            
          for(StavkaPonude sp : aktuelneStavkePonude){
        
          ukupnaSuma += sp.getKolicina()*sp.getAktuelnaCenaProizvoda();
              
                
            
            }
            
            
            
            response.getWriter().write(new  Gson().toJson(ukupnaSuma));
            
          }
           
           
            if(status.equals("obrisiStavkuPOnudeSaPonude")){
             
              response.setContentType("application/json");
              response.setCharacterEncoding("UTF-8");
              
             String idStavke = request.getParameter("id");
            
             String idStavkePOnude = "" ;
 
             String unutrasnjistring = idStavke.replaceAll("\\d","");
       
             idStavkePOnude = idStavke.substring(0, idStavke.indexOf(unutrasnjistring));
      
             
             Integer ukupnaSuma = 0;
           for(StavkaPonude sp : aktuelneStavkePonude){
        
            if(sp.getRbsPonude().equals(Integer.valueOf(idStavkePOnude))){
                sp.setStatusStavke("delete");
                sp.setVidljivo("false");
            
            }
            
            if(sp.getVidljivo().equals("true")){
            ukupnaSuma += sp.getKolicina() * sp.getAktuelnaCenaProizvoda();
            
            }
            
        }
        
        response.getWriter().write(new Gson().toJson(ukupnaSuma));
        
          }
            
            if(status.equals("updateKolicineNaStavciPonude")){
             
             
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
             
              String kolicina = request.getParameter("kolicina");
              String idStavkePon = request.getParameter("idrbs");
              
             Integer  idStavke = Validate.vratiBroj(idStavkePon);
              
               Integer suma = 0;
             
               for(StavkaPonude sp : aktuelneStavkePonude){
             
                 suma +=sp.getAktuelnaCenaProizvoda() * sp.getKolicina();
                
                 if(sp.getRbsPonude().equals(idStavke)){
                 
                     sp.setKolicina(Integer.valueOf(kolicina));
                     sp.setStatusStavke("update");
                 
                 }
             
                 
             }
             
              
              
           response.getWriter().write(new Gson().toJson(suma));
                 
             }
           
            
            
            
    
    
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
