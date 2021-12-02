package agh.ics.oop;

import java.util.*;

public class OptionsParser {
    List<MoveDirection> directions = Collections.emptyList();
    private static Set<String> legalMoves;

    public OptionsParser(){
        legalMoves =  new HashSet<>();
        legalMoves.add("f");
        legalMoves.add("forward");
        legalMoves.add("b");
        legalMoves.add("backward");
        legalMoves.add("l");
        legalMoves.add("left");
        legalMoves.add("r");
        legalMoves.add("right");
    }

    public static MoveDirection[] parse(String[] arr) {
        List<MoveDirection> directions = new ArrayList<>(Collections.emptyList());

        for (String s : arr) {

            if (!legalMoves.contains(s)){
                throw new IllegalArgumentException(s+ " is not legal move specification");
            }
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
