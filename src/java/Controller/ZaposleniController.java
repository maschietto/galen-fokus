/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewModels.ZaposleniDetalji;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Poruka;
import model.Zaposleni;
import util.DB_broker;
import util.Validate;

/**
 *
 * @author Aleksa Jonic
 */
public class ZaposleniController extends HttpServlet {

    public Zaposleni popuniZaposlenog(Zaposleni y, String ime, String prezime, String email, String jmbg, String telefon, String sifra, String isAdmin, Integer sifraFunkcije, String status) {

        try {
            y = DB_broker.findPoslednjiZaposleni();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (y == null) {
            y = new Zaposleni();
            y.setSifra_radnika(1);
        } else {
            Integer siraRadnika = y.getSifra_radnika();
            y.setSifra_radnika(siraRadnika + 1);
        }
        y.setIme(ime);
        y.setPrezime(prezime);
        y.setEmail(email);
        y.setJmbg(jmbg);
        y.setTelefon(telefon);
        y.setSifra(sifra);
        y.setIsadmin(isAdmin);
        y.setSifra_funkcije(sifraFunkcije);
        y.setStatus(status);

        return y;
    }    
  
    
    public Zaposleni popuniParametreZaposlenog(ZaposleniDetalji zap, Zaposleni zaposleni) {

        zaposleni.setIme(zap.getIme());
        zaposleni.setPrezime(zap.getPrezime());
        zaposleni.setJmbg(zap.getJmbg());
        zaposleni.setEmail(zap.getEmail());
        zaposleni.setTelefon(zap.getTelefon());
        zaposleni.setSifra(zap.getSifra());
        zaposleni.setSifra_radnika(zap.getSifra_radnika());
        zaposleni.setIsadmin(zap.getIsadmin());
        zaposleni.setStatus(zap.getStatus());
        zaposleni.setSifra_funkcije(zap.getSifra_funkcije());

        return zaposleni;

    }
    
    public void updateZaposlenog(Zaposleni zap){
    
                try {
                    Connection con = DB_broker.getConnection();
                    DB_broker.updateZaposlenog(con, zap);
                    DB_broker.commitTransaction();
                } catch (SQLException ex) {
                    DB_broker.rollbackTransaction();
                    ex.printStackTrace();
                }
    
    }
  
  
  
  
  public void sacuvajZaposlenog(Zaposleni zap) {

        Connection con = DB_broker.getConnection();
        try {
            DB_broker.saveZaposleni(con, zap);
            DB_broker.commitTransaction();

        } catch (SQLException ex) {
            ex.printStackTrace();
            DB_broker.rollbackTransaction();
        }
    }

 

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ZaposleniController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ZaposleniController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                  throws ServletException, IOException {

        String status = request.getParameter("status");

        if (status.equals("registracija")) {

            Boolean x = false;

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            String ime = request.getParameter("ime");
            String prezime = request.getParameter("prezime");
            String email = request.getParameter("email");
            String jmbg = request.getParameter("jmbg");
            String telefon = request.getParameter("telefon");
            String sifra = request.getParameter("sifra");

            ime = Validate.preurediStringZaIme(ime);
            prezime = Validate.preurediStringZaIme(prezime);

            Poruka p;
            p = Poruka.findPOslendjuPoruku();

            if (p == null) {
                p = new Poruka();
                p.setPorukaId(1);
            } else {
                p.setPorukaId(p.getPorukaId() + 1);
            }

            Zaposleni y = new Zaposleni();
            y = popuniZaposlenog(y, ime, prezime, email, jmbg, telefon, sifra, "FALSE", 3, "NEAKTIVAN");

            sacuvajZaposlenog(y);

            String poruka = "Uspesni ste se registrovali.Morate sacekati da vas administrator aktivira!";

            p.setPoruka(poruka);
            p.setSifraRadnika(y.getSifra_radnika());
            p.setBrojac(0);
            p.setVreme(Validate.dajVrijemeSada());
            p.setNazivAkcije("LOGIN");

        }

        if (status.equals("podaci_kor")) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ZaposleniDetalji zap;
            zap = (ZaposleniDetalji) request.getSession().getAttribute("korisnik");
            String ime = request.getParameter("ime");
            String prezime = request.getParameter("prezime");
            String jmbg = request.getParameter("jmbg");
            String email = request.getParameter("email");
            String telefon = request.getParameter("telefon");
            String sifra = request.getParameter("sifra");
            if (!Validate.exists(ime) && !Validate.exists(prezime) && !Validate.exists(jmbg) && !Validate.exists(email) && !Validate.exists(telefon) && !Validate.exists(sifra)) {

                response.getWriter().write(new Gson().toJson(zap));

            } else {

                zap.setIme(ime);
                zap.setPrezime(prezime);
                zap.setJmbg(jmbg);
                zap.setEmail(email);
                zap.setTelefon(telefon);
                zap.setSifra(sifra);

                   Zaposleni zaposleni;         
                   zaposleni = new Zaposleni();
                   
                zaposleni = popuniParametreZaposlenog(zap, zaposleni);
                updateZaposlenog(zaposleni);
                
                request.getSession().setAttribute("korisnik", zap);

                response.getWriter().write(new Gson().toJson(zap));

            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
