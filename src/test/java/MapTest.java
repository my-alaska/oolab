import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    @Test
    public void forwardAndRightTest() {
        String[] tab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse( tab );
        IWorldMap map = new RectangularMap(10, 10);
        Vector2d[] positions = { new Vector2d(3,3), new Vector2d(5,5) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertEquals(positions[0],new Vector2d(4,0));
        assertEquals(positions[1],new Vector2d(5,8));
        assertEquals(MapDirection.SOUTH, ((Animal) map.objectAt(new Vector2d(4,0))).getDirection());
        assertEquals(MapDirection.NORTH, ((Animal) map.objectAt(new Vector2d(5,8))).getDirection());

    }

}
