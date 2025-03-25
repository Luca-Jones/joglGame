package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

public class RunningState implements PlayerState {

    private Player player;
    private static RunningState instance;

    private RunningState(Player player) {
        this.player = player;
    }

    public static RunningState getInstance(Player player) {
        player.setVelocityX((player.direction == Direction.RIGHT) ? Player.SPEED_STAT : -Player.SPEED_STAT);
        if (instance == null) {
            instance = new RunningState(player);
        }
        return instance;
    }

    @Override
    public PlayerState update(double deltaTime) {
        if (Math.abs(player.getVelocityX()) <= deltaTime) {
            return IdleState.getInstance(player);
        }
        return this;
    }

    @Override
    public PlayerState handleEvent(PlayerEvent event) {
        switch (event) {
            case MOVE_LEFT:
                player.direction = Direction.LEFT;
                player.setVelocityX(-Player.SPEED_STAT);
                return this;
                case MOVE_RIGHT:
                player.direction = Direction.RIGHT;
                player.setVelocityX(Player.SPEED_STAT);
                return this;
            case JUMP:
                return null; // JumpingState.getInstance(player);
            default:
                return this;
        }
    }
    
}
