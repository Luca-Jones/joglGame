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
    public String name;

    protected Sprite(String iamgeFilePath) throws IOException {
        name = iamgeFilePath;
        File imageFile = new File(iamgeFilePath);
        image = ImageIO.read(imageFile);
        texture = null;
    }
    
    // TODO: remove
    public Color color;
    public Sprite(Color color) {
        image = null;
        this.color = color;
    }

}
