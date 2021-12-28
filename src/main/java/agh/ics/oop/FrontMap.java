package agh.ics.oop;

import agh.ics.oop.gui.IDayPassObserver;

import java.io.*;
import java.util.*;


public class FrontMap extends AbstractMap{
    private  Vector2d jungleCorner;
    private int jungleSize;
    private int jungleGrass;
    private IDayPassObserver observer;
    private BackMap backMap;
    private ArrayList<Animal> moveToBackMap = new ArrayList<Animal>(Collections.emptyList());
    private ArrayList<Vector2d> moveToBackMapVectors = new ArrayList<Vector2d>(Collections.emptyList());
    public File csvOutputFile;





    public FrontMap(Vector2d mapCorner, Vector2d jungleCorner, int grassEnergy, int dayUseEnergy, int sexEnergy, int startEnergy){
        if (!jungleCorner.follows(new Vector2d(0,0))){
            throw new IllegalArgumentException("Jungle Corner Vector must be positive");
        }
        if (!mapCorner.follows(jungleCorner)){
            throw new IllegalArgumentException("Map Corner Vector must follow JungleCorner Vector");
        }
        this.startEnergy = startEnergy;
        this.jungleCorner = jungleCorner;
        this.mapCorner = mapCorner;
        this.grassEnergy = grassEnergy;
        this.dayUseEnergy = dayUseEnergy;
        this.sexEnergy = sexEnergy;
        this.backMap = new BackMap(mapCorner,grassEnergy,dayUseEnergy,sexEnergy,this,startEnergy);
        updateArea();
        this.jungleGrass = 0;
        this.steppeGrass = 0;

        this.csvOutputFile = new File("FrontMapxd.csv");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("FrontMapData.csv",false)))) {
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public void updateArea(){
        this.jungleSize = jungleCorner.getX()*jungleCorner.getY()*4;
        this.steppeSize = mapCorner.getX()*mapCorner.getY()*4-jungleSize;
        if(this.jungleSize > this.steppeSize){
            throw new IllegalArgumentException("Steppe area must be larger than jungle area");
        }
        this.backMap.updateArea();
    }



    private boolean inJungle(Vector2d position){
        return position.precedes(jungleCorner) && position.follows(jungleCorner.opposite()) ;
    }



    public void dayPass(){
        deleteDeadAnimals();

        moveAnimals();
        processAnimalsOutside();
        backMap.dayPass();
        backMap.addAnimals(moveToBackMap);
        eatingPhase();
        reproductoryPhase();
        generateGrassJungle();
        generateGrassSteppe();
        exhaustionPhase();
        magicTactixAction();

        writeData();
        moveToBackMap = new ArrayList<Animal>(Collections.emptyList());
        moveToBackMapVectors = new ArrayList<Vector2d>(Collections.emptyList());
        worldAge += 1;

        dayPassed();
    }


















//

    public void animalOutsideMap(Animal animal, Vector2d newPosition){
        moveToBackMapVectors.add(newPosition);
        moveToBackMap.add(animal);
    }
    public  void processAnimalsOutside(){
        Animal animal;
        Vector2d newPosition;
        for(int i = 0; i < moveToBackMap.size();i++){
            animal = moveToBackMap.get(i);
            newPosition = moveToBackMapVectors.get(i);
            animalsList.remove(animal);
            animalsMap.get(animal.getPosition()).remove(animal);
            if(animalsMap.get(animal.getPosition()).size()==0){
                animalsMap.remove(animal.getPosition());
            }
            genesMap.get(animal.getGenotype()).remove(animal);
            if(genesMap.get(animal.getGenotype()).size()==0){
                genesMap.remove(animal.getGenotype());
            }
            animal.removeObserver(this);
            animal.setPosition(newPosition);
        }
    }










    private  void eatingPhase(){

        for(ArrayList<Animal> set : animalsMap.values()){
            Collections.shuffle(set);
            if (!set.isEmpty() && grassMap.get(set.get(0).getPosition()) != null){
                ArrayList<Animal> bestAnimalsNow = bestAnimalsInSet(set);
                int equalEnergy = grassEnergy/bestAnimalsNow.size();
                int excessEnergy = grassEnergy%bestAnimalsNow.size();
                for(Animal a : set){
                    a.setEnergy(a.getEnergy()+equalEnergy);
                    if(excessEnergy > 0){
                        a.setEnergy(a.getEnergy()+1);
                    }

                }
                if (inJungle(set.get(0).getPosition())){
                    jungleGrass -= 1;
                }else{
                    steppeGrass -= 1;
                }
                grassMap.remove(set.get(0).getPosition());
            }
        }

    }





    private  void generateGrassJungle(){
        if(jungleGrass >= jungleSize){
            return;
        }
        int newX = new Random().nextInt(2 * jungleCorner.getX()+1) - jungleCorner.getX();
        int newY = new Random().nextInt(2 * jungleCorner.getY()+1) - jungleCorner.getY();
        Vector2d newGrassPosition = new Vector2d(newX,newY);
        while(grassMap.get(newGrassPosition) != null) {
            newX = new Random().nextInt(2 * jungleCorner.getX()+1) - jungleCorner.getX();
            newY = new Random().nextInt(2 * jungleCorner.getY()+1) - jungleCorner.getY();
            newGrassPosition = new Vector2d(newX, newY);
        }
        grassMap.put(newGrassPosition,new Grass(newGrassPosition));
        jungleGrass += 1;
    }


    private  void generateGrassSteppe(){
        if(steppeGrass >= steppeSize){
            return;
        }
        int newX = new Random().nextInt(2 * mapCorner.getX()+1) - mapCorner.getX();
        int newY = new Random().nextInt(2 * mapCorner.getY()+1) - mapCorner.getY();
        Vector2d newGrassPosition = new Vector2d(newX,newY);
        while(grassMap.get(newGrassPosition) != null ||
                (inJungle(newGrassPosition))){
            newX = new Random().nextInt(2 * mapCorner.getX()+1) - mapCorner.getX();
            newY = new Random().nextInt(2 * mapCorner.getY()+1) - mapCorner.getY();
            newGrassPosition = new Vector2d(newX,newY);

        }
        grassMap.put(newGrassPosition,new Grass(newGrassPosition));
        steppeGrass += 1;
    }








    private void writeData() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("FrontMapData.csv",true)))) {

            int avgEnergy, avgNoChildren,avgAge;

            if (animalsMap.size() != 0){
                avgEnergy = wholeEnergy / animalsMap.size();
                avgNoChildren = this.getAllChildrenNumber() / animalsMap.size();
            }else{
                avgEnergy = 0;
                avgNoChildren = 0;
            }

            if(super.getAllDeadAnimals() != 0){
                avgAge = super.getAllDeadAge() / super.getAllDeadAnimals();
            }else{
                avgAge = 0;
            }

            int grass = steppeGrass + jungleGrass;
            writer.println("grass " + grass +
                    " animals " + animalsMap.size() +
                    " avg energy " + avgEnergy +
                    " avg no children " + avgNoChildren +
                    " avg death age " + avgAge);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






//    public String printStatistics(){
//        String statistics = "grass: " + getGrassNumber() +
//                "\nanimals: "+getAnimalsNumber()+
//                "\navrage Energy: " + avgEnergy()+getAvgAge() +
//                "\naverage no children: " +getAvgNOChildren() +
//                "\nbest genotype: " + getBestGenotype().toString();
//        return statistics;
//    }

    public int getGrassNumber(){
        return steppeGrass + jungleGrass + backMap.getGrassNumber();
    }

    public int getAnimalsNumber(){
        return animalsList.size() + backMap.getAnimalsNumber();
    }

    public int avgEnergy(){
        if (getAnimalsNumber() == 0){return 0;}
        return (wholeEnergy + backMap.getWholeEnergy())/(getAnimalsNumber());
    }

    public int getAvgAge(){
        if(super.getAllDeadAnimals() + backMap.getAllDeadAnimals() == 0){
            return 0;
        }
        return (super.getAllDeadAge() + backMap.getAllDeadAge())/(super.getAllDeadAnimals() + backMap.getAllDeadAnimals());
    }

    public int getAvgNOChildren(){
        if (getAnimalsNumber() == 0){return 0;}
        return (this.getAllChildrenNumber()+ backMap.getAllChildrenNumber())/this.getAnimalsNumber();
    }

    public  Genotype getBestGenotype(){
        Genotype bestGenes = new Genotype();
        int count = 0;
        for(Genotype genotype : genesMap.keySet()){
            if(genesMap.get(genotype).size() > count){
                count = genesMap.get(genotype).size();
                bestGenes = genotype;
            }}
        for(Genotype genotype : backMap.getGenesMap().keySet()){
            if(backMap.getGenesMap().get(genotype).size() > count){
                count = backMap.getGenesMap().get(genotype).size();
                bestGenes = genotype;
            }}
        return bestGenes;
    }

    public HashSet<Animal>[] bestGenesOnMap() {
        Genotype bestGenes = getBestGenotype();
        HashSet<Animal>[] bestGenesOnMap = new HashSet[2];
        bestGenesOnMap[0] = genesMap.get(bestGenes);
        if (bestGenesOnMap[0] == null){
            bestGenesOnMap[0] = new HashSet<Animal>(Collections.emptySet());
        }
        bestGenesOnMap[1] = backMap.getGenesMap().get(bestGenes);

        if (bestGenesOnMap[1] == null){
            bestGenesOnMap[1] = new HashSet<Animal>(Collections.emptySet());
            return bestGenesOnMap;
        }
        return bestGenesOnMap;
    }







    public void setMapCorner(Vector2d mapCorner) {
        this.mapCorner = mapCorner;
        this.backMap.setMapCorner(mapCorner);
        updateArea();
    }

    public void setJungleCorner(Vector2d jungleCorner) {
        this.jungleCorner = jungleCorner;
        updateArea();
    }

    public void setGrassEnergy(int grassEnergy){
        this.grassEnergy = grassEnergy;
        this.backMap.setGrassEnergy(grassEnergy);
    }

    public void setDayUseEnergy(int dayUseEnergy) {
        this.dayUseEnergy = dayUseEnergy;
        this.backMap.setDayUseEnergy(dayUseEnergy);
    }

    public void setSexEnergy(int sexEnergy) {
        this.sexEnergy = sexEnergy;
        this.backMap.setSexEnergy(sexEnergy);
    }



    public Vector2d getJungleCorner(){
        return this.jungleCorner;
    }

    public void dayPassed(){
        observer.dayPassed();
    }

    public void setObserver(IDayPassObserver observer){
        this.observer = observer;
    }

    public BackMap getBackMap(){
        return this.backMap;
    }
}




