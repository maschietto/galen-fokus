
package ViewModels;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Kilogram;
import util.DB_broker;


public class ProizvodDetalji {
    
    private Integer sifraProizvoda;
    private String nazivProizvoda;
    private Integer aktuelnaCena;
    private Integer sifraJM;
    private String nazivProizvodjaca;
    private String adresaProizvodjaca;
    private String gradProizvodjaca;
    private Integer brojProizvodjaca;
    private String drzavaProizvodjaca;

    private String nazivJM;
    private Double vrednost;

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

    public String getAdresaProizvodjaca() {
        return adresaProizvodjaca;
    }

    public void setAdresaProizvodjaca(String adresaProizvodjaca) {
        this.adresaProizvodjaca = adresaProizvodjaca;
    }

    public String getGradProizvodjaca() {
        return gradProizvodjaca;
    }

    public void setGradProizvodjaca(String gradProizvodjaca) {
        this.gradProizvodjaca = gradProizvodjaca;
    }

    public Integer getBrojProizvodjaca() {
        return brojProizvodjaca;
    }

    public void setBrojProizvodjaca(Integer brojProizvodjaca) {
        this.brojProizvodjaca = brojProizvodjaca;
    }

    public String getDrzavaProizvodjaca() {
        return drzavaProizvodjaca;
    }

    public void setDrzavaProizvodjaca(String drzavaProizvodjaca) {
        this.drzavaProizvodjaca = drzavaProizvodjaca;
    }

    public String getNazivJM() {
        return nazivJM;
    }

    public void setNazivJM(String nazivJM) {
        this.nazivJM = nazivJM;
    }

    public Double getVrednost() {
        return vrednost;
    }

    public void setVrednost(Double vrednost) {
        this.vrednost = vrednost;
    }
   

    public ProizvodDetalji setFromResultSet(ResultSet rs) {
        try {
            setSifraProizvoda(rs.getInt(1));
            setNazivProizvoda(rs.getString(2));
            setAktuelnaCena(rs.getInt(3));
            setSifraJM(rs.getInt(4));
            setNazivProizvodjaca(rs.getString(5));
            setAdresaProizvodjaca(rs.getString(6));
            setGradProizvodjaca(rs.getString(7));
            setBrojProizvodjaca(rs.getInt(8));
            setDrzavaProizvodjaca(rs.getString(9));
            setNazivJM(rs.getString(10));
            setVrednost(rs.getDouble(11));
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

   public static List<ProizvodDetalji> findByParameter(String parameterName, Object parameterValue) throws SQLException{
        try {
            String SQL = "SELECT P.SIFRA_PROIZVODA, P.NAZIV_PROIZVODA, P.AKTUELNA_CENA, P.SIFRA_JM, P.PROIZVODJAC.GET_NAZIV(), P.PROIZVODJAC.GET_ADRESA(), P.PROIZVODJAC.GET_GRAD(), P.PROIZVODJAC.GET_BROJ(), P.PROIZVODJAC.GET_DRZAVA(), JM.NAZIV_JM, JM.UDT_KG.GET_VREDNOST() FROM PROIZVOD P, JEDINICA_MERE JM WHERE P.SIFRA_JM = JM.SIFRA_JM";
           if (parameterName != null  && parameterValue != null) {
            SQL += " AND " + parameterName + " ='" + parameterValue + "'";
            } else {
                SQL += " ORDER BY P.SIFRA_PROIZVODA";
            }
            
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
           
            ResultSet rs = stat.executeQuery();
            List<ProizvodDetalji> lista = new ArrayList<ProizvodDetalji>();
            ProizvodDetalji artikal;
            while (rs.next()) {
                artikal = new ProizvodDetalji();
                artikal.setFromResultSet(rs);
 
                
                lista.add(artikal);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
   
   public static List<ProizvodDetalji> findByAutoComplete(String name, Object nameParam) throws SQLException {
        try {
            String SQL = "SELECT P.SIFRA_PROIZVODA, P.NAZIV_PROIZVODA, P.AKTUELNA_CENA, P.SIFRA_JM, P.PROIZVODJAC.GET_NAZIV(), P.PROIZVODJAC.GET_ADRESA(), P.PROIZVODJAC.GET_GRAD(), P.PROIZVODJAC.GET_BROJ(), P.PROIZVODJAC.GET_DRZAVA(), JM.NAZIV_JM, JM.UDT_KG.GET_VREDNOST() FROM PROIZVOD P, JEDINICA_MERE JM WHERE P.SIFRA_JM = JM.SIFRA_JM";
            if(name != null){
            SQL += " AND " + name + " LIKE ?";
            
            }
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            
            if (nameParam != null) {
                stat.setObject(1, nameParam + "%");
            }
            ResultSet rs = stat.executeQuery();
            List<ProizvodDetalji> lista = new ArrayList<ProizvodDetalji>();
            ProizvodDetalji pro;
            while (rs.next()) {
                 pro = new ProizvodDetalji();
                pro.setFromResultSet(rs);
                lista.add(pro);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

  

    
    public static ProizvodDetalji findByUsername(String username, String password) throws SQLException {
        ProizvodDetalji artikal = null;
        ProizvodDetalji objekat = null;
        String SQL = "SELECT * FROM PROIZVOD P JOIN CENA_PROIZVODA CP ON P.SIFRA_PROIZVODA=CP.SIFRA_PROIZVODA WHERE NAZIV_PROIZVODA=? ";
        if (password != null) {
            SQL += " AND SIFRA_PROIZVODA=?";
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

            objekat = artikal.setFromResultSet(rs);
        }
        rs.close();
        ps.close();
        return objekat;
    }

    public static ProizvodDetalji findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<ProizvodDetalji> artikli = findByParameter(parameterName, parameterValue);
         if (artikli.size() > 0) {
            return artikli.get(0);
        } else {
            return null;
        }
    }

    public static ProizvodDetalji findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("P.SIFRA_PROIZVODA", id);
    }

    public static List<ProizvodDetalji> findAll() throws SQLException, IllegalAccessException {
        return findByParameter(null, null);
    }

    
    
    
    
    


}
