package io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ReadPDFInfoTest {

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
    public void testReadPDFInfo() throws IOException {
        File testFile = new File("TestDocument.pdf");
        if (!testFile.exists()) {
            fail("Download the file " + testFile.getName() + " from http://www.inf.ed.ac.uk/teaching/courses/inf1/op/2016/labs/resources/TestDocument.pdf into your project directory first.");
        }
        
        String result = convertToUnix(ReadPDFInfo.readPDFInfo(testFile));
        assertEquals("Title: Test Document\n" + 
                "Subject: The Subject\n" + 
                "Author: A N Other\n" + 
                "Producer: Latex with hyperref\n" + 
                "Creator: pdflatex\n" + 
                "CreationDate: D:20160118224408Z", result.trim());
    }

}
