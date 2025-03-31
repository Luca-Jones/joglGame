package inputs;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import networking.Client;
import networking.packets.DisconnectPacket;
import networking.packets.PlayerUpdatePacket;

public class ClientKeyInputHandler implements KeyListener {
    
    private Client client;
    private Set<Short> keys;

    public ClientKeyInputHandler(Client client) {
        this.client = client;
        this.keys = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keys.contains(e.getKeyCode())) {
            keys.add(e.getKeyCode());
            handleQuickKey(e.getKeyCode());
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (!e.isAutoRepeat()){
            keys.remove(e.getKeyCode());
        }
    }

    public void handleKeys() {
        for (short key : keys) {
            handleKey(key);
        }
    }

    private void handleKey(short key) {
        PlayerUpdatePacket playerUpdatePacket = new PlayerUpdatePacket(client.username, key, false);
        client.sendPacket(playerUpdatePacket);
    }
    
    private void handleQuickKey(short key) {
        if (key == KeyEvent.VK_ESCAPE) {
            DisconnectPacket disconnectPacket = new DisconnectPacket(client.username);
            client.sendPacket(disconnectPacket);
        } else {
            PlayerUpdatePacket playerUpdatePacket = new PlayerUpdatePacket(client.username, key, true);
            client.sendPacket(playerUpdatePacket);
        }
    }

}
