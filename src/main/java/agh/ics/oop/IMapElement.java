package agh.ics.oop;


public interface IMapElement {
    Vector2d getPosition();
    String toString();
    String imageResource(); // dobrze by to było przenieść do GUI
}