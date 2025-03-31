package entities;

import entities.properties.Depth;
import graphics.Sprite;

public class ClientEntity extends GameObject {

    public ClientEntity(float x, float y, float width, float height, Sprite sprite, Depth depth, float rotation) {
        super(x, y, width, height, sprite, depth);
        this.rotation = rotation;
    }
    
}
