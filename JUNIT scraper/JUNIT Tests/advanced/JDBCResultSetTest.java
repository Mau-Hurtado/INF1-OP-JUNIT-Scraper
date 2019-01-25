package jdbc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

public class JDBCResultSetTest {

    @Test
    public void testListAllColumnsForTable() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        String[] columnNames = FilmDatabase.listAllColumnsForTable(connection, "Actor");
        assertNotNull(columnNames);
        String[] expectedNames = {"actorId", "name", "nationality", "age"};
        assertArrayEquals(expectedNames, columnNames);
        
        columnNames = FilmDatabase.listAllColumnsForTable(connection, "Film");
        assertNotNull(columnNames);
        expectedNames = new String[] {"filmId", "title", "year", "directorId"};
        assertArrayEquals(expectedNames, columnNames);
    }

    @Test
    public void testGetTableData() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        String[][] tableData = FilmDatabase.getTableData(connection, "Film", new String[] {"filmId", "title", "year"});
        assertNotNull(tableData);
        assertTrue("Too few rows in the result", tableData.length > 10);
        
        // Check the first few rows
        String[][] expectedData = {
                { "BAT92", "Batman Returns", "1992",}, 
                { "DK008", "The Dark Knight", "2008", },
                { "DKR12", "The Dark Knight Rises", "2012", }, 
        };

        for (int i = 0; i < expectedData.length; i++) {
            assertArrayEquals("Row " + i + " not as expected", expectedData[i], tableData[i]);
        }
    }

    @Test
    public void testViewTable() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        String tableData = FilmDatabase.viewTable(connection, "Film");
        assertNotNull(tableData);
        
        // Check the first few rows
        String expectedData = "filmId, title, year, directorId\n" + 
                "BAT92, Batman Returns, 1992, BUR34\n" + 
                "DK008, The Dark Knight, 2008, CN345\n" + 
                "DKR12, The Dark Knight Rises, 2012, CN345\n";
        assertTrue("Expected data to start with:\n" + expectedData + "\n\nbut was:\n" + tableData, 
                tableData.replaceAll("\r\n", "\n").startsWith(expectedData));
    }

}
