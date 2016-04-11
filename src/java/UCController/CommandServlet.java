package UCController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Proizvod;
import model.Zaposleni;



@WebServlet(name = "CommandServlet", urlPatterns = {"/CommandServlet"})
public class CommandServlet extends HttpServlet {

    Map<String, Command> komande = new HashMap<String, Command>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        komande.put("katalogmain", new KatalogMainCommand());
        komande.put("login", new LoginCommand());
        komande.put("logout", new LogoutCommand());
        komande.put("index", new NullCommand("index"));
        komande.put("editkorisnik", new EditUserCommand());
//        komande.put("aktivirajkorisnika", new ActivateUserCommand("TRUE"));
//        komande.put("deaktivirajkorisnika", new ActivateUserCommand("FALSE"));
        komande.put("pronadjikorisnika", new FindUserCommand());
      //  komande.put("dobavljacilista",new DobavljaciListaCommand());
       // komande.put("dobavljacupdate", new UpdateDobavljacCommand());
       // komande.put("prosledidobavljacaunos",new ProccedDobavljacCommand());
       // komande.put("korisnik", new UserCommand());
       // komande.put("korisnici", new UserListCommand());
  
}

    private Command lookupCommand(String cmd, HttpServletRequest request) {
        if (komande.containsKey(cmd.toLowerCase())) {
            return komande.get(cmd);
        } else {
            request.getSession().invalidate();
            return new NullCommand("index");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String cmd = request.getParameter("cmd");
        Command komanda = lookupCommand(cmd, request);
        String next;
        try {
            next = komanda.execute(request);
            getServletContext().getRequestDispatcher("/" + next + ".jsp").forward(request, response);
        } catch (IOException ex) {
           request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, ex);
           request.setAttribute(RequestDispatcher.ERROR_SERVLET_NAME, CommandServlet.class.getName());
           getServletContext().getRequestDispatcher("/ErrorHandlingServlet").forward(request, response); 
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CommandServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CommandServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Komandni servlet";
    }
}
