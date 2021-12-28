package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class GuiElementBox {
    private App app;
    private StackPane pane;
    private IMapElement mapElement;

    public GuiElementBox(IMapElement mapElement,int startEnergy,App app) {
        this.app = app;
        this.mapElement = mapElement;

        this.pane = new StackPane();

        Rectangle rectangle;
        if (mapElement.getClass() == Animal.class) {
            Color c = Color.rgb(
                    255 - Math.min(255, 255 * ((Animal) mapElement).getEnergy() / startEnergy),
                    0,
                    Math.min(255, 255 * ((Animal) mapElement).getEnergy() / startEnergy));
            rectangle = new Rectangle(10, 10, c);
        } else {
            rectangle = new Rectangle(5, 5, Color.GREEN);
        }

        pane.getChildren().addAll(rectangle);

        pane.setAlignment(Pos.CENTER);


        pane.setOnMouseClicked((EventHandler<? super MouseEvent>) actionEvent -> {
            if(this.mapElement.getClass() == Animal.class){
                ((Animal) this.mapElement).setObserved(true);
                app.setObservedAnimal((Animal) this.mapElement);
            }
        });


    }
    public StackPane getVBox(){
        return this.pane;
    }

}
