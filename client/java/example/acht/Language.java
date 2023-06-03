package example.acht;

import java.util.Locale;
import java.util.ResourceBundle;


public class Language {
    public ResourceBundle setLanguage(String language) {
        ResourceBundle resourceBundle;
        switch (language) {
            case "Russian" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("ru"));
            case "Macedonian" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("mk"));
            case "Croatian" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("hr"));
            default -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("en", "IN"));
        }
        return resourceBundle;
    }
}
