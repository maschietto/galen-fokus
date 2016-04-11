
package UCController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Zaposleni;

import javax.servlet.http.HttpServletRequest;
//import model.Dobavljac;
//import model.Mesto;
//import model.Ulica;
//import model.BrojZgrade;
//import model.DobavljacDetalji;


public class ProccedUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request)  {
       
            //        String tipInstrumenta = request.getParameter("tipinstrumenta");
            Zaposleni logged = (Zaposleni) request.getSession().getAttribute("korisnik");
            

     return "Korisnik";
    }
    
}
