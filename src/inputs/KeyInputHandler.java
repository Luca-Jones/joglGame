package inputs;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import entities.Player;

public class KeyInputHandler implements KeyListener {

    private Player player;
    private Set<Short> keys;

    public KeyInputHandler(Player player) {
        this.player = player;
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

        switch (key) {
            case KeyEvent.VK_UP:
            case 'W':
                player.jump();
                break;
            case KeyEvent.VK_LEFT:
            case 'A':
                player.moveLeft();
                break;
            case KeyEvent.VK_DOWN:
            case 'S':
                player.dash();
                break;
            case KeyEvent.VK_RIGHT:
            case 'D':
                player.moveRight();
                break;
            case KeyEvent.VK_ENTER:
                break;
            case 'Q':
                player.rotate(10);
                break;
            case 'E':
                player.rotate(-10);
                break;
            default:
                break;
        }
        
    }

    private void handleQuickKey(short key) {
        switch (key) {
            case KeyEvent.VK_UP:
            case 'W':
                player.doubleJump();
                break;
            case 'S':
                player.dash();
            default:
                break;
        }
    }
}
