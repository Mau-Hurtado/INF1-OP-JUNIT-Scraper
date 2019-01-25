package jdbc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.Test;

public class JDBCQueriesTest {

    @Test
    public void testGetActorById() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        String[] actorData = FilmDatabase.getActorById(connection, "UNKNOWN");
        assertNull(actorData);
        
        actorData = FilmDatabase.getActorById(connection, "EMG32");
        assertNotNull(actorData);
        String[] expectedData = {"EMG32", "Ewan McGregor", "British", "43"};
        assertArrayEquals(expectedData, actorData);
    }

    @Test
    public void testAddNewActor() throws Exception {
        Connection connection = FilmDatabase.getDatabaseConnection();
        assertNotNull(connection);
        
        boolean response = FilmDatabase.addNewActor(connection, "EMG32", "someone else", "somewhere else", 99);
        assertFalse(response);
        
        // Check record has not been corrupted
        String[]  actorData = FilmDatabase.getActorById(connection, "EMG32");
        assertNotNull(actorData);
        String[] expectedData = {"EMG32", "Ewan McGregor", "British", "43"};
        assertArrayEquals(expectedData, actorData);
        
        // Delete new actor (if somehow already there)
        deleteActor(connection, "NEW1");
        
        // Check record is not already there
        assertNull(FilmDatabase.getActorById(connection, "NEW1"));

        try {
            response = FilmDatabase.addNewActor(connection, "NEW1", "someone else", "somewhere else", 99);
            assertTrue(response);
        
            // Check added
            actorData = FilmDatabase.getActorById(connection, "NEW1");
            assertNotNull(actorData);
            expectedData = new String[] {"NEW1", "someone else", "somewhere else", "99"};
            assertArrayEquals(expectedData, actorData);
            
            // Check repeated addition fails
            response = FilmDatabase.addNewActor(connection, "NEW1", "someone else", "somewhere else", 99);
            assertFalse(response);

        }
        finally {
            // Clean up
            deleteActor(connection, "NEW1");
        }
    }
    
    
    private void deleteActor(Connection connection, String actorId) throws Exception {
        PreparedStatement statement = connection.prepareStatement("delete from \"Actor\" where \"actorId\" = ?");
        statement.setString(1, actorId);
        statement.executeUpdate();
    }

}
