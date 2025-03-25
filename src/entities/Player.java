package entities;

import java.awt.Color;

import entities.properties.BlockType;
import entities.properties.Depth;
import entities.properties.Direction;
import entities.properties.playerstates.IdleState;
import entities.properties.playerstates.PlayerEvent;
import entities.properties.playerstates.PlayerState;
import graphics.Camera;
import graphics.Graphics;
import graphics.SpriteStore;

/**
 * Handles players abilities and logic. The controller is a state machine
 */
public class Player extends MoveableEntity {
    
    // TODO: player control state machine

    private static float GRAVITY = -10f;
    private static float FRICTION_COEFFICIENT = 1.5f;
    private static final String SPRITE_FILE_NAME = "resources/image.png";
    public static final float JUMP_STAT = 2;
    public static final float SPEED_STAT = 1f;

    private String username;
    public Direction direction;
    private PlayerState state;

    public Player(String username, float x, float y) {
        super(
            x, y, 1, 1,
            SpriteStore.getInstance().getSprite(SPRITE_FILE_NAME), Depth.FRONT,
            FRICTION_COEFFICIENT, GRAVITY
        );
        this.username = username;
        this.direction = Direction.RIGHT;
        this.state = IdleState.getInstance(this);
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        super.draw(g, camera);
        g.setColor(Color.RED);
        g.drawString(username, x - camera.getX(), y - camera.getY() + 0.6f);
        g.drawString(state.getClass().getSimpleName(), x - camera.getX(), y - camera.getY() + 0.8f);
    }

    @Override
    public void handleCollision(Entity otherEntity) {
        super.handleCollision(otherEntity);
        if (otherEntity instanceof Block) {
            Block block = (Block) otherEntity;
            if (block.type == BlockType.PLATFORM) {
                if (isAbove(block)) {
                    y = block.y + block.height / 2 + height / 2;
                    velocityY = 0;
                } else if (isBelow(block)) {
                    // do nothing
                } else if (isRightOf(block)) {
                    x = block.x + block.width / 2 + width / 2;
                    velocityX = 0;
                } else if (isLeftOf(block)) {
                    x = block.x - block.width / 2 - width / 2;
                    velocityX = 0;
                }
            } else if (block.type == BlockType.WALL) {
                if (isAbove(block)) {
                    y = block.y + block.height / 2 + height / 2;
                    velocityY = 0;
                } else if (isBelow(block)) {
                    // do nothing
                } else if (isRightOf(block)) {
                    x = block.x + block.width / 2 + width / 2;
                    velocityX = 0;
                } else if (isLeftOf(block)) {
                    x = block.x - block.width / 2 - width / 2;
                    velocityX = 0;
                }
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        state = state.update(deltaTime);
        if (y - height/2 <= 0) {
            y = height/2;
            velocityY = 0;
        }
    }

    public void jump() {
        velocityY = JUMP_STAT;
    }

    public void doubleJump() {}

    public void moveLeft() {
        state = state.handleEvent(PlayerEvent.MOVE_LEFT);
    }
    
    public void moveRight() {
        state = state.handleEvent(PlayerEvent.MOVE_RIGHT);
    }

    public void dash() {}

    public void rotate(float angle) {
        rotation += angle;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    
    public float getAccelerationX() {
        return accelerationX;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

}
