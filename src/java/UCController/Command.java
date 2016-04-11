package UCController;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;



/**
 *
 * @author ibranovic
 */
public interface Command {

    public String execute(HttpServletRequest request) throws SQLException;
}
