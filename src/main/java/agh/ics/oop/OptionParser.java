package agh.ics.oop;

import java.util.*;

public class OptionParser {
    List<MoveDirection> directions = Collections.emptyList();

    public static MoveDirection[] parse(String[] arr) {
        List<MoveDirection> directions = new ArrayList<>(Collections.emptyList());
        for (String s : arr) {
            switch (s) {
                case "f", "forward" -> directions.add(MoveDirection.FORWARD);
                case "b", "backward" -> directions.add(MoveDirection.BACKWARD);
                case "r", "right" -> directions.add(MoveDirection.RIGHT);
                case "l", "left" -> directions.add(MoveDirection.LEFT);
            }
        }

        MoveDirection[] resultDirectionList = new MoveDirection[directions.size()];
        directions.toArray(resultDirectionList);
        return resultDirectionList;
    }
}
