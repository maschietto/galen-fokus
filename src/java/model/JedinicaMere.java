package model;

import ViewModels.ProizvodDetalji;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Kilogram;
import util.DB_broker;

public class JedinicaMere {

    private Integer sifraJM;
    private String nazivJM;
    private Double vrednost;

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

    public JedinicaMere setFromResultSet(ResultSet rs) {
        try {
            setSifraJM(rs.getInt(1));
            setNazivJM(rs.getString(2));
            //setVrednost(rs.getDouble(3));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void setStatementParams(PreparedStatement ps) {
        try {
            ps.setInt(1, sifraJM);
            ps.setString(2, nazivJM);
            ps.setDouble(3, vrednost);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setStatementParamsforUpdate(PreparedStatement ps) {
        try {

            ps.setString(1, nazivJM);
            ps.setDouble(2, vrednost);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveJedinicaMere() {
        try {
            String SQL = "INSERT INTO ALEKSA.JEDINICA_MERE (SIFRA_JM, NAZIV_JM, UDT_KG) VALUES (?,?, KILOGRAM(?))";
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
            String SQL = "UPDATE ALEKSA.JEDINICA_MERE SET NAZIV_JM = ?, UDT_KG= KILOGRAM(?) WHERE SIFRA_JM= ?";
            Connection connection = DB_broker.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL);
            setStatementParamsforUpdate(statement);
            statement.setInt(3, sifraJM);
            statement.executeUpdate();
            statement.close();
            //connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws SQLException {

        String SQL = "DELETE FROM ALEKSA.JEDINICA_MERE WHERE SIFRA_JM=?";
        Connection con = DB_broker.getConnection();
        PreparedStatement stat = con.prepareStatement(SQL);
        stat.setInt(1, sifraJM);
        stat.executeUpdate();
        stat.close();
    }

    public static JedinicaMere findByUsername(String username) throws SQLException {

        JedinicaMere jm = null;
        String SQL = "SELECT j.sifra_jm, j.naziv_jm, j.udt_kg.get_vrednost() FROM ALEKSA.JEDINICA_MERE j WHERE NAZIV_JM = ? ";

        try {
            Connection con = DB_broker.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            if (username != null) {
                ps.setString(1, username);;
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                jm = new JedinicaMere();
                jm.setFromResultSet(rs);

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return jm;

    }

    public static List<JedinicaMere> findByParameter() throws SQLException {
        try {
            String SQL = "SELECT DISTINCT NAZIV_JM FROM ALEKSA.JEDINICA_MERE ";
           
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);

            ResultSet rs = stat.executeQuery();
            List<JedinicaMere> lista = new ArrayList<JedinicaMere>();
            JedinicaMere jm;

            while (rs.next()) {

                jm = new JedinicaMere();
                jm.setNazivJM(rs.getString(1));

                lista.add(jm);
            }
            rs.close();
            stat.close();
            con.commit();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static JedinicaMere findPoslednjuJM() throws SQLException {
        try {

            String SQL = "SELECT * FROM ALEKSA.JEDINICA_MERE WHERE SIFRA_JM = (SELECT MAX(SIFRA_JM) FROM ALEKSA.JEDINICA_MERE)";
            Connection con = DB_broker.getConnection();
            PreparedStatement stat = con.prepareStatement(SQL);
            ResultSet rs = stat.executeQuery();

            JedinicaMere jm = null;

            while (rs.next()) {
                jm = new JedinicaMere();
                jm.setFromResultSet(rs);

            }
            rs.close();
            stat.close();

            return jm;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

}
