package agh.ics.oop;

public class SimulationEngine implements Runnable {
    private FrontMap frontMap;
    private int startAnimalsNumber;
    private int startEnergy;
    private int moveDelay = 50;
    private Object lock = new Object();
    boolean running;    // czemu nie private?

    public SimulationEngine(FrontMap frontMap, int startAnimalsNumber, int startEnergy) {
        this.frontMap = frontMap;
        this.startAnimalsNumber = startAnimalsNumber;
        this.startEnergy = startEnergy;
        this.running = false;
    }

    public void setRunning(Boolean bool) {
        running = bool;
    }

    public Boolean isRunning() {
        return running;
    }

    public Object getLock() {
        return lock;
    }

    public void run() {
        for (int i = 0; i < this.startAnimalsNumber; i++) {

            Animal animal = new Animal(this.frontMap, this.startEnergy, new Genotype(), frontMap.randomVectorInMap());

        }

        this.running = true;
        while (true) {  //  nieskończona pętla bez żadnego break'a ani returna w środku

            synchronized (lock) {    // synchronized (this)
                if (!running) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            frontMap.dayPass();

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }

    public void setStartAnimalsNumber(int newNumber) {
        this.startAnimalsNumber = newNumber;
    }

    public void setStartEnergy(int newNumber) {
        this.startEnergy = newNumber;
    }
}
