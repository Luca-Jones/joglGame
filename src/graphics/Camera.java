package graphics;

import entities.GameObject;

/**
 * Basic class for shifting rendering perspective.
 * may become more complicated to allow for camera movement effects.
 */
public class Camera {
    
    private GameObject focus;
    private float xOffset, yOffset;

    public Camera (GameObject focus, float xOffset, float yOffset) {
        this.focus = focus;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public float getX() {
        return focus.getX() - xOffset;
    }

    public float getY() {
        return focus.getY() - yOffset;
    }

    public void update(double deltaTime) {
        // TODO: implement camera movement
    }

    public void setFocus(GameObject focus) {
        this.focus = focus;
    }

}
