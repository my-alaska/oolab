package agh.ics.oop;

public class SimulationEngine implements IEngine{
    private MoveDirection[] moves;
    private IWorldMap map;
    private Vector2d[] positions;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions){
        this.moves = moves;
        this.map = map;
        this.positions = positions;
        for( Vector2d p : this.positions){
            new Animal(map, p);
        }
    }

    @Override
    public void run(){
        int numOfMoves = moves.length;
        int numOfAnimals = positions.length;
        System.out.println(map.toString());
        for(int i = 0; i < numOfMoves; i++){

            Animal animal = (Animal) map.objectAt(positions[i%numOfAnimals]);
            if(animal != null){
                animal.move(moves[i]);
                positions[i%numOfAnimals] = animal.getPosition();
                System.out.println(map.toString());
            }
        }

    }

}
