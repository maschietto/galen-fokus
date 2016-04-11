package model;

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

public class Zaposleni {

    private Integer sifra_radnika;
    private String ime;
    private String prezime;
    private String jmbg;
    private String telefon;
    private String email;
    private Integer sifra_funkcije;
    private String status;
    private String isadmin;
    private String sifra;

    //samo promenljive za rad sa klasom, za skupljanje podataka iz baze
    private String nazivFunkcije;
    private String poruka;
    private String brojac;

    public String getBrojac() {
        return brojac;
    }

    public void setBrojac(String brojac) {
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

    public Zaposleni setFromResultSet(ResultSet rs) {
        try {
            setSifra_radnika(rs.getInt(1));
            setIme(rs.getString(2));
            setPrezime(rs.getString(3));
            setJmbg(rs.getString(4));
            setTelefon(rs.getString(5));
            setEmail(rs.getString(6));
            setSifra_funkcije(rs.getInt(7));
            setStatus(rs.getString(8));
            setIsadmin(rs.getString(9));
            setSifra(rs.getString(10));
            setNazivFunkcije(rs.getString(11));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1, sifra_radnika);
            ps.setString(2, ime);
            ps.setString(3, prezime);
            ps.setString(4, jmbg);
            ps.setString(5, telefon);
            ps.setString(6, email);
            ps.setInt(7, sifra_funkcije);
            ps.setString(8, status);
            ps.setString(9, isadmin);
            ps.setString(10, sifra);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsForUpdate(PreparedStatement ps) {
        try {

            ps.setString(1, ime);
            ps.setString(2, prezime);
            ps.setString(3, jmbg);
            ps.setString(4, telefon);
            ps.setString(5, email);
            ps.setInt(6, sifra_funkcije);
            ps.setString(7, status);
            ps.setString(8, isadmin);
            ps.setString(9, sifra);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveZaposleni() throws SQLException {
      
            String SQL = "INSERT INTO ZAPOSLENI_VIEW(SIFRA_RADNIKA, IME, PREZIME, JMBG, TELEFON, EMAIL, SIFRA_FUNKCIJE,STATUS, IS_ADMIN, SIFRA) VALUES (?,?,?,?,?,?,?,?,?,?)";
            Connection con = DB_broker.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            setStatementParams(ps);
            ps.executeUpdate();
            ps.close();
            
       
    }

    public void update() throws SQLException {
        
            String SQL = "UPDATE ZAPOSLENI_VIEW SET IME=?, PREZIME=?, JMBG=?, TELEFON=?,"
                    + " EMAIL=?, SIFRA_FUNKCIJE=?, STATUS=?, IS_ADMIN=?, SIFRA=?  WHERE SIFRA_RADNIKA=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsForUpdate(statement);
            statement.setInt(10, sifra_radnika);
            statement.executeUpdate();
            statement.close();
            //connection.close();
         
    }

    public static List<Zaposleni> findByParameter(String parameterName, Object parameterValue) {
        try {
            String SQL = "SELECT Z.SIFRA_RADNIKA, Z.IME, Z.PREZIME, ZD.JMBG, ZD.TELEFON, ZD.EMAIL, ZD.SIFRA_FUNKCIJE, ZD.IS_ADMIN, ZD.STATUS,ZD.SIFRA, C.NAZIV_FUNKCIJE FROM ZAPOSLENI Z, ZAPOSLENI_DETALJI ZD, FUNKCIJA C WHERE Z.SIFRA_RADNIKA = ZD.SIFRA_RADNIKA AND ZD.SIFRA_FUNKCIJE = C.SIFRA_FUNKCIJE";
            if (parameterName != null) {
                SQL += " AND " + parameterName + " =?";
            } else {
                SQL += " ORDER BY Z.SIFRA_RADNIKA";
            }
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            if (parameterValue != null) {
                stat.setObject(1, parameterValue);
            }
            ResultSet rs = stat.executeQuery();
            List<Zaposleni> lista = new ArrayList<Zaposleni>();
            Zaposleni korisnik;
            while (rs.next()) {
                korisnik = new Zaposleni();
                korisnik.setFromResultSet(rs);
                lista.add(korisnik);
            }
            rs.close();
            stat.close();
            con.commit();
          // con.close();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete() throws SQLException {

        String SQL = "DELETE FROM ZAPOSLENI_VIEW WHERE SIFRA_RADNIKA=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifra_radnika);
        stat.executeUpdate();
        stat.close();

    }

    private void updateStatus(String status) {
        try {
            String SQL = "UPDATE zaposleni_view SET status=? WHERE sifra_radnika=?;";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, status);
            statement.setInt(2, sifra_radnika);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Zaposleni findByUsername(String username, String password) throws SQLException {
        Zaposleni korisnik = null;
        Zaposleni objekat = null;
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

    public static Zaposleni findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<Zaposleni> korisnici = findByParameter(parameterName, parameterValue);
        if (korisnici.size() > 0) {
            return korisnici.get(0);
        } else {
            return null;
        }
    }

    public static Zaposleni findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("Z.SIFRA_RADNIKA", id);
    }

    public static List<Zaposleni> findAll() throws SQLException {
        return findByParameter(null, null);
    }

    public static List<Zaposleni> findPoslednjiZaposleni() throws SQLException {
        try {
            String SQL = "SELECT * FROM ZAPOSLENI WHERE SIFRA_RADNIKA = (SELECT MAX(SIFRA_RADNIKA) FROM ZAPOSLENI)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

            Zaposleni zap;
            List<Zaposleni> lista = new ArrayList<Zaposleni>();
            while (rs.next()) {
                zap = new Zaposleni();
                zap.setFromResultSet(rs);
                lista.add(zap);
            }
            rs.close();
            stat.close();
            return lista;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static Zaposleni findUniquePoslednjiZaposleni() {
        List<Zaposleni> lista = null;
        try {
            lista = findPoslednjiZaposleni();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }

    }
}
