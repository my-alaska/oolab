package agh.ics.oop;
import java.util.*;

public abstract class AbstractWorldMap implements IPositionChangeObserver, IWorldMap{
    /// private List<Animal> animals;
    private Map<Vector2d, Animal> animalsMap;

    public AbstractWorldMap(){
        this.animalsMap = new LinkedHashMap<>();
        ///this.animals = new ArrayList<>();
    }

    public boolean canMoveTo(Vector2d position){
        return !(isOccupied(position));
    }


    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            ///this.animals.add(animal);
            this.animalsMap.put(animal.getPosition(),animal);
            return true;
        }else{
            return false;
        }
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return animalsMap.get(position);
        /*for(Animal a : animals){
            if (a.getPosition().equals(position)){
                return a;
            }
        }
        return null;*/
    }

    protected abstract Vector2d upright();
    protected abstract Vector2d lowleft();
    public String toString(){
        Vector2d ll = lowleft();
        Vector2d ur = upright();
        ///for(Animal a : animals)
        for(Animal a : animalsMap.values()){ /// czy można użyć wektorów (Vector2d v : animalsMap.keySet())
            ll = ll.lowerLeft(a.getPosition());
            ur = ur.upperRight(a.getPosition());
        }

        return new MapVisualizer(this).draw(ll, ur);
    }


    //obserwatorzy
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal animal = animalsMap.get(oldPosition);
        animalsMap.remove(oldPosition);
        animalsMap.put(newPosition, animal);
    }


}
