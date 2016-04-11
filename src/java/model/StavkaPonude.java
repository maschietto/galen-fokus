package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DB_broker;
import util.Validate;

public class StavkaPonude {

    private Integer rbsPonude;
    private Integer sifraPonude;
    private String datum;
    private Integer kolicina;
    private Integer sifraProizvoda;
    private String nazivProizvoda;
    
    private String statusStavke;

    //zaFrontEnd
   private Integer aktuelnaCenaProizvoda;
   private String vidljivo;

    public String getVidljivo() {
        return vidljivo;
    }

    public void setVidljivo(String vidljivo) {
        this.vidljivo = vidljivo;
    }
 
   public Integer getAktuelnaCenaProizvoda() {
        return aktuelnaCenaProizvoda;
    }

    public void setAktuelnaCenaProizvoda(Integer aktuelnaCenaProizvoda) {
        this.aktuelnaCenaProizvoda = aktuelnaCenaProizvoda;
    }
   
   public String getStatusStavke() {
        return statusStavke;
    }

    public void setStatusStavke(String statusStavke) {
        this.statusStavke = statusStavke;
    }

    public Integer getRbsPonude() {
        return rbsPonude;
    }

    public void setRbsPonude(Integer rbsPonude) {
        this.rbsPonude = rbsPonude;
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

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getSifraProizvoda() {
        return sifraProizvoda;
    }

    public void setSifraProizvoda(Integer sifraProizvoda) {
        this.sifraProizvoda = sifraProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

//    public StavkaPonude setFromResultSet(ResultSet rs) {
//        try {
//            setDatum(rs.getString(1));
//            setSifraPonude(rs.getInt(2));
//            setKolicina(rs.getInt(3));
//            setSifraProizvoda(rs.getInt(4));
//            setRbsPonude(rs.getInt(5));
//            setNazivProizvoda(rs.getString(6));
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return this;
//    }
//
//    public void setStatementParams(PreparedStatement ps) {
//        try {
//
//            ps.setInt(1, rbsPonude);
//            ps.setInt(2, sifraPonude);
//            ps.setInt(3, kolicina);
//            ps.setInt(4, sifraProizvoda);
//            //ps.setString(6, nazivProizvoda);
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void setStatementParamsforUpdate(PreparedStatement ps) {
//        try {
//            ps.setInt(1, sifraPonude);
//            ps.setString(2, datum);
//            ps.setInt(3, kolicina);
//            ps.setInt(4, sifraProizvoda);
//            ps.setString(5, nazivProizvoda);
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void saveStavkuPonude() {
//        try {
//            String SQL = "INSERT INTO STAVKE_PONUDE (RBS_PONUDE, SIFRA_PONUDE, KOLICINA, SIFRA_PROIZVODA) VALUES (?,?,?,?)";
//            Connection con = DB_broker.getConnection();
//            PreparedStatement ps = con.prepareStatement(SQL);
//            setStatementParams(ps);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public String update() {
//        String vrednost = "";
//        try {
//            String SQL = "UPDATE STAVKE_PONUDE  SET  DATUM = TO_DATE(?,'DD.MM.YYYY'), SIFRA_PONUDE= ?, KOLICINA= ?, SIFRA_PROIZVODA= ?, NAZIV_PROIZVODA= ? WHERE RBS_PONUDE=?";
//            Connection connection = DB_broker.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SQL);
//            setStatementParamsforUpdate(statement);
//            statement.setInt(6, rbsPonude);
//            statement.executeUpdate();
//            statement.close();
//            //connection.close();
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 20000) {
//
//                vrednost = Validate.pokupiPoruku(ex.getMessage());
//            } else {
//                ex.printStackTrace();
//            }
//        }
//        
//        return vrednost;
//    }
//
//    public void delete() throws SQLException {
//
//        String SQL = "DELETE FROM STAVKE_PONUDE WHERE RBS_PONUDE=? AND SIFRA_PONUDE=?";
//        Connection con = DB_broker.getConnection();
//        PreparedStatement stat = con.prepareStatement(SQL);
//        stat.setInt(1, rbsPonude);
//        stat.setInt(2, sifraPonude);
//        stat.executeUpdate();
//        stat.close();
//    }
//
//    public static List<StavkaPonude> findByParameter(String parameterName, Object parameterValue) throws SQLException {
//        try {
//            String SQL = "SELECT * FROM STAVKE_PONUDE";
//            if (parameterName != null) {
//                SQL += " WHERE " + parameterName + " =?";
//            }
//            Connection con = DB_broker.getConnection();
//            PreparedStatement stat = con.prepareStatement(SQL);
//            if (parameterName != null) {
//                stat.setObject(1, parameterValue);
//            }
//            ResultSet rs = stat.executeQuery();
//            List<StavkaPonude> lista = new ArrayList<StavkaPonude>();
//            StavkaPonude artikal;
//            while (rs.next()) {
//                artikal = new StavkaPonude();
//                artikal.setFromResultSet(rs);
//                lista.add(artikal);
//            }
//            rs.close();
//            stat.close();
//            return lista;
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//    
//        public static StavkaPonude nadjiPoslednjuStavkuPOnude(Object sifraPonude) throws SQLException {
//        try {
//
//            String SQL = "SELECT * FROM STAVKE_PONUDE WHERE RBS_PONUDE = (SELECT MAX(RBS_PONUDE) FROM STAVKE_PONUDE) AND SIFRA_PONUDE = ? ";
//          
//            
//            Connection con = DB_broker.getConnection();
//            PreparedStatement stat = con.prepareStatement(SQL);
//          if(sifraPonude != null){
//                
//                    stat.setObject(1, sifraPonude);
//              
//          }
//            ResultSet rs = stat.executeQuery();
//
//            StavkaPonude jm = null;
//
//            while (rs.next()) {
//                jm = new StavkaPonude();
//                jm.setFromResultSet(rs);
//
//            }
//            rs.close();
//            stat.close();
//
//            return jm;
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new RuntimeException(ex);
//        }
//
//    }
//    
//      public String updateZaGreskeDatum(String ulaz) {
//        String vrednost = "";
//                      
//        PreparedStatement statement = null;
//        
//        try {
//            
//            if(ulaz.equals("datum")){
//            String SQL = "UPDATE STAVKE_PONUDE  SET  DATUM = TO_DATE(?,'DD.MM.YYYY') WHERE RBS_PONUDE = ? and SIFRA_PONUDE = ? ";
//            Connection connection = DB_broker.getConnection();
//            statement = connection.prepareStatement(SQL);
//            }
//            if(ulaz.equals("nazivProizvoda")){
//            String Sql2 = "UPDATE STAVKE_PONUDE  SET  NAZIV_PROIZVODA = ? WHERE RBS_PONUDE = ? and SIFRA_PONUDE = ? ";
//            Connection connection = DB_broker.getConnection();
//            statement = connection.prepareStatement(Sql2);
//            }
//           
//            //setStatementParamsforUpdate(statement);
//            if(ulaz.equals("datum")){statement.setString(1, datum);}
//            if(ulaz.equals("nazivProizvoda")){
//               statement.setString(1, nazivProizvoda);}
//            statement.setInt(2, rbsPonude);
//            statement.setInt(3, sifraPonude);
//            statement.executeUpdate();
//            statement.close();
//            //connection.close();
//        } catch (SQLException ex) {
//            if (ex.getErrorCode() == 20000) {
//
//                vrednost = Validate.pokupiPoruku(ex.getMessage());
//            } else {
//                ex.printStackTrace();
//            }
//        }
//        
//        return vrednost;
//    }

}
