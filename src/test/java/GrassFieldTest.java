import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GrassFieldTest {
    @Test
    public void canMoveToTest(){
        IWorldMap map = new GrassField(10);
        Animal animal = new Animal(map);
        map.place(animal);
        assertFalse(map.canMoveTo(new Vector2d(2,2)));
    }

    @Test
    public void ObjectAtTest(){
        IWorldMap map = new GrassField(10);
        Animal a1 = new Animal(map, new Vector2d(3,4));
        Animal a2 = new Animal(map);
        map.place(a1);
        map.place(a2);
        assertEquals(map.objectAt(new Vector2d(3,4)),a1);
        assertEquals(map.objectAt(new Vector2d(2,2)),a2);

        Object oat100 = map.objectAt(new Vector2d(100,100));
        assertTrue(oat100 == null || oat100.equals(new Grass(new Vector2d(100, 100))));
    }

    @Test
    public void isOccupiedTest() {
        IWorldMap map = new GrassField(10);
        Animal a1 = new Animal(map, new Vector2d(3,4));
        Animal a2 = new Animal(map);
        map.place(a1);
        map.place(a2);
        assertTrue(map.isOccupied(new Vector2d(3,4)));
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertTrue(!map.isOccupied(new Vector2d(5,5)) || map.objectAt(new Vector2d(5,5)).getClass() == Grass.class);

    }



    @Test
    public void placeTest(){
        try{
            IWorldMap map = new GrassField(10);
            Animal a1 = new Animal(map);
            Animal a2 = new Animal(map);
            assertTrue(map.place(a1));
            assertFalse(map.place(a2));
        }catch(IllegalArgumentException ex){
            System.out.println(ex);
        }
    }

}
