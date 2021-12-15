package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IPositionChangeObserver{
    private AbstractWorldMap map;
    private GridPane gridPane;
    private Stage primaryStage;
    private VBox controlPanel;
    private int gridSize = 60;
    private int height;
    private int width;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        gridPane =  gridPaneGenerate();

        HBox sceneBox = new HBox();
        sceneBox.getChildren().add(gridPane);
        sceneBox.getChildren().add(controlPanel);

        Scene scene = new Scene(sceneBox, gridSize*(width+3), gridSize*(height+1));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane gridPaneGenerate(){
        GridPane grid = new GridPane();

        int low = map.lowleft().getY();
        int up = map.upright().getY();
        int left = map.lowleft().getX();
        int right = map.upright().getX();

        height = up - low+1;
        width = right - left+1;

        for (int i = 0; i < width+1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(gridSize);
            grid.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < height+1; i++) {
            RowConstraints rowConstraints = new RowConstraints(gridSize);
            grid.getRowConstraints().add(rowConstraints);
        }

        Label label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        grid.add(label, 0, 0);

        String text;
        int xIndex;
        int yIndex;
        for(int i = 1; i < width + 1; i++){
            xIndex = left + i - 1;
            text = ""+xIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i, 0);
        }
        for(int j = 1; j < height + 1; j++){
            yIndex = up - j +1;
            text = ""+yIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, j);
        }
        for (Vector2d v : map.getElementPositions()){
            IMapElement object = (IMapElement) map.objectAt(v);
            if (object != null) {
                VBox vbox = new GuiElementBox(object).getVBox();
                GridPane.setHalignment(vbox, HPos.CENTER);
                grid.add(vbox, v.getX() - left + 1, up - v.getY() + 1 );
            }
        }

        grid.setGridLinesVisible(true);
        return grid;
    }

    @Override
    public void init(){
        gridPane = new GridPane();
        try{
            String[] tab = getParameters().getRaw().toArray(new String[0]);
            MoveDirection[] directions = new OptionsParser().parse( tab );
            AbstractWorldMap map = new GrassField(10);
            this.map = map;
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4)};
            SimulationEngine engine = new SimulationEngine(directions, map, positions);

            for(Animal animal: map.getAnimals().values()){
                animal.addObserver(this);
            }



            Thread engineThread = new Thread(engine);

            TextField addedMoveDirections = new TextField();

            Button startButton = new Button("run/start");
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    engineThread.start();//... do something in here.

                }
            });

            Button setMovesButton = new Button("set moves");
            setMovesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    engine.setMoves(new OptionsParser().parse(addedMoveDirections.getText().split("")));

                }
            });

            controlPanel = new VBox();
            controlPanel.getChildren().add(startButton);
            controlPanel.getChildren().add(addedMoveDirections);
            controlPanel.getChildren().add(setMovesButton);
            controlPanel.setAlignment(Pos.CENTER);











        }catch(IllegalArgumentException ex){
            System.out.println(ex);
        }


    }

    private void drawMap(){
        gridPane.getChildren().clear();
        gridPane = gridPaneGenerate();

        HBox sceneBox = new HBox();
        sceneBox.getChildren().add(gridPane);
        sceneBox.getChildren().add(controlPanel);


        Scene scene = new Scene(sceneBox, gridSize*(width+3), gridSize*(height+1));



        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            drawMap();
            try{
            Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        });
    }

}

