package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Entity;
import entities.Player;
import main.Game;
import networking.packets.DisconnectPacket;
import networking.packets.EntityPacket;
import networking.packets.GameStateRequestPacket;
import networking.packets.LoginPacket;
import networking.packets.Packet;
import networking.packets.PlayerUpdatePacket;
import networking.packets.RedrawPacket;

public class Server extends Thread {
    
    public static final int SERVER_PORT = 4999;
    private DatagramSocket socket;
    private Map<String, ClientHandler> clients;

    private boolean running;
    private Game game;

    public Server() {
        try {
            socket = new DatagramSocket(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clients = new HashMap<>();
        game = new Game(false);
        running = true;
        System.out.println("Server started ...");
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
            parseDatagramPacket(datagramPacket);
        }

    }

    // TODO: who is this being sent to?
    private void sendPacket(Packet packet, InetAddress clientAddress, int clientPort) {
        
        DatagramPacket datagramPacket = new DatagramPacket(packet.serialize(), packet.serialize().length, clientAddress, clientPort);
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastPacket(Packet packet) {
        for (ClientHandler clientHandler : clients.values()) {
            sendPacket(packet, clientHandler.clientAddress, clientHandler.clientPort);
        }
    }

    private void parseDatagramPacket(DatagramPacket datagramPacket) {

        Packet packet = Packet.deserialize(datagramPacket.getData());
        InetAddress clientAddress = datagramPacket.getAddress();
        int clientPort = datagramPacket.getPort();

        if (packet instanceof LoginPacket) {

            LoginPacket loginPacket = (LoginPacket) packet;
            Player player = new Player(loginPacket.username, 0, 1); // TODO: change default spawn
            ClientHandler clientHandler = new ClientHandler(player, clientAddress, clientPort);
            // if (clients.isEmpty()) {clientHandler.player.it = true;}
            clients.put(loginPacket.username, clientHandler); // TODO: caan override existsing users
            game.addPlayer(player);
            System.out.println("[" + clientAddress + ":" + clientPort + "] " + loginPacket.username + " has connected!");
            broadcastPacket(loginPacket);

        } else if (packet instanceof DisconnectPacket) {

            DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
            clients.remove(disconnectPacket.username);
            game.removePlayer(disconnectPacket.username);
            System.out.println("[" + clientAddress + ":" + clientPort + "] " + disconnectPacket.username + " disconnected...");
            broadcastPacket(disconnectPacket);

        } else if (packet instanceof PlayerUpdatePacket) {

            PlayerUpdatePacket playerUpdatePacket = (PlayerUpdatePacket) packet;
            if (clients.containsKey(playerUpdatePacket.username)) {
                clients.get(playerUpdatePacket.username).handlePlayerUpdate(playerUpdatePacket);
            } else {
                System.out.println("Client not found: " + playerUpdatePacket.username);
            }

        } else if (packet instanceof GameStateRequestPacket) {

            GameStateRequestPacket gameStateRequestPacket = (GameStateRequestPacket) packet;
            List<Entity> entities = game.getEntities();

            for (Entity entity : entities) {
                EntityPacket entityPacket = new EntityPacket(gameStateRequestPacket.username, entity);
                sendPacket(entityPacket, clientAddress, clientPort);
            }
            RedrawPacket redrawPacket = new RedrawPacket();
            sendPacket(redrawPacket, clientAddress, clientPort);

        }
    }

}
