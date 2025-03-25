package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * Handles simple graphics related tasks.
 */
public class Graphics {

    public static final float DEFAULT_COLOR = 1.0f;
    public static final float DEFAULT_ALPHA = 1.0f;
    public static final float DEFAULT_ROTATION = 0.0f;

    private GL2 gl;
    private TextRenderer textRenderer;
    private int windowWidth, windowHeight, unitsWide;
    private float red, green, blue, alpha; // 0-1
    private float rotation; // degrees CCW

    public Graphics(GL2 gl, int unitsWide) {
        this.gl = gl;
        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 18));
        // gl.glEnable(GL2.GL_BLEND); // enable transparency
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        red = DEFAULT_COLOR;
        green = DEFAULT_COLOR;
        blue = DEFAULT_COLOR;
        alpha = DEFAULT_ALPHA;
        rotation = DEFAULT_ROTATION;
        windowWidth = 0;
        windowHeight = 0;
        this.unitsWide = unitsWide;
    }

    public void clearScreen() {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    }

    /*
     * Each value ranges from 0-1
     * for color: 0 is no color, 1 is full color
     * for alpha: 0 is fully transparent, 1 is fully opaque
     */
    public void setColor(float red, float green, float blue, float alpha) {
        this.red = Math.max(0, Math.min(1, red));
        this.green = Math.max(0, Math.min(1, green));
        this.blue = Math.max(0, Math.min(1, blue));
        this.alpha = Math.max(0, Math.min(1, alpha));
    }

    public void setColor(int red, int green, int blue, int alpha) {
        setColor(red/255.0f, green/255.0f, blue/255.0f, alpha/255.0f);
    }

    public void setColor(Color color) {
        setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void resetColor() {
        setColor(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_ALPHA);
    }

    public void setRotation(float rotation) {
        while (rotation < 0) {
            rotation += 360;
        }
        while (rotation >= 360) {
            rotation -= 360;
        }
        this.rotation = rotation;
    }

    public void resetRotation() {
        setRotation(DEFAULT_ROTATION);
    }

    /**
     * draws a rectangle outline with center (x,y) and dimensions (width, height)
     * with the rotation and color of this Graphics instance
     */
    public void drawRect(float x, float y, float width, float height) {

        gl.glColor4f(red, green, blue, alpha);

        gl.glTranslatef(x, y, 0);
        gl.glRotatef(rotation, 0, 0, 1);
        
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-width/2, -height/2);
        gl.glVertex2f(width/2, -height/2);
        gl.glVertex2f(width/2, height/2);
        gl.glVertex2f(-width/2, height/2);
        gl.glEnd();
        gl.glFlush();
        
        gl.glRotatef(-rotation, 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);
        
    }
    
    /**
     * draws a rectangle with center (x,y) and dimensions (width, height)
     * with the rotation and color of this Graphics instance
     */
    public void fillRect(float x, float y, float width, float height) {
        
        gl.glColor4f(red, green, blue, alpha);
        
        gl.glTranslatef(x, y, 0);
        gl.glRotatef(rotation, 0, 0, 1);
        
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(width/2, height/2);
        gl.glVertex2f(-width/2, height/2);
        gl.glVertex2f(-width/2, -height/2);
        gl.glVertex2f(width/2, -height/2);
        gl.glEnd();
        gl.glFlush();
        
        gl.glRotatef(-rotation, 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);
    }

    public void drawImage(BufferedImage image, float x, float y, float width, float height) {
        Texture texture = AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
        drawTexture(texture, x, y, width, height);
    }

    public void drawTexture(Texture texture, float x, float y, float width, float height) {
        if (texture == null) {
            return;
        }

        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
        gl.glColor4f(red, green, blue, alpha);

        gl.glTranslatef(x, y, 0);
        gl.glRotatef(rotation, 0, 0, 1);
        
        gl.glBegin(GL2.GL_QUADS);
        
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(width/2, height/2);
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(-width/2, height/2);
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(-width/2, -height/2);
        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(width/2, -height/2);
        
        gl.glEnd();
        gl.glFlush();

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        
        gl.glRotatef(-rotation, 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);
    }

    public void drawSprite(Sprite sprite, float x, float y, float width, float height) {
        if (sprite == null) {
            return;
        } 
        if (sprite.texture == null) {
            sprite.texture = AWTTextureIO.newTexture(gl.getGLProfile(), sprite.image, true);
        }
        drawTexture(sprite.texture, x, y, width, height);
    }

    public void setFont(Font font) {
        textRenderer = new TextRenderer(font);
    }

    public void drawString(String string, int x, int y, int windowWidth, int windowHeight) {
        gl.glRasterPos2f(x, y);
        textRenderer.beginRendering(windowWidth, windowHeight);
        textRenderer.setColor(red, green, blue, alpha);
        textRenderer.draw(string, x, y);
        textRenderer.endRendering();   
    }

    public void drawString(String string, float x, float y) {
        gl.glWindowPos2f(x, y);
        textRenderer.beginRendering(windowWidth, windowHeight);
        textRenderer.setColor(red, green, blue, alpha);
        textRenderer.draw(string, 
            (int) (x / (double) unitsWide * windowWidth) + windowWidth / 2, 
            (int) (y / (double) unitsWide * windowWidth) + windowHeight / 2
        );
        textRenderer.endRendering();
    }

    public void updateWindowSize(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void setGl(GL2 gl) {
        this.gl = gl;
    }

}
