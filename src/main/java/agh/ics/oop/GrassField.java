package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    private int numberGrass;
    private List<Grass> grass;
    private Map<Vector2d, Grass> grassMap;

    public GrassField(int numberGrass){
        this.numberGrass = numberGrass;
        //this.grass = new ArrayList<>();
        this.grassMap = new LinkedHashMap<>();

        for(int i = 0; i < numberGrass;){
            Vector2d position = new Vector2d((int) (Math.random() * Math.sqrt(numberGrass*10)) , (int) (Math.random() * Math.sqrt(numberGrass*10)));
            if(!isOccupied(position)){
                    //grass.add(new Grass(position));
                    grassMap.put(position,new Grass(position));
                i++;
            }
        }

    }

    public boolean canMoveTo(Vector2d position){
        return super.canMoveTo(position) || objectAt(position).getClass() != Animal.class;
    }


    public Object objectAt(Vector2d position) {
        Object a = super.objectAt(position);
        if (a != null){
            return a;
        }
        return grassMap.get(position);
        /*for(Grass g : grass){
            if (g.getPosition().equals(position)){
                return g;
            }
        }
        return null;*/
    }

    public Vector2d upright() {
        ///Vector2d ur = grass.get(0).getPosition();
        Vector2d ur = grassMap.keySet().iterator().next();
        ///for(Grass g : grass){
        for(Grass g : grassMap.values()){
            ur = ur.upperRight(g.getPosition());
        }
        return ur;
    }
    public Vector2d lowleft() {
        //Vector2d ll = grass.get(0).getPosition();
        Vector2d ll = grassMap.keySet().iterator().next(); // jak wziąć dowolny element z grassMap.values()
        //for(Grass g : grass){
        for(Grass g : grassMap.values()){
            ll = ll.lowerLeft(g.getPosition());
        }
        return ll;
    }

}
