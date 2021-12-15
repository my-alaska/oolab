package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement{
    private MapDirection direction;
    private Vector2d position = new Vector2d(2,2);
    private IWorldMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    //usunąć i poprawić testy
    /*public Animal(){
        this.observers = new ArrayList<>();
        this.direction = MapDirection.NORTH;
    }*/

    public Animal(IWorldMap map){
        ///
        this.observers = new ArrayList<>();
        this.direction = MapDirection.NORTH;
        this.map = map;
        //map.place(this); // z wyjątkami albo przenieść do simulation engine


    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.direction = MapDirection.NORTH;
        this.map = map;

        position = initialPosition;
        //map.place(this);
    }

    public Vector2d getPosition(){
        return position;
    }
    public MapDirection getDirection(){
        return direction;
    }

    public String toString(){
        return switch (direction) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case RIGHT -> rotateRight();
            case LEFT -> rotateLeft();
            case FORWARD -> moveForward();
            case BACKWARD -> moveBackward();
        }
    }


    private void rotateRight(){
        direction = direction.next();
        rotation();

    }
    private void rotateLeft(){
        direction = direction.previous();
        rotation();
    }
    private void moveForward(){
        if(map.canMoveTo(position.add(direction.toUnitVector()))){
            Vector2d newPosition = position.add(direction.toUnitVector());
            Vector2d oldPosition = position;
            position = newPosition;
            positionChanged(oldPosition,newPosition);
            // CO ZROBIć żeby zwierzaki zmieniające pozycje zmieniaiły mapboundary
        }
    }
    private void moveBackward(){
        if(map.canMoveTo(position.subtract(direction.toUnitVector()))){
            Vector2d newPosition = position.subtract(direction.toUnitVector());
            Vector2d oldPosition = position;
            position = newPosition;
            positionChanged(oldPosition,newPosition);
        }
    }



    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        for(IPositionChangeObserver o : observers){
            if(observer.equals(o)){
                observers.remove(observer);
                break;
            }
        }
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver o : observers){
            o.positionChanged(oldPosition,newPosition);
        }
    }

    private void rotation(){
        positionChanged(this.position,this.position);
    }

    @Override
    public String imageResource() {
        return switch (this.direction) {
            case NORTH -> "src/main/resources/up.png";
            case SOUTH -> "src/main/resources/down.png";
            case WEST -> "src/main/resources/left.png";
            case EAST -> "src/main/resources/right.png";
        };
    }
}
