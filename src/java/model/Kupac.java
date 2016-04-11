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

public class Kupac {
    private Integer kupacId;
    private String pib;
    private String imeKupca;
    private Integer mestoId;

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

    public Integer getMestoId() {
        return mestoId;
    }

    public void setMestoId(Integer mestoId) {
        this.mestoId = mestoId;
    }

    public Integer getKupacId() {
        return kupacId;
    }

    public void setKupacId(Integer kupacId) {
        this.kupacId = kupacId;
    }
  

    
    public Kupac setFromResultSet(ResultSet rs) {
        try {
            setKupacId(rs.getInt(1));
            setPib(rs.getString(2));
            setImeKupca(rs.getString(3));
            setMestoId(rs.getInt(4));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setString(1, pib);
            ps.setString(2, imeKupca);
            ps.setInt(3, mestoId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

        public void setStatementParamsforUpdate(PreparedStatement ps) {
        try {
            ps.setString(1, pib);
            ps.setString(2, imeKupca);
            ps.setInt(3, mestoId);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   public void saveKupac() {
        try {
            String SQL = "INSERT INTO KUPAC (KUPAC_ID, PIB_KUPCA, IME_KUPCA, ID_MESTO) VALUES (((select max(kupac_id) from kupac) + 1),?,?,?)";
            Connection con = DB_broker.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            setStatementParams(ps);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
   public void update() {
        try {
            String SQL = "UPDATE KUPAC SET PIB_KUPCA= ?, IME_KUPCA=?, ID_MESTO=?  WHERE KUPAC_ID=?";
            
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(4,kupacId);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
       public void delete() throws SQLException {

        String SQL = "DELETE FROM KUPAC WHERE KUPAC_ID =?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, kupacId);
        stat.executeUpdate();
        stat.close();
  }
       
        public static List<Kupac> findByParameter() throws SQLException {
        try {
            String SQL = "SELECT DISTINCT IME_KUPCA FROM ALEKSA.KUPAC ";
           
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);

            ResultSet rs = stat.executeQuery();
            List<Kupac> lista = new ArrayList<Kupac>();
            Kupac jm;

            while (rs.next()) {

                jm = new Kupac();
              
                jm.setImeKupca(rs.getString(1));
             
              
                lista.add(jm);
            }
            rs.close();
            stat.close();
            con.commit();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
       
        
        
        
        
       
}
       
