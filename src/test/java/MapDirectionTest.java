import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    void nextTest(){
        MapDirection test = MapDirection.NORTH;
        assertEquals(test.next(), MapDirection.EAST);
        test = MapDirection.EAST;
        assertEquals(test.next(), MapDirection.SOUTH);
        test = MapDirection.SOUTH;
        assertEquals(test.next(), MapDirection.WEST);
        test = MapDirection.WEST;
        assertEquals(test.next(), MapDirection.NORTH);
    }
    @Test
    void prevTest(){
        MapDirection test = MapDirection.NORTH;
        assertEquals(test.previous(), MapDirection.WEST);
        test = MapDirection.WEST;
        assertEquals(test.previous(), MapDirection.SOUTH);
        test = MapDirection.SOUTH;
        assertEquals(test.previous(), MapDirection.EAST);
        test = MapDirection.EAST;
        assertEquals(test.previous(), MapDirection.NORTH);
    }
}