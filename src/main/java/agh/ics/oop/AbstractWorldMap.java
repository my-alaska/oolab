package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    private List<Animal> animals;

    public AbstractWorldMap(){
        this.animals = new ArrayList<>();
    }

    public boolean canMoveTo(Vector2d position){ /// w klasie potomnej użyj super.canMoveTo()
        return !(isOccupied(position));
    }

    /*public boolean place(Animal animal){
        if (this.animals.contains(animal)){
            return false;
        }else if(isOccupied(animal.getPosition())){
            return false;
        }else{
            this.animals.add(animal);
            return true;
        }
    }*/

    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            this.animals.add(animal);
            return true;
        }else{
            return false;
        }
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        for(Animal a : animals){
            if (a.getPosition().equals(position)){
                return a;
            }
        }
        return null;
    }

    protected abstract Vector2d upright();
    protected abstract Vector2d lowleft();
    public String toString(){
        Vector2d ll = lowleft();
        Vector2d ur = upright();
        for(Animal a : animals){
            ll = ll.lowerLeft(a.getPosition());
            ur = ur.upperRight(a.getPosition());
        }
        return new MapVisualizer(this).draw(ll, ur);
    }




}
