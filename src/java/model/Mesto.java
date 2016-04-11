
package model;

import ViewModels.KupacDetalji;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DB_broker;


public class Mesto {
    
    private Integer mestoId;
    private String adresa;
    private String grad;

    public Integer getMestoId() {
        return mestoId;
    }

    public void setMestoId(Integer mestoId) {
        this.mestoId = mestoId;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }
    
     public Mesto setFromResultSet(ResultSet rs) {
        try {
            setMestoId(rs.getInt(1));
            setAdresa(rs.getString(2));
            setGrad(rs.getString(3));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try { 
           ps.setInt(1, mestoId);
            ps.setString(2, adresa);
            ps.setString(3, grad);
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

        public void setStatementParamsforUpdate(PreparedStatement ps) {
        try { 
            //ps.setInt(1,mestoId);
            ps.setString(1, adresa);
            ps.setString(2, grad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   public void saveMesto() {
        try {
            String SQL = "INSERT INTO MESTO (ID_MESTO, ADRESA, GRAD) VALUES (?, ? , ?)";
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
            String SQL = "UPDATE MESTO SET ADRESA = ?, GRAD = ? WHERE ID_MESTO=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(3,mestoId);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
       public void delete() throws SQLException {

        String SQL = "DELETE FROM MESTO WHERE ID_MESTO=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, mestoId);
        stat.executeUpdate();
        stat.close();
  }
    
    public static Mesto findPoslednjeMestoUBazi() throws SQLException {
        Mesto zap = null;
        try {
            String SQL = "SELECT * FROM MESTO WHERE ID_MESTO = (SELECT MAX(ID_MESTO) FROM MESTO)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

           
           
            while (rs.next()) {
                zap = new Mesto();
                zap.setFromResultSet(rs);
           
            }
            
            rs.close();
            stat.close();
      
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        
        return zap;
    }
    
       
       
       
    
}
