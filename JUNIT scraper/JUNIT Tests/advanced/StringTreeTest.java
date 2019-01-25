package generics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StringTreeTest {

    @Test
    public void testGetAllNodes() {
        StringTree node1 = new StringTree("node1");
        StringTree node2 = new StringTree("node2");
        StringTree node3 = new StringTree("node3");
        StringTree node4 = new StringTree("node4");
        StringTree node5 = new StringTree("node5");
        
        node1.addChild(node2);
        node1.addChild(node3);
        node3.addChild(node4);
        node3.addChild(node5);
        
        try {
            node2.getAllValues(-1);
            fail("should have thrown an IllegalArgumentException");
        }
        catch (IllegalArgumentException ex) {
            // Expected exception
        }
        
        assertArrayEquals(new String[] {"node2"}, node2.getAllValues(0).toArray());
        assertArrayEquals(new String[] {}, node2.getAllValues(1).toArray());

        assertArrayEquals(new String[] {"node1"}, node1.getAllValues(0).toArray());
        assertArrayEquals(new String[] {"node2", "node3"}, node1.getAllValues(1).toArray());
        assertArrayEquals(new String[] {"node4", "node5"}, node1.getAllValues(2).toArray());
        assertArrayEquals(new String[] {}, node1.getAllValues(3).toArray());

    }

    @Test
    public void testToString() {
        StringTree node1 = new StringTree("node1");
        StringTree node2 = new StringTree("node2");
        StringTree node3 = new StringTree("node3");
        StringTree node4 = new StringTree("node4");
        StringTree node5 = new StringTree("node5");
        
        node1.addChild(node2);
        node1.addChild(node3);
        node3.addChild(node4);
        node3.addChild(node5);
        
        assertEquals("'node2' => []", node2.toString());
        assertEquals("'node1' => ['node2' => [], 'node3' => ['node4' => [], 'node5' => []]]", node1.toString());
    }

}
