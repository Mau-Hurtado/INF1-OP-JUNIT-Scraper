package localization;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class LocalInfoTest {

    // Index into LOCAL_INFO for default locale of current PC - change as appropriate
    // Add default locale values to LOCAL_INFO if necessary.
    private static final int DEFAULT_LOCALE_INDEX = 0;
    
    // Unicode non-breaking space character - used in French number formatting
    private static final char NBSP = '\u00A0';
    
    private static final String[][] LOCAL_INFO = {
        {"en", "GB", "Welcome\n" + 
                "The current locale is en_GB\n" + 
                "The date is 16 February 2016\n" + 
                "The time is 22:37:12 GMT\n" + 
                "Number formatting: 1,234,567.89\n" + 
                "Currency formatting: £1,234,567.89"},

        {"en", "US", "Welcome\n" + 
                "The current locale is en_US\n" + 
                "The date is February 16, 2016\n" + 
                "The time is 10:37:12 PM GMT\n" + 
                "Number formatting: 1,234,567.89\n" + 
                "Currency formatting: $1,234,567.89"},

        // French number formatting uses the unicode non-breaking space to separate thousands        
        {"fr", "FR", "Bienvenue\n" + 
                "La localisation actuelle est fr_FR\n" + 
                "La date est le 16 février 2016\n" + 
                "L'heure est 22:37:12 GMT\n" + 
                "Format nombre: 1" + NBSP + "234" + NBSP + "567,89\n" + 
                "Format devise: 1" + NBSP + "234" + NBSP + "567,89 €"},

        {"de", "DE", "Herzlich Willkommen\n" + 
                "Aktueller Standort de_DE\n" + 
                "Das Datum ist 16. Februar 2016\n" + 
                "Es ist 22:37:12 GMT\n" + 
                "Zahlenformatierung: 1.234.567,89\n" + 
                "Währungsformatierung: 1.234.567,89 €"},
                
    };
    
    /**
     * Convert Windows format linefeeds to Unix/Linux/OSX format for string comparison
     * @param input input string containing newlines in '\n', '\r', '\r\n' format
     * @return string containing only '\n' newlines
     */
    private String convertToUnix(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
    }

    @Test
    public void testGetLocalInfo_default() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 16, 22, 37, 12);    
        
        assertEquals(LOCAL_INFO[DEFAULT_LOCALE_INDEX][2], convertToUnix(LocalInfo.getLocalInfo(null, null, calendar).trim()));
    }
    
    @Test
    public void testGetLocalInfo_all() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 16, 22, 37, 12);    
        
        for (String[] currentLocalInfo : LOCAL_INFO) {
            assertEquals(currentLocalInfo[2], convertToUnix(LocalInfo.getLocalInfo(currentLocalInfo[0], currentLocalInfo[1], calendar).trim()));
        }
    }

}
