package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap{
    private int width, height;
    public List<Animal> animals;

    public RectangularMap(int width, int height){
        this.animals = new ArrayList<>();
        if (width > 0){
            this.width = width;
        }else{
            this.width = 4;
        }

        if (height > 0){
            this.height = height;
        }else{
            this.height = 4;
        }
    }

    public boolean canMoveTo(Vector2d position){
        return (!isOccupied(position) && (position.x >= 0 && position.x <= width && position.y >= 0 && position.y <= height));
    }

    public boolean place(Animal animal){
        if (this.animals.contains(animal)){
            return false;
        }else if(isOccupied(animal.getPosition())){
            return false;
        }else{
            this.animals.add(animal);
            return true;
        }
    }

    public boolean isOccupied(Vector2d position){
        for(Animal a : animals){
            if (a.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        for(Animal a : animals){
            if (a.getPosition().equals(position)){
                return a;
            }
        }
        return null;
    }

    public String toString(){
        return new MapVisualizer(this).draw(new Vector2d(0,0), new Vector2d(width, height));
    }

}
