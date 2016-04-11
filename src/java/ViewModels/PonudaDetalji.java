package ViewModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.JedinicaMere;
import model.ProizvodjacTip;
import model.Kilogram;
import model.Proizvod;
import model.StavkaPonude;
import util.DB_broker;

public class PonudaDetalji {

    private Integer sifraPonude;
    private String datum;
    private Integer sifraRadnika;
    private Integer ukupnaSuma;
    private Integer kupacId;

    private Integer rbsPonude;
    private Integer kolicina;
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
    private String imeKupca;
    
    private String pibKupca;

    public String getPibKupca() {
        return pibKupca;
    }

    public void setPibKupca(String pibKupca) {
        this.pibKupca = pibKupca;
    }
    
    public String getImeKupca() {
        return imeKupca;
    }

    public void setImeKupca(String imeKupca) {
        this.imeKupca = imeKupca;
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

    public Integer getSifraRadnika() {
        return sifraRadnika;
    }

    public void setSifraRadnika(Integer sifraRadnika) {
        this.sifraRadnika = sifraRadnika;
    }

    public Integer getUkupnaSuma() {
        return ukupnaSuma;
    }

    public void setUkupnaSuma(Integer ukupnaSuma) {
        this.ukupnaSuma = ukupnaSuma;
    }

    public Integer getKupacId() {
        return kupacId;
    }

    public void setKupacId(Integer kupacId) {
        this.kupacId = kupacId;
    }

    public Integer getRbsPonude() {
        return rbsPonude;
    }

    public void setRbsPonude(Integer rbsPonude) {
        this.rbsPonude = rbsPonude;
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

    public PonudaDetalji setFromResultSet(ResultSet rs) {
        try {
            setSifraPonude(rs.getInt(1));
            setDatum(rs.getString(2));
            setSifraRadnika(rs.getInt(3));
            setUkupnaSuma(rs.getInt(4));
            setKupacId(rs.getInt(5));
            setKolicina(rs.getInt(6));
            setRbsPonude(rs.getInt(7));
            setSifraProizvoda(rs.getInt(8));
            setNazivProizvoda(rs.getString(9));
            setAktuelnaCena(rs.getInt(10));
            setNazivProizvodjaca(rs.getString(11));
            setAdresaProizvodjaca(rs.getString(12));
            setGradProizvodjaca(rs.getString(13));
            setBrojProizvodjaca(rs.getInt(14));
            setDrzavaProizvodjaca(rs.getString(15));
            setSifraJM(rs.getInt(16));
            setNazivJM(rs.getString(17));
            setVrednost(rs.getDouble(18));
            setImeKupca(rs.getString(19));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public static List<PonudaDetalji> findByParameter(String parameterName, Object parameterValue) throws SQLException {
        try {
            String SQL = "select p.sifra_ponude, p.datum, p.sifra_radnika, p.ukupna_suma, p.kupac_id, sp.kolicina, sp.rbs_ponude, \n"
                          + "pz.sifra_proizvoda, pz.naziv_proizvoda, pz.aktuelna_cena, pz.proizvodjac.get_naziv(), pz.proizvodjac.get_adresa(), pz.proizvodjac.get_grad(),pz.proizvodjac.get_broj(),\n"
                          + "pz.proizvodjac.get_drzava(), jm.sifra_jm, jm.naziv_jm, jm.udt_kg.get_vrednost(), k.ime_kupca from ponuda p, stavke_ponude sp, proizvod pz, jedinica_mere jm , kupac k where p.sifra_ponude= sp.sifra_ponude and jm.sifra_jm = pz.sifra_jm and pz.sifra_proizvoda = sp.sifra_proizvoda "
                          + "and p.kupac_id = k.kupac_id ";

            if (parameterName != null && parameterValue != null) {
                SQL += " AND " + parameterName + " ='" + parameterValue + "'";
            } 
            if(parameterName == null && parameterValue == null) {
                SQL += " ORDER BY pz.sifra_ponude";
            }
            if (parameterName != null && parameterValue == null) {
                 SQL += " ORDER BY pz.sifra_proizvoda";
            } 
            if (parameterName == null && parameterValue != null) {
                 SQL += " ORDER BY pz.sifra_proizvoda";
            } 


            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);

         
            ResultSet rs = stat.executeQuery();
            List<PonudaDetalji> lista = new ArrayList<PonudaDetalji>();
            PonudaDetalji pd;
            
            while (rs.next()) {
                pd = new PonudaDetalji();
                pd.setFromResultSet(rs);
                lista.add(pd);
            }
            rs.close();
            stat.close();
            con.commit();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<PonudaDetalji> findByAutoComplete(String name, Object nameParam, Object imeParam, Object prezimeParam, Object emailParam) throws SQLException {
        try {
            String SQL = "SELECT Z.SIFRA_RADNIKA, Z.IME, Z.PREZIME, ZD.JMBG, ZD.TELEFON, ZD.EMAIL, ZD.SIFRA_FUNKCIJE, ZD.IS_ADMIN, ZD.STATUS,ZD.SIFRA, C.NAZIV_FUNKCIJE, P.PORUKA, P.BROJAC,P.PORUKA_ID, P.NAZIV_AKCIJE FROM ZAPOSLENI Z, ZAPOSLENI_DETALJI ZD, FUNKCIJA C, PORUKA P WHERE Z.SIFRA_RADNIKA = ZD.SIFRA_RADNIKA AND ZD.SIFRA_FUNKCIJE = C.SIFRA_FUNKCIJE  AND Z.SIFRA_RADNIKA = P.SIFRA_RADNIKA";
            if (name != null) {
                SQL += " AND " + name + " LIKE ?";
            } else {
                if (imeParam != null && prezimeParam != null && emailParam != null) {
                    SQL += " AND Z.IME = '" + imeParam + "'  AND Z.PREZIME = '" + prezimeParam + "' AND ZD.EMAIL = '" + emailParam + "' ";
                }
                if (imeParam != null && prezimeParam == null && emailParam == null) {
                    SQL += " AND Z.IME = '" + imeParam + "' ";
                }
                if (imeParam != null && prezimeParam != null && emailParam == null) {
                    SQL += " AND Z.IME = '" + imeParam + "' AND Z.PREZIME = '" + prezimeParam + "'";
                }
                if (imeParam != null && prezimeParam == null && emailParam != null) {
                    SQL += " AND Z.IME = '" + imeParam + "' AND ZD.EMAIL = '" + emailParam + "' ";
                }
                if (imeParam == null && prezimeParam != null && emailParam == null) {
                    SQL += " AND Z.PREZIME = '" + prezimeParam + "' ";
                }
                if (imeParam == null && prezimeParam != null && emailParam != null) {
                    SQL += " AND Z.PREZIME = '" + prezimeParam + "' AND ZD.EMAIL = '" + emailParam + "' ";
                }
                if (imeParam == null && prezimeParam == null && emailParam != null) {
                    SQL += " AND ZD.EMAIL = '" + emailParam + "' ";
                }
            }

            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);

            if (nameParam != null) {
                stat.setObject(1, nameParam + "%");
            }
            ResultSet rs = stat.executeQuery();
            List<PonudaDetalji> lista = new ArrayList<PonudaDetalji>();
            PonudaDetalji pd;
            while (rs.next()) {
                pd = new PonudaDetalji();
                pd.setFromResultSet(rs);
                lista.add(pd);
            }
            rs.close();
            stat.close();
            con.commit();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

 
    public static PonudaDetalji findUnique(String parameterName, Object parameterValue) throws SQLException {
        List<PonudaDetalji> ponuda = findByParameter(parameterName, parameterValue);
        if (ponuda.size() > 0) {
            return ponuda.get(0);
        } else {
            return null;
        }
    }

    public static PonudaDetalji findByPrimaryKey(Integer id) throws SQLException {
        return findUnique("P.SIFRA_PONUDE", id);
    }

    public static List<PonudaDetalji> findAll() throws SQLException {
        return findByParameter(null, null);
    }
    
 public static PonudaDetalji findPonuduIkupca(String parameterName, Object parameterValue) throws SQLException {
        try {
            String SQL = "select p.sifra_ponude, p.datum, p.ukupna_suma, k.ime_kupca, k.pib_kupca from ponuda p , kupac k where p.kupac_id = k.kupac_id";

            if (parameterName != null && parameterValue != null) {
                SQL += " AND " + parameterName + " ='" + parameterValue + "'";
            } 
           


            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);

         
            ResultSet rs = stat.executeQuery();
            PonudaDetalji pon = null;
            
            
            while (rs.next()) {
              
                pon = new PonudaDetalji();
                pon.setSifraPonude(rs.getInt(1));
                pon.setDatum(rs.getString(2));
                pon.setUkupnaSuma(rs.getInt(3));
                pon.setImeKupca(rs.getString(4));
                pon.setPibKupca(rs.getString(5));
                
                
                
            }
            rs.close();
            stat.close();
            con.commit();

           return pon;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    

}
