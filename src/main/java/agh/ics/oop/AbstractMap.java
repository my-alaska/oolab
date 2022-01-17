package agh.ics.oop;

import java.util.*;

public abstract class AbstractMap implements IPositionChangeObserver {
    protected Vector2d mapCorner;
    protected java.util.Map<Vector2d, Grass> grassMap = new LinkedHashMap<>(Collections.emptyMap());
    protected java.util.Map<Vector2d, ArrayList<Animal>> animalsMap = new LinkedHashMap<>(Collections.emptyMap());
    protected ArrayList<Animal> animalsList = new ArrayList<Animal>(Collections.emptyList());
    protected int grassEnergy;
    protected int dayUseEnergy;
    protected int sexEnergy;
    protected int steppeSize;
    protected int steppeGrass;
    protected int startEnergy;

    protected int allDeadAnimals = 0;
    protected int allDeadAge = 0;
    protected int wholeEnergy;
    protected int allChildrenNumber;
    protected java.util.Map<Genotype, HashSet<Animal>> genesMap = new LinkedHashMap<>(Collections.emptyMap());
    protected int worldAge = 0;

    protected Boolean magicTactixOn = false;


    public boolean inMap(Vector2d position) {
        return position.precedes(mapCorner) && position.follows(mapCorner.opposite());
    }

    protected void deleteDeadAnimals() {
        ArrayList<Animal> deletedAnimals = new ArrayList<Animal>(Collections.emptyList());
        for (Animal a : animalsList) {
            if (a.getEnergy() <= 0) {
                animalsMap.get(a.getPosition()).remove(a);
                if (animalsMap.get(a.getPosition()).size() == 0) {
                    animalsMap.remove(a.getPosition());
                }
                genesMap.get(a.getGenotype()).remove(a);
                if (genesMap.get(a.getGenotype()).size() == 0) {
                    genesMap.remove(a.getGenotype());
                }
                deletedAnimals.add(a);
            }
        }
        for (Animal a : deletedAnimals) {
            animalsList.remove(a);
            allDeadAnimals += 1;
            allDeadAge += a.getAge();
            if (a.isObserved()) {
                a.setDayOfDeath(worldAge);
            }
            a = null;
        }
        deletedAnimals = null;
    }

    protected ArrayList<Animal> bestAnimalsInSet(ArrayList<Animal> animals) {
        int bestEnergy = 0;
        ArrayList<Animal> result = new ArrayList<>();
        for (Animal a : animals) {
            if (a.getEnergy() > bestEnergy) {
                bestEnergy = a.getEnergy();
                result.clear();
                result.add(a);
            } else if (a.getEnergy() == bestEnergy) {
                result.add(a);
            }
        }
        Collections.shuffle(result);
        return result;
    }

    protected void moveAnimals() {
        for (Animal a : animalsList) {
            a.move();
        }
    }

    protected void reproductoryPhase() {
        for (ArrayList<Animal> set : animalsMap.values()) {
            if (set.size() >= 2) {
                Collections.shuffle(set);
                Animal bestAnimal1 = set.get(0);
                Animal bestAnimal2 = null;
                for (Animal a : set) {
                    if (a.getEnergy() >= bestAnimal1.getEnergy() && a != bestAnimal1) {
                        bestAnimal2 = bestAnimal1;
                        bestAnimal1 = a;
                    } else if (bestAnimal2 != null && a.getEnergy() > bestAnimal2.getEnergy() && a != bestAnimal1) {
                        bestAnimal2 = a;
                    }
                }
                if (bestAnimal2 != null && bestAnimal2.getEnergy() >= sexEnergy) {
                    if (bestAnimal1.isObserved()) {
                        Animal newAnimal = bestAnimal1.reproduce(bestAnimal2);
                    } else {
                        Animal newAnimal = bestAnimal2.reproduce(bestAnimal1);
                    }

                }
            }
        }
    }


    protected void exhaustionPhase() {
        int newWholeEnergy = 0;
        int newChildrenNumber = 0;
        for (Animal a : animalsList) {
            a.setEnergy(a.getEnergy() - dayUseEnergy);
            a.increaseAge();
            newWholeEnergy += a.getEnergy();
            newChildrenNumber += a.getNumberOfChildren();
        }
        this.wholeEnergy = newWholeEnergy;
        this.allChildrenNumber = newChildrenNumber;
    }

    public void place(Animal animal) {
        if (animalsMap.get(animal.getPosition()) == null) {
            animalsMap.put(animal.getPosition(), new ArrayList<Animal>());
        }
        if (genesMap.get(animal.getGenotype()) == null) {
            genesMap.put(animal.getGenotype(), new HashSet<Animal>());
        }
        animalsMap.get(animal.getPosition()).add(animal);
        animalsList.add(animal);
        genesMap.get(animal.getGenotype()).add(animal);
        animal.setObserver(this);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animalsMap.get(oldPosition).remove(animal);
        if (animalsMap.get(oldPosition).size() == 0) {
            animalsMap.remove(oldPosition);
        }
        if (animalsMap.get(newPosition) == null) {
            animalsMap.put(newPosition, new ArrayList<>());
        }
        animalsMap.get(newPosition).add(animal);
    }

    public IMapElement bestObjectAt(Vector2d position) {
        if (animalsMap.get(position) != null) {
            return bestAnimalsInSet(animalsMap.get(position)).get(0);
        } else {
            return grassMap.get(position);
        }
    }

    public Set<Vector2d> getElementPositions() {
        Set<Vector2d> set = new HashSet<Vector2d>();
        for (Animal animal : animalsList) {
            set.add(animal.getPosition());
        }
        for (Grass grass : grassMap.values()) {
            set.add(grass.getPosition());
        }
        return set;
    }

    public Vector2d getMapCorner() {
        return this.mapCorner;
    }


    public int getAnimalsNumber() {
        return animalsList.size();
    }

    public int getAllDeadAnimals() {
        return allDeadAnimals;
    }

    public int getAllDeadAge() {
        return allDeadAge;
    }

    public int getWholeEnergy() {
        return wholeEnergy;
    }

    public int getAllChildrenNumber() {
        return allChildrenNumber;
    }

    public int getWorldAge() {
        return worldAge;
    }


    public java.util.Map<Genotype, HashSet<Animal>> getGenesMap() {
        return genesMap;
    }

    public void switchMagicTactics() {
        magicTactixOn = !magicTactixOn;
    }

    protected void magicTactixAction() {
        if (magicTactixOn && animalsList.size() < 5) {
            for (int i = 0; i < 5; i++) {
                Animal animal = new Animal(this, this.startEnergy, new Genotype(), this.randomVectorInMap());
            }
        }
    }

    public Vector2d randomVectorInMap() {
        int newX = new Random().nextInt(2 * mapCorner.getX() + 1) - mapCorner.getX();
        int newY = new Random().nextInt(2 * mapCorner.getY() + 1) - mapCorner.getY();
        return new Vector2d(newX, newY);
    }

}
