package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;

public class App extends Application implements IDayPassObserver {  // ta klasa jest przerośnięta
    private FrontMap frontMap;
    private GridPane gridPane;
    private Stage primaryStage;
    private VBox controlPanel;
    private int gridSize = 20;
    private int height;
    private int width;
    private HBox sceneBox;
    private Scene scene;
    private int startEnergy;
    private Animal observedAnimal = null;
    private Boolean geneMapToggle = false;
    private LineChart lineChart;
    private XYChart.Series dataSeries1;
    private XYChart.Series dataSeries2;
    private XYChart.Series dataSeries3;
    private XYChart.Series dataSeries4;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gridPane = gridPaneGenerate();

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("days");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("value");
        this.lineChart = new LineChart(xAxis,yAxis);
        lineChart.setAnimated(true);

        dataSeries1 = new XYChart.Series();
        dataSeries1.setName("grass");

        dataSeries2 = new XYChart.Series();
        dataSeries2.setName("animals");

        dataSeries3 = new XYChart.Series();
        dataSeries3.setName("avg energy");

        dataSeries4 = new XYChart.Series();
        dataSeries4.setName("avg no children");
        lineChart.setMaxHeight(250);
        lineChart.setMinWidth(300);

        lineChart.getData().add(dataSeries1);
        lineChart.getData().add(dataSeries2);
        lineChart.getData().add(dataSeries3);
        lineChart.getData().add(dataSeries4);


        sceneBox = new HBox();

        controlPanel.setMinWidth(150);
        sceneBox.getChildren().add(0, controlPanel);
        sceneBox.getChildren().add(1, gridPane);
        sceneBox.getChildren().add(2,lineChart);


        scene = new Scene(sceneBox);
        primaryStage.setHeight(gridSize * (height + 1) + 250);
        primaryStage.setWidth(gridSize * (2 * width + 7) + 400 + 14);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void init() {
        gridPane = new GridPane();

        try {


            int mapX = 10;
            int mapY = 10;
            int jungleX = mapX / 2;
            int jungleY = mapY / 2;
            int dayUseEnergy = 1;
            int grassEnergy = 100;
            int startAnimalsNumber = 10;
            int startEnergy = 100;
            this.startEnergy = startEnergy;


            int sexEnergy = startEnergy / 2;
            Vector2d mapCorner = new Vector2d(mapX, mapY);
            Vector2d jungleCorner = new Vector2d(jungleX, jungleY);


            FrontMap frontMap = new FrontMap(mapCorner, jungleCorner, grassEnergy, dayUseEnergy, sexEnergy, startEnergy);
            this.frontMap = frontMap;
            frontMap.setObserver(this);
            SimulationEngine engine = new SimulationEngine(frontMap, startAnimalsNumber, startEnergy);
            Thread engineThread = new Thread(engine);


            TextField modifyMapCorner = new TextField();
            TextField modifyJungleCorner = new TextField();
            TextField modifyDayUseEnergy = new TextField();
            TextField modifyGrassEnergy = new TextField();
            TextField modifyStartAnimalsNumber = new TextField();
            TextField modifyStartEnergy = new TextField();


            Button startButton = new Button("run/start");
            startButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent actionEvent) {
                    sceneUpdate();
                    engineThread.start();
                }
            });


            Button suspendButton = new Button("suspend/resume");
            suspendButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    sceneUpdate();
                    engine.setRunning(!engine.isRunning());
                    if (engine.isRunning()) {
                        synchronized (engine.getLock()) {
                            engine.getLock().notify();
                        }
                    }
                }
            });


            Button mapCornerButton = new Button("set frontMap corner");
            mapCornerButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String[] newMapCoordinates = modifyMapCorner.getText().split(" ");
                    int newMapx = Integer.parseInt(newMapCoordinates[0]);
                    int newMapY = Integer.parseInt(newMapCoordinates[1]);
                    Vector2d newMapCorner = new Vector2d(newMapx, newMapY);
                    if (!newMapCorner.follows(frontMap.getJungleCorner())) {
                        throw new IllegalArgumentException("Map corner vector must follow jungle corner vector");
                    }
                    frontMap.setMapCorner(newMapCorner);

                }
            });


            Button jungleCornerButton = new Button("map corner ratio");
            jungleCornerButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    float ratio = Float.parseFloat(modifyJungleCorner.getText().toString());
                    int newMapx = (int) (frontMap.getMapCorner().getX()*ratio);
                    int newMapY = (int) (frontMap.getMapCorner().getY()*ratio);
                    Vector2d newJungleCorner = new Vector2d(newMapx, newMapY);
                    if (!newJungleCorner.follows(new Vector2d(0, 0))) {
                        throw new IllegalArgumentException("Jungle corner must follow (0,0) vector");
                    }
                    if (!newJungleCorner.precedes(frontMap.getMapCorner())) {
                        throw new IllegalArgumentException("Jungle corner vector must precede frontMap corner vector");
                    }
                    frontMap.setJungleCorner(newJungleCorner);
                    System.out.println(frontMap.getJungleCorner());
                }
            });


            Button dayUseEnergyButton = new Button("set energy/day");
            dayUseEnergyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int newDayUseEnergy = Integer.parseInt(modifyDayUseEnergy.getText());
                    if (newDayUseEnergy <= 0) {
                        throw new IllegalArgumentException("energy used per day must be greater than 0");
                    }
                    frontMap.setDayUseEnergy(newDayUseEnergy);
                }
            });


            Button grassEnergyButton = new Button("set grass energy");
            grassEnergyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int newGrassEnergy = Integer.parseInt(modifyGrassEnergy.getText());
                    if (newGrassEnergy <= 0) {
                        throw new IllegalArgumentException("Grass energy must be greater than 0");
                    }
                    frontMap.setGrassEnergy(newGrassEnergy);
                }
            });


            Button startAnimalsNumberButton = new Button("set no. animals");
            startAnimalsNumberButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int newStartAnimalsNumber = Integer.parseInt(modifyStartAnimalsNumber.getText());
                    if (newStartAnimalsNumber < 2) {
                        throw new IllegalArgumentException("There must be at least 2 animals at the beginning");
                    }
                    engine.setStartAnimalsNumber(newStartAnimalsNumber);
                }
            });


            Button startEnergyButton = new Button("set start energy");
            startEnergyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int newStartEnergy = Integer.parseInt(modifyStartEnergy.getText());
                    setStartEnergy(newStartEnergy);
                    if (newStartEnergy < 1) {
                        throw new IllegalArgumentException("Anima's starting energy must be greater than 0");
                    }
                    engine.setStartEnergy(newStartEnergy);
                    frontMap.setSexEnergy(newStartEnergy / 2);
                }
            });

            Button magicFrontmapButton = new Button("magick front");
            magicFrontmapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    accessMap().switchMagicTactics();
                }
            });

            Button magicBackmapButton = new Button("magick back");
            magicBackmapButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    accessMap().getBackMap().switchMagicTactics();
                }
            });

            Button showBestGenesButton = new Button("show best genes");
            showBestGenesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(!geneMapToggle){
                        showBestGenes();
                        geneMapToggle = true;
                    }else{
                        drawMap();
                        geneMapToggle = false;
                    }

                }
            });


            this.controlPanel = new VBox();
            this.controlPanel.getChildren().add(startButton);
            this.controlPanel.getChildren().add(suspendButton);
            this.controlPanel.getChildren().add(modifyMapCorner);
            this.controlPanel.getChildren().add(mapCornerButton);
            this.controlPanel.getChildren().add(modifyJungleCorner);
            this.controlPanel.getChildren().add(jungleCornerButton);
            this.controlPanel.getChildren().add(modifyDayUseEnergy);
            this.controlPanel.getChildren().add(dayUseEnergyButton);
            this.controlPanel.getChildren().add(modifyGrassEnergy);
            this.controlPanel.getChildren().add(grassEnergyButton);
            this.controlPanel.getChildren().add(modifyStartAnimalsNumber);
            this.controlPanel.getChildren().add(startAnimalsNumberButton);
            this.controlPanel.getChildren().add(modifyStartEnergy);
            this.controlPanel.getChildren().add(startEnergyButton);
            this.controlPanel.getChildren().add(magicFrontmapButton);
            this.controlPanel.getChildren().add(magicBackmapButton);
            this.controlPanel.getChildren().add(showBestGenesButton);
            this.controlPanel.setAlignment(Pos.CENTER);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }





    void setStartEnergy(int newStartEnergy) {
        this.startEnergy = newStartEnergy;
    }
    private void sceneUpdate() {
        updateWidthAndHeight();
        primaryStage.setHeight(gridSize * (height + 1) + 250);
        primaryStage.setWidth(gridSize * (2 * width + 7) + 400 + 14);

    }
    private int[] updateWidthAndHeight() {
        int low = frontMap.getMapCorner().opposite().getY();
        int up = frontMap.getMapCorner().getY();
        int left = frontMap.getMapCorner().opposite().getX();
        int right = frontMap.getMapCorner().getX();

        height = up - low + 1;
        width = right - left + 1;
        return new int[]{low, up, left, right};
    }







    private GridPane gridPaneGenerateMap(AbstractMap map) {
        GridPane grid = new GridPane();
        int up, left;
        int[] borders = updateWidthAndHeight();
        up = borders[1];
        left = borders[2];
        for (int i = 0; i < width + 1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(gridSize);
            grid.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < height + 1; i++) {
            RowConstraints rowConstraints = new RowConstraints(gridSize);
            grid.getRowConstraints().add(rowConstraints);
        }
        Label label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        grid.add(label, 0, 0);

        String text;
        int xIndex;
        int yIndex;
        for (int i = 1; i < width + 1; i++) {
            xIndex = left + i - 1;
            text = "" + xIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i, 0);
        }

        for (int j = 1; j < height + 1; j++) {
            yIndex = up - j + 1;
            text = "" + yIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, j);
        }

        for (Vector2d v : map.getElementPositions()) {
            IMapElement object = (IMapElement) map.bestObjectAt(v);
            if (object != null) {
                StackPane stackPane = new GuiElementBox(object, startEnergy, this).getVBox();
                GridPane.setHalignment(stackPane, HPos.CENTER);
                grid.add(stackPane, v.getX() - left + 1, up - v.getY() + 1);
            }
        }
        grid.setGridLinesVisible(true);
        return grid;
    }

    private GridPane gridPaneGenerate() {
        GridPane grid = new GridPane();
        GridPane grid1 = gridPaneGenerateMap(frontMap);
        GridPane grid2 = gridPaneGenerateMap(frontMap.getBackMap());

        updateConstraints(grid);
        grid.add(grid1, 0, 0);
        grid.add(grid2, 2, 0);
        updateTextFields(grid);

        return grid;
    }
    private void updateConstraints(GridPane grid){
        grid.getColumnConstraints().add(new ColumnConstraints((gridSize * (width + 1))));
        grid.getColumnConstraints().add(new ColumnConstraints(gridSize));
        grid.getColumnConstraints().add(new ColumnConstraints((gridSize * (width + 1))));
        grid.getRowConstraints().add(new RowConstraints((gridSize * (height + 1))));
        grid.getRowConstraints().add(new RowConstraints((40)));
        grid.getRowConstraints().add(new RowConstraints((40)));
        grid.getRowConstraints().add(new RowConstraints((40)));
        grid.getRowConstraints().add(new RowConstraints((40)));
        grid.getRowConstraints().add(new RowConstraints((40)));
    }
    private void updateTextFields(GridPane grid){
        grid.add(new TextField("grass: " + frontMap.getGrassNumber()), 0, 1);
        grid.add(new TextField("animals: " + frontMap.getAnimalsNumber()), 0, 2);
        grid.add(new TextField("avrage Energy: " + frontMap.avgEnergy()), 0, 3);
        grid.add(new TextField("average no children: " + frontMap.getAvgNOChildren()), 0, 4);
        grid.add(new TextField(frontMap.getBestGenotype().toString()), 0, 5);

        if (observedAnimal != null) {
            grid.add(new TextField("observed children: " + observedAnimal.getNoObservedChildren()), 2, 1);
            grid.add(new TextField("observed descendants: " + observedAnimal.getNoObservedDescendants()), 2, 2);
            grid.add(new TextField("day of death: " + observedAnimal.getDayOfDeath()), 2, 3);
            grid.add(new TextField(observedAnimal.getGenotype().toString()), 2, 4);
        }
    }



    private void updateLineChart(){
        int day = frontMap.getWorldAge();
        dataSeries1.getData().add(new XYChart.Data(day,frontMap.getGrassNumber()));
        dataSeries2.getData().add(new XYChart.Data(day,frontMap.getAnimalsNumber()));
        dataSeries3.getData().add(new XYChart.Data(day,frontMap.avgEnergy()));
        dataSeries4.getData().add(new XYChart.Data(day,frontMap.getAvgNOChildren()));
    }

    private void drawMap() {
        sceneBox.getChildren().remove(1);
        gridPane = gridPaneGenerate();
        updateLineChart();
        sceneBox.getChildren().add(1, gridPane);
    }


    @Override
    public void dayPassed() {
        Platform.runLater(() -> {
            drawMap();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        });
    }

    public void setObservedAnimal(Animal animal) {
        this.observedAnimal = animal;
    }

    public FrontMap accessMap() {
        return this.frontMap;
    }











    //przepraszam za to -> -> -v
    private void showBestGenes() {
        HashSet<Animal>[] bestGenes = frontMap.bestGenesOnMap();
        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();

        int up, left;
        int[] borders = updateWidthAndHeight();

        up = borders[1];
        left = borders[2];


        for (int i = 0; i < width + 1; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(gridSize);
            grid1.getColumnConstraints().add(columnConstraints);
            grid2.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < height + 1; i++) {
            RowConstraints rowConstraints = new RowConstraints(gridSize);
            grid1.getRowConstraints().add(rowConstraints);
            grid2.getRowConstraints().add(rowConstraints);
        }

        Label label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        grid1.add(label, 0, 0);
        grid2.add(label, 0, 0);

        String text;
        int xIndex;
        int yIndex;
        for (int i = 1; i < width + 1; i++) {
            xIndex = left + i - 1;
            text = "" + xIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid1.add(label, i, 0);
            grid2.add(label, i, 0);
        }
        for (int j = 1; j < height + 1; j++) {
            yIndex = up - j + 1;
            text = "" + yIndex;
            label = new Label(text);
            GridPane.setHalignment(label, HPos.CENTER);
            grid1.add(label, 0, j);
            grid2.add(label, 0, j);
        }


        for (Animal a : bestGenes[0]) {
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(new Rectangle(10, 10, Color.rgb(255, 255, 0)));
            GridPane.setHalignment(stackPane, HPos.CENTER);
            grid1.add(stackPane, a.getPosition().getX() - left + 1, up - a.getPosition().getY() + 1);
        }
        grid1.setGridLinesVisible(true);

        for (Animal a : bestGenes[1]) {
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(new Rectangle(10, 10, Color.rgb(255, 255, 0)));
            GridPane.setHalignment(stackPane, HPos.CENTER);
            grid2.add(stackPane, a.getPosition().getX() - left + 1, up - a.getPosition().getY() + 1);
        }
        grid2.setGridLinesVisible(true);

        sceneBox.getChildren().remove(1);
        GridPane grid = new GridPane();
        updateConstraints(grid);

        grid.add(grid1, 0, 0);
        grid.add(grid2, 2, 0);
        updateTextFields(grid);
        sceneBox.getChildren().add(1, grid);
    }

}