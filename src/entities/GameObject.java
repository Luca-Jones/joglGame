package entities;

import entities.properties.Depth;
import graphics.Camera;
import graphics.Graphics;
import graphics.Sprite;

/**
 * Basic class for any drawable object in the game.
 */
public abstract class GameObject {
    
    protected float x, y, width, height;
    protected float rotation;
    protected Depth depth;
    protected Sprite sprite;

    public GameObject(float x, float y, float width, float height, Sprite sprite, Depth depth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.depth = depth;
        rotation = 0;
    }

    public void draw(Graphics g, Camera camera) {
        g.resetColor();
        g.setRotation(rotation);
        if (sprite.image == null && sprite.color != null) { // TODO: remove
            g.setColor(sprite.color);
            g.fillRect(x - camera.getX(), y - camera.getY(), width, height);
            return;
        }
        g.drawSprite(sprite, x - camera.getX(), y - camera.getY(), width, height); 
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
