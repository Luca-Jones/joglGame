package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

public class IdleState implements PlayerState {

    private Player player;
    private static IdleState instance;

    private IdleState(Player player) {
        this.player = player;
    }

    public static IdleState getInstance(Player player) {
        if (instance == null) {
            instance = new IdleState(player);
        }
        return instance;
    }

    @Override
    public PlayerState update(double deltaTime) {
        return this;
    }

    @Override
    public PlayerState handleEvent(PlayerEvent event) {
        switch (event) {
            case MOVE_LEFT:
                player.direction = Direction.LEFT;
                return RunningState.getInstance(player);
            case MOVE_RIGHT:
                player.direction = Direction.RIGHT;
                return RunningState.getInstance(player);
            case JUMP:
                return null; // JumpingState.getInstance(player);
            default:
                return this;
        }
    }
    
}
