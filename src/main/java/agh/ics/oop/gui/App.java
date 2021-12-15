package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
    AbstractWorldMap map;

    public void start(Stage primaryStage){

        /*Label label = new Label("Zwierzak");
        Label label2 = new Label("Zwierzak");*/

        primaryStage.setTitle("FX GridPane Example");
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        /*gridPane.add(label, 0,0,1,1);
        gridPane.add(label2, 12,12,1,1);*/

        int low = map.lowleft().getY();
        int up = map.upright().getY();
        int left = map.lowleft().getX();
        int right = map.upright().getX();
        int height = up - low+1;
        int width = right - left+1;

        for (int i = 0; i < width+1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(20); // width in pixels
//			columnConstraints.setPercentWidth(100.0 / noOfCols); // percentage of total width
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < height+1; i++) {
            RowConstraints rowConstraints = new RowConstraints(20);
            //rowConstraints.setPercentHeight(100.0 / noOfRows);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        int xIndex;
        int yIndex;
        for(int i = 0; i < width+1; i++){
            for(int j = 0; j < height+1; j++){
                String text;
                xIndex = left + i - 1;
                yIndex = height - j-1;
                if(i==0 & j == 0){
                    text = "y\\x";
                }else if(j == 0){
                    text = "" + xIndex;
                }else if(i==0){
                    text = ""+ yIndex;
                }else{
                    Object object = map.objectAt(new Vector2d(xIndex,yIndex));
                    if(object != null){
                        text = object.toString();
                    }else{
                        text = "";
                    }

                }
                Label label = new Label(text);
                GridPane.setHalignment(label, HPos.CENTER);
                gridPane.add(label, i, j);
            }
        }





        Scene scene = new Scene(gridPane, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void init(){
        try{
            System.out.println("system wystartował");

            // String[] tab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            String[] tab = getParameters().getRaw().toArray(new String[0]);
            MoveDirection[] directions = new OptionsParser().parse( tab );
            //IWorldMap map = new RectangularMap(11, 11);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            this.map = (AbstractWorldMap) map;
            // System.out.println(map.toString());

            System.out.println("system zakończył działanie");
        }catch(IllegalArgumentException ex){
            System.out.println(ex);
        }


    }


}

