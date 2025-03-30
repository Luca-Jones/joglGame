package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

public class DoubleJumpingState implements PlayerState {

    private Player player;
    private static DoubleJumpingState instance;

    public static DoubleJumpingState getInstance(Player player) {
        if (instance == null) {
            instance = new DoubleJumpingState(player);
        }
        player.setVelocityY(Player.JUMP_STAT);
        return instance;
    }

    private DoubleJumpingState(Player player) {
        this.player = player;
    }

    @Override
    public PlayerState update(double deltaTime) {
        // if (player.getVelocityY() <= 0) {
        //     return FallingState.getInstance(player);
        // }
        return this;
    }

    @Override
    public PlayerState handleEvent(PlayerEvent event) {
        switch (event) {
            case LAND:
                player.setVelocityY(0);
                return IdleState.getInstance(player);
            case MOVE_LEFT:
                player.direction = Direction.LEFT;
                player.setVelocityX(-Player.SPEED_STAT);
                return this;
            case MOVE_RIGHT:
                player.direction = Direction.RIGHT;
                player.setVelocityX(Player.SPEED_STAT);
                return this;
            case DASH:
                return DashingState.getInstance(player);
            default:
                return this;
        }
    }
    
}
