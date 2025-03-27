package entities.properties.playerstates;

import entities.Player;
import entities.properties.Direction;

// TODO: implement this state
public class DashingState implements PlayerState {

    private Player player;
    private static DashingState instance;

    private int dashCount;
    private static final int MAX_DASH_COUNT = 50;
    private static final float DASH_SPEED = 5 * Player.SPEED_STAT;

    private DashingState(Player player) {
        this.player = player;
        dashCount = MAX_DASH_COUNT;
    }

    public static DashingState getInstance(Player player) {
        if (instance == null) {
            instance = new DashingState(player);
        }
        player.setVelocityX(DASH_SPEED * (player.direction == Direction.RIGHT ? 1: -1));
        instance.dashCount = MAX_DASH_COUNT;
        return instance;
    }

    @Override
    public PlayerState update(double deltaTime) {
        if (dashCount > 0) {dashCount--;}
        if (dashCount == 0) {
            return IdleState.getInstance(player); // TODO: check proper conditions
        }
        return this;
    }

    @Override
    public PlayerState handleEvent(PlayerEvent event) {
        return this;
    }
    
}
