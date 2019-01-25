import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Test Gambler behaves as expected.
 * 
 * Code involving random numbers are not easy to test repeatably. The tests in
 * this class use 0% and 100% probabilities to avoid the problem.
 * 
 */
public class GamblerTest {

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
            Gambler.main(args);
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

    @Test
    public void test1() {
        // Call Gambler.main() and check the output is as expected.
        String[] args = { "2", "20", "20", "1" };
        assertEquals("100% wins\nAvg # bets: 18", captureOutputOfMain(args));
    }

    @Test
    public void test2() {
        // Call Gambler.main() and check the output is as expected.
        String[] args = { "2", "20", "20", "0" };
        assertEquals("0% wins\nAvg # bets: 2", captureOutputOfMain(args));
    }

}
