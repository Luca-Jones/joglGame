package graphics;

import java.util.Map;
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
            sprites.put(spriteFileName, new Sprite(spriteFileName));
        }
        return sprites.get(spriteFileName);
    }
}
