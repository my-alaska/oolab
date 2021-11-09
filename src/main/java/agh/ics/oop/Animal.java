package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position = new Vector2d(2,2);
    private IWorldMap map;

    public Animal(){
        this.direction = MapDirection.NORTH;
    }

    public Animal(IWorldMap map){
        this.direction = MapDirection.NORTH;
        this.map = map;
        map.place(this);

    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.direction = MapDirection.NORTH;
        this.map = map;
        position = initialPosition;
        map.place(this);
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
    }
    private void rotateLeft(){
        direction = direction.previous();
    }
    private void moveForward(){
        if(map.canMoveTo(position.add(direction.toUnitVector()))){
            position = position.add(direction.toUnitVector());
        }
    }
    private void moveBackward(){
        if(map.canMoveTo(position.subtract(direction.toUnitVector()))){
            position = position.subtract(direction.toUnitVector());
        }
    }



}
