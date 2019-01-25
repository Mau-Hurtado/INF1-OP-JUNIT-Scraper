import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Test LopsidedNumberTriangle behaves as expected
 * 
 */
public class LopsidedNumberTriangleTest {

    /**
     * Temporarily captures the output to the standard output stream, then
     * restores the standard output stream once complete.
     * 
     * Note: the implementation details of this function are advanced. If you
     * don't understand it just now, don't worry.
     * 
     * @param args
     *            arguments to pass to main function of class to be tested
     * @return output result of calling main function of class to be tested
     */
    private String captureOutputOfMain(String args[]) {
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            LopsidedNumberTriangle.main(args);
        }
        finally {
            System.setOut(originalOut);
        }
        return convertToUnix(outputStream.toString().trim());
    }

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

    /**
     * Test to ensure the test helper code works :)
     */
    @Test
    public void testConvertToUnix() {
        assertEquals(null, convertToUnix(null));
        assertEquals("", convertToUnix(""));
        assertEquals("   \n   \t\n\t", convertToUnix("   \n   \t\n\t"));
        assertEquals("\ntest\n\ntest\n", convertToUnix("\ntest\n\ntest\n"));
        assertEquals("\ntest\n\ntest\n", convertToUnix("\rtest\r\rtest\r"));
        assertEquals("\ntest\n\ntest\n", convertToUnix("\r\ntest\r\n\r\ntest\r\n"));
        
        String newline = System.getProperty("line.separator");
        assertEquals("\ntest\n\ntest\n", convertToUnix(newline + "test" + newline + newline + "test" + newline));
    }
    

    @Test
    public void test1() {
        // Call LopsidedNumberTriangle.main() and check the output is as
        // expected.
        assertEquals("1\n22\n333\n4444\n55555\n666666\n7777777\n88888888\n999999999", captureOutputOfMain(null));
    }
    
}
