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
import util.Validate;


public class Proizvod {

private Integer sifraProizvoda;
private String nazivProizvoda;
private Integer aktuelnaCena;
private Integer sifraJM;

private String nazivProizvodjaca ;
private String adresa ;
private String grad ;
private Integer broj;
private String drzava;

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

    public Integer getAktuelnaCena() {
        return aktuelnaCena;
    }

    public void setAktuelnaCena(Integer aktuelnaCena) {
        this.aktuelnaCena = aktuelnaCena;
    }

    public Integer getSifraJM() {
        return sifraJM;
    }

    public void setSifraJM(Integer sifraJM) {
        this.sifraJM = sifraJM;
    }

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
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

    public Integer getBroj() {
        return broj;
    }

    public void setBroj(Integer broj) {
        this.broj = broj;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    
 


   public Proizvod setFromResultSet(ResultSet rs) {
        try {
            setSifraProizvoda(rs.getInt(1));
            setNazivProizvoda(rs.getString(2));
            setAktuelnaCena(rs.getInt(3));
            setSifraJM(rs.getInt(4));
            setNazivProizvodjaca(rs.getString(5));
            setAdresa(rs.getString(6));
            setGrad(rs.getString(7));
            setBroj(rs.getInt(8));
            setDrzava(rs.getString(9));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1,sifraProizvoda);
            ps.setString(2, nazivProizvoda);
            ps.setInt(3, sifraJM);
            ps.setString(4, nazivProizvodjaca);
            ps.setString(5,adresa);
            ps.setString(6, grad);
            ps.setInt(7, broj);
            ps.setString(8, drzava);
            
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public void setStatementParamsAktCena(PreparedStatement ps) {
      
            try {
                ps.setInt(1,aktuelnaCena);
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
            
        }

        public void setStatementParamsforUpdate(PreparedStatement ps) {
        try {
         
            ps.setString(1, nazivProizvoda);
            ps.setInt(2, sifraJM);
            ps.setString(3, nazivProizvodjaca);
            ps.setString(4,adresa);
            ps.setString(5, grad);
            ps.setInt(6, broj);
            ps.setString(7, drzava);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   public void saveProizvod() {
        try {
            String SQL = "INSERT INTO PROIZVOD (SIFRA_PROIZVODA, NAZIV_PROIZVODA, SIFRA_JM, PROIZVODJAC) VALUES (?,?,?,PROIZVODJAC(?,?,?,?,?))";
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
            String SQL = "UPDATE proizvod  SET  NAZIV_PROIZVODA=?, SIFRA_JM=?, PROIZVODJAC=PROIZVODJAC(?,?,?,?,?) WHERE SIFRA_PROIZVODA=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(8,sifraProizvoda);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
       public void delete() throws SQLException {

        String SQL = "DELETE FROM PROIZVOD WHERE SIFRA_PROIZVODA=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifraProizvoda);
        stat.executeUpdate();
        stat.close();
  }
    
        public boolean isNew() {

        return sifraProizvoda == null;
    }

    public void saveOrUpdate() {
        if (isNew()) {
            saveProizvod();
        } else {
            update();
        }
    }
    
  public static List<Proizvod> findByParameter(String parameterName, Object parameterValue) throws SQLException {
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
            List<Proizvod> lista = new ArrayList<Proizvod>();
            Proizvod artikal;
            while (rs.next()) {
                artikal = new Proizvod();
                artikal.setFromResultSet(rs);
                lista.add(artikal);
            }
            rs.close();
            stat.close();
            return lista;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } 
    }

    public static Proizvod findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("SIFRA_PROIZVODA", id);
    }

    public static Proizvod findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<Proizvod> proizvod = findByParameter(parameterName, parameterValue);
        if (proizvod.size() > 0) {
            return proizvod.get(0);
        } else {
            return null;
        }
    }

        public static List<Proizvod> findAll() throws SQLException {
        return findByParameter(null, null);
    }
        
        
        public static Proizvod nadjiPoslednjiProizvod() throws SQLException {
        try {

            String SQL = "SELECT * FROM PROIZVOD WHERE SIFRA_PROIZVODA = (SELECT MAX(SIFRA_PROIZVODA) FROM PROIZVOD)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

            Proizvod jm = null;

            while (rs.next()) {
                jm = new Proizvod();
                jm.setSifraProizvoda(rs.getInt(1));

            }
            rs.close();
            stat.close();

            return jm;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
        
        public String updateAktuelnaCena() {
            
            String poruka = "";
            
        try {
            String SQL = "UPDATE PROIZVOD  SET  AKTUELNA_CENA=? WHERE SIFRA_PROIZVODA=?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsAktCena(statement);
            statement.setInt(2,sifraProizvoda);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        }  catch (SQLException ex) {
            if (ex.getErrorCode() == 20000) {

                poruka = Validate.pokupiPoruku(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }
        
        return poruka;
    }
    
        
        
        

}
