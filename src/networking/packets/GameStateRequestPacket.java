package networking.packets;

public class GameStateRequestPacket extends Packet {

    public String username;

    public GameStateRequestPacket(String username) {
        this.username = username;
    }
}
