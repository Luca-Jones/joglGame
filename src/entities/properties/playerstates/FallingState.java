package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

public class FallingState implements PlayerState {
    
    private Player player;
    private static FallingState instance;
    
    private FallingState(Player player) {
        this.player = player;
    }
    
    public static FallingState getInstance(Player player) {
        if (instance == null) {
            instance = new FallingState(player);
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
            case LAND:
                player.setVelocityY(0);
                // player.setVelocityX(0);
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
