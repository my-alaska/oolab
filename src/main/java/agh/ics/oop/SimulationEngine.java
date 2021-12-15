package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    private MoveDirection[] moves;
    private IWorldMap map;
    //private Vector2d[] positions; ///needed only for tests
    private ArrayList<Animal> animals;
    int moveDelay = 300;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions) {
        this.moves = moves;
        this.map = map;
        //this.positions = positions; ///needed only for tests
        animals = new ArrayList<Animal>();

        for (Vector2d p : positions) {
            Animal a = new Animal(map, p);
            //if (map.canMoveTo(p)){
            animals.add(a);
            map.place(a); //}
        }
    }

    public void run() {
        int numOfMoves = moves.length;
        int numOfAnimals = animals.size();

        System.out.println(map.toString());

        for (int i = 0; i < numOfMoves; i++) {

            Animal animal = animals.get(i % numOfAnimals);
            if (animal != null) {
                try {
                    Thread.sleep(moveDelay);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                animal.move(moves[i]);

                //positions[i % numOfAnimals] = animal.getPosition(); ///needed only for tests
            }

            System.out.println(map.toString());
        }

    }

    public ArrayList<Animal> getAnimals(){
        return this.animals;
    }

    public void setMoves(MoveDirection[] moves){
        this.moves = moves;
    }
}
