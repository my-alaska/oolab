package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    private int numberGrass;
    private List<Grass> grass;
    private Map<Vector2d, Grass> grassMap;
    private MapBoundary boundaries;

    public GrassField(int numberGrass){
        this.boundaries = new MapBoundary();
        this.numberGrass = numberGrass;
        this.grassMap = new LinkedHashMap<>();

        for(int i = 0; i < numberGrass;){
            Vector2d position = new Vector2d((int) (Math.random() * Math.sqrt(numberGrass*10)) , (int) (Math.random() * Math.sqrt(numberGrass*10)));
            if(!isOccupied(position)){
                    grassMap.put(position,new Grass(position));
                    boundaries.addPosition(position);
                i++;
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        return super.canMoveTo(position) || objectAt(position).getClass() != Animal.class;
    }

    public boolean place(Animal animal){
        if(super.place(animal)){
            boundaries.addPosition(animal.getPosition());
            animal.addObserver(this.boundaries);
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        Object a = super.objectAt(position);
        if (a != null){
            return a;
        }
        return grassMap.get(position);
    }

    public Vector2d upright() {
        /*Vector2d ur = grassMap.keySet().iterator().next();
        for(Grass g : grassMap.values()){
            ur = ur.upperRight(g.getPosition());
        }
        for(Animal a : getAnimalsMap().values()){
            ur = ur.upperRight(a.getPosition());
        }
        return ur;*/
        return boundaries.upright();
    }

    public Vector2d lowleft() {
        /*Vector2d ll = grassMap.keySet().iterator().next();
        for(Grass g : grassMap.values()){
            ll = ll.lowerLeft(g.getPosition());
        }
        for(Animal a : getAnimalsMap().values()){ /// czy można użyć wektorów (Vector2d v : animalsMap.keySet())
            ll = ll.lowerLeft(a.getPosition());
        }
        return ll;*/
        return boundaries.lowleft();
    }



}
