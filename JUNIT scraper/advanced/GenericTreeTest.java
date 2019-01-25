package generics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the Tree template class using Strings and Integers
 *
 */
public class GenericTreeTest {

    @Test
    public void testGetAllNodes_Integer() {
        Tree<Integer> node1 = new Tree<>(1);
        Tree<Integer> node2 = new Tree<>(2);
        Tree<Integer> node3 = new Tree<>(3);
        Tree<Integer> node4 = new Tree<>(4);
        Tree<Integer> node5 = new Tree<>(5);
        
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
        
        assertArrayEquals(new Integer[] {2}, node2.getAllValues(0).toArray());
        assertArrayEquals(new Integer[] {}, node2.getAllValues(1).toArray());

        assertArrayEquals(new Integer[] {1}, node1.getAllValues(0).toArray());
        assertArrayEquals(new Integer[] {2, 3}, node1.getAllValues(1).toArray());
        assertArrayEquals(new Integer[] {4, 5}, node1.getAllValues(2).toArray());
        assertArrayEquals(new Integer[] {}, node1.getAllValues(3).toArray());

    }

    @Test
    public void testToString_Integer() {
        Tree<Integer> node1 = new Tree<>(1);
        Tree<Integer> node2 = new Tree<>(2);
        Tree<Integer> node3 = new Tree<>(3);
        Tree<Integer> node4 = new Tree<>(4);
        Tree<Integer> node5 = new Tree<>(5);
        
        node1.addChild(node2);
        node1.addChild(node3);
        node3.addChild(node4);
        node3.addChild(node5);
        
        assertEquals("'2' => []", node2.toString());
        assertEquals("'1' => ['2' => [], '3' => ['4' => [], '5' => []]]", node1.toString());
    }

    @Test
    public void testGetAllNodes_String() {
        Tree<String> node1 = new Tree<>("node1");
        Tree<String> node2 = new Tree<>("node2");
        Tree<String> node3 = new Tree<>("node3");
        Tree<String> node4 = new Tree<>("node4");
        Tree<String> node5 = new Tree<>("node5");
        
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
    public void testToString_String() {
        Tree<String> node1 = new Tree<>("node1");
        Tree<String> node2 = new Tree<>("node2");
        Tree<String> node3 = new Tree<>("node3");
        Tree<String> node4 = new Tree<>("node4");
        Tree<String> node5 = new Tree<>("node5");
        
        node1.addChild(node2);
        node1.addChild(node3);
        node3.addChild(node4);
        node3.addChild(node5);
        
        assertEquals("'node2' => []", node2.toString());
        assertEquals("'node1' => ['node2' => [], 'node3' => ['node4' => [], 'node5' => []]]", node1.toString());
    }

}
