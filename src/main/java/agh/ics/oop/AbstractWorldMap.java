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
            animal.addObserver(this); //albo animal.addObserver((IPositionChangeObserver) this);
            return true;
        }else{
            throw new IllegalArgumentException(animal.getPosition().toString() + " is not legal placement");
            //return false;
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



    public  Map<Vector2d, Animal> getsAnimalMap(){
        return animalsMap;
    }

    public abstract Vector2d upright();
    public abstract Vector2d lowleft();

    public String toString(){
        Vector2d ll = lowleft();
        Vector2d ur = upright();
        ///for(Animal a : animals)

        // przenieść do grassfield

        return new MapVisualizer(this).draw(ll, ur);
    }

    public Map<Vector2d, Animal> getAnimals(){
        return animalsMap;
    }

    //obserwatorzy
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        if(!oldPosition.equals(newPosition) ){
            Animal animal = animalsMap.get(oldPosition);
            animalsMap.remove(oldPosition);
            animalsMap.put(newPosition, animal);
        }
    }

    public Set<Vector2d> getElementPositions(){
        Set <Vector2d> set = new HashSet<>();
        for(Animal animal : animalsMap.values()){
            set.add(animal.getPosition());
        }
        return set;
    }


}
