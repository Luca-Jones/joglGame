package networking.packets;

import entities.ClientEntity;
import entities.GameObject;
import entities.properties.Depth;
import graphics.Sprite;
import graphics.SpriteStore;

public class EntityPacket extends Packet {
    
    public String username;
    public float x,y,width,height;
    public float rotation;
    public Depth depth;
    public String spriteName;

    public EntityPacket(String username, GameObject gameObject) {
        this.username = username;
        this.x = gameObject.getX();
        this.y = gameObject.getY();
        this.width = 0;
        this.height = 0;
        this.rotation = gameObject.getRotation();
        this.depth = gameObject.getDepth();
        this.spriteName = gameObject.getSpriteName();
    }

    public ClientEntity getEntity() {
        Sprite sprite = SpriteStore.getInstance().getSprite(spriteName);
        ClientEntity entity = new ClientEntity(x, y, width, height, sprite, depth, rotation);
        return entity;
    }
}
