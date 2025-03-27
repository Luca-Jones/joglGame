package entities;

import entities.properties.CollisionPriorityComparator;
import entities.properties.Depth;
import graphics.Sprite;
import utils.PriorityCollection;

/**
 * Handles collision logic and update logic for all entities in the game.
 */
public abstract class Entity extends GameObject implements Comparable<Entity> {
    
    protected PriorityCollection<Entity> collisions;

    public Entity(float x, float y, float width, float height, Sprite sprite, Depth depth) {
        super(x, y, width, height, sprite, depth);
        collisions = new PriorityCollection<>(new CollisionPriorityComparator(this));
    }
    
    public abstract void update(double deltaTime);
    public abstract void handleCollision(Entity otherEntity);

    public void addCollision(Entity otherEntity) {
        collisions.add(otherEntity);
    }

    public void handleCollisions() {
        for (Entity entity : collisions) {
            handleCollision(entity);
        }
        collisions.clear();
    }

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
