package entities;

import entities.properties.CollisionPriorityComparator;
import entities.properties.Depth;
import graphics.Sprite;
import utils.PriorityCollection;

/**
 * handles movement logic for moveable entities in the game.
 */
public abstract class MoveableEntity extends Entity {
    
    protected float previousX, previousY;
    protected float velocityX, velocityY;
    protected float accelerationX, accelerationY;
    protected float frictionCoefficient;
    protected PriorityCollection<Entity> collisions;

    public MoveableEntity(float x, float y, float width, float height, Sprite sprite, Depth depth, float frictionCoefficient, float gravity) {
        super(x, y, width, height, sprite, depth);
        previousX = x;
        previousY = y;
        velocityX = 0;
        velocityY = 0;
        this.frictionCoefficient = frictionCoefficient;
        accelerationX = 0;
        accelerationY = gravity;
        collisions = new PriorityCollection<>(new CollisionPriorityComparator(this));
    }

    @Override
    public void update(double deltaTime) {
        
        previousX = x;
        previousY = y;

        accelerationX = -frictionCoefficient * velocityX;

        velocityX += accelerationX / 2 * deltaTime;
        velocityY += accelerationY / 2 * deltaTime;
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        velocityX += accelerationX / 2 * deltaTime;
        velocityY += accelerationY / 2 * deltaTime;
        
    }

    protected boolean isAbove(Entity otherEntity) {
        return previousY - height / 2 >= otherEntity.y + otherEntity.height / 2;
    }

    protected boolean isBelow(Entity otherEntity) {
        return previousY + height / 2 <= otherEntity.y - otherEntity.height / 2;
    }

    protected boolean isRightOf(Entity otherEntity) {
        return previousX - width / 2 >= otherEntity.x + otherEntity.width / 2;
    }

    protected boolean isLeftOf(Entity otherEntity) {
        return previousX + width / 2 <= otherEntity.x - otherEntity.width / 2;
    }

    public abstract void handleCollision(Entity otherEntity);

    public void addCollision(Entity otherEntity) {
        collisions.add(otherEntity);
    }

    public void handleCollisions() {
        for (Entity entity : collisions) {
            if (isColliding(entity)) {
                handleCollision(entity);
            }
        }
        collisions.clear();
    }

}
