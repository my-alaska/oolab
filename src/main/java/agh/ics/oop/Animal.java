package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){
        return position + " " + direction;
    }
    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    private void rotateLeft(){
        direction = direction.previous();
    }

    private void rotateRight(){
        direction = direction.next();
    }

    private void moveForward(){

        switch (direction){
            case NORTH:
                if(position.y < 4){
                    position = position.add(new Vector2d(0,1));}
                break;
            case EAST:
                if (position.x < 4){
                    position = position.add(new Vector2d(1,0));}
                break;
            case SOUTH:
                if (position.y > 0){
                    position = position.add(new Vector2d(0,-1));}
                break;
            case WEST:
                if (position.x > 0){
                    position = position.add(new Vector2d(-1,0));}
                break;
        }
    }

    private void moveBackward(){
        switch (direction){
            case NORTH:
                if(position.y > 0){
                    position = position.subtract(new Vector2d(0,1));}
                break;
            case EAST:
                if (position.x > 0){
                    position = position.subtract(new Vector2d(1,0));}
                break;
            case SOUTH:
                if (position.y < 4){
                    position = position.subtract(new Vector2d(0,-1));}
                break;
            case WEST:
                if (position.x < 4){
                    position = position.subtract(new Vector2d(-1,0));}
                break;
        }
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case RIGHT -> rotateRight();
            case LEFT -> rotateLeft();
            case FORWARD -> moveForward();
            case BACKWARD -> moveBackward();
        }
    }

}
