package india21.logic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringUtils {

    private StringUtils() {
    }

    public static Boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static Date parseDate(String date) {
        try {
            DateFormat dateFormat=new SimpleDateFormat(getLabel("dateFormat"));
            return dateFormat.parse(date);
        } catch (ParseException exception) {
            Logger.getLogger("StringUtils").log(Level.WARNING, "ParseException", exception);
            return null;
        }
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat=new SimpleDateFormat(getLabel("dateFormat"));
        return date == null ? null : dateFormat.format(date);
    }

    public static String getLabel(String key, Object... parameters) {
        String label = getResource("india21.logic.i18n.strings", key);
        return parameters == null ? label : String.format(label, parameters);
    }


    public static String getResource(String resourceName, String key){
        return ResourceBundle
                .getBundle(resourceName)
                .getString(key);
    }
}
