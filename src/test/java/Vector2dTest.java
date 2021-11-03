import org.junit.jupiter.api.Test;
import agh.ics.oop.Vector2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Vector2dTest {
    @Test
    void equalsTest(){
        assertEquals(new Vector2d(2,4), new Vector2d(2,4));
    }
    @Test
    void toStringTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals( testVector.toString(), "(1,2)");
    }
    @Test
    void precedesTest(){
        Vector2d testVector = new Vector2d(1,1);
        assertTrue( testVector.precedes(new Vector2d(2,2)));
        assertFalse(testVector.precedes(new Vector2d(2,0)));
        assertFalse(testVector.precedes(new Vector2d(0,2)));
        assertFalse(testVector.precedes(new Vector2d(0,0)));
    }
    @Test
    void followsTest(){
        Vector2d testVector = new Vector2d(1,1);
        assertTrue( testVector.follows(new Vector2d(0,0)));
        assertFalse(testVector.follows(new Vector2d(2,0)));
        assertFalse(testVector.follows(new Vector2d(0,2)));
        assertFalse(testVector.follows(new Vector2d(2,2)));
    }
    @Test
    void upperRightTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals(testVector.upperRight(new Vector2d(2,1)), new Vector2d(2,2) );
    }
    @Test
    void lowerLeftTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals(testVector.lowerLeft(new Vector2d(2,1)), new Vector2d(1,1) );
    }
    @Test
    void addTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals(testVector.add(new Vector2d(2,1)) ,new Vector2d(3,3));
    }
    @Test
    void subtractTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals(testVector.subtract(new Vector2d(2,1)) ,new Vector2d(-1,1));
    }
    @Test
    void oppositeTest(){
        Vector2d testVector = new Vector2d(1,2);
        assertEquals(testVector.opposite() ,new Vector2d(-1,-2));
    }
}