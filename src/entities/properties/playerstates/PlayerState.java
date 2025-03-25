package entities.properties.playerstates;

public interface PlayerState {

    void update(double deltaTime);

    PlayerState handleEvent(PlayerEvent event);

    // TODO: outline interface
    
}
