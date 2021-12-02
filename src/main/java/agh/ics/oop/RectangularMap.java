package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap{
    private Vector2d corner;
    private Vector2d mapStart;

    public RectangularMap(int width, int height){
        this.mapStart = new Vector2d(0,0);
        int a,b;
        if (width > 0){
            a = width-1;
        }else{
            a = 4;
        }

        if (height > 0){
            b = height-1;
        }else{
            b = 4;
        }
        corner = new Vector2d(a,b);
    }

    public boolean canMoveTo(Vector2d position){
        return super.canMoveTo(position) && position.follows(mapStart) && position.precedes(corner);
    }

    public Vector2d lowleft(){return mapStart;}
    public Vector2d upright(){return corner;}

}