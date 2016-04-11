package model;

import ViewModels.ProizvodDetalji;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB_broker;

public class CenaProizvoda {

    private String datum;
    private Integer sifra_proizvoda;
    private Integer iznos;

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Integer getSifra_proizvoda() {
        return sifra_proizvoda;
    }

    public void setSifra_proizvoda(Integer sifra_proizvoda) {
        this.sifra_proizvoda = sifra_proizvoda;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    public CenaProizvoda setFromResultSet(ResultSet rs) {
        try {   
            
            setIznos(rs.getInt(1));
            setDatum(rs.getString(2));
            setSifra_proizvoda(rs.getInt(3));
  
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setString(1, datum);
            ps.setInt(2, sifra_proizvoda);
            ps.setInt(3, iznos);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsforUpdate(PreparedStatement ps) {
        try {

            ps.setInt(1, iznos);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveProizvod() {
        try {
            String SQL = "INSERT INTO CENA_PROIZVODA (DATUM, SIFRA_PROIZVODA, IZNOS) VALUES (TO_DATE(?, 'DD.MM.YYYY'),?,?)";
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
            String SQL = "UPDATE CENA_PROIZVODA  SET IZNOS=? WHERE SIFRA_PROIZVODA=? AND DATUM = TO_DATE(?, 'DD.MM.YYYY')";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(2, sifra_proizvoda);
            statement.setString(3, datum);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws SQLException {

        String SQL = "DELETE FROM CENA_PROIZVODA WHERE SIFRA_PROIZVODA=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifra_proizvoda);
        stat.executeUpdate();
        stat.close();
    }

    public static CenaProizvoda izvadiPoslednjiDatum(Integer sifra) throws SQLException {

        String SQL = "SELECT * FROM CENA_PROIZVODA WHERE SIFRA_PROIZVODA=" + sifra + " AND DATUM = (SELECT MAX(DATUM)FROM CENA_PROIZVODA WHERE SIFRA_PROIZVODA=" + sifra + ") ";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);

        ResultSet rs = stat.executeQuery();

        CenaProizvoda proizvod = null;

        while (rs.next()) {

            proizvod = new CenaProizvoda();
            proizvod.setFromResultSet(rs);

        }
        rs.close();
        stat.close();
        con.commit();

        return proizvod;

    }
    
    
   public static String azuriranjeAktuelneCene(Integer sifraProizvoda){
       String vrati = "";
       try {
            Connection con = DB_broker.getConnection();
            CallableStatement cstm = con.prepareCall("{call AKTUELNACENA_PROCEDURE(?)}");
            cstm.setInt(1,sifraProizvoda);
            cstm.execute();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
   return vrati;
   }

}
