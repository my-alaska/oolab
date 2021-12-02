import agh.ics.oop.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

//testy do wersji kodu bez mapy
public class AnimalMoveTest {


    @Test
    void forwardAndRightTest(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,3));
        zwierz.move(MoveDirection.RIGHT);
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,3));
        zwierz.move(MoveDirection.FORWARD);
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,3));
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,3));
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,2));
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,2));
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,2));
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,2));
    }

    @Test
    void bakwardAndLeftTest(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,1));
        zwierz.move(MoveDirection.LEFT);
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,1));
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,1));
        zwierz.move(MoveDirection.LEFT);
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,1));
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,2));
        zwierz.move(MoveDirection.LEFT);
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,2));
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,2));
        zwierz.move(MoveDirection.LEFT);
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,2));
    }

    @Test
    void forwardBoundTest(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,4));

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,0));
    }

    @Test
    void backwardBoundTest(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("^", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,0));

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("v", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(2,4));
    }

    @Test
    void forwardBoundTestHorzontal(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        zwierz.move(MoveDirection.RIGHT);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(4,2));

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(0,2));
    }

    @Test
    void backwardBoundTestHorizontal(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);
        zwierz.move(MoveDirection.RIGHT);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(0,2));

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("<", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(4,2));
    }

    @Test
    void arrayTest(){
        IWorldMap map = new RectangularMap(5,5);
        Animal zwierz = new Animal(map);

        String[] directions = {"cofee","r","Tea", "f", "f", "f", "right", "forward", "figgwasd", "l", "backward"};
        for (MoveDirection d : OptionsParser.parse(directions)){
            zwierz.move(d);
        }
        assertEquals(">", zwierz.toString());
        assertEquals(zwierz.getPosition(),new Vector2d(3,1));
    }

}
