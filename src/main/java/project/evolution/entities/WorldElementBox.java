package project.evolution.entities;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class WorldElementBox {

    private final VBox box;

    public WorldElementBox(Entity element, int breedingEnergy, int energy, double size, int option) {

        String resourceName;
        if(option==1){
            resourceName = element.getEnhancedResourceName();
        }
        else {
            resourceName = element.getResourceName();
        }

        Image entityImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/"+resourceName)));

        ImageView entityImageView = new ImageView(entityImage);

        entityImageView.setFitWidth(size);
        entityImageView.setFitHeight(size);

        box = new VBox();

        int proportion = (8 * energy) / breedingEnergy;
        if(energy==0){
            proportion=0;
        }


        if(energy!=-1){
            resourceName = switch (proportion){
                case 0 -> "healthBar0.png";
                case 1 -> "healthBar1.png";
                case 2 -> "healthBar2.png";
                case 3 -> "healthBar3.png";
                case 4 -> "healthBar4.png";
                case 5 -> "healthBar5.png";
                case 6 -> "healthBar6.png";
                case 7 -> "healthBar7.png";
                default -> "healthBar8.png";
            };
            Image barImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/"+resourceName)));
            ImageView barImageView = new ImageView(barImage);

            barImageView.setFitWidth(size);
            barImageView.setFitHeight(size / 8);

            box.getChildren().addAll(entityImageView,barImageView);
        }
        else{
            box.getChildren().addAll(entityImageView);
        }

        box.setAlignment(Pos.CENTER);
    }

    public VBox getContainer() {
        return box;
    }
}
