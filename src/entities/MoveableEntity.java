package entities;

import entities.properties.Depth;
import graphics.Sprite;

/**
 * handles movement logic for moveable entities in the game.
 */
public abstract class MoveableEntity extends Entity {
    
    protected float previousX, previousY;
    protected float velocityX, velocityY;
    protected float accelerationX, accelerationY;
    protected float frictionCoefficient;

    public MoveableEntity(float x, float y, float width, float height, Sprite sprite, Depth depth, float frictionCoefficient, float gravity) {
        super(x, y, width, height, sprite, depth);
        previousX = x;
        previousY = y;
        velocityX = 0;
        velocityY = 0;
        this.frictionCoefficient = frictionCoefficient;
        accelerationX = 0;
        accelerationY = gravity;
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

    @Override
    public void handleCollision(Entity otherEntity) {}

}
