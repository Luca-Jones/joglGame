package networking;

import com.jogamp.newt.event.KeyEvent;

import entities.Player;
import networking.packets.PlayerUpdatePacket;

public class ClientHandler {

    public Player player;
    
    public ClientHandler(Player player) {
        this.player = player;
    }

    public void handlePlayerUpdate(PlayerUpdatePacket playerUpdatePacket) {
        if (playerUpdatePacket.isQuickKey) {
            switch (playerUpdatePacket.key) {
                case KeyEvent.VK_UP:
                case 'W':
                    player.doubleJump();
                    break;
                case KeyEvent.VK_DOWN:
                case 'S':
                    player.dash();
                default:
                    break;
            }
        } else {
            switch (playerUpdatePacket.key) {
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
                    break;
                case KeyEvent.VK_RIGHT:
                case 'D':
                    player.moveRight();
                    break;
                case KeyEvent.VK_ENTER:
                    break;
                default:
                    break;
            }
        }

    }
}
