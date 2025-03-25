package entities;

import java.awt.Color;

import entities.properties.BlockType;
import entities.properties.Depth;
import graphics.Sprite;

public class Block extends Entity {

    protected BlockType type;

    public Block(BlockType type, float x, float y, float width, float height) {
        super(x, y, width, height, getSprite(type), Depth.BACK);
        this.type = type;
    }

    @Override
    public void update(double deltaTime) {
        // update animation
    }

    @Override
    public void handleCollision(Entity otherEntity) {
        
    }

    private static Sprite getSprite(BlockType type) {
        switch (type) {
            case BlockType.SOLID:
                return new Sprite(Color.BLUE);
            default:
                return null;
        }
    }
    
}
