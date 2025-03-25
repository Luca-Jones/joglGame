package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.util.texture.Texture;

/**
 * Represents the image texture to be drawn on the screen.
 */
public class Sprite {
    
    public BufferedImage image;
    public Texture texture;

    protected Sprite(String iamgeFilePath) {
        File imageFile = new File(iamgeFilePath);
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        texture = null;
    }
    
    // TODO: remove
    public Color color;
    public Sprite(Color color) {
        image = null;
        this.color = color;
    }

}
