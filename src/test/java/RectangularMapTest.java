import agh.ics.oop.*;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.RectangularMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RectangularMapTest {
    @Test
    public void canMoveToTest(){
        IWorldMap map = new RectangularMap(11, 11);
        Animal animal = new Animal(map);
        map.place(animal);
        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertFalse(map.canMoveTo(new Vector2d(-1,2)));
        assertFalse(map.canMoveTo(new Vector2d(2,-1)));
        assertFalse(map.canMoveTo(new Vector2d(-1,100)));
        assertFalse(map.canMoveTo(new Vector2d(100,-1)));
    }

    @Test
    public void isOccupiedTest() {
        IWorldMap map = new RectangularMap(11,11);
        Animal a1 = new Animal(map, new Vector2d(3,4));
        Animal a2 = new Animal(map);
        map.place(a1);
        map.place(a2);
        assertTrue(map.isOccupied(new Vector2d(3,4)));
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertFalse(map.isOccupied(new Vector2d(5,5)));
        assertFalse(map.isOccupied(new Vector2d(100,100)));
    }

    @Test
    public void ObjectAtTest(){
        IWorldMap map = new RectangularMap(11,11);
        Animal a1 = new Animal(map, new Vector2d(3,4));
        Animal a2 = new Animal(map);
        map.place(a1);
        map.place(a2);
        assertEquals(map.objectAt(new Vector2d(3,4)),a1);
        assertEquals(map.objectAt(new Vector2d(2,2)),a2);
        assertEquals(map.objectAt(new Vector2d(100,100)),null);
        assertEquals(map.objectAt(new Vector2d(5,5)),null);
    }

    @Test
    public void placeTest(){
        try {
            IWorldMap map = new RectangularMap(11,11);
            Animal a1 = new Animal(map);
            Animal a2 = new Animal(map);
            assertTrue(map.place(a1));
            assertFalse(map.place(a2));
        }catch(IllegalArgumentException ex){
            System.out.println(ex);
        }
    }
}
