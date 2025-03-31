package networking.packets;

public class DisconnectPacket extends Packet {
    
    public String username;

    public DisconnectPacket(String username) {
        this.username = username;
    }

}
