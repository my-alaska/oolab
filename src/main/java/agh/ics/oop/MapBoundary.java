package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private SortedSet<Vector2d> xSet;
    private SortedSet<Vector2d> ySet;

    public MapBoundary(){
        this.xSet = new TreeSet<>(Comparator.comparing(e -> e.getX()));
        this.ySet = new TreeSet<>(Comparator.comparing(e -> e.getY()));
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        removePosition(oldPosition);
        addPosition(newPosition);
    }

    public void addPosition(Vector2d position){
        xSet.add(position);
        ySet.add(position);
    }

    public void removePosition(Vector2d position){
        xSet.remove(position);
        ySet.remove(position);
    }

    public Vector2d upright(){
        return new Vector2d(xSet.last().getX(), ySet.last().getY());
    }
    public Vector2d lowleft(){
        return new Vector2d(xSet.first().getX(), ySet.first().getY());
    }

}
