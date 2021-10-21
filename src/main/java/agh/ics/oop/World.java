package agh.ics.oop;

public class World {

    static void run(Direction[] arr){
        System.out.println("moves forward");
        for(int i = 0; i < arr.length; i++){
            switch(arr[i]){
                case f:
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case b:
                    System.out.println("Zwierzak idzie do tyłu");
                    break;
                case l:
                    System.out.println("Zwierzak skręca w lewo");
                    break;
                case r:
                    System.out.println("Zwierzak skręca w prawo");
                    break;
            }
        }
    }
    public static void main(String[] args){
        System.out.println("system wystartorwał");
        /*Direction[] tab =  {Direction.f, Direction.b, Direction.r, Direction.l};
        run(tab);*/
        System.out.println("system zakończył działanie");
    }
}