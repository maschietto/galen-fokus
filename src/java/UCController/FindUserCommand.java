package UCController;


import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import model.Zaposleni;
;

/**
 *
 * @author ibranovic
 */
public class FindUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws SQLException{
        
        String name = request.getParameter("demo_ime");
      
        Zaposleni logged = (Zaposleni) request.getSession().getAttribute("korisnik");
        
        request.setAttribute("lista", Zaposleni.findByParameter("NAZIV_KORISNIKA",name));
       
       return "Admin";
    }
    
}
