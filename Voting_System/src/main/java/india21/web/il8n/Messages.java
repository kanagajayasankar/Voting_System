package india21.web.i18n;

import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private static final String MESSAGE_BUNDLE = "india21.web.i18n.strings";

    public static String getMessage(String key, Object... arguments) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_BUNDLE, fc.getViewRoot().getLocale());
        try {
            String message = bundle.getString(key);
            if (message == null) {
                return "???" + key + "???";
            }
            return MessageFormat.format(message, arguments);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

}
