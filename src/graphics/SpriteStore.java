package graphics;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

/**
 * Singleton class to store references to srprites in the game.
 */
public class SpriteStore {

    public static SpriteStore instance;
    private Map<String, Sprite> sprites;

    public static SpriteStore getInstance() {
        if (instance == null) {
            instance = new SpriteStore();
        }
        return instance;
    }

    private SpriteStore() {
        sprites = new HashMap<String, Sprite>();
    }

    public Sprite getSprite(String spriteFileName) {
        if (!sprites.containsKey(spriteFileName)) {
            try {
                sprites.put(spriteFileName, new Sprite(spriteFileName));
            } catch (IOException | NullPointerException e) { // stinky
                sprites.put(spriteFileName, new Sprite(java.awt.Color.BLACK)); // TODO: remove
            }
        }
        return sprites.get(spriteFileName);
    }
}
