package UCController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ibranovic
 * komanda za prosledjivanje na jsp stranu bez akcije
 */
public class NullCommand implements Command {
    private String next="";

    NullCommand(String next) {
        this.next = next;
    }
    
    @Override
    public String execute(HttpServletRequest request) {
        return next;
    }

}
