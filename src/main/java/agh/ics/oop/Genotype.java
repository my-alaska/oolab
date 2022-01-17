package agh.ics.oop;

import java.util.*;


public class Genotype {
    private ArrayList<Integer> genes;


    public Genotype() {
        ArrayList<Integer> genes = new ArrayList<Integer>(Collections.emptyList());
        int[] randomGeneSlices = new int[9];
        randomGeneSlices[0] = 0;
        randomGeneSlices[8] = 32;
        for (int i = 0; i < 7; i++) {
            randomGeneSlices[i + 1] = new Random().nextInt(32);
        }
        java.util.Arrays.sort(randomGeneSlices);
        int[] initialGenes = new int[32];
        for (int i = 0; i < 8; i++) {
            for (int j = randomGeneSlices[i]; j < randomGeneSlices[i + 1]; j++) {
                initialGenes[j] = i;
            }
        }

        for (int i : initialGenes) {
            genes.add(i);
        }
        this.genes = genes;
    }

    public Genotype(ArrayList<Integer> genes) {  // brak kontroli poprawności
        this.genes = genes;
    }


    int generateRotationGenes() { //

        int i = new Random().nextInt(32);   // nowy obiekt co wywołanie
        return genes.get(i);
    }


    Genotype mixGenesWith(Genotype otherGenes, int cut) {   // nie byłoby czytelniej zrobić z tego metody statycznej przyjmującej oba genotypy? Albo konstruktora?
        int leftOrRight = new Random().nextInt(2);
        ArrayList<Integer> newGenes = new ArrayList<>(Collections.emptyList());
        if (leftOrRight == 0) {
            for (int i = 0; i < 32; i++) {
                if (i < cut) {
                    newGenes.add(genes.get(i));
                } else {
                    newGenes.add(otherGenes.getGenes().get(i));
                }
            }
        } else {
            for (int i = 0; i < 32; i++) {
                if (i < 32 - cut) {
                    newGenes.add(otherGenes.getGenes().get(i));
                } else {
                    newGenes.add(genes.get(i));
                }
            }
        }
        return new Genotype(newGenes);
    }


    public ArrayList<Integer> getGenes() {  // zwraca Pan na zewnątrz obiekt modyfikowalny
        return genes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Objects.equals(genes, genotype.genes);   // jestem zaskoczony, że to działa
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }

    public String toString() {
        return genes.toString();
    }
}
