package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import entities.Player;
import main.Game;
import networking.packets.DisconnectPacket;
import networking.packets.LoginPacket;
import networking.packets.Packet;
import networking.packets.PlayerUpdatePacket;

public class Server extends Thread {
    
    public static final int SERVER_PORT = 4999;
    private DatagramSocket socket;
    private Map<String, ClientHandler> clients;

    private boolean running;
    private Game game;

    public Server() {
        try {
            socket = new DatagramSocket(SERVER_PORT); // TODO: what does the addr really mean?
        } catch (Exception e) {
            e.printStackTrace();
        }
        Game game = new Game(false);
        running = true;
    }

    @Override
    public void start() {
        super.start();
        game.start();
    }

    @Override
    public void run() {
        
        while (running) {
            byte[] data = new byte[2048];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Packet packet = Packet.deserialize(datagramPacket.getData());
            parsePacket(packet);
        }

    }

    // TODO: who is this being sent to?
    public void sendPacket(Packet packet) {
        
        DatagramPacket datagramPacket = new DatagramPacket(packet.serialize(), packet.serialize().length);
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void parsePacket(Packet packet) {
        if (packet instanceof LoginPacket) {
            LoginPacket loginPacket = (LoginPacket) packet;
            Player player = new Player(loginPacket.username, 0, 1); // TODO: change default spawn
            ClientHandler clientHandler = new ClientHandler(player);
            clients.put(loginPacket.username, clientHandler); // TODO: caan override existsing users
            // if (clients.isEmpty()) {clientHandler.player.it = true;}
            game.addPlayer(player);
            sendPacket(loginPacket); // Broadcasts the event out to all clients?
        } else if (packet instanceof DisconnectPacket) {
            DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
            System.out.println("someone disconnected");
            clients.remove(disconnectPacket.username);
            sendPacket(disconnectPacket);
        } else if (packet instanceof PlayerUpdatePacket) {
            PlayerUpdatePacket playerUpdatePacket = (PlayerUpdatePacket) packet;
            if (clients.containsKey(playerUpdatePacket.username)) {
                clients.get(playerUpdatePacket.username).handlePlayerUpdate(playerUpdatePacket);
            } else {
                System.out.println("Client not found: " + playerUpdatePacket.username);
            }
        }
    }

}
