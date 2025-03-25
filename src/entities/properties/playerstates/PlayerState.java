package entities.properties.playerstates;

public interface PlayerState {

    PlayerState update(double deltaTime); // does update need time?
    PlayerState handleEvent(PlayerEvent event);
    
}
