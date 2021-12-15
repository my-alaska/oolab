package agh.ics.oop.gui;
import agh.ics.oop.*;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private VBox vbox;

    public GuiElementBox(IMapElement mapElement) {
        try{
            Image image = new Image(new FileInputStream(mapElement.imageResource()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            Label label;
            if (mapElement.getClass() == Animal.class){
                label = new Label("Z " + mapElement.getPosition().toString() + mapElement.toString());
            }else{
                label = new Label("Trawa");
            }
            this.vbox = new VBox();
            vbox.getChildren().addAll(imageView, label);
            vbox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }


    }

    public VBox getVBox(){
        return this.vbox;
    }



}
