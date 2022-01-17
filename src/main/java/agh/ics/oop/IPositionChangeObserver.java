package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);    // czy jest sens przekazywać nową pozycję, skoro przekazujemy całe zwierzę?
}
