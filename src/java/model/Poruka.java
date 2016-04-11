package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyleConstants;
//import oracle.sql.TIMESTAMP;
import util.DB_broker;

public class Poruka {

    
    
  private Integer porukaId;
  private String poruka;
  private Integer sifraRadnika;
  private Integer brojac;
  private String vreme;
  private String nazivAkcije;

    public String getNazivAkcije() {
        return nazivAkcije;
    }

    public void setNazivAkcije(String nazivAkcije) {
        this.nazivAkcije = nazivAkcije;
    }
  
   public Integer getPorukaId() {
        return porukaId;
    }

    public void setPorukaId(Integer porukaId) {
        this.porukaId = porukaId;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public Integer getSifraRadnika() {
        return sifraRadnika;
    }

    public void setSifraRadnika(Integer sifraRadnika) {
        this.sifraRadnika = sifraRadnika;
    }

    public Integer getBrojac() {
        return brojac;
    }

    public void setBrojac(Integer brojac) {
        this.brojac = brojac;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

  
   public Poruka setFromResultSet(ResultSet rs) {
        try {
            setPorukaId(rs.getInt(1));
            setPoruka(rs.getString(2));
            setSifraRadnika(rs.getInt(3));
            setBrojac(rs.getInt(4));
            setVreme(rs.getString(5));
            setNazivAkcije(rs.getString(6));
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1, porukaId);
            ps.setString(2, poruka);
            ps.setInt(3,sifraRadnika );
            ps.setInt(4, brojac);
            ps.setString(5, vreme);
            ps.setString(6, nazivAkcije);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsForUpdate(PreparedStatement ps) {
        try {
            ps.setString(1, poruka);
            ps.setInt(2,sifraRadnika );
            ps.setInt(3, brojac);
            ps.setString(4, vreme);
            ps.setString(5,nazivAkcije);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void save() throws SQLException {
       
            String SQL = "INSERT INTO PORUKA (PORUKA_ID, PORUKA, SIFRA_RADNIKA, BROJAC, VREME, NAZIV_AKCIJE) VALUES (?,?,?,?,TO_TIMESTAMP(?, 'DD.MM.YYYY HH24:MI:SS'),?)";
            Connection con = DB_broker.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            setStatementParams(ps);
            ps.executeUpdate();
            ps.close();
        
    }

    public void update() {
        try {
            String SQL = "UPDATE PORUKA SET PORUKA=?, SIFRA_RADNIKA=?, BROJAC=?,  VREME=TO_TIMESTAMP(?,'DD.MM.YYYY HH24:MI:SS'), NAZIV_AKCIJE=? WHERE PORUKA_ID=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsForUpdate(statement);
            statement.setInt(6, porukaId);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

  

    public void delete() throws SQLException {

        String SQL = "DELETE FROM PORUKA WHERE PORUKA_ID=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, porukaId);
        stat.executeUpdate();
        stat.close();

    }

    
    
    
    
    public static List<Poruka> findPoslednjaPoruka() throws SQLException {
        try {
            String SQL = "SELECT * FROM PORUKA WHERE PORUKA_ID = (SELECT MAX(PORUKA_ID) FROM PORUKA)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

            Poruka zap;
            List<Poruka> lista = new ArrayList<Poruka>();
            while (rs.next()) {
                zap = new Poruka();
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

    public static Poruka findPOslendjuPoruku() {
        List<Poruka> lista = null;
        try {
            lista = findPoslednjaPoruka();
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
