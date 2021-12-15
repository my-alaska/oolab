package agh.ics.oop;
import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args){
        Application.launch(App.class, args);

        /*try {
            System.out.println("system wystartorwał");
            String[] tab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            MoveDirection[] directions = new OptionsParser().parse( tab );
            //IWorldMap map = new RectangularMap(11, 11);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            System.out.println(map.toString());

            System.out.println("system zakończył działanie");
        }catch(IllegalArgumentException ex){
            System.out.println(ex);
        }*/

    }

}