package util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validate {

    public static boolean exists(String param) {
        return (param != null && !param.isEmpty());
    }

    public static boolean isInteger(String param) {
        try {
            if (!exists(param)) {
                throw new Exception();
            } else {
                Integer.valueOf(param);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isDouble(String param) {
        try {
            if (!exists(param)) {
                throw new Exception();
            } else {
                Double.valueOf(param);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static String prekontrolisiEmail(String Email) {

        String poruka = "";
        Email = Email.toLowerCase();

        if (!Email.contains("@") && !Email.contains(".")) {
            poruka = "Mail Mora Da Sadrzi @ I .";
        }

        return poruka;
    }

    public static String pokupiPoruku(String message) {

        String error = message.substring(11);
        error = error.substring(0, error.indexOf("\nORA-06512"));
        return error;
    }

    public static String preurediStringZaIme(String ime) {

        String novoIme;

        novoIme = Character.toUpperCase(ime.charAt(0)) + ime.substring(1).toLowerCase();
        return novoIme;
    }

    public static String kontrolisiJmbg(String jmbg) {

        String poruka = "";

        if (jmbg.length() != 13) {
            return poruka = "neispravna duzina Jmbg-a!";
        }

        for (int i = 0; i < jmbg.length(); i++) {
            if (!Character.isDigit(jmbg.charAt(i))) {
                return poruka = "jmbg ne sme sadrzati slova!";
            }
        }
        return poruka;

    }

    public static String kontrolisiTelefon(String telefon) {
        String pozivniBrojevi[] = {"060", "061", "062", "063", "064", "065", "066", "069"};
        String poruka = "";
        if (telefon.length() >= 11 && telefon.length() <= 12) {
            String pozivni = telefon.substring(0, 3);
            String slash = String.valueOf(telefon.charAt(3));
            String prvaTri = telefon.substring(4, 7);
            String crtica = String.valueOf(telefon.charAt(7));
            String zadnjiBrojevi;
            if (telefon.length() == 11) {
                zadnjiBrojevi = telefon.substring(8, 11);
            } else {
                zadnjiBrojevi = telefon.substring(8, 12);
            }

            boolean isIt = false;
            for (int i = 0; i < pozivniBrojevi.length; i++) {
                if (pozivni.equals(pozivniBrojevi[i])) {
                    isIt = true;
                    break;
                }
            }
            if (isIt != true) {
                return poruka = "Nije dobar pozivni broj!";
            }

            if (!slash.equals("/")) {
                return poruka = "nejie dobar format";
            }

            for (int i = 0; i < prvaTri.length(); i++) {
                if (!Character.isDigit(prvaTri.charAt(i))) {
                    return poruka = "Nije dobar format";
                }
            }
            if (!crtica.equals("-")) {
                return poruka = "nejie dobar format";
            }

            for (int i = 0; i < zadnjiBrojevi.length(); i++) {
                if (!Character.isDigit(zadnjiBrojevi.charAt(i))) {
                    poruka = "Nije dobar format";
                }
            }

        } else {
            return poruka = " netacan broj karakterA!";
        }

        return poruka;
    }

    public static String dajVrijemeSada() {

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
}
    
    public static Integer vratiBroj(String id){
    
                id = id.replaceAll("\\D+", "");
                Integer sifra = Integer.valueOf(id);
                return sifra;
    }
    
    
    public static String konvertujDatum(String ulaz){
           
            String povratak ="";
        
            try {
       
            SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
            
            Date date;
            
            date = sdfSource.parse(ulaz);
            
            SimpleDateFormat sdfDestination = new SimpleDateFormat("dd.MM.yyyy");
            
            povratak = (sdfDestination.format(date));
            
          
        } catch (ParseException ex) {
           ex.printStackTrace();
        }
          return povratak;
    }
    

    
}
