package networking;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
// import java.util.ArrayList;
import java.util.List;

import entities.ClientEntity;
import entities.GameObject;
import entities.properties.Depth;
import graphics.Renderer;
import graphics.Sprite;
import inputs.ClientKeyInputHandler;
import networking.packets.DisconnectPacket;
import networking.packets.EntityPacket;
import networking.packets.LoginPacket;
import networking.packets.Packet;
import networking.packets.RedrawPacket;
import networking.packets.GameStateRequestPacket;
import utils.ConcurrentSortedList;

public class Client extends Thread{

    public static final int SERVER_PORT = Server.SERVER_PORT;
    private InetAddress serverAddress;
    private DatagramSocket socket;
    public String username;
    private boolean running = true;

    private Renderer renderer;
    private List<GameObject> gameObjects;
    // private List<GameObject> entityBuffer;

    // TODO: update game objects rather than rebuild them every time
    public Client(InetAddress serverAddress, String username) {
        this.serverAddress = serverAddress;
        this.username = username;
        this.gameObjects = new ConcurrentSortedList<>();
        // this.entityBuffer = new ArrayList<>();
        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientEntity temp = new ClientEntity(0, 0, 1, 1, new Sprite(Color.BLACK), Depth.BACK, 0, false);
        gameObjects.add(temp);
        renderer = new Renderer(10, gameObjects, temp);
        renderer.setKeyListener(new ClientKeyInputHandler(this));
        running = true;
        System.out.println("Client started ...");
    }

    @Override
    public void run() {

        sendPacket(new LoginPacket(username));

        while(running) {

            renderer.update(0); // TODO: change when camera movement is added

            // send a request
            GameStateRequestPacket gameStateRequestPacket = new GameStateRequestPacket(username);
            sendPacket(gameStateRequestPacket);

            // wait for a reply
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
            if (entity.isFocus) {
                renderer.setCameraFocus(entity);
            }
            // entityBuffer.add(entity);
            gameObjects.add(entity);
        } else if (packet instanceof RedrawPacket) {
            // gameObjects.clear();
            // gameObjects.addAll(entityBuffer);
            // entityBuffer.clear();
            renderer.render();
        }
    }
    
}
