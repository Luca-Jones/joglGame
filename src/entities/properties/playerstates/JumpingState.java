package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

public class JumpingState implements PlayerState {
    
    private Player player;
    private static JumpingState instance;

    private int jumpCount = 0;
    private static final int MAX_JUMP_COUNT = 10;

    
    private JumpingState(Player player) {
        this.player = player;
    }
    
    public static JumpingState getInstance(Player player) {
        if (instance == null) {
            instance = new JumpingState(player);
        }
        player.setVelocityY(Player.JUMP_STAT);
        instance.jumpCount = 0;
        return instance;
    }
    
    @Override
    public PlayerState update(double deltaTime) {
        if (player.getVelocityY() <= 0) {
            return FallingState.getInstance(player);
        }
        return this;
    }
    
    @Override
    public PlayerState handleEvent(PlayerEvent event) {
        switch (event) {
            case JUMP:
                if (jumpCount == MAX_JUMP_COUNT) {
                    player.setVelocityY(Player.JUMP_STAT);
                }
                jumpCount++;
                return this;
            case MOVE_LEFT:
                player.direction = Direction.LEFT;
                player.setVelocityX(-Player.SPEED_STAT);
                return this;
            case MOVE_RIGHT:
                player.direction = Direction.RIGHT;
                player.setVelocityX(Player.SPEED_STAT);
                return this;
            default:
                return this;
        }
    }
    
}
