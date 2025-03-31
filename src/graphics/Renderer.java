package graphics;

import java.awt.Color;
import java.util.List;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import entities.GameObject;
import inputs.ClientKeyInputHandler;
import inputs.KeyInputHandler;

/**
 * Resposible for rendering the game and all game objects in it.
 * 
 */
public class Renderer implements GLEventListener {
    
    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    
    private Graphics g;
    private GLWindow window;
    private Camera camera;
    private int windowWidth;
    private int windowHeight;
    private int unitsWide;
    private List<? extends GameObject> gameObjects;
    private KeyListener keyListener;
    
    private long currentTime;
    private long previousTime;
    private int frames;
    private int fps;

    public Renderer(int unitsWide, List<? extends GameObject> gameObjects, GameObject cameraFocus) {

        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        this.unitsWide = unitsWide;
        this.gameObjects = gameObjects;
        camera = new Camera(cameraFocus, 0, -1);

        currentTime = 0;
        previousTime = System.nanoTime();
        frames = 0;
        fps = 0;

        window = GLWindow.create(capabilities);
        window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        window.addGLEventListener(this);
        window.setResizable(true);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);

    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
        window.addKeyListener(keyListener);
    }

    public void render() {
        window.display();
    }

    // TODO: stinky code
    public void update(double deltaTime) {

        camera.update(deltaTime);

        if (keyListener != null) {
            if (keyListener instanceof KeyInputHandler) {
                ((KeyInputHandler) keyListener).handleKeys();
            } else if (keyListener instanceof ClientKeyInputHandler) {
                ((ClientKeyInputHandler) keyListener).handleKeys();
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        frames++;
        currentTime = System.nanoTime();
        if (currentTime - previousTime >= 1e9) {
            fps = frames / ((int) ((currentTime - previousTime) / 1e9));
            frames = 0;
            previousTime = currentTime;
        }

        g.clearScreen();
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + fps, 0, (int) (0.9 * windowHeight), windowWidth, windowHeight);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g, camera);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.exit(0);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        g = new Graphics(gl, unitsWide);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
        GL2 gl = drawable.getGL().getGL2();
        if (g != null) {
            g.setGl(gl);
            g.updateWindowSize(width, height);
        }

        windowWidth = width;
        windowHeight = height;

        gl.glMatrixMode(GL2.GL_PROJECTION);             // set matrix to projection
        gl.glLoadIdentity();                            // load identity matrix
        
        // apply orthographic projection
        gl.glOrtho(
            -unitsWide / 2,                                 // left 
            unitsWide /2 ,                                  // right
            - ((double) height) / width * unitsWide / 2,    // bottom 
            ((double) height) / width * unitsWide / 2,      // top
            -1, 1
        ); 

        gl.glMatrixMode(GL2.GL_MODELVIEW);              // set matrix back to modelview

    }


}   
