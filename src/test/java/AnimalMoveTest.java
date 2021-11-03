import agh.ics.oop.Animal;

import agh.ics.oop.MoveDirection;
import agh.ics.oop.OptionParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AnimalMoveTest {
    @Test
    void forwardAndRightTest(){
        Animal zwierz = new Animal();
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("(2,3) Północ", zwierz.toString());
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("(2,3) Wschód", zwierz.toString());
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("(3,3) Wschód", zwierz.toString());
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("(3,3) Południe", zwierz.toString());
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("(3,2) Południe", zwierz.toString());
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("(3,2) Zachód", zwierz.toString());
        zwierz.move(MoveDirection.FORWARD);
        assertEquals("(2,2) Zachód", zwierz.toString());
        zwierz.move(MoveDirection.RIGHT);
        assertEquals("(2,2) Północ", zwierz.toString());
    }

    @Test
    void bakwardAndLeftTest(){
        Animal zwierz = new Animal();
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("(2,1) Północ", zwierz.toString());
        zwierz.move(MoveDirection.LEFT);
        assertEquals("(2,1) Zachód", zwierz.toString());
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("(3,1) Zachód", zwierz.toString());
        zwierz.move(MoveDirection.LEFT);
        assertEquals("(3,1) Południe", zwierz.toString());
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("(3,2) Południe", zwierz.toString());
        zwierz.move(MoveDirection.LEFT);
        assertEquals("(3,2) Wschód", zwierz.toString());
        zwierz.move(MoveDirection.BACKWARD);
        assertEquals("(2,2) Wschód", zwierz.toString());
        zwierz.move(MoveDirection.LEFT);
        assertEquals("(2,2) Północ", zwierz.toString());
    }

    @Test
    void forwardBoundTest(){
        Animal zwierz = new Animal();
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("(2,4) Północ", zwierz.toString());

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("(2,0) Południe", zwierz.toString());
    }

    @Test
    void backwardBoundTest(){
        Animal zwierz = new Animal();
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("(2,0) Północ", zwierz.toString());

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("(2,4) Południe", zwierz.toString());
    }

    @Test
    void forwardBoundTestHorzontal(){
        Animal zwierz = new Animal();
        zwierz.move(MoveDirection.RIGHT);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("(4,2) Wschód", zwierz.toString());

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.FORWARD);
        }
        assertEquals("(0,2) Zachód", zwierz.toString());
    }

    @Test
    void backwardBoundTestHorizontal(){
        Animal zwierz = new Animal();
        zwierz.move(MoveDirection.RIGHT);
        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("(0,2) Wschód", zwierz.toString());

        zwierz.move(MoveDirection.LEFT);
        zwierz.move(MoveDirection.LEFT);

        for(int i = 0; i < 7; i++){
            zwierz.move(MoveDirection.BACKWARD);
        }
        assertEquals("(4,2) Zachód", zwierz.toString());
    }

    @Test
    void arrayTest(){
        Animal zwierz = new Animal();

        String[] directions = {"cofee","r","Tea", "f", "f", "f", "right", "forward", "figgwasd", "l", "backward"};
        for (MoveDirection d : OptionParser.parse(directions)){
            zwierz.move(d);
        }
        assertEquals("(3,1) Wschód", zwierz.toString());
    }

}
