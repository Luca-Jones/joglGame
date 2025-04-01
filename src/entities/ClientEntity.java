package entities;

import entities.properties.Depth;
import graphics.Sprite;

public class ClientEntity extends GameObject {

    public boolean isFocus;

    public ClientEntity(float x, float y, float width, float height, Sprite sprite, Depth depth, float rotation, boolean isFocus) {
        super(x, y, width, height, sprite, depth);
        this.rotation = rotation;
        this.isFocus = isFocus;
    }
    
}
