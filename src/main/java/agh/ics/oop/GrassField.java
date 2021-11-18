package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap{
    private int numberGrass;
    private List<Grass> grass;

    public GrassField(int numberGrass){
        this.numberGrass = numberGrass;
        this.grass = new ArrayList<>();
        for(int i = 0; i < numberGrass;){
            Vector2d position = new Vector2d((int) (Math.random() * Math.sqrt(numberGrass*10)) , (int) (Math.random() * Math.sqrt(numberGrass*10)));
            if(!isOccupied(position)){
                grass.add(new Grass(position));
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
        for(Grass g : grass){
            if (g.getPosition().equals(position)){
                return g;
            }
        }
        return null;
    }

    public Vector2d upright() {
        Vector2d ur = grass.get(0).getPosition();
        for(Grass g : grass){
            ur = ur.upperRight(g.getPosition());
        }
        return ur;
    }
    public Vector2d lowleft() {
        Vector2d ll = grass.get(0).getPosition();
        for(Grass g : grass){
            ll = ll.lowerLeft(g.getPosition());
        }
        return ll;
    }

}

/*public class GrassField implements IWorldMap{
    private int numberGrass;
    private List<Grass> grass;
    private List<Animal> animals;

    public GrassField(int numberGrass){
        this.numberGrass = numberGrass;
        this.animals = new ArrayList<>();
        this.grass = new ArrayList<>();

        for(int i = 0; i < numberGrass;){
            Vector2d position = new Vector2d((int) (Math.random() * Math.sqrt(numberGrass*10)) , (int) (Math.random() * Math.sqrt(numberGrass*10)));
            if(!isOccupied(position)){
                grass.add(new Grass(position));
                i++;
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        return !(isOccupied(position) && objectAt(position).getClass() == Animal.class);
    }

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

        for(Animal a : animals){ /// jako, że animal jest zwrócony przed grass to trawa nigdy nie zasłoni zwierza w MapVisualizer
            if (a.getPosition().equals(position)){
                return a;
            }
        }

        for(Grass g : grass){
            if (g.getPosition().equals(position)){
                return g;
            }
        }
        return null;
    }

    public String toString(){
        Vector2d lowleft =  grass.get(0).getPosition();
        Vector2d upright = grass.get(0).getPosition();
        for(Animal a : animals){
            lowleft = lowleft.lowerLeft(a.getPosition());
            upright = upright.upperRight(a.getPosition());
        }
        for(Grass g : grass){
            lowleft = lowleft.lowerLeft(g.getPosition());
            upright = upright.upperRight(g.getPosition());
        }

        return new MapVisualizer(this).draw(lowleft, upright);
    }

}

*/