
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DB_broker;
import util.Validate;

public class Ponuda {
    
    private Integer sifraPonude;
    private String datum;
    private Integer sifraRadnika;
    private Integer ukupnaSuma;
    private Integer kupacId;
    
    //Za pretragu
   private String pib;
   private String imeKupca;
   
   List<StavkaPonude> listaStavki;
 //  Kupac k ;

    public List<StavkaPonude> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List<StavkaPonude> listaStavki) {
        this.listaStavki = listaStavki;
    }

//    public Kupac getK() {
//        return k;
//    }
//
//    public void setK(Kupac k) {
//        this.k = k;
//    }
//   
    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getImeKupca() {
        return imeKupca;
    }

    public void setImeKupca(String imeKupca) {
        this.imeKupca = imeKupca;
    }
   

    public Integer getSifraPonude() {
        return sifraPonude;
    }

    public void setSifraPonude(Integer sifraPonude) {
        this.sifraPonude = sifraPonude;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Integer getSifraRadnika() {
        return sifraRadnika;
    }

    public void setSifraRadnika(Integer sifraRadnika) {
        this.sifraRadnika = sifraRadnika;
    }

    public Integer getUkupnaSuma() {
        return ukupnaSuma;
    }

    public void setUkupnaSuma(Integer ukupnaSuma) {
        this.ukupnaSuma = ukupnaSuma;
    }

    public Integer getKupacId() {
        return kupacId;
    }

    public void setKupacId(Integer kupacId) {
        this.kupacId = kupacId;
    }
    
    
    
    public Ponuda() {
    }
 
    //KONSTRUKTOR SA ARGUMENTIMA:
    public Ponuda(Ponuda p , List<StavkaPonude> spList) {
        this.sifraPonude = p.getSifraPonude();
        this.datum = p.getDatum();
        this.sifraRadnika = p.getSifraRadnika();
        this.ukupnaSuma = p.getUkupnaSuma();
        this.kupacId = p.getKupacId();
        this.pib = p.getPib();
        this.imeKupca = p.getImeKupca();
        this.listaStavki =  spList;
       
    }
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //ZA RAD SA BAZOM
//      public String updateZaGresku(Integer suma, Integer ponudaId) {
//       String poruka = "";
//          try {
//            String SQL = "UPDATE PONUDA  SET UKUPNA_SUMA =" + suma + " WHERE SIFRA_PONUDE=" + ponudaId + "";
//            Connection connection = DB_broker.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            statement.executeUpdate();
//            statement.close();
//            //connection.close();
//        } catch (SQLException ex) {
//           
//            if (ex.getErrorCode() == 20000) {
//
//                poruka = Validate.pokupiPoruku(ex.getMessage());
//            
//            } else {
//                ex.printStackTrace();
//            }
//        }
//          return poruka;
//    }
    
}
