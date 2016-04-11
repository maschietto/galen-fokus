package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import util.DB_broker;

public class Funkcija {

    
    
  private Integer sifraFunkcije;
  private String nazivFunckije;

    public Integer getSifraFunkcije() {
        return sifraFunkcije;
    }

    public String getNazivFunckije() {
        return nazivFunckije;
    }

    public void setSifraFunkcije(Integer sifraFunkcije) {
        this.sifraFunkcije = sifraFunkcije;
    }

    public void setNazivFunckije(String nazivFunckije) {
        this.nazivFunckije = nazivFunckije;
    }
  
  
  
  
  public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1, sifraFunkcije);
            ps.setString(2, nazivFunckije);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsForUpdate(PreparedStatement ps) {
        try {
             ps.setString(2, nazivFunckije);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            String SQL = "INSERT INTO FUNKCIJA (SIFRA_FUNKCIJE, NAZIV_FUNKCIJE) VALUES (?,?)";
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
            String SQL = "UPDATE FUNKCIJA SET NAZIV_FUNKCIJE =? WHERE SIFRA_FUNKCIJE=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsForUpdate(statement);
            statement.setInt(2, sifraFunkcije);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

  

    public void delete() throws SQLException {

        String SQL = "DELETE FROM FUNKCIJA WHERE SIFRA_FUNKCIJE=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifraFunkcije);
        stat.executeUpdate();
        stat.close();

    }

}
