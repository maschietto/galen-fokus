package ViewModels;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyleConstants;
import util.DB_broker;

public class ZaposleniDetalji {

    
    private Integer sifra_radnika ;
    private String ime ;
    private String prezime ;
    private String jmbg ;
    private String telefon ;
    private String email ;
    private Integer sifra_funkcije ;
    private String status;
    private String isadmin ;
    private String sifra ;

 
    private String nazivFunkcije ;
    private String poruka ;
    private Integer brojac;
    private Integer porukaId;
    private String nazivAkcija;

    private Integer brojStrane;

    public Integer getBrojStrane() {
        return brojStrane;
    }

    public void setBrojStrane(Integer brojStrane) {
        this.brojStrane = brojStrane;
    }
    
    public String getNazivAkcija() {
        return nazivAkcija;
    }

    public void setNazivAkcija(String nazivAkcija) {
        this.nazivAkcija = nazivAkcija;
    }

    public Integer getPorukaId() {
        return porukaId;
    }

    public void setPorukaId(Integer porukaId) {
        this.porukaId = porukaId;
    }

    
    
    public Integer getBrojac() {
        return brojac;
    }

    public void setBrojac(Integer brojac) {
        this.brojac = brojac;
    }
    
    public String getNazivFunkcije() {
        return nazivFunkcije;
    }

    public void setNazivFunkcije(String nazivFunkcije) {
        this.nazivFunkcije = nazivFunkcije;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public Integer getSifra_radnika() {
        return sifra_radnika;
    }

    public void setSifra_radnika(Integer sifra_radnika) {
        this.sifra_radnika = sifra_radnika;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSifra_funkcije() {
        return sifra_funkcije;
    }

    public void setSifra_funkcije(Integer sifra_funkcije) {
        this.sifra_funkcije = sifra_funkcije;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public ZaposleniDetalji setFromResultSet(ResultSet rs) {
        try {
            setSifra_radnika(rs.getInt(1));
            setIme(rs.getString(2));
            setPrezime(rs.getString(3));
            setJmbg(rs.getString(4));
            setTelefon(rs.getString(5));
            setEmail(rs.getString(6));
            setSifra_funkcije(rs.getInt(7));
            setIsadmin(rs.getString(8));
            setStatus(rs.getString(9));
            setSifra(rs.getString(10));
            setNazivFunkcije(rs.getString(11));
            setPoruka(rs.getString(12));
            setBrojac(rs.getInt(13));
            setPorukaId(rs.getInt(14));
            setNazivAkcija(rs.getString(15));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

   public static List<ZaposleniDetalji> findByParameter(String parameterName, Object parameterValue, Integer pocetak, Integer inkrement) throws SQLException {
        try {
            String SQL = "SELECT Z.SIFRA_RADNIKA, Z.IME, Z.PREZIME, ZD.JMBG, ZD.TELEFON, ZD.EMAIL, ZD.SIFRA_FUNKCIJE, ZD.IS_ADMIN, ZD.STATUS,ZD.SIFRA, C.NAZIV_FUNKCIJE, P.PORUKA, P.BROJAC,P.PORUKA_ID, P.NAZIV_AKCIJE FROM ZAPOSLENI Z, ZAPOSLENI_DETALJI ZD, FUNKCIJA C, PORUKA P WHERE Z.SIFRA_RADNIKA = ZD.SIFRA_RADNIKA AND ZD.SIFRA_FUNKCIJE = C.SIFRA_FUNKCIJE  AND Z.SIFRA_RADNIKA = P.SIFRA_RADNIKA";
            
            if(pocetak != null && inkrement != null){
                    SQL += " AND z.sifra_radnika >= ? AND z.sifra_radnika <= ?";
            }
            if (parameterName != null) {
                SQL += " AND " + parameterName + " =?";
            } else {
                SQL += " ORDER BY Z.SIFRA_RADNIKA";
            }
            
            
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
             
            if (pocetak != null && inkrement != null) {
                stat.setObject(1, pocetak);
                stat.setObject(2, inkrement);
            }
             
             if (parameterValue != null && pocetak != null && inkrement != null ) {
                stat.setObject(3, parameterValue);
            }
            
             if(parameterValue != null && pocetak == null && inkrement == null ){
                
                stat.setObject(1, parameterValue);
             
             }
           
            ResultSet rs = stat.executeQuery();
            List<ZaposleniDetalji> lista = new ArrayList<ZaposleniDetalji>();
            ZaposleniDetalji korisnik;
            while (rs.next()) {
                korisnik = new ZaposleniDetalji();
                korisnik.setFromResultSet(rs);
                lista.add(korisnik);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
   
   public static List<ZaposleniDetalji> findByAutoComplete(String name, Object nameParam , Object imeParam, Object prezimeParam, Object emailParam) throws SQLException {
        try {
            String SQL = "SELECT Z.SIFRA_RADNIKA, Z.IME, Z.PREZIME, ZD.JMBG, ZD.TELEFON, ZD.EMAIL, ZD.SIFRA_FUNKCIJE, ZD.IS_ADMIN, ZD.STATUS,ZD.SIFRA, C.NAZIV_FUNKCIJE, P.PORUKA, P.BROJAC,P.PORUKA_ID, P.NAZIV_AKCIJE FROM ZAPOSLENI Z, ZAPOSLENI_DETALJI ZD, FUNKCIJA C, PORUKA P WHERE Z.SIFRA_RADNIKA = ZD.SIFRA_RADNIKA AND ZD.SIFRA_FUNKCIJE = C.SIFRA_FUNKCIJE  AND Z.SIFRA_RADNIKA = P.SIFRA_RADNIKA";
            if(name != null){
            SQL += " AND " + name + " LIKE ?";
            }else{
            if( imeParam  != null && prezimeParam != null && emailParam != null){
            SQL += " AND Z.IME = '" + imeParam + "'  AND Z.PREZIME = '" + prezimeParam + "' AND ZD.EMAIL = '"+ emailParam + "' "; 
            }
            if( imeParam != null && prezimeParam == null && emailParam == null){
             SQL += " AND Z.IME = '" + imeParam + "' ";   
            }
            if( imeParam != null && prezimeParam != null && emailParam == null){
                SQL += " AND Z.IME = '" + imeParam + "' AND Z.PREZIME = '" + prezimeParam + "'";
            }
            if( imeParam != null && prezimeParam == null && emailParam != null){
            SQL += " AND Z.IME = '" + imeParam + "' AND ZD.EMAIL = '" + emailParam + "' " ;
            }
            if( imeParam == null && prezimeParam != null && emailParam == null){
                SQL += " AND Z.PREZIME = '" + prezimeParam + "' ";
            }
            if( imeParam == null && prezimeParam != null && emailParam != null){
                SQL += " AND Z.PREZIME = '" + prezimeParam + "' AND ZD.EMAIL = '" + emailParam + "' ";
            }
            if( imeParam == null && prezimeParam == null && emailParam != null){
                SQL += " AND ZD.EMAIL = '" + emailParam + "' ";
            }
            }
           
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            
            if (nameParam != null) {
                stat.setObject(1, nameParam + "%");
            }
            ResultSet rs = stat.executeQuery();
            List<ZaposleniDetalji> lista = new ArrayList<ZaposleniDetalji>();
            ZaposleniDetalji korisnik;
            while (rs.next()) {
                korisnik = new ZaposleniDetalji();
                korisnik.setFromResultSet(rs);
                lista.add(korisnik);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

  

    
    public static ZaposleniDetalji findByUsername(String username, String password) throws SQLException {
        ZaposleniDetalji korisnik = null;
        ZaposleniDetalji objekat = null;
        String SQL = "SELECT * FROM ZAPOSLENI Z JOIN ZAPOSLENI_DETALJI ZD ON Z.SIFRA_RADNIKA=ZD.SIFRA_RADNIKA WHERE IME=? ";
        if (password != null) {
            SQL += " AND SIFRA=?";
        }
        Connection con = DB_broker.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setString(1, username);
        if (password != null) {
            ps.setString(2, password);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            //korisnik.setFromResultSet(rs);

            objekat = korisnik.setFromResultSet(rs);
        }
        rs.close();
        ps.close();
        return objekat;
    }

    public static ZaposleniDetalji findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<ZaposleniDetalji> korisnici = findByParameter(parameterName, parameterValue, null , null);
         if (korisnici.size() > 0) {
            return korisnici.get(0);
        } else {
            return null;
        }
    }

    public static ZaposleniDetalji findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("Z.SIFRA_RADNIKA", id);
    }

    public static List<ZaposleniDetalji> findAll() throws SQLException {
        return findByParameter(null, null, null, null);
    }

    
}
