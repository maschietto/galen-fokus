package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Zaposleni;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Ponuda;
import model.Proizvod;
import model.StavkaPonude;
import static model.Zaposleni.findByParameter;


public class DB_broker {

    static Connection conn = null;

    public static Connection getConnection()  {
        try {
            if (conn == null) {
           
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Properties prop = new Properties();
                InputStream is = DB_broker.class.getResourceAsStream("db.properties");
                prop.load(is);
                String url = prop.getProperty("url");
                String username = prop.getProperty("username");
                String password = prop.getProperty("password");
                conn = DriverManager.getConnection(url, username, password);
      
            }
        } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection()  {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

   
    public static void commitTransaction() {
        try {
            conn.commit();
          //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void rollbackTransaction() {
        try {
            conn.rollback();
          //  conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //CUVANJE PONUDE:
     public static void savePonuda(Ponuda p, Connection con) throws SQLException{
       
         String SQL = "INSERT INTO PONUDA (SIFRA_PONUDE, DATUM, SIFRA_RADNIKA,UKUPNA_SUMA, KUPAC_ID) VALUES (?,TO_DATE(?,'DD.MM.YYYY'),?,?,?)";
 
            PreparedStatement ps = con.prepareStatement(SQL);
            
            setStatementParams(ps, p);
            ps.executeUpdate();
            ps.close();
            
            //UGNJEZDENA FUNKCIJA ZA RAD SA STAVKAMA PONUDE:
            manipulacijaStavkamaPonude(p.getListaStavki(), con);
            
       }
     
     
  //UPDATE PONUDE:
   public static void updatePonuda(Ponuda p, Connection connection) throws SQLException{
    
            String SQL = "UPDATE PONUDA  SET  DATUM = TO_DATE(?,'DD.MM.YYYY'), SIFRA_RADNIKA=?,  KUPAC_ID=? WHERE SIFRA_PONUDE=?";
            
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement, p);
            statement.setInt(4,p.getSifraPonude());
            statement.executeUpdate();
            statement.close();
       
         //UGNJEZDENA FUNKCIJA ZA RAD SA STAVKAMA PONUDE:
            manipulacijaStavkamaPonude(p.getListaStavki(), connection);
      
       
    }

    public static void manipulacijaStavkamaPonude(List<StavkaPonude> stavka, Connection con) throws SQLException {

        for (StavkaPonude sp : stavka) {
            if (!sp.getStatusStavke().equals("")) {
                String SQL = "";
                if (sp.getStatusStavke().equals("insert")) {
                    SQL = "INSERT INTO STAVKE_PONUDE (RBS_PONUDE, SIFRA_PONUDE, KOLICINA, SIFRA_PROIZVODA) VALUES (" + sp.getRbsPonude() + "," + sp.getSifraPonude() + "," + sp.getKolicina() + "," + sp.getSifraProizvoda() + " )";
                }
                if (sp.getStatusStavke().equals("update")) {
                    SQL = "UPDATE STAVKE_PONUDE  SET KOLICINA= " + sp.getKolicina() + " WHERE RBS_PONUDE=" + sp.getRbsPonude() + " AND SIFRA_PONUDE = " + sp.getSifraPonude() + "";
                }
                if (sp.getStatusStavke().equals("delete")) {
                    SQL = "DELETE FROM STAVKE_PONUDE WHERE RBS_PONUDE=" + sp.getRbsPonude() + " AND SIFRA_PONUDE=" + sp.getSifraPonude() + "";
                }

                PreparedStatement ps = con.prepareStatement(SQL);
                ps.executeUpdate();
                ps.close();
            }
        }
    }

       public static void deletePonuda(Ponuda ponuda) throws SQLException {

        String SQL = "DELETE FROM PONUDA WHERE SIFRA_PONUDE=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, ponuda.getSifraPonude());
        stat.executeUpdate();
        stat.close();
  }
         
       public static Ponuda setFromResultSet(ResultSet rs, Ponuda p) {
        try {
            p.setSifraPonude(rs.getInt(1));
            p.setDatum(rs.getString(2));
            p.setSifraRadnika(rs.getInt(3));
            p.setUkupnaSuma(rs.getInt(4));
            p.setKupacId(rs.getInt(5));
            p.setPib(rs.getString(6));
            p.setImeKupca(rs.getString(7));
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }
     
     public static Ponuda setFromResultSet2(ResultSet rs, Ponuda p) {
        try {
            
            p.setSifraPonude(rs.getInt(1));
            p.setDatum(rs.getString(2));
            p.setSifraRadnika(rs.getInt(3));
            p.setUkupnaSuma(rs.getInt(4));
            p.setKupacId(rs.getInt(5));
         
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }
     

    public static void setStatementParams(PreparedStatement ps, Ponuda p) {
        try {
            ps.setInt(1,p.getSifraPonude());
            ps.setString(2, p.getDatum());
            ps.setInt(3,  p.getSifraRadnika());
            ps.setInt(4, p.getUkupnaSuma());
            ps.setInt(5, p.getKupacId());
          
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

        public static void setStatementParamsforUpdate(PreparedStatement ps, Ponuda p) {
        try {
             ps.setString(1, p.getDatum());
             ps.setInt(2, p.getSifraRadnika());
             ps.setInt(3, p.getKupacId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
  
       
       public static List<Ponuda> findByParameter(String parameterName, Object parameterValue,List<Ponuda> listaPonuda){
        try {
            String SQL = "SELECT * from PONUDA p join KUPAC k ON (p.kupac_id = k.kupac_id) ";
           if(parameterName != null){
             SQL += " AND " + parameterName + " =? " ;
           }
           
             Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            if (parameterValue != null){
            stat.setObject(1, parameterValue);
             }
            ResultSet rs = stat.executeQuery();
            Ponuda sp;
            
            while (rs.next()) {

               sp = new Ponuda();
               DB_broker.setFromResultSet(rs, sp);
                listaPonuda.add(sp);
            }
            rs.close();
            stat.close();
            con.commit();

            return listaPonuda;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
       
        public static Ponuda findPoslednjuPonuduUBazi(Ponuda p)  {
     
        try {
            String SQL = "SELECT * FROM PONUDA WHERE SIFRA_PONUDE = (SELECT MAX(SIFRA_PONUDE) FROM PONUDA)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();
           
              while (rs.next()) {
                
           
                  p =  new Ponuda();
                  p = DB_broker.setFromResultSet2(rs, p);
            }
            
            rs.close();
            stat.close();
      
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        
        return p;
    }
       
 public static Ponuda findUnique(String parameterName, Object parameterValue, List<Ponuda> lista) throws SQLException {
        List<Ponuda> ponuda = findByParameter(parameterName,parameterValue, lista );
        if (ponuda.size() > 0) {
            return ponuda.get(0);
        } else {
            return null;
        }
    }

    public static Ponuda findByPrimaryKey(Integer id, List<Ponuda> list) throws SQLException {
        return findUnique("P.SIFRA_PONUDE", id, list);
        
    }
    


    public void setStatementParams(PreparedStatement ps, StavkaPonude sp) {
        try {

            ps.setInt(1, sp.getRbsPonude());
            ps.setInt(2, sp.getSifraPonude());
            ps.setInt(3, sp.getKolicina());
            ps.setInt(4, sp.getSifraProizvoda());
            //ps.setString(6, nazivProizvoda);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsforUpdateSp(PreparedStatement ps, StavkaPonude sp) {
        try {
            ps.setInt(1, sp.getSifraPonude());
            ps.setString(2, sp.getDatum());
            ps.setInt(3, sp.getKolicina());
            ps.setInt(4, sp.getSifraProizvoda());
            ps.setString(5, sp.getNazivProizvoda());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveStavkuPonude(StavkaPonude sp) {
        try {
            String SQL = "INSERT INTO STAVKE_PONUDE (RBS_PONUDE, SIFRA_PONUDE, KOLICINA, SIFRA_PROIZVODA) VALUES (?,?,?,?)";
            Connection con = DB_broker.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            setStatementParams(ps, sp);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String update(Integer rbsPonude, StavkaPonude sp) {
        String vrednost = "";
        try {
            String SQL = "UPDATE STAVKE_PONUDE  SET  DATUM = TO_DATE(?,'DD.MM.YYYY'), SIFRA_PONUDE= ?, KOLICINA= ?, SIFRA_PROIZVODA= ?, NAZIV_PROIZVODA= ? WHERE RBS_PONUDE=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdateSp(statement, sp);
            statement.setInt(6, rbsPonude);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
         ex.printStackTrace();
        }
        return vrednost;
    }

    public void delete(Integer sifraPonude, Integer rbsPonude) throws SQLException {

        String SQL = "DELETE FROM STAVKE_PONUDE WHERE RBS_PONUDE=? AND SIFRA_PONUDE=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, rbsPonude);
        stat.setInt(2, sifraPonude);
        stat.executeUpdate();
        stat.close();
    }

    public static List<StavkaPonude> findByParameterSP(String parameterName, Object parameterValue) throws SQLException {
        try {
            String SQL = "SELECT * FROM STAVKE_PONUDE";
            if (parameterName != null) {
                SQL += " WHERE " + parameterName + " =?";
            }
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            if (parameterName != null) {
                stat.setObject(1, parameterValue);
            }
            ResultSet rs = stat.executeQuery();
            List<StavkaPonude> lista = new ArrayList<StavkaPonude>();
          StavkaPonude artikal;
            while (rs.next()) {
                artikal = new StavkaPonude();
                DB_broker.setFromResultSetSp(rs, artikal);
                lista.add(artikal);
            }
            rs.close();
            stat.close();
            return lista;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
        public static StavkaPonude nadjiPoslednjuStavkuPOnude(Object sifraPonude) throws SQLException {
        try {

            String SQL = "SELECT * FROM STAVKE_PONUDE WHERE RBS_PONUDE = (SELECT MAX(RBS_PONUDE) FROM STAVKE_PONUDE) AND SIFRA_PONUDE = ? ";
          
            
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
          if(sifraPonude != null){
                
                    stat.setObject(1, sifraPonude);
              
          }
            ResultSet rs = stat.executeQuery();

            StavkaPonude jm = null;

            while (rs.next()) {
                jm = new StavkaPonude();
                DB_broker.setFromResultSetSp(rs, jm);

            }
            rs.close();
            stat.close();

            return jm;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
    
    public static StavkaPonude setFromResultSetSp(ResultSet rs, StavkaPonude sp) {
        try {
            sp.setDatum(rs.getString(1));
            sp.setSifraPonude(rs.getInt(2));
            sp.setKolicina(rs.getInt(3));
            sp.setSifraProizvoda(rs.getInt(4));
            sp.setRbsPonude(rs.getInt(5));
            sp.setNazivProizvoda(rs.getString(6));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sp;
    }

  
    

    
    public static List<Proizvod> findAllProizvode(String parameterName, Object parameterValue, List<Proizvod> listaProizvoda){
     
      try {
            String SQL = "SELECT P.SIFRA_PROIZVODA, P.NAZIV_PROIZVODA, P.AKTUELNA_CENA, P.SIFRA_JM, P.PROIZVODJAC.GET_NAZIV(), P.PROIZVODJAC.GET_ADRESA(), P.PROIZVODJAC.GET_GRAD(), P.PROIZVODJAC.GET_BROJ(), P.PROIZVODJAC.GET_DRZAVA()"
                          + " FROM PROIZVOD P ";
            if (parameterName != null) {
                SQL += " WHERE " + parameterName + " =?";
            }
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            if (parameterName != null) {
                stat.setObject(1, parameterValue);
            }
            ResultSet rs = stat.executeQuery();
           
            Proizvod artikal;
            while (rs.next()) {
                artikal =  new Proizvod();
                artikal = setFromResultSetProizvod(rs,artikal);
                listaProizvoda.add(artikal);
            }
            rs.close();
            stat.close();
           
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
      
     return listaProizvoda;
         
     }
     
     public static Proizvod setFromResultSetProizvod(ResultSet rs, Proizvod p) {
        try {
            p.setSifraProizvoda(rs.getInt(1));
            p.setNazivProizvoda(rs.getString(2));
            p.setAktuelnaCena(rs.getInt(3));
            p.setSifraJM(rs.getInt(4));
            p.setNazivProizvodjaca(rs.getString(5));
            p.setAdresa(rs.getString(6));
            p.setGrad(rs.getString(7));
            p.setBroj(rs.getInt(8));
            p.setDrzava(rs.getString(9));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }
     
     
     
     
     
     
      public static List<Zaposleni> findByParameterZaposleni(String parameterName, Object parameterValue, List<Zaposleni> listaZap) {
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
    
            Zaposleni korisnik;
            while (rs.next()) {
                korisnik = new Zaposleni();
                korisnik = setFromResultSetZaposleni(rs,korisnik);
                listaZap.add(korisnik);
            }
            rs.close();
            stat.close();
            con.commit();
          // con.close();

            return listaZap;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
     
         public static Zaposleni findUniqueZaposleni(String parameterName, Object parameterValue ,List<Zaposleni> lista) throws SQLException {
        List<Zaposleni> korisnici = findByParameterZaposleni(parameterName, parameterValue, lista);
        if (korisnici.size() > 0) {
            return korisnici.get(0);
        } else {
            return null;
        }
    }

    public static Zaposleni findByPrimaryKeyZaposleni(Integer id,List<Zaposleni> lista) throws SQLException {
        return findUniqueZaposleni("Z.SIFRA_RADNIKA", id, lista);
    }

    public static List<Zaposleni> findAllZaposleni(List<Zaposleni> lista) throws SQLException {
        return findByParameterZaposleni(null, null,lista);
    }
     
      public static Zaposleni setFromResultSetZaposleni(ResultSet rs, Zaposleni zap) {
        try {
            zap.setSifra_radnika(rs.getInt(1));
            zap.setIme(rs.getString(2));
            zap.setPrezime(rs.getString(3));
            zap.setJmbg(rs.getString(4));
            zap.setTelefon(rs.getString(5));
            zap.setEmail(rs.getString(6));
            zap.setSifra_funkcije(rs.getInt(7));
            zap.setStatus(rs.getString(8));
            zap.setIsadmin(rs.getString(9));
            zap.setSifra(rs.getString(10));
            zap.setNazivFunkcije(rs.getString(11));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return zap;
    }
     
       public static void saveZaposleni(Connection con, Zaposleni zap) throws SQLException {
      
            String SQL = "INSERT INTO ZAPOSLENI_VIEW(SIFRA_RADNIKA, IME, PREZIME, JMBG, TELEFON, EMAIL, SIFRA_FUNKCIJE,STATUS, IS_ADMIN, SIFRA) VALUES (?,?,?,?,?,?,?,?,?,?)";
           
            PreparedStatement ps = con.prepareStatement(SQL);
            setStatementParamsZaposleni(ps,zap);
            ps.executeUpdate();
            ps.close();
   
       }
       
       public static void setStatementParamsZaposleni(PreparedStatement ps, Zaposleni zap) {
        try {
            ps.setInt(1, zap.getSifra_radnika());
            ps.setString(2, zap.getIme());
            ps.setString(3, zap.getPrezime());
            ps.setString(4, zap.getJmbg());
            ps.setString(5, zap.getTelefon());
            ps.setString(6, zap.getEmail());
            ps.setInt(7, zap.getSifra_funkcije());
            ps.setString(8, zap.getStatus());
            ps.setString(9, zap.getIsadmin());
            ps.setString(10, zap.getSifra());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
        public static void updateZaposlenog(Connection con, Zaposleni zap) throws SQLException {
        
            String SQL = "UPDATE ZAPOSLENI_VIEW SET IME=?, PREZIME=?, JMBG=?, TELEFON=?,"
                    + " EMAIL=?, SIFRA_FUNKCIJE=?, STATUS=?, IS_ADMIN=?, SIFRA=?  WHERE SIFRA_RADNIKA=?";
          
            PreparedStatement statement = con.prepareStatement(SQL);
            setStatementParamsForUpdateZAposleni(statement, zap);
            statement.setInt(10, zap.getSifra_radnika());
            statement.executeUpdate();
            statement.close();
            //connection.close();
         
    }
         
          public static void setStatementParamsForUpdateZAposleni(PreparedStatement ps, Zaposleni zap) {
        try {

            ps.setString(1, zap.getIme());
            ps.setString(2, zap.getPrezime());
            ps.setString(3, zap.getJmbg());
            ps.setString(4, zap.getTelefon());
            ps.setString(5, zap.getEmail());
            ps.setInt(6, zap.getSifra_funkcije());
            ps.setString(7, zap.getStatus());
            ps.setString(8, zap.getIsadmin());
            ps.setString(9, zap.getSifra());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
          
          
           public static Zaposleni findPoslednjiZaposleni() throws SQLException {
       
               Zaposleni zap = null;
               try {
            String SQL = "SELECT * FROM ZAPOSLENI WHERE SIFRA_RADNIKA = (SELECT MAX(SIFRA_RADNIKA) FROM ZAPOSLENI)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

               while (rs.next()) {
                zap = new Zaposleni();
            zap.setSifra_radnika(rs.getInt(1));
            zap.setIme(rs.getString(2));
            zap.setPrezime(rs.getString(3));
     
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
