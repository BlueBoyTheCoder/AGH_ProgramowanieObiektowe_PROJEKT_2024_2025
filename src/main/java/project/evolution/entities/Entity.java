package project.evolution.entities;

import project.evolution.entities.traits.Vector2d;

public interface Entity {

    Vector2d getPosition();

    String getResourceName();

    String getEnhancedResourceName();
}
