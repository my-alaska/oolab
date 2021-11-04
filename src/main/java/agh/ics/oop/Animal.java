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
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
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

        switch (direction){
            case NORTH:
                if(map.canMoveTo(new Vector2d(position.x,position.y+1))){
                    position = position.add(new Vector2d(0,1));}
                break;
            case EAST:
                if (map.canMoveTo(new Vector2d(position.x+1,position.y))){
                    position = position.add(new Vector2d(1,0));}
                break;
            case SOUTH:
                if (map.canMoveTo(new Vector2d(position.x,position.y-1))){
                    position = position.add(new Vector2d(0,-1));}
                break;
            case WEST:
                if (map.canMoveTo(new Vector2d(position.x-1,position.y))){
                    position = position.add(new Vector2d(-1,0));}
                break;
        }
    }
    private void moveBackward(){
        switch (direction){
            case NORTH:
                if(map.canMoveTo(new Vector2d(position.x,position.y-1))){
                    position = position.subtract(new Vector2d(0,1));}
                break;
            case EAST:
                if (map.canMoveTo(new Vector2d(position.x-1,position.y))){
                    position = position.subtract(new Vector2d(1,0));}
                break;
            case SOUTH:
                if (map.canMoveTo(new Vector2d(position.x,position.y+1))){
                    position = position.subtract(new Vector2d(0,-1));}
                break;
            case WEST:
                if (map.canMoveTo(new Vector2d(position.x+1,position.y))){
                    position = position.subtract(new Vector2d(-1,0));}
                break;
        }
    }



}
