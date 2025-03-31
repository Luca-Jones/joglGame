package networking.packets;

public class LoginPacket extends Packet {

    public String username;

    public LoginPacket(String username) {
        this.username = username;
    }
    
}
