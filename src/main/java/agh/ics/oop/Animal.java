package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement{
    private MapDirection direction;
    private Vector2d position;
    private FrontMap frontMap;
    private BackMap backMap;
    private List<IPositionChangeObserver> observers;
    private Genotype genotype;
    private int energy;
    private boolean isOnFrontMap;
    private int age = 0;
    private int numberOfChildren = 0;

    private Boolean isObserved = false;
    private Animal ancestor = null;
    private Animal parent = null;
    private int noObservedChildren = 0;
    private int noObservedDescendants = 0;
    private int dayOfDeath = -1;


    public Animal(AbstractMap map, int energy, Genotype genotype ,Vector2d initialPosition){
        this.observers = new ArrayList<>();
        this.direction = MapDirection.generateRandom(); //
        this.energy = energy;
        this.position = initialPosition;
        this.genotype = genotype;
        if (map.getClass() == FrontMap.class){
            this.frontMap = (FrontMap) map;
            this.backMap = frontMap.getBackMap();
            frontMap.place(this);
            this.isOnFrontMap = true;
        }else{
            this.backMap = (BackMap) map;
            this.frontMap = backMap.getFrontMap();
            backMap.place(this);
            this.isOnFrontMap = false;
        }


    }





    public Vector2d getPosition(){
        return position;
    }



    public String toString(){ //
        return direction.toString();
    }




    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }




    private int rotate(){
        int rotationNumber = genotype.generateRotationGenes();
        switch (rotationNumber) {
            case 0:
            case 1:
                for (int i = 0; i < rotationNumber; i++){direction = direction.next();}
            case 2:
                for (int i = 0; i < rotationNumber; i++){direction = direction.next();}
            case 3:
                for (int i = 0; i < rotationNumber; i++){direction = direction.next();}
            case 4:
                direction = direction.opposite();
            case 5:
                for (int i = 0; i < 8-rotationNumber; i++){direction = direction.previous();}
            case 6:
                for (int i = 0; i < 8-rotationNumber; i++){direction = direction.previous();}
            case 7:
                for (int i = 0; i < 8-rotationNumber; i++){direction = direction.previous();}
        }
        return rotationNumber;
    }




    public void move(){
        int rotationNumber = rotate();
        if (rotationNumber == 0 || rotationNumber == 4){
            Vector2d newPosition = position.add(direction.toUnitVector());

            if(frontMap.inMap(newPosition)){
                Vector2d oldPosition = position;
                position = newPosition;
                positionChanged(oldPosition,newPosition);
            }else if(isOnFrontMap){
                isOnFrontMap = false;
                frontMap.animalOutsideMap(this, newPosition);
            }

        }


    }





    public Animal reproduce(Animal otherAnimal){
        int childEnergy = (this.energy + otherAnimal.getEnergy())/4;
        int cutGenesAt = 32 * this.energy/(this.energy+otherAnimal.getEnergy());
        this.energy = (this.energy*3)/4;
        otherAnimal.setEnergy((otherAnimal.getEnergy()*3)/4);
        Genotype childGenotype = genotype.mixGenesWith(otherAnimal.getGenotype(), cutGenesAt);
        this.increaseNOChildren();
        otherAnimal.increaseNOChildren();
        Animal child;
        if(isOnFrontMap){
            child = new Animal(frontMap, childEnergy, childGenotype, position);
        }else{
            child = new Animal(backMap, childEnergy, childGenotype, position);
        }

        if(!this.isObserved && this.ancestor == null){
            return child;
        }
        if(this.isObserved()){
            child.setParent(this);
            child.setAncestor(this);
            this.noObservedChildren +=1;
            return child;
        }else if(this.ancestor.isObserved()){
            child.setAncestor(this.ancestor);
            this.ancestor.increaseObservedDescendants();
        }
        return child;

    }
    public void increaseNOChildren(){
        this.numberOfChildren += 1;
    }
    public int getNumberOfChildren(){
        return numberOfChildren;
    }



    public void increaseAge(){
        age += 1;
    }

    public int getAge(){
        return age;
    }


    public int getEnergy(){
        return this.energy;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setDirection(MapDirection direction){
        this.direction = direction;
    }







    public void setObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        for(IPositionChangeObserver o : observers){
            if(observer.equals(o)){
                observers.remove(observer);
                break;
            }
        }
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver o : observers){
            o.positionChanged(oldPosition,newPosition,this);
        }
    }

    @Override
    public String imageResource() {
        return null;
    }

    public MapDirection getDirection(){
        return direction;
    }





    public void setObserved(Boolean observed) {
        isObserved = observed;
    }





    public void setDayOfDeath(int day){
        this.dayOfDeath = day;
    }
    public int getDayOfDeath(){
        return this.dayOfDeath;
    }
    public int getNoObservedChildren() {
        return noObservedChildren;
    }
    public int getNoObservedDescendants() {
        return noObservedDescendants;
    }
    public void increaseObservedDescendants(){
        this.noObservedDescendants += 1;
    }
    public void setAncestor(Animal ancestor){
        this.ancestor = ancestor;
    }
    public void setParent(Animal parent){
        this.parent = parent;
    }
    public Boolean isObserved() {
        return isObserved;
    }

}
