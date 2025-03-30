package entities;

import entities.properties.Depth;
import graphics.Sprite;

/**
 * Handles some collision logic and declares update logic for all entities in the game.
 */
public abstract class Entity extends GameObject implements Comparable<Entity> {
    
    public Entity(float x, float y, float width, float height, Sprite sprite, Depth depth) {
        super(x, y, width, height, sprite, depth);
    }
    
    public abstract void update(double deltaTime);
    
    @Override
    public int compareTo(Entity o) {
        return depth.compareTo(o.depth);
    }

    public boolean isColliding(Entity otherEntity) {
        // TODO: assuming no rotation for now
        return !(
            x - width / 2 >= otherEntity.x + otherEntity.width / 2 ||
            x + width / 2 <= otherEntity.x - otherEntity.width / 2 ||
            y - height / 2 >= otherEntity.y + otherEntity.height / 2 ||
            y + height / 2 <= otherEntity.y - otherEntity.height / 2
        );
    }

}
