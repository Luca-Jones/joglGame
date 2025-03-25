package entities;

import entities.properties.BlockType;
import entities.properties.Depth;
import entities.properties.Direction;
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
    private static final float JUMP_STAT = 2;
    private static final float SPEED_STAT = 1f;

    private String username;
    @SuppressWarnings("unused")
    private Direction direction;

    public Player(String username, float x, float y) {
        super(
            x, y, 1, 1,
            SpriteStore.getInstance().getSprite(SPRITE_FILE_NAME), Depth.FRONT,
            FRICTION_COEFFICIENT, GRAVITY
        );
        this.username = username;
        this.direction = Direction.RIGHT;
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        super.draw(g, camera);
        g.drawString(username, 1, 1, 2, 2);
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
        direction = Direction.LEFT;
        velocityX = -SPEED_STAT;
    }
    
    public void moveRight() {
        direction = Direction.RIGHT;
        velocityX = SPEED_STAT;
    }

    public void dash() {}

    public void rotate(float angle) {
        rotation += angle;
    }
    

}
