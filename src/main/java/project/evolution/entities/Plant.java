package project.evolution.entities;

import project.evolution.entities.traits.Vector2d;

public class Plant implements  Entity{
    private Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getResourceName(){
        return "plant.png";
    }

    @Override
    public String getEnhancedResourceName(){
        return "plant.png";
    }
}
