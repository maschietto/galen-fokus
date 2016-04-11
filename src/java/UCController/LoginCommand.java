package UCController;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Zaposleni;

import util.Validate;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws SQLException  {

        String user = request.getParameter("user");
        String pwd = request.getParameter("password");
        String next = "index";

        //provera parametara na serverskoj strani
        boolean valid = Validate.exists(user) && Validate.exists(pwd);
       // && korisnik.getPassword().equals(pwd)
        if (valid) {
          
                Zaposleni korisnik = Zaposleni.findUnique("NAZIV_KORISNIKA", user);
                if (korisnik != null) {
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("korisnik", korisnik);
                    
                    
                    if(!korisnik.getStatus().equals("AKTIVAN")){
                        request.setAttribute("poruka", "Morate sacekati da vas odobri administrator!");
                        return "index";
                    }
                    
                    if (korisnik.getIsadmin().equals("DA")) {
                        
                        next = new UserListCommand().execute(request);
                    } else {
                        ProccedUserCommand pu = new ProccedUserCommand();
                        next = pu.execute(request);
                    }
                    // next = new FilmListCommand().execute(request);
                } else {
                    //pogresni podaci
                    request.setAttribute("poruka", "POgresni podaci, pokusajte ponovo");
                }
        } else {
            request.setAttribute("poruka", "MOLIM VAS REGISTRUJTE SE!");
        }
        return next;
    }

}