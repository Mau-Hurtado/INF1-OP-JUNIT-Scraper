package jdbc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

public class JDBCConnectionTest {

    @Test
    public void testGetDatabaseConnection() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        assertEquals("HSQL Database Engine Driver", connection.getMetaData().getDriverName());
    }

    @Test
    public void testListAllTables() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        String[] tableNames = FilmDatabase.listAllTables(connection);
        assertNotNull(tableNames);
        String[] expectedNames = {"Actor", "Film", "Director", "PerformsIn"};
        assertArrayEquals(expectedNames, tableNames);
    }

}
