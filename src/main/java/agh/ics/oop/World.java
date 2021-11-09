package agh.ics.oop;

public class World {

    static void run(Direction[] arr){
        System.out.println("moves forward");
        for(int i = 0; i < arr.length; i++){
            switch (arr[i]) {
                case f -> System.out.println("Zwierzak idzie do przodu");
                case b -> System.out.println("Zwierzak idzie do tyłu");
                case l -> System.out.println("Zwierzak skręca w lewo");
                case r -> System.out.println("Zwierzak skręca w prawo");
            }
        }
    }
    public static void main(String[] args){
        System.out.println("system wystartorwał");

        /*
        Direction[] tab =  {Direction.f, Direction.b, Direction.r, Direction.l};
        run(tab);

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        */

        /*
        Animal zwierz = new Animal();

        String[] directions = {"cofee","r", "f", "f", "f"};
        for (MoveDirection d : OptionParser.parse(directions)){
            zwierz.move(d);
        }

        System.out.println(zwierz.toString());
        */

        String[] tab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse( tab );
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map.toString());

        System.out.println("system zakończył działanie");
    }
}