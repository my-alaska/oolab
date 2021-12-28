package agh.ics.oop;

import java.io.*;
import java.util.*;


public class BackMap extends AbstractMap {


    private int grassEnergy;
    private int steppeSize;
    private int steppeGrass;
    private FrontMap frontMap;
    private File csvOutputFile;


    public BackMap(Vector2d mapCorner, int grassEnergy, int dayUseEnergy, int sexEnergy, FrontMap frontMap, int startEnergy) {
        if (!mapCorner.follows(new Vector2d(0, 0))) {
            throw new IllegalArgumentException("Map Corner Vector must be positive");
        }
        this.startEnergy = startEnergy;
        this.frontMap = frontMap;
        this.mapCorner = mapCorner;
        this.grassEnergy = grassEnergy;
        this.dayUseEnergy = dayUseEnergy;
        this.sexEnergy = sexEnergy;
        this.steppeGrass = 0;
        this.csvOutputFile = new File("BackMapData.csv");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("FrontMapData.csv", false)))) {
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateArea() {
        steppeSize = mapCorner.getX() *mapCorner.getY() * 4;
    }

    protected void addAnimals(ArrayList<Animal> moveToBackMap) {
        for (Animal a : moveToBackMap) {
            a.setPosition(backMapPosition(a.getPosition()));
            updateDirection(a);
            place(a);
        }
    }

    private void updateDirection(Animal animal) {
        switch (animal.getDirection()) {
            case NORTH: {
                animal.setDirection(MapDirection.SOUTH);
            }
            case SOUTH: {
                animal.setDirection(MapDirection.NORTH);
            }
            case NORTHWEST: {
                if (animal.getPosition().equals(mapCorner)) {
                    animal.setDirection(MapDirection.SOUTHWEST);
                } else if (animal.getPosition().getY() == mapCorner.getY()) {
                    animal.setDirection(animal.getDirection().opposite());
                }
            }
            case SOUTHWEST: {
                if (animal.getPosition().getX() == mapCorner.getX() && animal.getPosition().getY() == -1 * mapCorner.getY()) {
                    animal.setDirection(MapDirection.NORTHWEST);
                } else if (animal.getPosition().getY() == -1 * mapCorner.getY()) {
                    animal.setDirection(animal.getDirection().opposite());
                }
            }
            case NORTHEAST: {
                if (animal.getPosition().getX() == -1 * mapCorner.getX() && animal.getPosition().getY() == mapCorner.getY()) {
                    animal.setDirection(MapDirection.SOUTHEAST);
                } else if (animal.getPosition().getY() == mapCorner.getY()) {
                    animal.setDirection(animal.getDirection().opposite());
                }
            }
            case SOUTHEAST: {
                if (animal.getPosition().equals(mapCorner)) {
                    animal.setDirection(MapDirection.NORTHEAST);
                } else if (animal.getPosition().getY() == -1 * mapCorner.getY()) {
                    animal.setDirection(animal.getDirection().opposite());
                }
            }
        }
    }

    private Vector2d backMapPosition(Vector2d newPosition) {
        int newY;
        if (newPosition.getY() > mapCorner.getY()) {
            newY = mapCorner.getY();
        } else if (newPosition.getY() < -1 * mapCorner.getY()) {
            newY = -1 * mapCorner.getY();
        } else {
            newY = newPosition.getY();
        }

        int newX;
        if (newPosition.getX() > mapCorner.getX()) {
            newX = -1 * mapCorner.getX();
        } else if (newPosition.getX() < -1 * mapCorner.getX()) {
            newX = mapCorner.getX();
        } else {
            newX = -1 * newPosition.getX();
        }
        return new Vector2d(newX, newY);
    }


    public void dayPass() {
        deleteDeadAnimals();
        moveAnimals();
        eatingPhase();
        reproductoryPhase();
        generateGrassSteppe();
        exhaustionPhase();
        magicTactixAction();
        writeData();
        worldAge += 1;
    }


    private void eatingPhase() {

        for (ArrayList<Animal> set : animalsMap.values()) {
            Collections.shuffle(set);
            if (!set.isEmpty() && grassMap.get(set.get(0).getPosition()) != null) {
                ArrayList<Animal> bestAnimalsNow = bestAnimalsInSet(set);
                int equalEnergy = grassEnergy / bestAnimalsNow.size();
                int excessEnergy = grassEnergy % bestAnimalsNow.size();
                for (Animal a : set) {
                    a.setEnergy(a.getEnergy() + equalEnergy);
                    if (excessEnergy > 0) {
                        a.setEnergy(a.getEnergy() + 1);
                    }

                }

                steppeGrass -= 1;

                grassMap.remove(set.get(0).getPosition());
            }
        }


    }


    private void generateGrassSteppe() {
        if (steppeGrass >= steppeSize) {
            return;
        }
        int newX = new Random().nextInt(2 * mapCorner.getX() + 1) - mapCorner.getX();
        int newY = new Random().nextInt(2 * mapCorner.getY() + 1) - mapCorner.getY();
        Vector2d newGrassPosition = new Vector2d(newX, newY);
        while (grassMap.get(newGrassPosition) != null) {
            newX = new Random().nextInt(2 * mapCorner.getX() + 1) - mapCorner.getX();
            newY = new Random().nextInt(2 * mapCorner.getY() + 1) - mapCorner.getY();
            newGrassPosition = new Vector2d(newX, newY);

        }
        grassMap.put(newGrassPosition, new Grass(newGrassPosition));
        steppeGrass += 1;
    }


    public int getGrassNumber() {
        return steppeGrass;
    }


    public void setMapCorner(Vector2d mapCorner) { // modyfikować liczby
        this.mapCorner = mapCorner;
    }

    public void setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }

    public void setDayUseEnergy(int dayUseEnergy) {
        this.dayUseEnergy = dayUseEnergy;
    }

    public void setSexEnergy(int sexEnergy) {
        this.sexEnergy = sexEnergy;
    }


    public FrontMap getFrontMap() {
        return this.frontMap;
    }


    private void writeData() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("BackMapData.csv", true)))) {

            int avgEnergy, avgNoChildren, avgAge;

            if (animalsMap.size() != 0) {
                avgEnergy = wholeEnergy / animalsMap.size();
                avgNoChildren = getAllChildrenNumber() / animalsMap.size();
            } else {
                avgEnergy = 0;
                avgNoChildren = 0;
            }

            if (super.getAllDeadAnimals() != 0) {
                avgAge = getAllDeadAge() / getAllDeadAnimals();
            } else {
                avgAge = 0;
            }
            writer.println("grass " + steppeGrass +
                    " animals " + animalsMap.size() +
                    " avg energy " + avgEnergy +
                    " avg no children " + avgNoChildren +
                    " avg death age " + avgAge);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}