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
    
    private static float GRAVITY = -5f;
    private static float FRICTION_COEFFICIENT = 2f;
    private static final String SPRITE_FILE_NAME = "resources/image.png";
    public static final float JUMP_STAT = 5;
    public static final float SPEED_STAT = 3;

    public String username;
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
        if (otherEntity instanceof Block) {
            Block block = (Block) otherEntity;
            if (block.type == BlockType.SOLID) {
                if (isAbove(block)) {
                    y = block.y + block.height / 2 + height / 2;
                    state = state.handleEvent(PlayerEvent.LAND);
                    velocityY = 0;
                } else if (isBelow(block)) {
                    y = block.y - block.height / 2 - height / 2;
                    velocityY = 0;
                } else if (isRightOf(block)) {
                    x = block.x + block.width / 2 + width / 2;
                    velocityX = 0;
                } else if (isLeftOf(block)) {
                    x = block.x - block.width / 2 - width / 2;
                    velocityX = 0;
                }
            } else if (block.type == BlockType.PLATFORM) {
                if (isAbove(block)) {
                    y = block.y + block.height / 2 + height / 2;
                    state = state.handleEvent(PlayerEvent.LAND);
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
                    state = state.handleEvent(PlayerEvent.LAND);
                    velocityY = 0;
                } else if (isBelow(block)) {
                    y = block.y - block.height / 2 - height / 2;
                    velocityY = 0;
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
            state = state.handleEvent(PlayerEvent.LAND);
        }
    }

    public void jump() {
        state = state.handleEvent(PlayerEvent.JUMP);
    }

    public void doubleJump() {
        state = state.handleEvent(PlayerEvent.DOUBLEJUMP);
    }

    public void moveLeft() {
        state = state.handleEvent(PlayerEvent.MOVE_LEFT);
    }
    
    public void moveRight() {
        state = state.handleEvent(PlayerEvent.MOVE_RIGHT);
    }

    public void dash() {
        state = state.handleEvent(PlayerEvent.DASH);
    }

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
