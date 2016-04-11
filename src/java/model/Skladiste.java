package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DB_broker;


public class Skladiste {

    private Integer sifraSkladista;
    private String nazivSkladista;
    private Integer mestoId;

    public Integer getSifraSkladista() {
        return sifraSkladista;
    }

    public void setSifraSkladista(Integer sifraSkladista) {
        this.sifraSkladista = sifraSkladista;
    }

    public String getNazivSkladista() {
        return nazivSkladista;
    }

    public void setNazivSkladista(String nazivSkladista) {
        this.nazivSkladista = nazivSkladista;
    }

    public Integer getMestoId() {
        return mestoId;
    }

    public void setMestoId(Integer mestoId) {
        this.mestoId = mestoId;
    }
    
    
     public Skladiste setFromResultSet(ResultSet rs) {
        try {
            setSifraSkladista(rs.getInt(1));
            setNazivSkladista(rs.getString(2));
            setMestoId(rs.getInt(3));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1, sifraSkladista);
            ps.setString(2, nazivSkladista);
            ps.setInt(3, mestoId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

        public void setStatementParamsforUpdate(PreparedStatement ps) {
        try {
            ps.setString(1, nazivSkladista);
            ps.setInt(2, mestoId);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   public void saveSkladiste() {
        try {
            String SQL = "INSERT INTO SKLADISTE (SIFRA_SKLADISTA, NAZIV, ID_MESTO) VALUES (?,?,?)";
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
            String SQL = "UPDATE SKLADISTE SET NAZIV= ?, ID_MESTO=?  WHERE SIFRA_SKLADISTA=?";
            
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(3,sifraSkladista);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
       public void delete() throws SQLException {

        String SQL = "DELETE FROM SKLADISTE WHERE SIFRA_SKLADISTA =?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifraSkladista);
        stat.executeUpdate();
        stat.close();
  }
    
    
    
}
