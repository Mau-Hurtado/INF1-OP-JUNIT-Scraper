package strings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CheckAddressTest {

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
    public void testIsValidPostcode() {
        assertTrue(CheckAddress.isValidPostcode("eh5 4aa"));
        assertTrue(CheckAddress.isValidPostcode("EH5 4AA"));
        assertTrue(CheckAddress.isValidPostcode("eh55 4aa"));
        assertTrue(CheckAddress.isValidPostcode("e5 4ab"));
        assertTrue(CheckAddress.isValidPostcode("eh5a 4ab"));
        assertTrue(CheckAddress.isValidPostcode("e5a 4ab"));
        
        assertFalse(CheckAddress.isValidPostcode("EH54AA"));
        assertFalse(CheckAddress.isValidPostcode("EH5 4A"));
        assertFalse(CheckAddress.isValidPostcode("EH5 AA"));
        assertFalse(CheckAddress.isValidPostcode("EH 4AA"));
        assertFalse(CheckAddress.isValidPostcode("5 4AA"));
        assertFalse(CheckAddress.isValidPostcode("EH5 4"));
        assertFalse(CheckAddress.isValidPostcode(""));
    }
    
    @Test
    public void testIsCanonicalPostcode() {
        assertFalse(CheckAddress.isCanonicalPostcode("eh5 4aa"));
        assertTrue(CheckAddress.isCanonicalPostcode("EH5 4AA"));
        assertTrue(CheckAddress.isCanonicalPostcode("E5 4AA"));
        assertTrue(CheckAddress.isCanonicalPostcode("EH5A 4AA"));
        assertTrue(CheckAddress.isCanonicalPostcode("E5A 4AA"));
        assertFalse(CheckAddress.isCanonicalPostcode("eh55 4aa"));
        assertFalse(CheckAddress.isCanonicalPostcode("e5 4ab"));
        assertFalse(CheckAddress.isCanonicalPostcode("eh5a 4ab"));
        assertFalse(CheckAddress.isCanonicalPostcode("e5a 4ab"));
        
        assertFalse(CheckAddress.isCanonicalPostcode("EH54AA"));
        assertFalse(CheckAddress.isCanonicalPostcode("EH5 4A"));
        assertFalse(CheckAddress.isCanonicalPostcode("EH5 AA"));
        assertFalse(CheckAddress.isCanonicalPostcode("EH 4AA"));
        assertFalse(CheckAddress.isCanonicalPostcode("5 4AA"));
        assertFalse(CheckAddress.isCanonicalPostcode("EH5 4"));
        assertFalse(CheckAddress.isCanonicalPostcode(""));
    }

    @Test
    public void testGetCanonicalPostcode() {
        assertEquals("EH5 4AA", CheckAddress.getCanonicalPostcode("eh5 4aa"));
        assertEquals("EH5 4AA", CheckAddress.getCanonicalPostcode("EH5 4AA"));
        assertEquals("E5 4AA", CheckAddress.getCanonicalPostcode("E5 4AA"));
        assertEquals("EH55 4AA", CheckAddress.getCanonicalPostcode("eh55 4aa"));
        assertEquals("E5 4AB", CheckAddress.getCanonicalPostcode("e5 4ab"));
        assertEquals("EH5A 4AB", CheckAddress.getCanonicalPostcode("eh5a 4ab"));
        assertEquals("E5A 4AB", CheckAddress.getCanonicalPostcode("e5a 4ab"));
        
        assertEquals("", CheckAddress.getCanonicalPostcode("EH54AA"));
        assertEquals("", CheckAddress.getCanonicalPostcode("EH5 4A"));
        assertEquals("", CheckAddress.getCanonicalPostcode("EH5 AA"));
        assertEquals("", CheckAddress.getCanonicalPostcode("EH 4AA"));
        assertEquals("", CheckAddress.getCanonicalPostcode("5 4AA"));
        assertEquals("", CheckAddress.getCanonicalPostcode("EH5 4"));
        assertEquals("", CheckAddress.getCanonicalPostcode(""));
    }

    @Test
    public void testIsValidAddress() {
        assertTrue(CheckAddress.isValidAddress("10 some street, edinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street, edinburgh, eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street\nedinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street\nedinburgh\neh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street,\nedinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street,\nedinburgh,\neh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street,\nedinburgh,\neh5a 4aa"));

        assertTrue(CheckAddress.isValidAddress("10 some street, some district, edinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street, some district, edinburgh, eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street\nsome district\nedinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street\nsome district\nedinburgh\neh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street,\nsome district,\nedinburgh eh5 4aa"));
        assertTrue(CheckAddress.isValidAddress("10 some street,\nsome district,\nedinburgh,\neh5 4aa"));

        assertFalse(CheckAddress.isValidAddress("edinburgh eh5 4aa"));
        assertFalse(CheckAddress.isValidAddress("edinburgh, eh5 4aa"));
        assertFalse(CheckAddress.isValidAddress("10 some street,\nedinburgh"));
        assertFalse(CheckAddress.isValidAddress("10 some street, some district, edinburgh"));
    }

    @Test
    public void testIsCanonicalAddress() {
        assertFalse(CheckAddress.isCanonicalAddress("10 some street, edinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street, edinburgh, eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nedinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nedinburgh\neh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street,\nedinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street,\nedinburgh,\neh5 4aa"));

        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nedinburgh\nEH5 4AA"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nEDINBURGH\neh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nEDINBURGH EH5 4AA"));
        assertTrue(CheckAddress.isCanonicalAddress("10 some street\nEDINBURGH\nEH5 4AA"));
        
        assertFalse(CheckAddress.isCanonicalAddress("10 some street, some district, edinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street, some district, edinburgh, eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nsome district\nedinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nsome district\nedinburgh\neh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street,\nsome district,\nedinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street,\nsome district,\nedinburgh,\neh5 4aa"));

        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nsome district\nedinburgh\nEH5 4AA"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nsome district\nEDINBURGH\neh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street\nsome district\nEDINBURGH EH5 4AA"));
        assertTrue(CheckAddress.isCanonicalAddress("10 some street\nsome district\nEDINBURGH\nEH5 4AA"));
        assertTrue(CheckAddress.isCanonicalAddress("10 some street\nsome district\nEDINBURGH\nEH5A 4AA"));

        assertFalse(CheckAddress.isCanonicalAddress("edinburgh eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("edinburgh, eh5 4aa"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street,\nedinburgh"));
        assertFalse(CheckAddress.isCanonicalAddress("10 some street, some district, edinburgh"));
    }

    @Test
    public void testGetCanonicalAddress() {
        String address = "10 some street\nEDINBURGH\nEH5 4AA";
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street, edinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street, edinburgh, eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nedinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nedinburgh\neh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street,\nedinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street,\nedinburgh,\neh5 4aa")));

        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nedinburgh\nEH5 4AA")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nEDINBURGH\neh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nEDINBURGH EH5 4AA")));
        
        address = "10 some street\nsome district\nEDINBURGH\nEH5 4AA";
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street, some district, edinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street, some district, edinburgh, eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nedinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nedinburgh\neh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street,\nsome district,\nedinburgh eh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street,\nsome district,\nedinburgh,\neh5 4aa")));

        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nedinburgh\nEH5 4AA")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nEDINBURGH\neh5 4aa")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nEDINBURGH EH5 4AA")));
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nEDINBURGH\nEH5 4AA")));
        
        address = "10 some street\nsome district\nEDINBURGH\nEH5A 4AA";
        assertEquals(address, convertToUnix(CheckAddress.getCanonicalAddress("10 some street\nsome district\nEDINBURGH\nEH5A 4AA")));

        assertEquals("", convertToUnix(CheckAddress.getCanonicalAddress("edinburgh eh5 4aa")));
        assertEquals("", convertToUnix(CheckAddress.getCanonicalAddress("edinburgh, eh5 4aa")));
        assertEquals("", convertToUnix(CheckAddress.getCanonicalAddress("10 some street,\nedinburgh")));
        assertEquals("", convertToUnix(CheckAddress.getCanonicalAddress("10 some street, some district, edinburgh")));
    }

}
