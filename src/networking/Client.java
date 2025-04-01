package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import entities.ClientEntity;
import entities.GameObject;
import graphics.Renderer;
import inputs.ClientKeyInputHandler;
import networking.packets.DisconnectPacket;
import networking.packets.EntityPacket;
import networking.packets.LoginPacket;
import networking.packets.Packet;
import networking.packets.RedrawPacket;
import utils.ConcurrentSortedList;

public class Client extends Thread{

    public static final int SERVER_PORT = Server.SERVER_PORT;
    private InetAddress serverAddress;
    private DatagramSocket socket;
    public String username;
    private boolean running = true;

    private Renderer renderer;
    private List<GameObject> gameObjects;
    private List<GameObject> entityBuffer;
    private GameObject player;

    // TODO: update game objects rather than rebuild them every time
    public Client(InetAddress serverAddress, String username) {
        this.serverAddress = serverAddress;
        this.username = username;
        this.gameObjects = new ConcurrentSortedList<>();
        this.entityBuffer = new ArrayList<>();
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderer = new Renderer(10, gameObjects, player);
        renderer.setKeyListener(new ClientKeyInputHandler(this));
        running = true;
    }

    @Override
    public void run() {

        sendPacket(new LoginPacket(username));

        while(running) {

            byte[] data = new byte[2048];
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
            System.out.println("Waiting for packet ...");
            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Packet packet = Packet.deserialize(datagramPacket.getData());
            parsePacket(packet);

            
            renderer.update(0); // TODO: change when camera movement is added
            
            renderer.render();
        }
        
    }

    public void sendPacket(Packet packet) {
        byte [] data = packet.serialize();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverAddress, SERVER_PORT); // idk if you need to include the port and addr
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parsePacket(Packet packet) {
        if (packet instanceof LoginPacket) {
            LoginPacket loginPacket = (LoginPacket) packet;
            System.out.println("[" + serverAddress.getHostAddress() + ":" + SERVER_PORT + "] " + loginPacket.username
                    + " has joined the game ...");
        } else if (packet instanceof DisconnectPacket) {
            DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
            System.out.println("[" + serverAddress.getHostAddress() + ":" + SERVER_PORT + "] " + disconnectPacket.username
                    + " has left the game ...");
            if (disconnectPacket.username.equals(username)) {
                running = false;
            }
        } else if (packet instanceof EntityPacket) {
            EntityPacket entityPacket = (EntityPacket) packet;
            ClientEntity entity = entityPacket.getEntity();
            entityBuffer.add(entity);
        } else if (packet instanceof RedrawPacket) {
            gameObjects.clear();
            gameObjects.addAll(entityBuffer);
            entityBuffer.clear();
        }
    }
    
}
