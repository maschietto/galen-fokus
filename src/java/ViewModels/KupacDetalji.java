
package ViewModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Zaposleni;
import util.DB_broker;


public class KupacDetalji {
     
    
    private Integer kupacId;
    private String pib;
    private String imeKupca;
    private Integer mestoId;
    private String adresa;
    private String grad;

    public Integer getKupacId() {
        return kupacId;
    }

    public void setKupacId(Integer kupacId) {
        this.kupacId = kupacId;
    }

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

    public KupacDetalji setFromResultSet(ResultSet rs) {
        try {
            setKupacId(rs.getInt(1));
          
            setPib(rs.getString(2));
            setImeKupca(rs.getString(3));
            setMestoId(rs.getInt(4));
            setAdresa(rs.getString(5));
            setGrad(rs.getString(6));
       
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

   public static List<KupacDetalji> findByParameter(String parameterName, Object parameterValue) throws SQLException {
        try {
            String SQL = "SELECT K.KUPAC_ID, K.PIB_KUPCA, K.IME_KUPCA, K.ID_MESTO, M.ADRESA, M.GRAD FROM KUPAC K, MESTO M WHERE K.ID_MESTO = M.ID_MESTO";
            
            if(parameterName != null){
            SQL += " AND " + parameterName + " LIKE ?";
            }
            
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            
              if (parameterValue != null) {
                stat.setObject(1, parameterValue + "%");
            }
            
            ResultSet rs = stat.executeQuery();
            List<KupacDetalji> lista = new ArrayList<KupacDetalji>();
            KupacDetalji kupac;
            while (rs.next()) {
                kupac  = new KupacDetalji();
                kupac.setFromResultSet(rs);
                lista.add(kupac);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   
   
   public static List<KupacDetalji> findByAutoComplete(String name, Object nameParam) throws SQLException {
        try {
            String SQL = "SELECT K.KUPAC_ID, K.PIB_KUPCA, K.IME_KUPCA, K.ID_MESTO, M.ADRESA, M.GRAD FROM KUPAC K, MESTO M WHERE K.ID_MESTO = M.ID_MESTO";
            if(name != null){
            SQL += " AND " + name + " LIKE ?";
            }
           
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            
            if (nameParam != null) {
                stat.setObject(1, nameParam + "%");
            }
            ResultSet rs = stat.executeQuery();
            List<KupacDetalji> lista = new ArrayList<KupacDetalji>();
            KupacDetalji korisnik;
            while (rs.next()) {
                korisnik = new KupacDetalji();
                korisnik.setFromResultSet(rs);
                lista.add(korisnik);
            }
            rs.close();
            stat.close();
            con.commit();
          
            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

  

    
    public static KupacDetalji findByUsername(String username, String password) throws SQLException {
        KupacDetalji kupac = null;
        KupacDetalji objekat = null;
        String SQL = "SELECT * FROM KUPAC K JOIN MESTO M ON K.ID_MESTO=M.ID_MESTO WHERE IME_KUPCA=? ";
        if (password != null) {
            SQL += " AND KUPAC_ID=?";
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

            objekat = kupac.setFromResultSet(rs);
        }
        rs.close();
        ps.close();
        return objekat;
    }

    public static KupacDetalji findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<KupacDetalji> kupci = findByParameter(parameterName, parameterValue);
         if (kupci.size() > 0) {
            return kupci.get(0);
        } else {
            return null;
        }
    }

    public static KupacDetalji findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("K.KUPAC_ID", id);
    }

    public static List<KupacDetalji> findAll() throws SQLException {
        return findByParameter(null, null);
    }

    
    
    
}
