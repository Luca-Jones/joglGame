package networking.packets;

public class PlayerUpdatePacket extends Packet {
    
    public String username;
    public int key;
    public boolean isQuickKey;

    public PlayerUpdatePacket(String username, int key, boolean isQuickKey) {
        this.username = username;
        this.key = key;
        this.isQuickKey = isQuickKey;
    }
    
}
